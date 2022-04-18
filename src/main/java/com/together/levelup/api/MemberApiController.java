package com.together.levelup.api;

import com.together.levelup.domain.member.Authority;
import com.together.levelup.domain.member.Member;
import com.together.levelup.dto.*;
import com.together.levelup.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public ResponseEntity findAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers()
                .stream()
                .map(m -> new MemberResponse(m.getEmail(), m.getName(), m.getGender(), m.getBirthday(), m.getPhone()))
                .collect(Collectors.toList());

        return new ResponseEntity(new Result(members, members.size()), HttpStatus.OK);
    }

    @GetMapping("/api/member/{id}")
    public MemberResponse findMember(@PathVariable("id") Long id) {
        Member findMember = memberService.findOne(id);

        return new MemberResponse(findMember.getEmail(), findMember.getName(),
                findMember.getGender(), findMember.getBirthday(), findMember.getPhone());
    }

    @PostMapping("/api/member")
    public ResponseEntity createMember(@RequestBody @Valid CreateMemberRequest memberRequest, HttpServletRequest request) {
        memberRequest.setAuthority(Authority.NORMAL);
        Long memberId = memberService.join(memberRequest.toEntity());

        String nextUri = getNextUri("/{id}", memberId);

        Links links = new Links();
        links.setSelf(request.getRequestURL().toString());
        links.setNext(nextUri);

        CreateMemberResponse response = new CreateMemberResponse(memberRequest.getEmail(),
                memberRequest.getPassword(), memberRequest.getName(),
                memberRequest.getGender(), memberRequest.getBirthday(), memberRequest.getPhone(), links);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    private String getNextUri(String path, Long memberId) {
        String nextUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(memberId)
                .toString();
        return nextUri;
    }

}
