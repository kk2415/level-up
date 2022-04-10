package com.together.community.repository;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.channel.ChannelMember;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.repository.channel.ChannelRepository;
import com.together.community.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class JpaChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Channel channel = Channel.createChannel("모두모두 모여라 요리왕", "김탁구", 20L, "요리 친목도모");
        channelRepository.save(channel);
        Channel findChannel = channelRepository.findById(channel.getId());
        Assertions.assertThat(findChannel).isEqualTo(channel);
    }

    @Test
    @Commit
    void findByMemberId() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel = Channel.createChannel("모두모두 모여라 요리왕", "김탁구", 20L, "요리 친목도모");
        channelRepository.save(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);

        List<Channel> findChannels = channelRepository.findByMemberId(member1.getId());
        Assertions.assertThat(findChannels.size()).isEqualTo(1);
    }

    @Test
    void delete() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel = Channel.createChannel("모두모두 모여라 요리왕", "김탁구", 20L, "요리 친목도모");
        channelRepository.save(channel);

        channelRepository.delete(channel.getId());
        List<Channel> findChannels = channelRepository.findAll();
        Assertions.assertThat(findChannels.size()).isEqualTo(0);

    }

    private Member getMember(String loginId, String birthday, String email, String name, Gender gender) {
        Member member1 = new Member();
        member1.setLoginId(loginId);
        member1.setPassword("0000");
        member1.setBirthday(birthday);
        member1.setEmail(email);
        member1.setDateCreated(LocalDateTime.now());
        member1.setGender(gender);
        member1.setPhone("010-2354-9960");
        member1.setName(name);
        return member1;
    }

}