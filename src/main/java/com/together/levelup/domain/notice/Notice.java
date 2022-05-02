package com.together.levelup.domain.notice;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.file.File;
import com.together.levelup.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "notice")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "notice")
    private List<File> files = new ArrayList<>();

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getNotices().add(this);
    }

    //==생성 메서드==//
    public static Notice createNotice(Member member, String title, String writer, String content) {
        Notice notice = new Notice();

        notice.setMember(member);
        notice.setTitle(title);
        notice.setWriter(writer);
        notice.setContent(content);
        notice.setDateCreated(LocalDateTime.now());
        notice.setViews(0L);

        return notice;
    }

    //==비즈니스 로직==//
    public void change(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    public void addViews() {
        this.setViews(this.views + 1);
    }

}
