package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("멤버 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
public class MemberRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;

    public MemberRepositoryTest(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("멤버 생성 및 조회 테스트")
    @Test
    void saveMemberAndSelectTest() {
        // Given
        Member member = createMember("testEmail@test.com", "testUser");

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
        Member member = createMember("testEmail@test.com", "testUser");
        memberRepository.save(member);

        // When
        memberRepository.delete(member.getId());
        Member findMember = memberRepository.findById(member.getId());

        // Then
        assertThat(findMember).isNull();
    }

}
