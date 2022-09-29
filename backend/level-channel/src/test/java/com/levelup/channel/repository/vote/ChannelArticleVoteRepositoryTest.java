package com.levelup.channel.repository.vote;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.entity.*;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.repository.vote.ChannelArticleVoteRepository;
import com.levelup.member.MemberApplication;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.repository.MemberRepository;
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

@DisplayName("채널 게시글 추천 서비스 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class, MemberApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelArticleVoteRepositoryTest extends TestSupporter {

    @Autowired private MemberRepository memberRepository;
    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelArticleRepository channelArticleRepository;
    @Autowired private ChannelArticleVoteRepository channelArticleVoteRepository;

    @DisplayName("채널 게시글 추천 조회")
    @Test
    void findByChannelMemberIdAndArticleId() {
        // Given
        Member member = createMember("test", "test");
        memberRepository.save(member);

        ChannelMember channelMember = createChannelMember(member, true, false);
        Channel channel = createChannel(channelMember, "test channel", ChannelCategory.STUDY);
        channelRepository.save(channel);

        ChannelArticle article1 = createChannelArticle(channelMember, channel, "test article1");
        ChannelArticle article2 = createChannelArticle(channelMember, channel, "test article2");
        channelArticleRepository.save(article1);
        channelArticleRepository.save(article2);

        ChannelArticleVote channelArticleVote1 = createChannelArticleVote(channelMember, article1);
        channelArticleVoteRepository.save(channelArticleVote1);

        // When
        List<ChannelArticleVote> votes
                = channelArticleVoteRepository.findByChannelMemberIdAndArticleId(channelMember.getId(), article1.getId());

        // Then
        assertThat(votes.size()).isEqualTo(1L);
        assertThat(votes.get(0).getArticle().getId()).isEqualTo(article1.getId());
        assertThat(votes.get(0).getChannelMember().getId()).isEqualTo(channelMember.getId());
        assertThat(votes.get(0).getChannelMember().getChannel().getId()).isEqualTo(channel.getId());
    }
}