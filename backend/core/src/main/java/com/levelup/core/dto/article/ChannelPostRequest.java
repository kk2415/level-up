package com.levelup.core.dto.article;

import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelPostRequest {

    private String title;
    private String content;
    private ArticleType articleType;
    private PostCategory postCategory;

    public ChannelPost toEntity(Member member, Channel channel) {
        ChannelPost channelPost = ChannelPost.builder()
                .postCategory(postCategory)
                .channel(channel)
                .build();

        channelPost.setMember(member);
        channelPost.setTitle(title);
        channelPost.setWriter(member.getName());
        channelPost.setContent(content);
        channelPost.setViews(0L);
        channelPost.setVoteCount(0L);
        channelPost.setCommentCount(0L);
        channelPost.setArticleType(articleType);

        return channelPost;
    }

}
