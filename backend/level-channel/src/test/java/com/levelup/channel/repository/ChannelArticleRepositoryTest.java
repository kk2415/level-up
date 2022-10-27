package com.levelup.channel.repository;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJasyptConfig;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.common.CommonApplication;
import com.levelup.common.config.JasyptConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("채널 게시글 레포지토리 테스트")
@Transactional
@ActiveProfiles("test")
@Import({TestJpaConfig.class, JasyptConfig.class})
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class, CommonApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelArticleRepositoryTest extends TestSupporter {

    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;
    @Autowired ChannelArticleRepository channelArticleRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        channelMemberRepository.deleteAll();
        channelArticleRepository.deleteAll();
    }

    @Disabled
    @DisplayName("채널 게시글 제목 검색")
    @Test
    void findByChannelIdAndTitle() {
        ChannelMember channelManager1 = createChannelMember(1L, "manager1", "manager1", true, false);
        ChannelMember channelManager2 = createChannelMember(2L, "manager2", "manager2", true, false);
        ChannelMember channelManager3 = createChannelMember(3L, "manager3", "manager3", true, false);

        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(channelManager2, "test channel2", ChannelCategory.PROJECT);
        channelRepository.save(channel1);
        channelRepository.save(channel2);

        ChannelArticle channelPost1 = createChannelArticle(channelManager2, channel1, "test post1 in channel1");
        ChannelArticle channelPost2 = createChannelArticle(channelManager1, channel2, "test post2 in channel2");
        ChannelArticle channelPost3 = createChannelArticle(channelManager3, channel2, "test post2 in channel2");
        channelArticleRepository.save(channelPost1);
        channelArticleRepository.save(channelPost2);
        channelArticleRepository.save(channelPost3);

        Page<ChannelArticle> channelArticles = channelArticleRepository.findByChannelIdAndTitle(
                channel2.getId(), "post2", PageRequest.of(0, 10));

        assertThat(channelArticles.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("채널 게시글 작성자 검색")
    @Test
    void findByChannelIdAndNickname() {
        ChannelMember channelManager1 = createChannelMember(1L, "manager1", "manager1", true, false);
        ChannelMember channelManager2 = createChannelMember(2L, "manager2", "manager2", true, false);
        ChannelMember channelManager3 = createChannelMember(3L, "manager3", "manager3", true, false);

        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(channelManager2, "test channel2", ChannelCategory.PROJECT);
        channel2.addChannelMember(channelManager3);
        channelRepository.save(channel1);
        channelRepository.save(channel2);

        ChannelArticle channelArticle1 = createChannelArticle(channelManager2, channel1, "test post1 in channel1");
        ChannelArticle channelArticle2 = createChannelArticle(channelManager1, channel2, "test post1 in channel2");
        ChannelArticle channelArticle3 = createChannelArticle(channelManager3, channel2, "test post2 in channel2");
        channelArticleRepository.save(channelArticle1);
        channelArticleRepository.save(channelArticle2);
        channelArticleRepository.save(channelArticle3);

        Page<ChannelArticle> channelArticles = channelArticleRepository.findByChannelIdAndNickname(
                channel2.getId(), channelManager1.getNickname(), PageRequest.of(0, 10));

        assertThat(channelArticles.getTotalElements()).isEqualTo(1);
        for (ChannelArticle channelPost : channelArticles) {
            assertThat(channelPost.getChannelMember().getEmail()).isEqualTo(channelManager1.getEmail());
        }
    }
}