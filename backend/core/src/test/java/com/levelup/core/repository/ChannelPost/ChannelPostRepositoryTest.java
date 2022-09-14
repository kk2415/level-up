package com.levelup.core.repository.ChannelPost;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.channelMember.ChannelMemberRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelPostRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;
    @Autowired ChannelPostRepository channelPostRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        memberRepository.deleteAll();
        channelMemberRepository.deleteAll();
        channelPostRepository.deleteAll();
    }

    @Test
    void findByChannelIdAndTitleAndArticleType() {
        Member manager1 = createMember("manager1", "manager1");
        Member manager2 = createMember("manager2", "manager2");
        Member manager3 = createMember("manager3", "manager3");
        memberRepository.save(manager1);
        memberRepository.save(manager2);
        memberRepository.save(manager3);

        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(manager2, "test channel2", ChannelCategory.PROJECT);
        channelRepository.save(channel1);
        channelRepository.save(channel2);

        ChannelPost channelPost1 = createChannelPost(manager2, channel1, "test post1 in channel1");
        ChannelPost channelPost2 = createChannelPost(manager1, channel2, "test post1 in channel2");
        ChannelPost channelPost3 = createChannelPost(manager3, channel2, "test post2 in channel2");
        channelPostRepository.save(channelPost1);
        channelPostRepository.save(channelPost2);
        channelPostRepository.save(channelPost3);

        Page<ChannelPost> channelPosts = channelPostRepository.findByChannelIdAndTitleAndArticleType(
                channel2.getId(), ArticleType.CHANNEL_POST, "post", PageRequest.of(0, 10));

        assertThat(channelPosts.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findByChannelIdAndWriterAndArticleType() {
        Member manager1 = createMember("manager1", "manager1");
        Member manager2 = createMember("manager2", "manager2");
        Member manager3 = createMember("manager3", "manager3");
        memberRepository.save(manager1);
        memberRepository.save(manager2);
        memberRepository.save(manager3);

        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(manager2, "test channel2", ChannelCategory.PROJECT);
        channelRepository.save(channel1);
        channelRepository.save(channel2);

        ChannelPost channelPost1 = createChannelPost(manager2, channel1, "test post1 in channel1");
        ChannelPost channelPost2 = createChannelPost(manager1, channel2, "test post1 in channel2");
        ChannelPost channelPost3 = createChannelPost(manager3, channel2, "test post2 in channel2");
        channelPostRepository.save(channelPost1);
        channelPostRepository.save(channelPost2);
        channelPostRepository.save(channelPost3);

        Page<ChannelPost> channelPosts = channelPostRepository.findByChannelIdAndWriterAndArticleType(
                channel2.getId(), ArticleType.CHANNEL_POST, manager1.getNickname(), PageRequest.of(0, 10));

        assertThat(channelPosts.getTotalElements()).isEqualTo(1);
        for (ChannelPost channelPost : channelPosts) {
            assertThat(channelPost.getMember().getEmail()).isEqualTo(manager1.getEmail());
        }
    }
}