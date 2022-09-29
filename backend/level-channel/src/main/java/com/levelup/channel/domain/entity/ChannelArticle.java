package com.levelup.channel.domain.entity;

import com.levelup.channel.domain.ChannelArticleCategory;
import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "channel_article")
@Entity
public class ChannelArticle extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_article_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChannelArticleCategory category;

    @Lob @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long views;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "channel_member_id")
    private ChannelMember channelMember;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<ChannelComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<ChannelArticleVote> votes = new ArrayList<>();

    protected ChannelArticle() {}

    public void setChannel(Channel channel) {
        if (this.channel != null) {
            this.channel.getChannelArticles().remove(this);
        }

        this.channel = channel;
        channel.getChannelArticles().add(this);
    }

    public void update(String title, String content, ChannelArticleCategory postCategory) {
        this.title = title;
        this.content = content;
        this.category = postCategory;
    }

    public void addViews() {
        this.views += 1;
    }
}
