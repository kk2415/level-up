package com.levelup;

import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.ChannelRequest;
import com.levelup.core.dto.member.CreateMemberRequest;

public class TestSupporter {

    protected Member createMember(String email, String name) {
        CreateMemberRequest memberRequest = CreateMemberRequest.of(email, "00000000", name,
                "testNickname", Gender.MALE, "19970927", "010-2354-9960", new UploadFile("", ""));
        EmailAuth authEmail = EmailAuth.createAuthEmail(memberRequest.getEmail());

        Member member = memberRequest.toEntity();
        member.setEmailAuth(authEmail);

        return member;
    }

    protected Channel createChannel(Member manager, String channelName) {
        ChannelRequest channelRequest = ChannelRequest.of(manager.getEmail(), channelName, 5L, "testChannel",
                ChannelCategory.STUDY, "test", new UploadFile("", ""), null);

        Channel channel = channelRequest.toEntity(manager.getNickname());
        ChannelMember channelMember = ChannelMember.createChannelMember(manager, true, false);
        channel.setChannelMember(channelMember);

        return channel;
    }
}
