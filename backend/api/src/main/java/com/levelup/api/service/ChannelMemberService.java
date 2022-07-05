package com.levelup.api.service;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channelMember.ChannelMemberResponse;
import com.levelup.core.exception.DuplicateChannelMemberException;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.exception.NoPlaceChnnelException;
import com.levelup.core.repository.channel.ChannelMemberRepository;
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


    /**
     * 생성
     * */
    public void createChannelMember(Long channelId, Long memberId, Boolean isWaitingMember) {
        Channel channel = channelRepository.findById(channelId);
        Member findMember = memberRepository.findById(memberId);

        channelMemberRepository.findByChannelIdAndMemberId(channelId, memberId)
                .ifPresent(channelMemberList -> {
                    if (!channelMemberList.isEmpty()) {
                        throw new DuplicateChannelMemberException("채널에 이미 존재하는 멤버입니다.");
                    }

                    if (channel.getChannelMembers().size() >= channel.getLimitedMemberNumber() ) {
                        throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
                    }


                    ChannelMember channelMember = ChannelMember.createChannelMember(findMember, false, isWaitingMember);

                    channel.setChannelMember(channelMember);
                });
    }


    /**
     * 조회
     * */
    public Page<ChannelMemberResponse> getChannelMembers(Long channelId, Boolean isWaitingMember, Pageable pageable) {
        Page<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndIsWaitingMember(channelId, isWaitingMember, pageable);
        return channelMembers.map(ChannelMemberResponse::new);
    }


    /**
     * 수정
     * */
    public void approvalMember(Long channelMemberId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new MemberNotFoundException("채널 멤버를 찾을 수 없습니다."));

        channelMember.setIsWaitingMember(false);
    }


    /**
     * 삭제
     * */
    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void deleteChannelMember(Long channelMemberId, Long channelId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new MemberNotFoundException("채널 멤버를 찾을 수 없습니다."));

        Channel channel = channelRepository.findById(channelId);
        channel.removeMember(List.of(channelMember));

        channelMemberRepository.delete(channelMember);
    }

}
