package com.levelup.member.service;

import com.levelup.common.domain.entity.SkillEntity;
import com.levelup.common.domain.repository.SkillRepository;
import com.levelup.event.events.EventPublisher;
import com.levelup.event.events.MemberCreatedEvent;
import com.levelup.event.events.MemberUpdatedEvent;
import com.levelup.member.TestSupporter;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.service.MemberService;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import com.levelup.member.domain.domain.Member;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.member.domain.service.dto.UpdatePasswordDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberEntityServiceTest extends TestSupporter {

    @Mock private MemberRepository memberRepository;
    @Mock private SkillRepository skillRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private MemberService memberService;

    private MockedStatic<EventPublisher> eventPublisher;

    @BeforeEach
    public void before() {
        eventPublisher = Mockito.mockStatic(EventPublisher.class);
    }

    @AfterEach
    public void after() {
        eventPublisher.close();
    }

    @DisplayName("회원가입 테스트")
    @Test
    void save() throws IOException {
        // Given
        List<SkillEntity> skills = List.of(SkillEntity.of(1L, "Spring"), SkillEntity.of(2L, "Java"), SkillEntity.of(2L, "PHP"));
        Member memberDto1 = Member.from(createMember("test1@email.com", "test1"));

        given(passwordEncoder.encode(eq(memberDto1.getPassword()))).willReturn("changed password");
        given(memberRepository.save(any(MemberEntity.class))).willReturn(memberDto1.toEntity());
        given(skillRepository.findAllById(anyList())).willReturn(skills);
        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any(MemberEntity.class)))
                .thenReturn(MemberCreatedEvent.of(1L, memberDto1.getEmail(), memberDto1.getNickname()));

        // When
        CreateMemberDto response = memberService.save(memberDto1);

        // Then
        assertThat(response.getEmail()).isEqualTo(memberDto1.getEmail());
        assertThat(response.getPassword()).isEqualTo("changed password");
        assertThat(response.getRole()).isEqualTo(RoleName.ANONYMOUS);
        assertThat(response.getSkills()).isNotEmpty();
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(memberRepository, times(1)).save(any(MemberEntity.class));
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void update() throws IOException {
        // Given
        UpdateMemberDto updateMemberDto = UpdateMemberDto.of("changed nickname");
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.of("changed password");
        MemberEntity member = Member.from(createMember("test1@email.com", "test1")).toEntity();

        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any(MemberEntity.class)))
                .thenReturn(MemberUpdatedEvent.of(1L, member.getEmail(), member.getNickname()));
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));
        given(passwordEncoder.encode(anyString())).willReturn(updatePasswordDto.getPassword());

        // When
        memberService.update(updateMemberDto, member.getId());
        memberService.updatePassword(updatePasswordDto, member.getEmail());

        // Then
        assertThat(member.getNickname()).isEqualTo(updateMemberDto.getNickname());
        assertThat(member.getPassword()).isEqualTo(updatePasswordDto.getPassword());
    }
}