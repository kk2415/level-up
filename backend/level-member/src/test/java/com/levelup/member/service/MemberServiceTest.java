package com.levelup.member.service;

import com.levelup.event.events.EventPublisher;
import com.levelup.event.events.MemberCreatedEvent;
import com.levelup.member.TestSupporter;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.service.MemberService;
import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.repository.MemberRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest extends TestSupporter {

    @Mock private MemberRepository mocKMemberRepository;
    @Mock private PasswordEncoder mockPasswordEncoder;

    @InjectMocks private MemberService mockMemberService;

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
    void save() {
        // Given
        MemberDto memberDto1 = MemberDto.from(createMember("test1@email.com", "test1"));

        given(mockPasswordEncoder.encode(eq(memberDto1.getPassword()))).willReturn("changed password");
        given(mocKMemberRepository.save(any(Member.class))).willReturn(memberDto1.toEntity());
        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any(Member.class)))
                .thenReturn(MemberCreatedEvent.of(1L, memberDto1.getEmail(), memberDto1.getNickname()));

        // When
//        MemberDto newMemberDto1 = mockMemberService.save(memberDto1);

        // Then
//        assertThat(newMemberDto1.getEmail()).isEqualTo(memberDto1.getEmail());
//        assertThat(newMemberDto1.getPassword()).isEqualTo("changed password");
//        assertThat(newMemberDto1.getRole()).isEqualTo(RoleName.ANONYMOUS);
//        verify(mockPasswordEncoder, times(1)).encode(anyString());
//        verify(mocKMemberRepository, times(1)).save(any(Member.class));
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void update() {
        // Given
//        UpdateMemberDto updateMemberDto = UpdateMemberDto.of("changed password", "changed nickname", new UploadFile());
//        Member member = MemberDto.from(createMember("test1@email.com", "test1")).toEntity();
//
//        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any())).thenReturn(any());
//        given(mocKMemberRepository.findById(member.getId())).willReturn(Optional.of(member));
//        given(mocKMemberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));
//        given(mockPasswordEncoder.encode(anyString())).willReturn(updateMemberDto.getPassword());

        // When
//        mockMemberService.update(updateMemberDto, member.getId());
//        mockMemberService.updatePassword(updateMemberDto, member.getEmail());

        // Then
//        assertThat(member.getNickname()).isEqualTo(updateMemberDto.getNickname());
//        assertThat(member.getPassword()).isEqualTo(updateMemberDto.getPassword());
    }
}