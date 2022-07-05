package com.levelup.api.service;

import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.S3FileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.auth.EmailAuthResponse;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.dto.member.CreateMemberResponse;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.exception.*;
import com.levelup.core.repository.auth.EmailAuthRepository;
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
    private final EmailAuthService emailAuthService;
    private final EmailService emailService;
    private final FileService fileService;
//    private final LocalFileStore fileStore;
    private final S3FileStore fileStore;


    /**
     * 생성
     * */
    public CreateMemberResponse create(CreateMemberRequest memberRequest) {
        validationDuplicateMember(memberRequest.getEmail());

        Member member = memberRequest.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword())); //중복 회원 검증

        EmailAuth authEmail = EmailAuth.createAuthEmail(member.getEmail());
        member.setEmailAuth(authEmail);

        memberRepository.save(member);

//        emailService.sendConfirmEmail(member.getEmail(), authEmail.getSecurityCode());

        return new CreateMemberResponse(member);
    }

    private void validationDuplicateMember(String email) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        Member findMembers = memberRepository.findByEmail(email);

        if (findMembers != null) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }
    }

    public UploadFile createProfileImage(MultipartFile file) throws IOException {
        /**
         * json이랑 이미지 파일을 동시에 서버에 보낼 수가 없으니
         * 먼저 이 api로 이미지를 저장한 후 이미지의 경로명을 클라이언트에게 리턴(ResponseEntity(uploadFile, HttpStatus.OK))
         * 그러면 클라이언트는 받은 경로를 객체에 저장한 후 회원가입 api를 호출함
         * */
        return fileStore.storeFile(ImageType.MEMBER, file);
    }


    /**
     * 멤버조회
     * */
    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public MemberResponse findById(Long memberId) {
        Member member = memberRepository.findById(memberId);

        if (member == null) {
            throw new MemberNotFoundException("가입된 회원이 아닙니다");
        }

        return new MemberResponse(member);
    }

    @Cacheable(cacheNames = "member") //'member'라는 이름의 캐시에 MemberResponse를 저장함. 키는 파라미터 이름인 'email'
    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new MemberNotFoundException("가입된 회원이 아닙니다");
        }
        return new MemberResponse(member);
    }

    public List<Member> findByChannelId(Long channelId) {
        return memberRepository.findByChannelId(channelId);
    }

    public List<MemberResponse> findByChannelId(Long channelId, Long page, Long count) {
        return memberRepository.findByChannelId(channelId, Math.toIntExact(page), Math.toIntExact(count))
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }

    public List<Member> findWaitingMemberByChannelId(Long channelId) {
        return memberRepository.findWaitingMemberByChannelId(channelId);
    }

    public List<MemberResponse> findWaitingMemberByChannelId(Long channelId, Long page) {
        return memberRepository.findWaitingMemberByChannelId(channelId, Math.toIntExact(page), 5)
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }

    public UrlResource getProfileImage(String email) throws MalformedURLException {
        Member findMember = memberRepository.findByEmail(email);

        if (findMember.getProfileImage() == null) {
            throw new ImageNotFoundException("프로필 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findMember.getProfileImage();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        /*
         * @ResponseBody + byte[]또는, Resource를 반환하는 경우 바이트 정보가 반환됩니다.
         * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
         *
         * Resource를 리턴할 때 ResourceHttpMessageConverter가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
         * */
        return new UrlResource("file:" + fullPath);
    }


    /**
     * 멤버 수정
     * */
    @CacheEvict(cacheNames = "member", allEntries = true)
    public void modifyProfileImage(MultipartFile file, Long memberId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException("존재하지 않는 이미지파일입니다.");
        }

        Member member = memberRepository.findById(memberId);

        deleteS3Profile(member.getProfileImage().getStoreFileName());

        UploadFile uploadFile = fileStore.storeFile(ImageType.MEMBER, file);
        member.modifyProfileImage(uploadFile); //변경 감지의 의한 update문 쿼리 발생
    }


    /**
     * 멤버 삭제
     * */
    @CacheEvict(cacheNames = "member", allEntries = true)
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }

    private void deleteS3Profile(String storeFileName) {
        if (!storeFileName.equals(S3FileStore.DEFAULT_IMAGE)) {
            fileStore.deleteS3File(storeFileName);
        }
    }

    private void deleteLocalProfile(String storeFileName) {
        if (!storeFileName.equals(LocalFileStore.MEMBER_DEFAULT_IMAGE)) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }


    /**
     * 로그인 처리
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername start");

        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 이메일입니다.");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getAuthority().name()));

        return new User(member.getEmail(), member.getPassword(), true, true,
                true, true, authorities);
    }

}
