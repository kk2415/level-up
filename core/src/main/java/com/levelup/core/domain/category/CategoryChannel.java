package com.levelup.core.domain.category;

import com.levelup.core.domain.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CategoryChannel {

    @Id
    @GeneratedValue
    @Column(name = "category_channel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    //==생성 메서드==//
    public static CategoryChannel createCategoryChannel(Channel channel) {
        CategoryChannel categoryChannel = new CategoryChannel();
        categoryChannel.setChannel(channel);
        return categoryChannel;
    }

}
