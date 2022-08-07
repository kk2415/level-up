package com.levelup.core.dto.vote;

import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.domain.vote.CommentVote;
import lombok.Data;

@Data
public class VoteResponse {

    private Long memberId;
    private Long targetId;

    private VoteResponse(Long memberId, Long targetId) {
        this.memberId = memberId;
        this.targetId = targetId;
    }

    public static VoteResponse from(ArticleVote vote) {
        return new VoteResponse(vote.getMemberId(), vote.getArticle().getId());
    }

    public static VoteResponse from(CommentVote vote) {
        return new VoteResponse(vote.getMemberId(), vote.getComment().getId());
    }
}
