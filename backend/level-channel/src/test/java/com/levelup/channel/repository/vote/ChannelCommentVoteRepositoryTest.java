package com.levelup.channel.repository.vote;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.*;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.repository.comment.ChannelCommentRepository;
import com.levelup.channel.domain.repository.vote.ChannelCommentVoteRepository;
import org.junit.jupiter.api.BeforeEach;
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

@DisplayName("채널 댓글 추천 레포지토리 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelCommentVoteRepositoryTest extends TestSupporter {

    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelArticleRepository channelArticleRepository;
    @Autowired private ChannelCommentRepository channelCommentRepository;
    @Autowired private ChannelCommentVoteRepository channelCommentVoteRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        channelArticleRepository.deleteAll();
        channelCommentRepository.deleteAll();
        channelCommentVoteRepository.deleteAll();
    }

    @DisplayName("채널 댓글 추천 조회")
    @Test
    void findByChannelMemberIdAndCommentId() {
        // Given
        ChannelMember channelManager = createChannelMember(1L, "manager1", "manager1", true, false);
        Channel channel = createChannel(channelManager, "test channel", ChannelCategory.STUDY);
        channelRepository.save(channel);

        ChannelArticle article1 = createChannelArticle(channelManager, channel, "test article1");
        ChannelArticle article2 = createChannelArticle(channelManager, channel, "test article2");
        channelArticleRepository.save(article1);
        channelArticleRepository.save(article2);

        ChannelComment comment1 = createChannelComment(channelManager, article1);
        ChannelComment comment2 = createChannelComment(channelManager, article1);
        ChannelComment comment3 = createChannelComment(channelManager, article2);
        channelCommentRepository.save(comment1);
        channelCommentRepository.save(comment2);
        channelCommentRepository.save(comment3);

        ChannelCommentVote vote1 = createChannelCommentVote(channelManager, comment1);
        ChannelCommentVote vote2 = createChannelCommentVote(channelManager, comment2);
        channelCommentVoteRepository.save(vote1);
        channelCommentVoteRepository.save(vote2);

        // When
        List<ChannelCommentVote> votes
                = channelCommentVoteRepository.findByChannelMemberIdAndCommentId(channelManager.getId(), comment1.getId());

        // Then
        assertThat(votes.size()).isEqualTo(1L);
        assertThat(votes.get(0).getComment().getId()).isEqualTo(comment1.getId());
        assertThat(votes.get(0).getChannelMember().getId()).isEqualTo(channelManager.getId());
        assertThat(votes.get(0).getChannelMember().getMemberId()).isEqualTo(channelManager.getMemberId());
        assertThat(votes.get(0).getChannelMember().getChannel().getId()).isEqualTo(channel.getId());
    }
}