package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.exception.ChannelMemberDuplicationException;
import com.levelup.channel.exception.ChannelNotFountExcpetion;
import com.levelup.channel.exception.NoPlaceChannelException;
import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.exception.MemberNotFoundException;
import com.levelup.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelMemberService {

    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public ChannelMemberDto create(Long channelId, Long memberId, Boolean isWaitingMember) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        final Optional<ChannelMember> channelMembers
                = channelMemberRepository.findByChannelIdAndMemberId(channelId, memberId);

        validate(channel, channelMembers);

        ChannelMember channelMember = ChannelMember.of(member, false, isWaitingMember);
        channel.addChannelMember(channelMember);

        return ChannelMemberDto.from(channelMember);
    }

    private void validate(Channel channel, Optional<ChannelMember> channelMember) {
        if (channelMember.isPresent()) {
            throw new ChannelMemberDuplicationException("채널에 이미 존재하는 멤버입니다.");
        }

        if (channel.getChannelMembers().size() >= channel.getMemberMaxNumber() ) {
            throw new NoPlaceChannelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }
    }


    public Page<ChannelMemberDto> getChannelMembers(Long channelId, Boolean isWaitingMember, Pageable pageable) {
        final Page<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndIsWaitingMember(channelId, isWaitingMember, pageable);

        return channelMembers.map(ChannelMemberDto::from);
    }


    public void approvalMember(Long channelMemberId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new MemberNotFoundException("채널 멤버를 찾을 수 없습니다."));

        channelMember.setIsWaitingMember(false);
    }


    public void delete(Long channelMemberId, Long channelId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new MemberNotFoundException("채널 멤버를 찾을 수 없습니다."));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));
        channel.removeMembers(List.of(channelMember));

        channelMemberRepository.delete(channelMember);
    }
}
