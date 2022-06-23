package com.levelup.core.domain.file;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    //==연관관계 메서드==//
    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getFiles().add(this);
    }

    //==생성 메서드==//
    public static File createFile(Object object, UploadFile uploadFile) {
        File file = new File();

        file.setUploadFile(uploadFile);

        if (object instanceof Channel) {
            file.setChannel((Channel)object);
        }
        else {
            throw new IllegalArgumentException("file 생성자에 정확한 객체를 넣어주세요");
        }

        return file;
    }


}
