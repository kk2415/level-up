package com.levelup;

import com.levelup.article.domain.entity.*;
import com.levelup.member.domain.constant.Gender;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.constant.RoleName;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

    protected MemberEntity createMember(Long id, String email, String nickname) {
        MemberEntity member = MemberEntity.of(
                id,
                email,
                "00000000",
                nickname,
                nickname,
                Gender.MALE,
                LocalDate.of(1997, 9, 27),
                "010-2354-9960",
                new ArrayList<>(),
                email);

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected MemberEntity createMember(String email, String nickname) {
        MemberEntity member = MemberEntity.of(
                null,
                email,
                "00000000",
                nickname,
                nickname,
                Gender.MALE,
                LocalDate.of(1997, 9, 27),
                "010-2354-9960",
                new ArrayList<>(),
                email);

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
