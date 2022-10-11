package com.levelup.channel;

import com.levelup.channel.domain.entity.ChannelArticleCategory;
import com.levelup.channel.domain.entity.ChannelCategory;
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
                .managerName(manager.getNickname())
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
                .managerName(manager.getNickname())
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


    protected ChannelMember createChannelMember(Long id, Long memberId, String email, String nickname, String profileImage, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.builder()
                .id(id)
                .memberId(memberId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();
    }

    protected ChannelMember createChannelMember(Long memberId, String email, String nickname, String profileImage, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.builder()
                .memberId(memberId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();
    }

    protected ChannelMember createChannelMember(Member member, Channel channel, boolean isManager) {
        return ChannelMember.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage().getStoreFileName())
                .isManager(isManager)
                .isWaitingMember(false)
                .comments(new ArrayList<>())
                .build();
    }

    protected ChannelMember createChannelMember(Long id, Member member, boolean isManager, boolean isWaitingMember) {
        return ChannelMember.builder()
                .id(id)
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage().getStoreFileName())
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();
    }

    protected ChannelMember createChannelMember(Member member, boolean isManager, boolean isWaitingMember) {
        return ChannelMember.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage().getStoreFileName())
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();
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
