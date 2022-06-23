package com.levelup.core.domain.post;

import com.levelup.core.domain.base.BaseEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Lob
    private String content;
    private String title;
    private String writer;

    private Long voteCount;
    private Long commentCount;
    private Long views;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category")
    private PostCategory postCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<File> files;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        if (member != null) {
            member.getPosts().remove(this);
        }

        this.member = member;
        member.getPosts().add(this);
    }

    public void setChannel(Channel channel) {
        if (channel != null) {
            channel.getPosts().remove(this);
        }

        this.channel = channel;
        channel.getPosts().add(this);
    }

    //==비즈니스 로직==//
    public void changePost(String title, String content, PostCategory category) {
        this.title = (title);
        this.content = (content);
        this.postCategory = (category);
    }

    public void addViews() {
        this.views += 1;
    }

    public void addVoteCount() {
        this.voteCount += 1;
    }

    public void addCommentCount() {
        this.commentCount += 1;
    }



}
