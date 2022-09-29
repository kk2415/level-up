package com.levelup.channel.repository;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.repository.comment.ChannelCommentRepository;
import com.levelup.member.MemberApplication;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.repository.MemberRepository;
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

@DisplayName("채널 댓글 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class, MemberApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelCommentRepositoryTest extends TestSupporter {

    @Autowired private MemberRepository memberRepository;
    @Autowired private ChannelMemberRepository channelMemberRepository;
    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelArticleRepository channelArticleRepository;
    @Autowired private ChannelCommentRepository channelCommentRepository;

    @BeforeEach
    public void before() {
        memberRepository.deleteAll();
        channelMemberRepository.deleteAll();
        channelRepository.deleteAll();
        channelArticleRepository.deleteAll();
        channelCommentRepository.deleteAll();
    }

    @DisplayName("게시글 ID로 댓글 조회")
    @Test
    void findByArticleId() {
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

        ChannelComment comment1 = createChannelComment(channelMember, article1);
        ChannelComment comment2 = createChannelComment(channelMember, article1);
        ChannelComment comment3 = createChannelComment(channelMember, article2);
        channelCommentRepository.save(comment1);
        channelCommentRepository.save(comment2);
        channelCommentRepository.save(comment3);

        // When
        List<ChannelComment> comments = channelCommentRepository.findByArticleId(article1.getId());

        // Then
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getChannelMember().getId()).isEqualTo(channelMember.getId());
        assertThat(comments.get(0).getChannelMember().getMember().getId()).isEqualTo(member.getId());
        assertThat(comments.get(0).getArticle().getId()).isEqualTo(article1.getId());
    }

    @DisplayName("부모 ID로 대댓글 조회")
    @Test
    void findReplyByParentId() {
        // Given
        Member member = createMember("test", "test");
        memberRepository.save(member);

        ChannelMember channelMember = createChannelMember(member, true, false);
        Channel channel = createChannel(channelMember, "test channel", ChannelCategory.STUDY);
        channelRepository.save(channel);

        ChannelArticle article = createChannelArticle(channelMember, channel, "test article1");
        channelArticleRepository.save(article);

        ChannelComment parent = createChannelComment(channelMember, article);
        channelCommentRepository.save(parent);

        ChannelComment reply1 = createChannelReplyComment(channelMember, article, parent);
        ChannelComment reply2 = createChannelReplyComment(channelMember, article, parent);
        ChannelComment savedReply1 = channelCommentRepository.save(reply1);
        ChannelComment savedReply2 = channelCommentRepository.save(reply2);

        // When
        List<ChannelComment> replies = channelCommentRepository.findReplyByParentId(parent.getId());

        // Then
        assertThat(replies.size()).isEqualTo(2);
        assertThat(replies.get(0).getParent().getId()).isEqualTo(parent.getId());
    }
}