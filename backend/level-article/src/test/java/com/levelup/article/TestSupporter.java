package com.levelup.article;

import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.*;

import java.util.ArrayList;

public class TestSupporter {

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

    protected Comment createComment(Writer writer, Article article) {
        Comment comment = Comment.builder()
                .writer(writer)
                .content("test")
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }

    protected Comment createReplyComment(Writer writer, Article article, Comment parentComment) {
        Comment comment = Comment.builder()
                .writer(writer)
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
