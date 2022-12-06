package com.levelup.article.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "article_vote")
@Entity
public class ArticleVote extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "article_vote_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    protected ArticleVote() {}

    public void setArticle(Article article) {
        if (article != null) {
            article.getVotes().remove(this);
        }

        this.article = article;
        article.getVotes().add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArticleVote)) return false;
        return id != null && id.equals(((ArticleVote) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
