package com.levelup.article.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleVote> votes = new ArrayList<>();

    public Article() {}

    public void addViews() {
        this.views++;
    }

    public void update(String title, String content) {
        this.title = (title);
        this.content = (content);
    }

    public boolean isWriter(Long memberId) {
        return this.writer.getMemberId().equals(memberId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Article)) return false;
        return id != null && id.equals(((Article) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
