package com.levelup.core.domain.Article;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long articleId;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "vote_count")
    private Long voteCount;

    @Column(name = "comment_count")
    private Long commentCount;

    @Column(name = "views")
    private Long views;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private ArticleType articleType;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Vote> votes = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getArticles().add(this);
    }

    public static Article createArticle(Member member, String title, String content, ArticleType articleType) {
        Article article = new Article();

        article.setMember(member);
        article.setTitle(title);
        article.setWriter(member.getName());
        article.setContent(content);
        article.setVoteCount(0L);
        article.setCommentCount(0L);
        article.setViews(0L);
        article.setArticleType(articleType);

        return article;
    }

    public void addViews() {
        this.views += 1;
    }

    public void addVoteCount() {
        this.voteCount += 1;
    }

    public void addCommentCount() {
        this.commentCount += 1;
    }

    public void modifyArticle(String title, String content) {
        this.title = (title);
        this.content = (content);
    }

}
