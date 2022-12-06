package com.levelup.member.domain.service;

import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.event.events.*;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.member.domain.service.dto.UpdatePasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public CreateMemberDto save(MemberDto dto) throws IOException {
        validateDuplicationMember(dto.getEmail(), dto.getNickname());

        Member member = dto.toEntity();
        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        member.addRole(Role.of(RoleName.ANONYMOUS, member));
        memberRepository.save(member);

        EventPublisher.raise(MemberCreatedEvent.of(member.getId(), member.getEmail(), member.getNickname()));

        return CreateMemberDto.from(member);
    }

    private void validateDuplicationMember(String email, String nickname) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.EMAIL_DUPLICATION);});

        memberRepository.findByNickname(nickname)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.NICKNAME_DUPLICATION);});
    }


    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "MEMBER", key = "#memberId")
    public MemberDto get(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberDto.from(member);
    }


    @CacheEvict(cacheNames = "MEMBER", key = "#memberId")
    public void update(UpdateMemberDto dto, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(dto.getNickname());

        EventPublisher.raise(MemberUpdatedEvent.of(
                member.getId(),
                member.getEmail(),
                member.getNickname()));
    }

    public void updatePassword(UpdatePasswordDto dto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }


    @CacheEvict(cacheNames = "MEMBER", key = "#memberId")
    public void delete(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        EventPublisher.raise(MemberDeletedEvent.of(member.getId(), member.getEmail(), member.getNickname()));

        memberRepository.delete(member);
    }
}
