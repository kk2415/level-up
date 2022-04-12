package com.together.community.service;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.channel.ChannelMember;
import com.together.community.domain.member.Member;
import com.together.community.repository.channel.ChannelRepository;
import com.together.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    /**
     * 채널 추가
     * */
    @Transactional
    public Long create(Long memberId, String name, Long limitNumber, String descript) {
        Member findMember = memberRepository.findById(memberId);
        validationDuplicateChannel(name);
        Channel channel = Channel.createChannel(findMember, name, limitNumber, descript);
        channelRepository.save(channel);
        return channel.getId();
    }

    private void validationDuplicateChannel(String name) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        List<Channel> findChannels = channelRepository.findByName(name);

        if (findChannels.size() > 0) {
            throw new IllegalStateException("이미 존재하는 채널입니다.");
        }
    }

    /**
     * 채널에 멤버 추가
     * */
    @Transactional
    public void addMember(Long channelId, List<Long> memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            channelMembers.add(ChannelMember.createChannelMember(findMember));
        }

        channel.addMember(channelMembers);
    }

    public void addMember(Long channelId, Long... memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            channelMembers.add(ChannelMember.createChannelMember(findMember));
        }

        channel.addMember(channelMembers);
    }

    /**
     * 채널 수정
     * */
    @Transactional
    public void update(Long channelId, String name, Long limitNumber, String descript) {
        Channel channel = channelRepository.findById(channelId);
        channel.changeChannel(name, limitNumber, descript);
    }

    /**
     * 채널 삭제
     * */
    @Transactional
    public void deleteChannel(Long channelId) {
        channelRepository.delete(channelId);
    }

    /**
     * 채널 조회
     * */
    public Channel findOne(Long channelId) {
        return channelRepository.findById(channelId);
    }

    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    public List<Channel> findAll(int start, int end) {
        return channelRepository.findAll(start, end);
    }

    public List<Channel> findByMemberId(Long memberId) {
        return channelRepository.findByMemberId(memberId);
    }

}
