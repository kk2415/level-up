package com.levelup.core.domain.member;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {

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
    private UploadFile profileImage;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

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

    @OneToMany(mappedBy = "waitingMember")
    private List<ChannelMember> channelWaitingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Channel> channels = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Qna> qna = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Vote> votes;

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void modifyProfileImage(UploadFile profileImage) {
        this.profileImage = profileImage;
    }

}
