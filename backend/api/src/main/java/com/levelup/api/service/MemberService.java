package com.levelup.api.service;


import com.levelup.core.domain.file.FileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.dto.member.CreateMemberResponse;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.exception.DuplicateEmailException;
import com.levelup.core.exception.ImageNotFoundException;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    /**
     * 생성
     * */
    public CreateMemberResponse create(CreateMemberRequest memberRequest) {
        validationDuplicateMember(memberRequest.getEmail());

        Member member = memberRequest.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));//중복 회원 검증

        memberRepository.save(member);

        return new CreateMemberResponse(member.getEmail(), member.getPassword(), member.getName(),
                member.getGender(), member.getBirthday(), member.getPhone());
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
    public Member findRegisterMember(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getGender(), m.getBirthday(),
                        m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public MemberResponse findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email);

        if (findMember == null) {
            throw new MemberNotFoundException("가입된 이메일이 없습니다");
        }
        return new MemberResponse(findMember.getEmail(), findMember.getName(),
                findMember.getGender(), findMember.getBirthday(), findMember.getPhone(), findMember.getUploadFile());
    }

    public List<Member> findByChannelId(Long channelId) {
        return memberRepository.findByChannelId(channelId);
    }

    public List<MemberResponse> findByChannelId(Long channelId, Long page, Long count) {
        return memberRepository.findByChannelId(channelId, Math.toIntExact(page), Math.toIntExact(count))
                .stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getGender(), m.getBirthday(),
                        m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toList());
    }

    public List<Member> findWaitingMemberByChannelId(Long channelId) {
        return memberRepository.findWaitingMemberByChannelId(channelId);
    }

    public List<MemberResponse> findWaitingMemberByChannelId(Long channelId, Long page) {
        return memberRepository.findWaitingMemberByChannelId(channelId, Math.toIntExact(page), 5)
                .stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getGender(), m.getBirthday(),
                        m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toList());
    }

    public UrlResource getProfileImage(String email) throws MalformedURLException {
        Member findMember = memberRepository.findByEmail(email);

        if (findMember.getUploadFile() == null) {
            throw new ImageNotFoundException("프로필 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findMember.getUploadFile();
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
    public void modifyProfileImage(MultipartFile file, Long memberId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException("존재하지 않는 이미지파일입니다.");
        }

        Member member = memberRepository.findById(memberId);

        deleteProfileImage(member.getUploadFile().getStoreFileName());

        UploadFile uploadFile = fileStore.storeFile(ImageType.MEMBER, file);
        member.setUploadFile(uploadFile); //변경 감지의 의한 update문 쿼리 발생
    }

    private void deleteProfileImage(String storeFileName) {
        if (!storeFileName.equals(FileStore.MEMBER_DEFAULT_IMAGE)) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }


    /**
     * 멤버 삭제
     * */
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }


    /**
     * 로그인 처리
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername start");

        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new MemberNotFoundException("잘못된 이메일입니다.");
        }

        return new User(member.getEmail(), member.getPassword(), true, true,
                true, true, new ArrayList<>());
    }
}
