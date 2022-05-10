package com.together.levelup.repository;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.service.ChannelService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class JpaMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelService channelService;

    private Member member1;
    private Member member2;

    private Channel channel;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
    }

    @Test
    public void memberRepositoryTest() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId());
        Member findMember2 = memberRepository.findById(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);
    }

    @Test
    public void waitingMemberTest() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel);

        channelService.addWaitingMember(channel.getId(), member1.getId(), member2.getId());
        List<Member> findMembers = memberRepository.findWaitingMemberByChannelId(channel.getId());

        Assertions.assertThat(findMembers.size()).isEqualTo(2);
    }

}
