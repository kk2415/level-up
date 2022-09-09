package com.levelup;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.api.dto.channel.ChannelRequest;
import com.levelup.api.dto.comment.CreateCommentRequest;
import com.levelup.api.dto.comment.CreateReplyCommentRequest;
import com.levelup.api.dto.member.CreateMemberRequest;

import java.time.LocalDate;

public class TestSupporter {

    protected Member createMember(String email, String name) {
        CreateMemberRequest memberRequest = CreateMemberRequest.of(email, "00000000", name,
                "testNickname", Gender.MALE, LocalDate.now(), "010-2354-9960", new UploadFile("", ""));
        Member member = memberRequest.toEntity();
        Role role = Role.of(RoleName.MEMBER, member);

        member.addRole(role);
        return member;
    }

    protected Channel createChannel(Member manager, String channelName, ChannelCategory category) {
        ChannelRequest channelRequest = ChannelRequest.of(manager.getId(), channelName, 5L, "testChannel",
                category, "test", new UploadFile("", ""), null);

        Channel channel = channelRequest.toEntity(manager.getNickname());
        ChannelMember channelMember = ChannelMember.of(manager, true, false);
        channel.setChannelMember(channelMember);

        return channel;
    }

    protected Article createArticle(Member member, String title, ArticleType articleType) {
        return Article.of(member, title, "test", articleType);
    }

    protected Comment createComment(Member member, Article article) {
        CreateCommentRequest dto = CreateCommentRequest.of(
                member.getEmail(), article.getId(), "test", article.getArticleType());

        Comment comment = dto.toEntity(member, article);
        comment.setArticle(article);
        return comment;
    }

    protected Comment createReplyComment(Member member, Article article, Comment parentComment) {
        CreateReplyCommentRequest dto = CreateReplyCommentRequest.of(
                parentComment.getId(), article.getId(), "tset", article.getArticleType());

        Comment childComment = dto.toEntity(member, article);
        parentComment.addChildComment(childComment);
        return childComment;
    }
}
