package com.levelup;

import com.levelup.api.config.SecurityConfig;
import com.levelup.api.dto.service.member.MemberDto;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.api.dto.request.member.MemberRequest;
import com.levelup.core.repository.member.MemberRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private MemberRepository memberRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        //테스트용 계정 정보 하나 저장
        //UserDetailsService에서 userAccountRepository.findById을 사용하니까 미리 데이터를 설정함
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(
                MemberDto.of(
                        null,
                        "test@test",
                        "00000000",
                        "test",
                        "test",
                        Gender.MALE,
                        LocalDate.now(),
                        "010-2354-9960",
                        new UploadFile("", ""),
                        new ArrayList<>()
                ).toEntity()
        ));
    }
}
