package com.levelup.core.domain.file;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Table(name = "file")
@Entity
public class File extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getFiles().add(this);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File)) return false;
        return id != null && id.equals(((File) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
