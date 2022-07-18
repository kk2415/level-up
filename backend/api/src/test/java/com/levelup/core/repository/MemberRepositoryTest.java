package com.levelup.core.repository;

import com.levelup.api.ApiApplication;
import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("멤버 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
public class MemberRepositoryTest {

    private final MemberRepository memberRepository;

    public MemberRepositoryTest(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("멤버 생성 및 조회 테스트")
    @Test
    void saveMemberAndSelectTest() {
        // Given
        Member member = createMember();

        memberRepository.save(member);

        // When
        Member findMember = memberRepository.findByEmail(member.getEmail());

        // Then
        assertThat(findMember).isNotNull();
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
    }

    @DisplayName("멤버 삭제 테스트")
    @Test
    void deleteMemberTest() {
        // Given
        Member member = createMember();

        memberRepository.save(member);

        // When
        memberRepository.delete(member.getId());

        // Then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    public Member createMember() {
        CreateMemberRequest memberDto = CreateMemberRequest.of("test@test.com", "00000000", "test",
                "testNickname", Gender.MALE, "19970927", "010-2354-9960", new UploadFile("", ""));
        EmailAuth authEmail = EmailAuth.createAuthEmail(memberDto.getEmail());

        Member member = memberDto.toEntity();
        member.setEmailAuth(authEmail);

        return member;
    }

}
