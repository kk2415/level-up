package com.together.levelup.repository.notice;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.repository.channel.ChannelRepository;
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
class JpaChannelNoticeRepositoryTest {

    @Autowired ChannelNoticeRepository channelNoticeRepository;
    @Autowired ChannelRepository channelRepository;
    @Autowired MemberRepository memberRepository;

    private Member member1;
    private Channel channel;
    private ChannelNotice channelNotic1;
    private ChannelNotice channelNotic2;
    private ChannelNotice channelNotic3;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모",
                "요리 친목도모", ChannelCategory.STUDY,
                new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));

        channelNotic1 = ChannelNotice.createChannelNotice(channel, "첫 공지사항 입니다.", member1.getName(), "주목해주세요");
        channelNotic2 = ChannelNotice.createChannelNotice(channel, "두번째 공지사항 입니다.", member1.getName(), "주목해주세요");
        channelNotic3 = ChannelNotice.createChannelNotice(channel, "세번째 공지사항 입니다.", member1.getName(), "주목해주세요");

        memberRepository.save(member1);
        channelRepository.save(channel);
    }

    @Test
    public void 생성_테스트() {
        channelNoticeRepository.save(channelNotic1);

        ChannelNotice findNotice = channelNoticeRepository.findById(channelNotic1.getId());
        Assertions.assertThat(findNotice).isEqualTo(channelNotic1);
    }

    @Test
    public void 채널별_조회_테스트() {
        channelNoticeRepository.save(channelNotic1);
        channelNoticeRepository.save(channelNotic2);
        channelNoticeRepository.save(channelNotic3);

        List<ChannelNotice> findChannelNotices = channelNoticeRepository.findByChannelId(channel.getId(), 1);
        Assertions.assertThat(findChannelNotices.size()).isEqualTo(3);
    }

    @Test
    public void 멤버별_조회_테스트() {
        channelNoticeRepository.save(channelNotic1);

        List<ChannelNotice> findChannelNotices = channelNoticeRepository.findByMemberId(member1.getId());
        Assertions.assertThat(findChannelNotices.size()).isEqualTo(1);
    }

    @Test
    public void 다음페이지_테스트() throws InterruptedException {
        channelNoticeRepository.save(channelNotic1);
        TimeUnit.MILLISECONDS.sleep(100);
        channelNoticeRepository.save(channelNotic2);
        TimeUnit.MILLISECONDS.sleep(100);
        channelNoticeRepository.save(channelNotic3);

        ChannelNotice nextPage = channelNoticeRepository.findNextPage(channelNotic2.getId());

        Assertions.assertThat(nextPage.getId()).isEqualTo(channelNotic3.getId());
    }

    @Test
    public void 이전페이지_테스트() throws InterruptedException {
        channelNoticeRepository.save(channelNotic1);
        TimeUnit.MILLISECONDS.sleep(100);
        channelNoticeRepository.save(channelNotic2);
        TimeUnit.MILLISECONDS.sleep(100);
        channelNoticeRepository.save(channelNotic3);

        ChannelNotice prevPage = channelNoticeRepository.findPrevPage(channelNotic2.getId());

        Assertions.assertThat(prevPage.getId()).isEqualTo(channelNotic1.getId());
    }

}