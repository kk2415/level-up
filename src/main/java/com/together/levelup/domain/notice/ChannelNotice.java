package com.together.levelup.domain.notice;

import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.file.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "channel_notice")
@Getter @Setter
public class ChannelNotice {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "channelNotice", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "channelNotice", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    //==연관관계 메서드==//
    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getChannelNotices().add(this);
    }

    //==생성 메서드==//
    public static ChannelNotice createChannelNotice(Channel channel, String title, String writer, String content) {
        ChannelNotice channelNotice = new ChannelNotice();

        channelNotice.setChannel(channel);
        channelNotice.setTitle(title);
        channelNotice.setWriter(writer);
        channelNotice.setContent(content);
        channelNotice.setDateCreated(LocalDateTime.now());
        channelNotice.setViews(0L);

        return channelNotice;
    }

    //==비즈니스 로직==//
    public void change(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public void addViews() {
        this.setViews(this.views + 1);
    }

}
