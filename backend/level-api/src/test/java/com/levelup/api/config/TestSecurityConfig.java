package com.levelup.api.config;

import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.service.dto.MemberDto;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

//테스트 전용 설정 파일
@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private MemberRepository memberRepository;

    //각 테스트 메서드 실행 전에 이 코드를 실행해서 인증정보를 넣어줌
    //스프링 테스트 전용 애노테이션이지만 스프링 시큐리티을 쓴다는건 스프링을 쓴다는 거니까 인증관련 테스트를 진행할땐 이 메소드가 안성맞춤
    @BeforeTestMethod
    public void securitySetUp() {
        //테스트용 계정 정보 하나 저장
        //UserDetailsService에서 memberRepository.findById을 사용하니까 미리 데이터를 설정함
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(
                MemberDto.of(
                        1L,
                        "test@email.com",
                        "00000000",
                        "test",
                        "test",
                        Gender.MALE,
                        LocalDate.now(),
                        "010-2354-9960",
                        new UploadFile("", ""),
                        RoleName.MEMBER).toEntity()
        ));
    }
}
