package com.levelup.core.domain.Article;

import com.levelup.core.domain.channel.Channel;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DiscriminatorValue("channel_post")
@AllArgsConstructor
@NoArgsConstructor
public class ChannelPost extends Article {

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public void modifyChannelPost(String title, String contents, PostCategory postCategory) {
        this.modifyArticle(title, contents);
        this.postCategory = postCategory;
    }

}
