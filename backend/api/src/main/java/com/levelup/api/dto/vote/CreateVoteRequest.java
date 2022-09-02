package com.levelup.api.dto.vote;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.domain.vote.CommentVote;
import com.levelup.core.domain.vote.VoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class  CreateVoteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long targetId;

    @NotNull
    private VoteType voteType;

    private CreateVoteRequest(Long memberId, Long targetId, VoteType voteType) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.voteType = voteType;
    }

    public static CreateVoteRequest of(Long memberId, Long targetId, VoteType voteType) {
        return new CreateVoteRequest(memberId, targetId, voteType);
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
