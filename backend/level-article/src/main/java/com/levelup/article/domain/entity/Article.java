package com.levelup.article.domain.entity;

import com.levelup.article.domain.ArticleType;
import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.member.domain.entity.Member;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<ArticleComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<ArticleVote> votes = new ArrayList<>();

    public Article() {}

    public void setMember(Member member) {
        this.member = member;
    }

    public void addViews() {
        this.views++;
    }

    public void update(String title, String content) {
        this.title = (title);
        this.content = (content);
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
