package com.levelup.core.domain.Article;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.PostCategory;
import com.levelup.core.domain.vote.Vote;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    private String title;
    private String writer;

    @Lob
    private String content;

    @Column(name = "vote_count")
    private Long voteCount;

    @Column(name = "views")
    private Long views;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ArticleCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category")
    private PostCategory postCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Vote> votes = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getArticles().add(this);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void modifyArticle(String title, String content, PostCategory category) {
        this.title = (title);
        this.content = (content);

        if (category != null) {
            this.postCategory = (category);
        }
    }
}
