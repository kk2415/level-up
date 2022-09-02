package com.levelup.api.service;

import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.api.util.S3FileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.api.dto.member.CreateMemberRequest;
import com.levelup.api.dto.member.CreateMemberResponse;
import com.levelup.api.dto.member.MemberResponse;
import com.levelup.api.dto.member.UpdateMemberRequest;
import com.levelup.core.exception.*;
import com.levelup.core.exception.member.DuplicateEmailException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final S3FileStore fileStore;

    public CreateMemberResponse save(CreateMemberRequest memberRequest) {
//        validateDuplicationMember(memberRequest.getEmail(), memberRequest.getNickname()); //중복 이메일 검증

        Member member = memberRequest.toEntity();
        EmailAuth authEmail = EmailAuth.from(member.getEmail());
        Role role = Role.of(RoleName.ANONYMOUS, member);

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEmailAuth(authEmail);
        member.addRole(role);

        memberRepository.save(member);
        return CreateMemberResponse.from(member);
    }

    private void validateDuplicationMember(String email, String nickname) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException("중복된 이메일입니다.");
                });

        memberRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new DuplicateEmailException("이미 사용중인 닉네임입니다.");
                });
    }

    /**
     * json이랑 이미지 파일을 동시에 서버에 보낼 수가 없으니
     * 먼저 이 api로 이미지를 저장한 후 이미지의 경로명을 클라이언트에게 리턴(ResponseEntity(uploadFile, HttpStatus.OK))
     * 그러면 클라이언트는 받은 경로를 객체에 저장한 후 회원가입 api를 호출함
     * */
    public UploadFile createProfileImage(MultipartFile file) throws IOException {
        return fileStore.storeFile(ImageType.MEMBER, file);
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Cacheable(cacheNames = "member", key = "#memberId") //'member'라는 이름의 캐시에 MemberResponse를 저장함. 키는 파라미터 이름인 'memberId'
    public MemberResponse getById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        return MemberResponse.from(member);
    }

    /*
     * @ResponseBody + byte[]또는, Resource 를 반환하는 경우 바이트 정보가 반환됩니다.
     * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
     *
     * Resource 를 리턴할 때 ResourceHttpMessageConverter 가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
     * */
    public UrlResource getProfileImage(String email) throws MalformedURLException {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));

        if (findMember.getProfileImage() == null) {
            throw new ImageNotFoundException("프로필 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findMember.getProfileImage();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        return new UrlResource("file:" + fullPath);
    }

    @CacheEvict(cacheNames = "member", key = "#memberId")
    public void modify(UpdateMemberRequest updateMemberRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        member.modifyMember(updateMemberRequest.getNickname(), updateMemberRequest.getProfileImage());
    }

    @CacheEvict(cacheNames = "member", key = "#memberId")
    public UploadFile modifyProfile(MultipartFile file, Long memberId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException("존재하지 않는 이미지파일입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if (member.getProfileImage() != null) {
            deleteProfileInS3(member.getProfileImage().getStoreFileName());
        }

        UploadFile uploadFile = fileStore.storeFile(ImageType.MEMBER, file);
        member.modifyProfileImage(uploadFile);

        return uploadFile;
    }

    @CacheEvict(cacheNames = {"ChannelCategory", "member"}, allEntries = true)
    public void delete(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        List<ChannelMember> channelMembers = member.getChannelMembers().stream()
                .filter(ChannelMember::getIsManager)
                .collect(Collectors.toUnmodifiableList());

        channelMembers.forEach(channelMember -> channelRepository.delete(channelMember.getChannel()));

        memberRepository.delete(member);
    }

    private void deleteProfileInS3(String storeFileName) {
        if (!storeFileName.equals(S3FileStore.DEFAULT_IMAGE)) {
            fileStore.deleteS3File(storeFileName);
        }
    }

    private void deleteProfileInLocal(String storeFileName) {
        if (!storeFileName.equals(LocalFileStore.MEMBER_DEFAULT_IMAGE)) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));

        Collection<GrantedAuthority> authorities = new ArrayList<>(10);
        List<Role> roles = member.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getName())));

        return new User(member.getEmail(), member.getPassword(), true, true,
                true, true, authorities);
    }
}
