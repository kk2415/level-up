package domain.member;

import com.levelup.core.CoreApplication;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest(classes = CoreApplication.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 멤버_생성() {
        Member member = new Member();
        member.setEmail("test1@test.com");
        member.setPassword("00000000");
        member.setName("test");
        member.setGender(Gender.MALE);
        member.setBirthday("970927");
        member.setPhone("010-4646-4654");
        member.setUploadFile(null);
        member.setDateCreated(LocalDateTime.now());
        member.setAuthority(Authority.NORMAL);

        Long memberId = memberRepository.save(member);
        Member findMember = memberRepository.findById(memberId);
        Assertions.assertThat(memberId).isEqualTo(findMember.getId());
    }

}
