package com.levelup.core.domain.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.CommentVote;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Comment> child;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentVote> commentVotes;

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

    }
    public void changeComment(String content) {
        this.content = (content);
    }
}
