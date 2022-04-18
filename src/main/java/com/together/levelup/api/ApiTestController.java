package com.together.levelup.api;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequiredArgsConstructor
//public class ApiTestController {

//    private final MemberService memberService;

//    @GetMapping("/api/members")
//    public Result members() {
//        List<MemberForm> memberForms = new ArrayList<>();
//        List<Member> findMembers = memberService.findAllMembers();
//        for (Member findMember : findMembers) {
//            System.out.println(findMember.getName());
//            memberForms.add(new MemberForm(findMember.getEmail(), findMember.getPassword(), findMember.getName(),
//                    findMember.getGender(), findMember.getBirthday(), findMember.getPhone()));
//        }
//        return new Result(memberForms.size(), memberForms);
//    }
//
//    @Data
//    @AllArgsConstructor
//    public static class MemberForm {
//        private String email;
//        private String password;
//        private String name;
//        private Gender gender;
//        private String birthday;
//        private String phone;
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private int count;
//        private T data;
//    }
//
//}
