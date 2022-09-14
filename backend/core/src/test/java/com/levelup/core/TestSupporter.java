package com.levelup.core;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.PostCategory;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.domain.vote.CommentVote;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

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
                .articles(new ArrayList<>())
                .comments(new ArrayList<>())
                .channelMembers(new ArrayList<>())
                .roles(new ArrayList<>())
                .build();

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected Channel createChannel(Member manager, String channelName, ChannelCategory category) {
        Channel channel = Channel.builder()
                .name(channelName)
                .managerName(manager.getNickname())
                .description("test")
                .memberMaxNumber(10L)
                .thumbnailImage(new UploadFile("default.png", "thumbnail/as154-asda"))
                .category(category)
                .expectedStartDate(LocalDate.of(1997, 9, 27))
                .expectedEndDate(LocalDate.of(1997, 9, 27))
                .channelMembers(new ArrayList<>())
                .channelPosts(new ArrayList<>())
                .files(new ArrayList<>())
                .build();

        manager.addRole(Role.of(RoleName.CHANNEL_MANAGER, manager));
        ChannelMember channelMember = ChannelMember.of(manager, true, false);
        channel.setChannelMember(channelMember);

        return channel;
    }

    protected ChannelMember createChannelMember(Member member, Channel channel, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.of(member, false, isWaitingMember);
        channel.setChannelMember(channelMember);

        return channelMember;
    }

    protected ChannelPost createChannelPost(Member member, Channel channel, String title) {
        ChannelPost channelPost = ChannelPost.builder()
                .channelPostCategory(PostCategory.INFO)
                .channel(channel)
                .build();

        channelPost.setMember(member);
        channelPost.setTitle(title);
        channelPost.setContent("test");
        channelPost.setViews(0L);
        channelPost.setArticleType(ArticleType.CHANNEL_POST);
        return channelPost;
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

    protected Comment createComment(Member member, Article article) {
        Comment comment = Comment.builder()
                .member(member)
                .content("test")
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }

    protected Comment createReplyComment(Member member, Article article, Comment parentComment) {
        Comment comment = Comment.builder()
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

    protected CommentVote createCommentVote(Comment comment, Long memberId) {
        CommentVote commentVote = CommentVote.builder()
                .memberId(memberId)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
