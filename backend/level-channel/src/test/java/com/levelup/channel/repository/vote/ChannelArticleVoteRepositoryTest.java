package com.levelup.channel.repository.vote;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.*;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.repository.vote.ChannelArticleVoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("채널 게시글 추천 레포지토리 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelArticleVoteRepositoryTest extends TestSupporter {

    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelArticleRepository channelArticleRepository;
    @Autowired private ChannelArticleVoteRepository channelArticleVoteRepository;

    @DisplayName("채널 게시글 추천 조회")
    @Test
    void findByChannelMemberIdAndArticleId() {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, "manager1", "manager1", true, false);
        Channel channel = createChannel(channelManager1, "test channel", ChannelCategory.STUDY);
        channelRepository.save(channel);

        ChannelArticle article1 = createChannelArticle(channelManager1, channel, "test article1");
        ChannelArticle article2 = createChannelArticle(channelManager1, channel, "test article2");
        channelArticleRepository.save(article1);
        channelArticleRepository.save(article2);

        ChannelArticleVote channelArticleVote1 = createChannelArticleVote(channelManager1, article1);
        channelArticleVoteRepository.save(channelArticleVote1);

        // When
        List<ChannelArticleVote> votes
                = channelArticleVoteRepository.findByChannelMemberIdAndArticleId(channelManager1.getId(), article1.getId());

        // Then
        assertThat(votes.size()).isEqualTo(1L);
        assertThat(votes.get(0).getArticle().getId()).isEqualTo(article1.getId());
        assertThat(votes.get(0).getChannelMember().getId()).isEqualTo(channelManager1.getId());
        assertThat(votes.get(0).getChannelMember().getChannel().getId()).isEqualTo(channel.getId());
    }
}