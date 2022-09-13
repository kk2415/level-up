package com.levelup.api.dto.request.vote;

import com.levelup.api.dto.service.vote.VoteDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.domain.vote.CommentVote;
import com.levelup.core.domain.vote.VoteType;
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

    public CommentVote toEntity(Comment comment) {
        CommentVote commentVote = CommentVote.builder()
                .memberId(memberId)
                .comment(comment)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
