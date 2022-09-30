package com.levelup;

import com.levelup.api.config.SecurityConfig;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.repository.MemberRepository;
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
                        RoleName.MEMBER
                ).toEntity()
        ));
    }
}
