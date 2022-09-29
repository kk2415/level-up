package com.levelup.article;

import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.*;
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
