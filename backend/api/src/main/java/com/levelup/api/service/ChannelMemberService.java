package com.levelup.api.service;

import com.levelup.api.dto.service.chanelMember.ChannelMemberDto;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.channel.ChannelNotFountExcpetion;
import com.levelup.core.exception.channelMember.DuplicateChannelMemberException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.channel.NoPlaceChannelException;
import com.levelup.core.repository.channelMember.ChannelMemberRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        final List<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndMemberId(channelId, memberId);

        validate(channel, channelMembers);

        ChannelMember channelMember = ChannelMember.of(member, false, isWaitingMember);
        channel.setChannelMember(channelMember);

        return ChannelMemberDto.from(channelMember);
    }

    private void validate(Channel channel, List<ChannelMember> channelMembers) {
        if (!channelMembers.isEmpty()) {
            throw new DuplicateChannelMemberException("채널에 이미 존재하는 멤버입니다.");
        }

        if (channel.getChannelMembers().size() >= channel.getMemberMaxNumber() ) {
            throw new NoPlaceChannelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }
    }




    public Page<ChannelMemberDto> getByPaging(Long channelId, Boolean isWaitingMember, Pageable pageable) {
        final Page<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndIsWaitingMember(channelId, isWaitingMember, pageable);

        return channelMembers.map(ChannelMemberDto::from);
    }



    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
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
