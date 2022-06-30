package com.levelup.core.domain.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.member.Member;
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
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Comment> child;


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
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

    public void addVote() {
        addVoteCount();
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
