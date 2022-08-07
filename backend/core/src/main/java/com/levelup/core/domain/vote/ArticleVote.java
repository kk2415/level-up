package com.levelup.core.domain.vote;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "article_vote")
@Entity
public class ArticleVote extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "article_vote_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    protected ArticleVote() {}

    public void setArticle(Article article) {
        if (article != null) {
            article.getArticleVotes().remove(this);
        }

        this.article = article;
        article.getArticleVotes().add(this);
    }
}
