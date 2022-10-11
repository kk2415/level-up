package com.levelup;

import com.levelup.article.domain.entity.*;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelArticleCategory;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;

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

    protected Writer createWriter(Long memberId, String nickname, String email) {
        return Writer.builder()
                .memberId(memberId)
                .nickname(nickname)
                .email(email)
                .build();
    }

    protected Writer createWriter(Long id, Long memberId, String nickname, String email) {
        return Writer.builder()
                .id(id)
                .memberId(memberId)
                .nickname(nickname)
                .email(email)
                .build();
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

    protected Article createArticle(Long id, Writer writer, String title, ArticleType articleType) {
        Article article = new Article();

        article.setId(id);
        article.setWriter(writer);
        article.setTitle(title);
        article.setContent("test");
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }

    protected Article createArticle(Writer writer, String title, ArticleType articleType) {
        Article article = new Article();

        article.setWriter(writer);
        article.setTitle(title);
        article.setContent("test");
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }

    protected ArticleVote createArticleVote(Article article, Long memberId) {
        ArticleVote articleVote = ArticleVote.builder()
                .memberId(memberId)
                .build();

        articleVote.setArticle(article);
        return articleVote;
    }

    protected CommentVote createCommentVote(Comment comment, Long memberId) {
        CommentVote commentVote = CommentVote.builder()
                .memberId(memberId)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
