package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    protected ChannelComment() {}

    public static ChannelComment of(
            Long id,
            String content,
            ChannelMember channelMember,
            ChannelArticle article)
    {
        ChannelComment comment = new ChannelComment(
                id,
                content,
                channelMember,
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>());

        comment.setArticle(article);
        return comment;
    }

    public void setArticle(ChannelArticle article) {
        if (this.article != null) {
            this.article.getComments().remove(this);
        }

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
