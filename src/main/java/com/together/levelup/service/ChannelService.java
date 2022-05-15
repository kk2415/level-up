package com.together.levelup.service;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.repository.channel.ChannelMemberRepository;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
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
    private final ChannelMemberRepository channelMemberRepository;
    private final MemberRepository memberRepository;
//    private final ChannelMemberRepository channelMemberRepository;

    /**
     * 채널 추가
     * */
    @Transactional
    public Long create(Long memberId, String name, Long limitNumber, String descript, String thumbnailDescript, ChannelCategory category, UploadFile uploadFile) {
        Member member = memberRepository.findById(memberId);

        validationDuplicateChannel(name);
        Channel channel = Channel.createChannel(member, name, limitNumber, descript, thumbnailDescript, category, uploadFile);
        channelRepository.save(channel);
        return channel.getId();
    }

    @Transactional
    public Long create(String email, String name, Long limitNumber, String descript, String thumbnailDescript, ChannelCategory category, UploadFile uploadFile) {
        Member member = memberRepository.findByEmail(email);
        validationDuplicateChannel(name);

        Channel channel = Channel.createChannel(member, name, limitNumber, descript, thumbnailDescript, category, uploadFile);
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
            ChannelMember channelMember = ChannelMember.createChannelMember(findMember);
            channelMembers.add(channelMember);
        }

        channel.addMember(channelMembers);
    }

    @Transactional
    public void addMember(Long channelId, Long... memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            ChannelMember channelMember = ChannelMember.createChannelMember(findMember);
            channelMembers.add(channelMember);
        }

        channel.addMember(channelMembers);
    }

    @Transactional
    public void addWaitingMember(Long channelId, Long... memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            ChannelMember channelMember = ChannelMember.createChannelWaitingMember(findMember);
            channelMembers.add(channelMember);
        }

        channel.addMember(channelMembers);
    }

    /**
     * 채널 수정
     * */
    @Transactional
    public void update(Long channelId, String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        Channel channel = channelRepository.findById(channelId);
        channel.changeChannel(name, limitNumber, description, thumbnailDescription, thumbnailImage);
    }

    @Transactional
    public void update(Long channelId, String name, Long limitNumber, String description, String thumbnailDescription, ChannelCategory category, UploadFile thumbnailImage) {
        Channel channel = channelRepository.findById(channelId);
        channel.changeChannel(name, limitNumber, description, thumbnailDescription, category, thumbnailImage);
    }

    /**
     * 채널 삭제
     * */
    @Transactional
    public void deleteChannel(Long channelId) {
        channelRepository.delete(channelId);
    }

    @Transactional
    public void deleteChannelMember(Long channelId, Long memberId) {
//        ChannelMember channelMember = channelMemberRepository.findByChannelAndMember(channelId, memberId)

//        Channel findChannel = channelRepository.findById(channelId);
//        Member findMember = memberRepository.findById(memberId);

//        findChannel.getChannelMembers().remove(channelMember);
//        findMember.getChannelMembers().remove(channelMember);
//
//        channelMemberRepository.delete(channelMember.getId());
    }

    @Transactional
    public void deleteMember(Long channelId, String email) {
        Channel findChannel = findOne(channelId);
        Member findMember = memberRepository.findByEmail(email);

        List<ChannelMember> members = channelMemberRepository.findByChannelAndMember(channelId, findMember.getId());
        findChannel.removeMember(members);

        for (ChannelMember channelMember : members) {
            channelMemberRepository.delete(channelMember.getId());
        }
    }

    @Transactional
    public void deleteWaitingMember(Long channelId, String email) {
        Channel findChannel = findOne(channelId);
        Member findMember = memberRepository.findByEmail(email);

        List<ChannelMember> waitingMember = channelMemberRepository
                .findByChannelAndWaitingMember(channelId, findMember.getId());
        findChannel.removeWaitingMember(waitingMember);

        for (ChannelMember channelMember : waitingMember) {
            channelMemberRepository.delete(channelMember.getId());
        }
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

    public List<Channel> findByCategory(ChannelCategory category) {
        return channelRepository.findByCategory(category);
    }

}
