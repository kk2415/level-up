package com.levelup.core.domain.qna;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String writer;
    private String content;

    @Column(name = "vote_count")
    private Long voteCount;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "qna")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "qna")
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "qna")
    private List<Vote> votes = new ArrayList<>();

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getQna().add(this);
    }

    //==생성 메서드==//
    public static Qna createQna(Member member, String title, String writer, String content) {
        Qna qna = new Qna();

        qna.setMember(member);
        qna.setTitle(title);
        qna.setWriter(writer);
        qna.setContent(content);
        qna.setVoteCount(0L);
        qna.setViews(0L);

        return qna;
    }


    //==비즈니스 로직==//
    public void changeQna(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public void addViews() {
        this.setViews(this.views + 1);
    }

    public void addVoteCount() {
        this.setVoteCount(this.voteCount + 1);
    }

}
