package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "channel_article_vote")
@Entity
public class ChannelArticleVote extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_article_vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_member_id")
    private ChannelMember channelMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_article_id")
    private ChannelArticle article;

    protected ChannelArticleVote() {}

    public static ChannelArticleVote of(ChannelMember channelMember, ChannelArticle article) {
        ChannelArticleVote articleVote = new ChannelArticleVote(null, channelMember, null);

        articleVote.setArticle(article);
        return articleVote;
    }

    public void setChannelMember(ChannelMember member) {
        this.channelMember = member;
    }

    public void setArticle(ChannelArticle article) {
        if (this.article != null) {
            this.article.getVotes().remove(this);
        }

        this.article = article;
        article.getVotes().add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChannelArticleVote)) return false;
        return id != null && id.equals(((ChannelArticleVote) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
