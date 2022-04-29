package com.together.levelup.repository;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class JpaChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member1;
    private Member member2;
    private Channel channel;
    private Channel channel1;
    private Channel channel2;
    private Channel channel3;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
        channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));

        channel1 = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        channel2 = Channel.createChannel(member2, "스프링 프로젝트1", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.PROJECT, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        channel3 = Channel.createChannel(member2, "스프링 프로젝트2", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.PROJECT, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
    }

    @Test
    void save() {
        memberRepository.save(member1);

        channelRepository.save(channel);
        Channel findChannel = channelRepository.findById(channel.getId());
        Assertions.assertThat(findChannel).isEqualTo(channel);
    }

    @Test
    void findByMemberId() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);

        List<Channel> findChannels = channelRepository.findByMemberId(member1.getId());
        Assertions.assertThat(findChannels.size()).isEqualTo(1);
    }

    @Test
    void delete() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel);

        channelRepository.delete(channel.getId());
        List<Channel> findChannels = channelRepository.findAll();
        Assertions.assertThat(findChannels.size()).isEqualTo(0);
    }

    @Test
    void 카테고리별_조회_테스트() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);

        List<Channel> findChannels2 = channelRepository.findByCategory(ChannelCategory.PROJECT);
        Assertions.assertThat(findChannels2.size()).isEqualTo(2);
    }

}