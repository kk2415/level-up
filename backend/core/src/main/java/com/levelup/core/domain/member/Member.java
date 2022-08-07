package com.levelup.core.domain.member;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    String name;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String phone;

    @Embedded
    private UploadFile profileImage;

    @OneToOne(mappedBy = "member", fetch = LAZY, cascade = CascadeType.ALL)
    private EmailAuth emailAuth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Role> roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Article> articles;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ChannelMember> channelMembers;

    public void setEmailAuth(EmailAuth emailAuth) {
        this.emailAuth = emailAuth;
        emailAuth.setMember(this);
    }

    public void addRole(Role role) {
        role.setMember(this);
        this.roles.add(role);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void modifyProfileImage(UploadFile profileImage) {
        this.profileImage = profileImage;
    }

    public void modifyMember(String nickname, UploadFile profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
