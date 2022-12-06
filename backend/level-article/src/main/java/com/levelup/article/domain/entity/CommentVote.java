package com.levelup.article.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "comment_vote")
@Entity
public class CommentVote extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_vote_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    protected CommentVote() {}

    public void setComment(Comment comment) {
        if (comment != null) {
            comment.getCommentVotes().remove(this);
        }

        this.comment = comment;
        comment.getCommentVotes().add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CommentVote)) return false;
        return id != null && id.equals(((CommentVote) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
