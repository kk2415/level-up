package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.ChannelInfo;
import com.levelup.core.dto.member.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ManagerResponse {

    private ChannelInfo channelInfo;
    private List<MemberResponse> waitingMembers = new ArrayList<>();
    private List<MemberResponse> members = new ArrayList<>();
    private List<ManagerPostResponse> posts = new ArrayList<>();

}
