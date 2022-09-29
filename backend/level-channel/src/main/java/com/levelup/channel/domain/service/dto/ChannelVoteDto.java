package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.VoteType;
import com.levelup.channel.domain.entity.*;
import lombok.Getter;

@Getter
public class ChannelVoteDto {

    private Long memberId;
    private Long channelId;
    private Long targetId;
    private VoteType voteType;
    private boolean isSuccessful;

    protected ChannelVoteDto() {}

    private ChannelVoteDto(Long memberId, Long channelId, Long targetId, VoteType voteType, boolean isSuccessful) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.targetId = targetId;
        this.voteType = voteType;
        this.isSuccessful = isSuccessful;
    }

    public static ChannelVoteDto of(Long memberId, Long channelId, Long targetId, VoteType voteType, boolean isSuccessful) {
        return new ChannelVoteDto(memberId, channelId, targetId, voteType, isSuccessful);
    }

    public static ChannelVoteDto of(ChannelArticleVote vote, boolean isSuccessful) {
        return new ChannelVoteDto(
                vote.getChannelMember().getId(),
                vote.getChannelMember().getChannel().getId(),
                vote.getArticle().getId(),
                VoteType.ARTICLE, isSuccessful);
    }

    public static ChannelVoteDto of(ChannelCommentVote vote, boolean isSuccessful) {
        return new ChannelVoteDto(
                vote.getChannelMember().getId(),
                vote.getChannelMember().getChannel().getId(),
                vote.getComment().getId(),
                VoteType.COMMENT, isSuccessful);
    }

    public ChannelArticleVote toEntity(ChannelMember member, ChannelArticle article) {
        ChannelArticleVote articleVote = ChannelArticleVote.builder()
                .channelMember(member)
                .build();

        articleVote.setArticle(article);
        return articleVote;
    }

    public ChannelCommentVote toEntity(ChannelMember member, ChannelComment comment) {
        ChannelCommentVote commentVote = ChannelCommentVote.builder()
                .channelMember(member)
                .comment(comment)
                .build();

        commentVote.setComment(comment);
        return commentVote;
    }
}
