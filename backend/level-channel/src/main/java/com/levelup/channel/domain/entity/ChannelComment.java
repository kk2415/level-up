package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "channel_comment")
@Entity
public class ChannelComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_member_id")
    private ChannelMember channelMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_article_id")
    private ChannelArticle article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ChannelComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<ChannelComment> replies;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<ChannelCommentVote> votes;

    public void setChannelMember(ChannelMember channelMember) {
        this.channelMember = channelMember;
        channelMember.getComments().add(this);
    }

    public void setArticle(ChannelArticle article) {
            this.article = article;
        article.getComments().add(this);
    }

    public void addReply(ChannelComment reply) {
        this.replies.add(reply);
        reply.parent = this;
    }

    public void update(String content) {
        this.content = (content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChannelComment)) return false;
        return id != null && id.equals(((ChannelComment) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
