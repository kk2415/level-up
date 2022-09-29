package com.levelup.channel;

import com.levelup.channel.domain.ChannelArticleCategory;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.entity.*;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;

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

    protected Channel createChannel(Long id, ChannelMember manager, String channelName, ChannelCategory category) {
        Channel channel = Channel.builder()
                .id(id)
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

        channel.addChannelMember(manager);
        return channel;
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

        channel.addChannelMember(manager);
        return channel;
    }


    protected ChannelMember createChannelMember(Long id, Member member, Boolean isManager, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.builder()
                .id(id)
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();

        channelMember.setMember(member);
        return channelMember;
    }

    protected ChannelMember createChannelMember(Member member, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.of(member, isManager, isWaitingMember);
    }

    protected ChannelMember createChannelMember(Member member, Channel channel, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.of(member, false, isWaitingMember);
        channel.addChannelMember(channelMember);

        return channelMember;
    }

    protected ChannelArticle createChannelArticle(Long id, ChannelMember channelMember, Channel channel, String title) {
        ChannelArticle channelArticle = ChannelArticle.builder()
                .id(id)
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

    protected ChannelComment createChannelComment(Long id, ChannelMember channelMember, ChannelArticle article) {
        ChannelComment comment = ChannelComment.builder()
                .id(id)
                .content("content")
                .replies(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        comment.setChannelMember(channelMember);
        comment.setArticle(article);
        return comment;
    }

    protected ChannelComment createChannelComment(ChannelMember channelMember, ChannelArticle article) {
        ChannelComment comment = ChannelComment.builder()
                .content("content")
                .replies(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        comment.setChannelMember(channelMember);
        comment.setArticle(article);
        return comment;
    }

    protected ChannelComment createChannelReplyComment(
            ChannelMember channelMember,
            ChannelArticle article,
            ChannelComment parent)
    {
        ChannelComment comment = ChannelComment.builder()
                .content("content")
                .replies(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        comment.setChannelMember(channelMember);
        comment.setArticle(article);

        parent.addReply(comment);
        return comment;
    }

    protected ChannelArticleVote createChannelArticleVote(ChannelMember channelMember, ChannelArticle article) {
        ChannelArticleVote articleVote = ChannelArticleVote.builder()
                .channelMember(channelMember)
                .build();

        articleVote.setArticle(article);
        return articleVote;
    }

    protected ChannelCommentVote createChannelCommentVote(ChannelMember channelMember, ChannelComment comment) {
        ChannelCommentVote commentVote = ChannelCommentVote.builder()
                .channelMember(channelMember)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
