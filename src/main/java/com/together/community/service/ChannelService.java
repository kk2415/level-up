package com.together.community.service;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.member.Member;
import com.together.community.repository.channel.ChannelRepository;
import com.together.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    /**
     * 채널 추가
     * */
    public Long create(Long memberId, String name, Long limitNumber, String descript) {
        Member findMember = memberRepository.findById(memberId);
        Channel channel = Channel.createChannel(findMember, name, limitNumber, descript);
        validationDuplicateChannel(channel);
        channelRepository.save(channel);
        return channel.getId();
    }

    private void validationDuplicateChannel(Channel channel) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        List<Channel> findChannels = channelRepository.findByName(channel.getName());

        if (findChannels.size() > 0) {
            throw new IllegalStateException("이미 존재하는 채널입니다.");
        }
    }


    /**
     * 채널 수정
     * */
    public void update(String name, Long limitNumber, String descript) {

    }

    /**
     * 채널 삭제
     * */

    /**
     * 채널 조회
     * */
}
