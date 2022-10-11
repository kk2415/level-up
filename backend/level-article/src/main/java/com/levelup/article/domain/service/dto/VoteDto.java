package com.levelup.article.domain.service.dto;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.entity.CommentVote;
import com.levelup.article.domain.entity.VoteType;
import lombok.Getter;

@Getter
public class VoteDto {

    private Long memberId;
    private Long targetId;
    private VoteType voteType;
    private boolean isSuccessful;


    protected VoteDto() {}

    private VoteDto(Long memberId, Long targetId, VoteType voteType, boolean isSuccessful) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.voteType = voteType;
        this.isSuccessful = isSuccessful;
    }

    public static VoteDto of(Long memberId, Long targetId, VoteType voteType, boolean isSuccessful) {
        return new VoteDto(memberId, targetId, voteType, isSuccessful);
    }

    public static VoteDto of(ArticleVote vote, boolean isSuccessful) {
        return new VoteDto(vote.getMemberId(), vote.getArticle().getId(), VoteType.ARTICLE, isSuccessful);
    }

    public static VoteDto of(CommentVote vote, boolean isSuccessful) {
        return new VoteDto(vote.getMemberId(), vote.getComment().getId(), VoteType.COMMENT, isSuccessful);
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
