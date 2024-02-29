package com.levelup.member.domain.handler;

import com.levelup.common.exception.ErrorCode;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class MemberHandler {

    private final MemberRepository memberRepository;

    @EventListener(ChannelCreatedEvent.class)
    public void handleChannelCreatedEvent(ChannelCreatedEvent event) {
        MemberEntity member = memberRepository.findById(event.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        member.addRole(Role.of(RoleName.CHANNEL_MANAGER, member));
    }
}
