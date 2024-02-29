package com.levelup.member.domain.service;

import com.levelup.common.domain.repository.SkillRepository;
import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.event.events.*;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.entity.MemberSkillEntity;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.domain.Member;
import com.levelup.member.domain.service.dto.UpdatePasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final SkillRepository skillRepository;
    private final MemberRepository memberRepository;

    public CreateMemberDto save(Member member) throws IOException {
        validateDuplicationMember(member.getEmail(), member.getNickname());

        MemberEntity memberEntity = member.toEntity();
        List<MemberSkillEntity> memberSkills = skillRepository.findAllById(member.getSkillIds())
                .stream().map(skill -> MemberSkillEntity.of(memberEntity, skill))
                .collect(Collectors.toUnmodifiableList());

        memberEntity.updatePassword(passwordEncoder.encode(memberEntity.getPassword()));
        memberEntity.addRole(Role.of(RoleName.ANONYMOUS, memberEntity));
        memberEntity.addMemberSkills(memberSkills);
        memberRepository.save(memberEntity);

        EventPublisher.raise(MemberCreatedEvent.of(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getNickname()));

        return CreateMemberDto.from(memberEntity);
    }

    private void validateDuplicationMember(String email, String nickname) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.EMAIL_DUPLICATION);});

        memberRepository.findByNickname(nickname)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.NICKNAME_DUPLICATION);});
    }


    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "MEMBER", key = "#memberId")
    public Member get(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return Member.from(member);
    }


    @CacheEvict(cacheNames = "MEMBER", key = "#memberId")
    public void update(UpdateMemberDto dto, Long memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(dto.getNickname());

        EventPublisher.raise(MemberUpdatedEvent.of(
                member.getId(),
                member.getEmail(),
                member.getNickname()));
    }

    public void updatePassword(UpdatePasswordDto dto, String email) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }


    @CacheEvict(cacheNames = "MEMBER", key = "#memberId")
    public void delete(Long memberId) {
        final MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        EventPublisher.raise(MemberDeletedEvent.of(member.getId(), member.getEmail(), member.getNickname()));

        memberRepository.delete(member);
    }
}
