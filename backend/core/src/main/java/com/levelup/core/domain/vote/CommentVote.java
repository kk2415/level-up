package com.levelup.core.domain.vote;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_vote")
@Entity
public class CommentVote extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_vote_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public void setComment(Comment comment) {
        if (comment != null) {
            comment.getCommentVotes().remove(this);
        }

        this.comment = comment;
        comment.getCommentVotes().add(this);
    }
}
