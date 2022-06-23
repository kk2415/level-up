package com.levelup.core.domain.vote;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;


    //==연관관계 메서드==//
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getVotes().remove(this);
        }

        this.member = member;
        member.getVotes().add(this);
    }

    public void setArticle(Article article) {
        if (this.article != null) {
            this.article.getVotes().remove(this);
        }

        this.article = article;
        article.getVotes().add(this);
    }

    public void setComment(Comment comment) {
        if (this.comment != null) {
            this.comment.getVotes().remove(this);
        }

        this.comment = comment;
        comment.getVotes().add(this);
    }


    //==생성 메서드==//
    public static Vote createVote(Member member) {
        return Vote.builder()
                .member(member)
                .build();
    }

}
