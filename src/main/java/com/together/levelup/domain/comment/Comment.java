package com.together.levelup.domain.comment;

import com.together.levelup.domain.qna.Qna;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.domain.notice.Notice;
import com.together.levelup.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String writer;
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "vote_count")
    private Long voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
        notice.getComments().add(this);
    }

    public void setPost(ChannelNotice channelNotice) {
        this.channelNotice = channelNotice;
        channelNotice.getComments().add(this);
    }

    public void setPost(Qna qna) {
        this.qna = qna;
        qna.getComments().add(this);
    }

    //==생성 메서드==//
    public static Comment createComment(Member member, Object article, String content) {
        Comment comment = new Comment();

        comment.setMember(member);
        comment.setWriter(member.getName());
        comment.setDateCreated(LocalDateTime.now());
        comment.setContent(content);
        comment.setVoteCount(0L);

        setArticle(article, comment);

        return comment;
    }

    private static void setArticle(Object object, Comment comment) {
        if (object instanceof Post) {
            comment.setPost((Post) object);
        }
        else if (object instanceof Notice) {
            comment.setNotice((Notice) object);
        }
        else if (object instanceof ChannelNotice) {
            comment.setChannelNotice((ChannelNotice) object);
        }
        else if (object instanceof Qna) {
            comment.setQna((Qna) object);
        }
    }

    //==비즈니스 로직==//
    public void changeComment(String content) {
        this.setContent(content);
    }

    public void addVoteCount() {
        this.setVoteCount(this.voteCount + 1);
    }

}
