package com.levelup.core.domain.notice;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "channel_notice")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelNotice extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "channelNotice", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "channelNotice", cascade = CascadeType.REMOVE)
    private List<File> files;

    //==연관관계 메서드==//
    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getChannelNotices().add(this);
    }

    //==생성 메서드==//
    public static ChannelNotice createChannelNotice(Channel channel, String title, String writer, String content) {
        return ChannelNotice.builder()
                .channel(channel)
                .title(title)
                .writer(writer)
                .content(content)
                .views(0L)
                .comments(new ArrayList<>())
                .files(new ArrayList<>())
                .build();
    }

    //==비즈니스 로직==//
    public void change(String title, String content) {
        this.title = (title);
        this.content = (content);
    }

    public void addViews() {
        this.views = (this.views + 1);
    }

}
