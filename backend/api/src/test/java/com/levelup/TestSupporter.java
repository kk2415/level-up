package com.levelup;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.api.dto.request.channel.ChannelRequest;
import com.levelup.api.dto.request.comment.CommentRequest;
import com.levelup.api.dto.request.comment.ReplyCommentRequest;
import com.levelup.api.dto.request.member.MemberRequest;

import java.time.LocalDate;
import java.util.List;

public class TestSupporter {

    protected Member createMember(String email, String name) {
//        MemberRequest memberRequest = MemberRequest.of(email, "00000000", name,
//                "testNickname", Gender.MALE, LocalDate.now(), "010-2354-9960", new UploadFile("", ""));
//        Member member = memberRequest.toEntity();
//        Role role = Role.of(RoleName.MEMBER, member);
//
//        member.addRole(role);
//        return member;
        return null;
    }

    protected Channel createChannel(Member manager, String channelName, ChannelCategory category) {
//        ChannelRequest channelRequest = ChannelRequest.of(
//                manager.getId(),
//                channelName,
//                5L,
//                "testChannel",
//                category,
//                new UploadFile("", ""),
//                LocalDate.now(),
//                LocalDate.now(),
//                List.of());
//
//        Channel channel = channelRequest.toEntity(manager.getNickname());
//        ChannelMember channelMember = ChannelMember.of(manager, true, false);
//        channel.setChannelMember(channelMember);
//
//        return channel;
        return null;
    }

    protected Article createArticle(Member member, String title, ArticleType articleType) {
        return Article.of(member, title, "test", articleType);
    }

    protected Comment createComment(Member member, Article article) {
//        CommentRequest dto = CommentRequest.of(
//                member.getEmail(), article.getId(), "test", article.getArticleType());
//
//        Comment comment = dto.toEntity(member, article);
//        comment.setArticle(article);
//        return comment;
        return null;
    }

    protected Comment createReplyComment(Member member, Article article, Comment parentComment) {
//        ReplyCommentRequest dto = ReplyCommentRequest.of(
//                parentComment.getId(), article.getId(), "tset", article.getArticleType());
//
//        Comment childComment = dto.toEntity(member, article);
//        parentComment.addChildComment(childComment);
//        return childComment;
        return null;
    }
}
