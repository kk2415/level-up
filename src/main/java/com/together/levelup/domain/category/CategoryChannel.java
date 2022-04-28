package com.together.levelup.domain.category;

import com.together.levelup.domain.channel.Channel;
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

    //==연관관계 메서드==//
//    void setChannel(Channel channel) {
//        this.channel = channel;
//        channel.getCategoryChannels().add(this);
//    }

    //==생성 메서드==//
    public static CategoryChannel createCategoryChannel(Channel channel) {
        CategoryChannel categoryChannel = new CategoryChannel();
        categoryChannel.setChannel(channel);
        return categoryChannel;
    }

}
