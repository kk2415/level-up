package com.levelup.api.service.dto.channelPost;

import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.PostCategory;
import com.levelup.core.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChannelPostDto {

    private Long channelPostId;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private PostCategory postCategory;
    private ArticleType articleType;

    protected ChannelPostDto() {}

    public ChannelPostDto(
            Long channelPostId,
            Long memberId,
            String title,
            String writer,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long views,
            Long commentCount,
            PostCategory postCategory,
            ArticleType articleType)
    {
        this.channelPostId = channelPostId;
        this.memberId = memberId;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
        this.postCategory = postCategory;
        this.articleType = articleType;
    }

    public static ChannelPostDto from(ChannelPost channelPost) {
        return new ChannelPostDto(
            channelPost.getId(),
            channelPost.getMember().getId(),
            channelPost.getTitle(),
            channelPost.getMember().getNickname(),
            channelPost.getContent(),
            channelPost.getCreatedAt(),
            (long) channelPost.getArticleVotes().size(),
            channelPost.getViews(),
            (long) channelPost.getComments().size(),
            channelPost.getChannelPostCategory(),
            channelPost.getArticleType()
        );
    }

    public ChannelPost toEntity(Member member, Channel channel) {
        ChannelPost channelPost = ChannelPost.builder()
                .channelPostCategory(postCategory)
                .channel(channel)
                .build();

        channelPost.setMember(member);
        channelPost.setTitle(title);
        channelPost.setContent(content);
        channelPost.setViews(0L);
        channelPost.setArticleType(articleType);
        return channelPost;
    }
}
