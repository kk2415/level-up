package com.levelup.core.domain.channelPost;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.channel.Channel;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@DiscriminatorValue("channel_post")
@Table(name = "channel_post")
@Entity
public class ChannelPost extends Article {

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_post_category")
    private PostCategory channelPostCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    protected ChannelPost() {}

    public void modifyChannelPost(String title, String contents, PostCategory postCategory) {
        this.modifyArticle(title, contents);
        this.channelPostCategory = postCategory;
    }
}
