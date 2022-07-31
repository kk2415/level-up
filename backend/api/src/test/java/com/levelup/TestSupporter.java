package com.levelup;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.ChannelRequest;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
import com.levelup.core.dto.member.CreateMemberRequest;

public class TestSupporter {

    protected Member createMember(String email, String name) {
        CreateMemberRequest memberRequest = CreateMemberRequest.of(email, "00000000", name,
                "testNickname", Gender.MALE, "19970927", "010-2354-9960", new UploadFile("", ""));
        EmailAuth authEmail = EmailAuth.createAuthEmail(memberRequest.getEmail());

        Member member = memberRequest.toEntity();
        member.setEmailAuth(authEmail);

        return member;
    }

    protected Channel createChannel(Member manager, String channelName, ChannelCategory category) {
        ChannelRequest channelRequest = ChannelRequest.of(manager.getEmail(), channelName, 5L, "testChannel",
                category, "test", new UploadFile("", ""), null);

        Channel channel = channelRequest.toEntity(manager.getNickname());
        ChannelMember channelMember = ChannelMember.createChannelMember(manager, true, false);
        channel.setChannelMember(channelMember);

        return channel;
    }

    protected Article createArticle(Member member, String title, ArticleType articleType) {
        return Article.createArticle(member, title, "test", articleType);
    }

    protected Comment createComment(Member member, Article article) {
        CreateCommentRequest dto = CreateCommentRequest.of(
                member.getEmail(), article.getArticleId(), "test", article.getArticleType());

        Comment comment = dto.toEntity(member, article);
        comment.setArticle(article);
        return comment;
    }

    protected Comment createReplyComment(Member member, Article article, Comment parentComment) {
        CreateReplyCommentRequest dto = CreateReplyCommentRequest.of(
                parentComment.getId(), article.getArticleId(), "tset", article.getArticleType());

        Comment childComment = dto.toEntity(member, article);
        parentComment.addChildComment(childComment);
        return childComment;
    }
}
