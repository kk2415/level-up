package com.levelup.core.domain.Article;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import lombok.*;

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
    private Long articleId;

    @Lob
    private String content;
    private String title;
    private String writer;

    private Long voteCount;
    private Long commentCount;
    private Long views;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Vote> votes = new ArrayList<>();


    /**
     * 생성 메서드
     * */
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


    /**
     * 연관관계 매핑
     * */
    public void setMember(Member member) {
        this.member = member;
        member.getArticles().add(this);
    }

    public void addVote(Vote vote) {
        vote.setArticle(this);
        addVoteCount();
    }


    /**
     * 비즈니스 로직
     * */
    public void addViews() {
        this.views++;
    }

    public void addVoteCount() {
        this.voteCount++;
    }

    public void removeVoteCount() {
        this.voteCount--;
    }

    public void addCommentCount() {
        this.commentCount++;
    }

    public void removeCommentCount() {
        this.commentCount--;
    }

    public void modifyArticle(String title, String content) {
        this.title = (title);
        this.content = (content);
    }

}
