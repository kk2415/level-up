package com.levelup.channel;

import com.levelup.channel.domain.entity.ChannelArticleCategory;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.*;

import java.time.LocalDate;

public class TestSupporter {

    protected Channel createChannel(Long id, ChannelMember manager, String channelName, ChannelCategory category) {
        Channel channel = Channel.of(
                id,
                "test",
                channelName,
                10L,
                category,
                LocalDate.of(1997, 9, 27),
                LocalDate.of(1997, 9, 27)
        );

        channel.addChannelMember(manager);
        return channel;
    }

    protected Channel createChannel(ChannelMember manager, String channelName, ChannelCategory category) {
        Channel channel = Channel.of(
                null,
                "test",
                channelName,
                10L,
                category,
                LocalDate.of(1997, 9, 27),
                LocalDate.of(1997, 9, 27)
            );

        channel.addChannelMember(manager);
        return channel;
    }

    protected ChannelMember createChannelMember(Long id, Long memberId, String email, String nickname, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.of(
                id,
                memberId,
                email,
                nickname,
                isManager,
                isWaitingMember);
    }

    protected ChannelMember createChannelMember(Long memberId, String email, String nickname, boolean isManager, boolean isWaitingMember) {
        return ChannelMember.of(
                null,
                memberId,
                email,
                nickname,
                isManager,
                isWaitingMember);
    }

    protected ChannelArticle createChannelArticle(Long id, ChannelMember channelMember, Channel channel, String title) {
        return ChannelArticle.of(id, ChannelArticleCategory.INFO, "test", title, channel, channelMember);
    }

    protected ChannelArticle createChannelArticle(ChannelMember channelMember, Channel channel, String title) {
        return ChannelArticle.of(null, ChannelArticleCategory.INFO, "test", title, channel, channelMember);
    }

    protected ChannelComment createChannelComment(Long id, ChannelMember channelMember, ChannelArticle article) {
        return ChannelComment.of(id, "content", channelMember, article);
    }

    protected ChannelComment createChannelComment(ChannelMember channelMember, ChannelArticle article) {
        return ChannelComment.of(null, "content", channelMember, article);
    }

    protected ChannelComment createChannelReplyComment(
            ChannelMember channelMember,
            ChannelArticle article,
            ChannelComment parent)
    {
        ChannelComment comment = ChannelComment.of(null, "content", channelMember, article);

        parent.addReply(comment);
        return comment;
    }

    protected ChannelArticleVote createChannelArticleVote(ChannelMember channelMember, ChannelArticle article) {
        return ChannelArticleVote.of(channelMember, article);
    }

    protected ChannelCommentVote createChannelCommentVote(ChannelMember channelMember, ChannelComment comment) {
        return ChannelCommentVote.of(channelMember, comment);
    }
}
