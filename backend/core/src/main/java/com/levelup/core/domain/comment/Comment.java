package com.levelup.core.domain.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.domain.vote.Vote;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String writer;
    private String content;

    private Long voteCount;
    private Long replyCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_notice_id")
    private ChannelNotice channelNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Comment> child;


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

    public void setChannelNotice(ChannelNotice channelNotice) {
        this.channelNotice = channelNotice;
    }

    public void setQna(Qna qna) {
        this.qna = qna;
    }

    public void setArticle(Article article) {
            this.article = article;
            article.getComments().add(this);
    }

    public void addChildComment(Comment child) {
        this.child.add(child);
        child.parent = (this);

        this.addReplyCount();
    }


    //==비즈니스 로직==//
    public void changeComment(String content) {
        this.content = (content);
    }

    public void addVoteCount() {
        this.voteCount++;
    }

    public void addReplyCount() {
        this.replyCount++;
    }

    public void removeReplyCount() {
        this.replyCount++;
    }

}
