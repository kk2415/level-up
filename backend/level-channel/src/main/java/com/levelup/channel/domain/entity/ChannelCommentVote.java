package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "channel_comment_vote")
@Entity
public class ChannelCommentVote extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_comment_vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_member_id")
    private ChannelMember channelMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_comment_id")
    private ChannelComment comment;

    protected ChannelCommentVote() {}

    public static ChannelCommentVote of(ChannelMember channelMember, ChannelComment comment) {
        ChannelCommentVote commentVote = new ChannelCommentVote(null, channelMember, null);

        commentVote.setComment(comment);
        return commentVote;
    }

    public void setComment(ChannelComment comment) {
        if (this.comment != null) {
            this.comment.getVotes().remove(this);
        }

        this.comment = comment;
        comment.getVotes().add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChannelCommentVote)) return false;
        return id != null && id.equals(((ChannelCommentVote) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
