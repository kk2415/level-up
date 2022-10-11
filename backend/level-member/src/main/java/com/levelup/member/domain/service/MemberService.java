package com.levelup.member.domain.service;

import com.levelup.common.domain.FileType;
import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.exception.FileNotFoundException;
import com.levelup.common.util.file.FileStore;
import com.levelup.common.util.file.UploadFile;
import com.levelup.event.events.*;
import com.levelup.member.domain.MemberPrincipal;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.service.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final FileStore fileStore;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberDto save(MemberDto dto) {
        validateDuplicationMember(dto.getEmail(), dto.getNickname());

        Member member = dto.toEntity();
        Role role = Role.of(RoleName.ANONYMOUS, member);

        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        member.addRole(role);
        memberRepository.save(member);

        EventPublisher.raise(MemberCreatedEvent.of(member.getId(), member.getEmail(), member.getNickname()));

        return MemberDto.from(member);
    }

    private void validateDuplicationMember(String email, String nickname) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.EMAIL_DUPLICATION);});

        memberRepository.findByNickname(nickname)
                .ifPresent(user -> {throw new EntityDuplicationException(ErrorCode.NICKNAME_DUPLICATION);});
    }

    public UploadFile createProfileImage(MultipartFile file) throws IOException {
        return fileStore.storeFile(FileType.MEMBER, file);
    }


    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "member", key = "#memberId")
    public MemberDto get(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberDto.from(member);
    }



    @CacheEvict(cacheNames = "member", key = "#memberId")
    public void update(UpdateMemberDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(dto.getNickname(), dto.getProfileImage());

        EventPublisher.raise(MemberUpdatedEvent.of(member.getId(), member.getEmail(), member.getNickname()));
    }

    public void updatePassword(UpdateMemberDto dto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @CacheEvict(cacheNames = "member", key = "#memberId")
    public UploadFile updateProfileImage(MultipartFile file, Long memberId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new FileNotFoundException(ErrorCode.IMAGE_NOT_FOUND);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getProfileImage() != null) {
            fileStore.deleteFile(member.getProfileImage().getStoreFileName());
        }

        UploadFile uploadFile = fileStore.storeFile(FileType.MEMBER, file);
        member.modifyProfileImage(uploadFile);

        EventPublisher.raise(MemberProfileImageUpdatedEvent.of(
                member.getId(),
                member.getProfileImage().getUploadFileName(),
                member.getProfileImage().getStoreFileName()));

        return uploadFile;
    }



    @CacheEvict(cacheNames = "member", key = "#memberId")
    public void delete(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        EventPublisher.raise(MemberDeletedEvent.of(member.getId(), member.getEmail(), member.getNickname()));

        memberRepository.delete(member);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("start loadUserByUsername");

       final Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Collection<GrantedAuthority> authorities = new ArrayList<>(10);
        List<Role> roles = member.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getName())));
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(RoleName.ADMIN.getName()));

        return new MemberPrincipal(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                isAdmin,
                true,
                true,
                true,
                true,
                authorities);
    }
}
