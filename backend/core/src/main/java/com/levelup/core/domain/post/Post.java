package com.levelup.core.domain.post;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "vote_count")
    private Long voteCount;

    @Column(name = "views")
    private Long views;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category")
    private PostCategory postCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Vote> votes = new ArrayList<>();

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

    //==생성 메서드==//
    public static Post createPost(Member member, String title, String content) {
        Post post = new Post();

        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);
        post.setDateCreated(LocalDateTime.now());
        post.setVoteCount(0L);
        post.setWriter(member.getName());
        post.setViews(0L);

        return post;
    }

    public static Post createPost(Member member, Channel channel, String title, String content) {
        return createPost(member, channel, title, content,PostCategory.INFO);
    }

    public static Post createPost(Member member, Channel channel, String title, String content, PostCategory postCategory) {
        Post post = new Post();

        post.setMember(member);
        post.setChannel(channel);
        post.setTitle(title);
        post.setContent(content);
        post.setDateCreated(LocalDateTime.now());
        post.setVoteCount(0L);
        post.setWriter(member.getEmail());
        post.setPostCategory(postCategory);
        post.setViews(0L);

        return post;
    }

    //==비즈니스 로직==//
    public void changePost(String title, String content, PostCategory category) {
        this.setTitle(title);
        this.setContent(content);
        this.setPostCategory(category);
    }

    public void addViews() {
        this.setViews(this.views + 1);
    }

    public void addVoteCount() {
        this.setVoteCount(this.voteCount + 1);
    }

}
