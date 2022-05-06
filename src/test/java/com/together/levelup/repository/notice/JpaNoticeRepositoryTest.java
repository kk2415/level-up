package com.together.levelup.repository.notice;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.Notice;
import com.together.levelup.dto.post.PostSearch;
import com.together.levelup.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Transactional
class JpaNoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    MemberRepository memberRepository;

    private Member member1;
    private Member member2;

    private Notice notic1;
    private Notice notic2;
    private Notice notic3;
    private Notice notic4;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);

        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.MALE, "010927", "010-2354-9960", null);

        memberRepository.save(member1);
        memberRepository.save(member2);

        notic1 = Notice.createNotice(member1, "첫 공지사항 입니다.", member1.getName(), "주목해주세요");
        notic2 = Notice.createNotice(member1, "두번째 공지사항 입니다.", member1.getName(), "주목해주세요");
        notic3 = Notice.createNotice(member2, "세번째 공지사항 입니다.", member2.getName(), "주목해주세요");
        notic4 = Notice.createNotice(member2, "사이트 폐쇠됨", member2.getName(), "주목해주세요");
    }

    @Test
    public void 생성_테스트() {
        noticeRepository.save(notic1);
        noticeRepository.save(notic2);
        noticeRepository.save(notic3);

        Notice findNotice1 = noticeRepository.findById(notic1.getId());
        Notice findNotice2 = noticeRepository.findById(notic2.getId());
        Notice findNotice3 = noticeRepository.findById(notic3.getId());
        Assertions.assertThat(findNotice1).isEqualTo(notic1);
        Assertions.assertThat(findNotice2).isEqualTo(notic2);
        Assertions.assertThat(findNotice3).isEqualTo(notic3);
    }

    @Test
    public void 멤버별_조회_테스트() {
        noticeRepository.save(notic1);
        noticeRepository.save(notic2);
        noticeRepository.save(notic3);

        List<Notice> findNotices = noticeRepository.findByMemberId(member1.getId());
        Assertions.assertThat(findNotices.size()).isEqualTo(2);
    }

    @Test
    public void 검색_테스트() throws InterruptedException {
        noticeRepository.save(notic1);
        noticeRepository.save(notic2);
        noticeRepository.save(notic3);
        noticeRepository.save(notic4);

        List<Notice> findNotice1 = noticeRepository.findAll(1L, new PostSearch("title", "두번째"));
        Assertions.assertThat(findNotice1.size()).isEqualTo(1);
        Assertions.assertThat(findNotice1.get(0).getTitle()).isEqualTo("두번째 공지사항 입니다.");

        List<Notice> findNotice2 = noticeRepository.findAll(null, new PostSearch("title", "공지사항"));
        Assertions.assertThat(findNotice2.size()).isEqualTo(3);

        List<Notice> findNotice3 = noticeRepository.findAll(null, new PostSearch(null, null));
        Assertions.assertThat(findNotice3.size()).isEqualTo(4);
    }

    @Test
    public void 공지사항_개수_테스트() throws InterruptedException {
        noticeRepository.save(notic1);
        noticeRepository.save(notic2);
        noticeRepository.save(notic3);
        noticeRepository.save(notic4);

        Long count1 = noticeRepository.count(1L, new PostSearch("title", "두번째"));
        Long count2 = noticeRepository.count(null, new PostSearch("title", "공지사항"));
        Long count3 = noticeRepository.count(null, new PostSearch(null, null));

        Assertions.assertThat(count1).isEqualTo(1);
        Assertions.assertThat(count2).isEqualTo(3);
        Assertions.assertThat(count3).isEqualTo(4);
    }

    @Test
    public void 다음페이지_테스트() throws InterruptedException {
        noticeRepository.save(notic1);
        TimeUnit.MILLISECONDS.sleep(100);
        noticeRepository.save(notic2);
        TimeUnit.MILLISECONDS.sleep(100);
        noticeRepository.save(notic3);

        Notice nextPage = noticeRepository.findNextPage(notic2.getId());
        Assertions.assertThat(nextPage.getId()).isEqualTo(notic3.getId());
    }

    @Test
    public void 이전페이지_테스트() throws InterruptedException {
        noticeRepository.save(notic1);
        TimeUnit.MILLISECONDS.sleep(100);
        noticeRepository.save(notic2);
        TimeUnit.MILLISECONDS.sleep(100);
        noticeRepository.save(notic3);

        Notice prevPage = noticeRepository.findPrevPage(notic2.getId());
        Assertions.assertThat(prevPage.getId()).isEqualTo(notic1.getId());
    }

}