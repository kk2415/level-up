package com.together.levelup.domain.vote;

import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.qna.Qna;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "qna_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Qna qna;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getVotes().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getVotes().add(this);
    }

    public void setQna(Qna qna) {
        this.qna = qna;
        qna.getVotes().add(this);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getVotes().add(this);
    }

    //==생성 메서드==//
    public static Vote createVote(Member member, Object object) {
        Vote vote = new Vote();
        vote.setMember(member);
        setArticle(object, vote);
        return vote;
    }

    private static void setArticle(Object object, Vote vote) {
        if (object instanceof Post) {
            vote.setPost((Post) object);
        }
        else if (object instanceof Qna) {
            vote.setQna((Qna) object);
        }
        else if (object instanceof Comment) {
            vote.setComment((Comment) object);
        }
    }

}
