package com.levelup.core.domain.Article;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.ArticleVote;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@DiscriminatorValue("article")
@Table(name = "article")
@Entity
public class Article extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Lob @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long views;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType articleType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<ArticleVote> articleVotes = new ArrayList<>();

    public Article() {}

    public static Article of(Member member, String title, String content, ArticleType articleType) {
        Article article = new Article();

        article.setMember(member);
        article.setTitle(title);
        article.setContent(content);
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getArticles().add(this);
    }

    public void addViews() {
        this.views++;
    }

    public void modifyArticle(String title, String content) {
        this.title = (title);
        this.content = (content);
    }
}
