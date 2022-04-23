package com.together.levelup.domain.post;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.member.Member;
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
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "vote_count")
    private Long voteCount;

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setChannel(Channel channel) {
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
        post.setWriter(member.getName());
        post.setPostCategory(postCategory);

        return post;
    }

    //==비즈니스 로직==//
    public void changePost(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }
}
