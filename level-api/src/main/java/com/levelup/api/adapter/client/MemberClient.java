package com.levelup.api.adapter.client;

import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "level-member", url = "http://localhost:8080/api/v1/members")
public interface MemberClient {

    @GetMapping("/{memberId}")
    MemberResponse get(@PathVariable("memberId") Long memberId);
}
