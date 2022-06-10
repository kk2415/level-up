package com.levelup.core.domain.notice;

import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getNotices().add(this);
    }

    //==생성 메서드==//
    public static Notice createNotice(Member member, String title, String writer, String content) {
        return Notice.builder()
                .member(member)
                .title(title)
                .writer(writer)
                .content(content)
                .dateCreated(LocalDateTime.now())
                .views(0L)
                .build();
    }

    //==비즈니스 로직==//
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addViews() {
        this.views = this.views + 1;
    }

}
