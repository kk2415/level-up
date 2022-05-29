package com.levelup.core.domain.comment;

import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.domain.vote.Vote;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment")
    private List<Vote> votes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();


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

    public void addChildComment(Comment child) {
        this.child.add(child);
        child.setParent(this);
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
