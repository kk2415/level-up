package com.levelup.article.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.member.domain.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_comment")
@Entity
public class ArticleComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "article_comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ArticleComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<ArticleComment> child;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentVote> commentVotes;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setArticle(Article article) {
            this.article = article;
            article.getComments().add(this);
    }

    public void addChildComment(ArticleComment child) {
        this.child.add(child);
        child.parent = (this);
    }

    public void changeComment(String content) {
        this.content = (content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArticleComment)) return false;
        return id != null && id.equals(((ArticleComment) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
