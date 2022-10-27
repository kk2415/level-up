package com.levelup.channel;

import com.levelup.channel.domain.entity.ChannelArticleCategory;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.*;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

    protected Member createMember(Long id, String email, String nickname) {
        Member member = Member.of(
                id,
                email,
                "00000000",
                nickname,
                nickname,
                Gender.MALE,
                LocalDate.of(1997, 9, 27),
                "010-2354-9960",
                new ArrayList<>(),
                email);

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected Member createMember(String email, String nickname) {
        Member member = Member.of(
                null,
                email,
                "00000000",
                nickname,
                nickname,
                Gender.MALE,
                LocalDate.of(1997, 9, 27),
                "010-2354-9960",
                new ArrayList<>(),
                email);

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

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

    protected ChannelMember createChannelMember(Long memberId, String email, String nickname, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.of(
                null,
                memberId,
                email,
                nickname,
                isManager,
                isWaitingMember);
    }

    protected ChannelMember createChannelMember(Member member, boolean isManager) {
        return ChannelMember.of(
                null,
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                isManager,
                false);
    }

    protected ChannelMember createChannelMember(Long id, Member member, boolean isManager, boolean isWaitingMember) {
        return ChannelMember.of(
                id,
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                isManager,
                isWaitingMember);
    }

    protected ChannelMember createChannelMember(Member member, boolean isManager, boolean isWaitingMember) {
        return ChannelMember.of(
                null,
                member.getId(),
                member.getEmail(),
                member.getNickname(),
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
