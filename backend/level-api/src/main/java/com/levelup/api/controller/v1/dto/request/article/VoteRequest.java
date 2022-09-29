package com.levelup.api.controller.v1.dto.request.article;

import com.levelup.article.domain.service.dto.VoteDto;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.entity.CommentVote;
import com.levelup.article.domain.VoteType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class VoteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long targetId;

    @NotNull
    private VoteType voteType;

    protected VoteRequest() {}

    private VoteRequest(Long memberId, Long targetId, VoteType voteType) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.voteType = voteType;
    }

    public static VoteRequest of(Long memberId, Long targetId, VoteType voteType) {
        return new VoteRequest(memberId, targetId, voteType);
    }

    public VoteDto toDto() {
        return VoteDto.of(memberId, targetId, voteType, false);
    }

    public ArticleVote toEntity(Article article) {
        ArticleVote articleVote = ArticleVote.builder()
                .memberId(memberId)
                .build();

        articleVote.setArticle(article);
        return articleVote;
    }

    public CommentVote toEntity(ArticleComment comment) {
        CommentVote commentVote = CommentVote.builder()
                .memberId(memberId)
                .comment(comment)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
