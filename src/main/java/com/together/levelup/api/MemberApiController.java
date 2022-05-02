package com.together.levelup.api;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.ImageType;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.UploadFile;
import com.together.levelup.dto.*;
import com.together.levelup.dto.member.CreateMemberRequest;
import com.together.levelup.dto.member.CreateMemberResponse;
import com.together.levelup.dto.member.MemberResponse;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.exception.ImageNotFoundException;
import com.together.levelup.service.LoginService;
import com.together.levelup.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final FileStore fileStore;

    /**
     * 생성
     * */
    @PostMapping("/member")
    public ResponseEntity createMember(@RequestBody @Valid CreateMemberRequest memberRequest, HttpServletRequest request) throws IOException {
        if (memberRequest.getUploadFile() != null) {
            System.out.println(memberRequest.getUploadFile().getStoreFileName());
        }

        Member member = Member.createMember(memberRequest.getEmail(), memberRequest.getPassword(),
                memberRequest.getName(), memberRequest.getGender(), memberRequest.getBirthday(),
                memberRequest.getPhone(), memberRequest.getUploadFile());

        Long memberId = memberService.join(member);

        String nextUri = getNextUri("/{id}", memberId);
        Links links = new Links();
        links.setSelf(request.getRequestURL().toString());
        links.setNext(nextUri);

        CreateMemberResponse response = new CreateMemberResponse(memberRequest.getEmail(),
                memberRequest.getPassword(), memberRequest.getName(),
                memberRequest.getGender(), memberRequest.getBirthday(), memberRequest.getPhone(), links);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/member/image")
    public ResponseEntity storeMemberImage(@ModelAttribute MultipartFile file) throws IOException {
        /**
         * json이랑 이미지 파일을 동시에 서버에 보낼 수가 없으니
         * 먼저 이 api로 이미지를 저장한 후 이미지의 경로명을 클라이언트에게 리턴(ResponseEntity(uploadFile, HttpStatus.OK))
         * 그러면 클라이언트는 받은 경로를 객체에 저장한 후 회원가입 api를 호출함
         * */

        UploadFile uploadFile;

        if (file == null || file.isEmpty()) {
            uploadFile = new UploadFile("default.png", fileStore.MEMBER_DEFAULT_IMAGE);
        }
        else {
            uploadFile = fileStore.storeFile(ImageType.MEMBER, file);
        }

        return new ResponseEntity(uploadFile, HttpStatus.OK);
    }

    /**
     * 로그인
     * */
    @PostMapping("/member/login")
    public ResponseEntity login(@RequestBody @Validated LoginForm loginForm, HttpServletRequest request) {
        Member member = loginService.login(loginForm.getEmail(), loginForm.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(SessionName.SESSION_NAME, member);

        return new ResponseEntity(new MemberResponse(member.getEmail(), member.getName(), member.getGender(),
                member.getBirthday(), member.getPhone(), member.getUploadFile()), HttpStatus.OK);
    }

    /**
     * 조회
     * */
    @GetMapping("/members")
    public ResponseEntity findAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers()
                .stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getGender(), m.getBirthday(), m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toList());

        return new ResponseEntity(new Result(members, members.size()), HttpStatus.OK);
    }

    @GetMapping("/member/{email}")
    public MemberResponse findMember(@PathVariable("email") String email) {
        Member findMember = memberService.findByEmail(email);

        return new MemberResponse(findMember.getEmail(), findMember.getName(),
                findMember.getGender(), findMember.getBirthday(), findMember.getPhone(), findMember.getUploadFile());
    }

    @GetMapping(path = "/member/{email}/image", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable String email) throws MalformedURLException {
        Member findMember = memberService.findByEmail(email);

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

    @GetMapping("/member")
    public MemberResponse confirmLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MemberNotFoundException("해당하는 회원이 없습니다.");
        }

        Member member = (Member)session.getAttribute(SessionName.SESSION_NAME);
        if (member == null) {
            throw new MemberNotFoundException("해당하는 회원이 없습니다.");
        }

        return new MemberResponse(member.getEmail(), member.getName(),
                member.getGender(), member.getBirthday(), member.getPhone(), member.getUploadFile());
    }

    /**
     * 수정
     * */
    @PatchMapping("/member/{email}/image")
    @Transactional //변경감지 때문에 추가함... 나중에 이 메서드를 service 계층으로 분리하겠음
    public void updateMemberProfile(@PathVariable String email, MultipartFile file) throws IOException {
        Member findMember = memberService.findByEmail(email);

        if (file != null && !file.isEmpty()) {
            //기존 프로필 사진 삭제
            deleteProfilImage(findMember);

            //새로운 프로필 사진 저장
            UploadFile uploadFile = fileStore.storeFile(ImageType.MEMBER, file);

            //새로운 프로필 사진 이미지 경로 DB에 저장
            findMember.setUploadFile(uploadFile); //변경 감지의 의한 update문 쿼리 발생
        }
    }



    private void deleteProfilImage(Member findMember) {
        String storeFileName = findMember.getUploadFile().getStoreFileName();

        if (storeFileName != fileStore.MEMBER_DEFAULT_IMAGE) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    private String getNextUri(String path, Long memberId) {
        String nextUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(memberId)
                .toString();
        return nextUri;
    }

}
