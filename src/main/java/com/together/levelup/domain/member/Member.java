package com.together.levelup.domain.member;

import com.together.levelup.domain.vote.Vote;
import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.qna.Qna;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.notice.Notice;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelMember;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email")
    private String email;

    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthday;
    private String phone;

    @Embedded
    private UploadFile uploadFile;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    /*
    * cascade = CascadeType.ALL : member를 persist()하면 member랑 맵핑된 post도 같이 영속화된다.
    * 하지만 postRepository를 따로 만들어서 em.persist를 할꺼라서 여기서 post를 persist 안해도 된다.
    * */
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChannelMember> channelMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Channel> channels = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Qna> qna = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Vote> votes;

    //==생성 메서드==//
    public static Member createMember(String email, String password, String name,
                                      Gender gender, String birthday, String phone, UploadFile uploadFile) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setGender(gender);
        member.setBirthday(birthday);
        member.setPhone(phone);
        member.setAuthority(Authority.NORMAL);
        member.setDateCreated(LocalDateTime.now());
        member.setUploadFile(uploadFile);
        return member;
    }

    public static Member createMember(Authority authority, String email, String password, String name,
                                      Gender gender, String birthday, String phone, UploadFile uploadFile) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setGender(gender);
        member.setBirthday(birthday);
        member.setPhone(phone);
        member.setAuthority(authority);
        member.setDateCreated(LocalDateTime.now());
        member.setUploadFile(uploadFile);
        return member;
    }
}
