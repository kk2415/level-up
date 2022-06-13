package com.levelup.core.domain.file;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
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
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_notice_id")
    private ChannelNotice channelNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    //==연관관계 메서드==//
    public void setPost(Post post) {
        this.post = post;
        post.getFiles().add(this);
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
        notice.getFiles().add(this);
    }

    public void setChannelNotice(ChannelNotice channelNotice) {
        this.channelNotice = channelNotice;
        channelNotice.getFiles().add(this);
    }

    public void setQna(Qna qna) {
        this.qna = qna;
        qna.getFiles().add(this);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getFiles().add(this);
    }

    //==생성 메서드==//
    public static File createFile(Object object, UploadFile uploadFile) {
        File file = new File();

        file.setUploadFile(uploadFile);

        //나중에 리팩토링 필요...
        if (object instanceof Post) {
            file.setPost((Post)object);
        }
        else if (object instanceof Notice) {
            file.setNotice((Notice)object);
        }
        else if (object instanceof ChannelNotice) {
            file.setChannelNotice((ChannelNotice)object);
        }
        else if (object instanceof Qna) {
            file.setQna((Qna)object);
        }
        else if (object instanceof Channel) {
            file.setChannel((Channel)object);
        }
        else {
            throw new IllegalArgumentException("file 생성자에 정확한 객체를 넣어주세요");
        }

        return file;
    }


}
