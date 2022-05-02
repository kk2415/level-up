package com.together.levelup.domain.file;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.UploadFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "channel_description_file")
@Getter @Setter
public class ChannelDescriptionFile {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private UploadFile file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    //==생성 메서드==//
    public static ChannelDescriptionFile createChannelDescriptionFile(UploadFile file) {
        ChannelDescriptionFile channelDescriptionFile = new ChannelDescriptionFile();
        channelDescriptionFile.setFile(file);

        return channelDescriptionFile;
    }

}
