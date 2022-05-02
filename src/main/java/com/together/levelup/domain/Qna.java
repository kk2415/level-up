package com.together.levelup.domain;

import com.together.levelup.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Qna {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String writer;
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "vote_count")
    private Long voteCount;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "qna")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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
        qna.setDateCreated(LocalDateTime.now());
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

}
