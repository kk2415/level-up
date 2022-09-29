package com.levelup;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.ChannelArticleCategory;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.entity.CommentVote;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

    protected Member createMember(Long id, String email, String nickname) {
        Member member = Member.builder()
                .id(id)
                .email(email)
                .password("00000000")
                .name(nickname)
                .nickname(nickname)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1997, 9, 27))
                .phone("010-2354-9960")
                .profileImage(new UploadFile("default.png", "thumbnail/as154-asda"))
                .roles(new ArrayList<>())
                .build();

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected Member createMember(String email, String nickname) {
        Member member = Member.builder()
                .email(email)
                .password("00000000")
                .name(nickname)
                .nickname(nickname)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1997, 9, 27))
                .phone("010-2354-9960")
                .profileImage(new UploadFile("default.png", "thumbnail/as154-asda"))
                .roles(new ArrayList<>())
                .build();

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected Channel createChannel(ChannelMember manager, String channelName, ChannelCategory category) {
        Channel channel = Channel.builder()
                .name(channelName)
                .managerName(manager.getMember().getNickname())
                .description("test")
                .memberMaxNumber(10L)
                .thumbnail(new UploadFile("default.png", "thumbnail/as154-asda"))
                .category(category)
                .expectedStartDate(LocalDate.of(1997, 9, 27))
                .expectedEndDate(LocalDate.of(1997, 9, 27))
                .channelMembers(new ArrayList<>())
                .channelArticles(new ArrayList<>())
                .build();

        manager.getMember().addRole(Role.of(RoleName.CHANNEL_MANAGER, manager.getMember()));
        channel.addChannelMember(manager);
        manager.setChannel(channel);

        return channel;
    }

    protected ChannelMember createChannelMember(Member member, Boolean isWaitingMember) {
        return ChannelMember.of(member, false, isWaitingMember);
    }

    protected ChannelMember createChannelMember(Member member, Channel channel, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.of(member, false, isWaitingMember);
        channel.addChannelMember(channelMember);

        return channelMember;
    }

    protected ChannelArticle createChannelArticle(ChannelMember channelMember, Channel channel, String title) {
        ChannelArticle channelArticle = ChannelArticle.builder()
                .category(ChannelArticleCategory.INFO)
                .channel(channel)
                .channelMember(channelMember)
                .title(title)
                .content("test")
                .views(0L)
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        channelArticle.setChannel(channel);
        return channelArticle;
    }

    protected Article createArticle(Long id, Member member, String title, ArticleType articleType) {
        Article article = new Article();

        article.setId(id);
        article.setMember(member);
        article.setTitle(title);
        article.setContent("test");
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }

    protected Article createArticle(Member member, String title, ArticleType articleType) {
        Article article = new Article();

        article.setMember(member);
        article.setTitle(title);
        article.setContent("test");
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }

    protected ArticleComment createComment(Member member, Article article) {
        ArticleComment comment = ArticleComment.builder()
                .member(member)
                .content("test")
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }

    protected ArticleComment createReplyComment(Member member, Article article, ArticleComment parentComment) {
        ArticleComment comment = ArticleComment.builder()
                .member(member)
                .content("test")
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        parentComment.addChildComment(comment);
        return comment;
    }

    protected ArticleVote createArticleVote(Article article, Long memberId) {
        ArticleVote articleVote = ArticleVote.builder()
                .memberId(memberId)
                .build();

        articleVote.setArticle(article);
        return articleVote;
    }

    protected CommentVote createCommentVote(ArticleComment comment, Long memberId) {
        CommentVote commentVote = CommentVote.builder()
                .memberId(memberId)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
