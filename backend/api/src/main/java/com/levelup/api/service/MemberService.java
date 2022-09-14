package com.levelup.api.service;

import com.levelup.api.dto.service.member.MemberDto;
import com.levelup.api.dto.service.member.UpdateMemberDto;
import com.levelup.api.dto.response.member.MemberResponse;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.api.util.S3FileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.exception.*;
import com.levelup.core.exception.member.DuplicateEmailException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final S3FileStore fileStore;

    public MemberDto save(MemberDto dto) {
        validateDuplicationMember(dto.getEmail(), dto.getNickname());

        Member member = dto.toEntity();
        Role role = Role.of(RoleName.ANONYMOUS, member);

        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        member.addRole(role);
        memberRepository.save(member);

        return MemberDto.from(member);
    }

    private void validateDuplicationMember(String email, String nickname) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {throw new DuplicateEmailException("중복된 이메일입니다.");});

        memberRepository.findByNickname(nickname)
                .ifPresent(user -> {throw new DuplicateEmailException("이미 사용중인 닉네임입니다.");});
    }

    public UploadFile createProfileImage(MultipartFile file) throws IOException {
        return fileStore.storeFile(ImageType.MEMBER, file);
    }


    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "member", key = "#memberId")
    public MemberResponse get(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        return MemberResponse.from(member);
    }



    @CacheEvict(cacheNames = "member", key = "#memberId")
    public void update(UpdateMemberDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        member.update(dto.getNickname(), dto.getProfileImage());
    }

    public void updatePassword(UpdateMemberDto dto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        member.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @CacheEvict(cacheNames = "member", key = "#memberId")
    public UploadFile updateProfileImage(MultipartFile file, Long memberId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException("존재하지 않는 이미지파일입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if (member.getProfileImage() != null) {
            deleteProfileInS3(member.getProfileImage().getStoreFileName());
        }

        UploadFile uploadFile = fileStore.storeFile(ImageType.MEMBER, file);
        member.modifyProfileImage(uploadFile);

        return uploadFile;
    }



    @CacheEvict(cacheNames = {"ChannelCategory", "member"}, allEntries = true)
    public void delete(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        List<ChannelMember> channelMembers = member.getChannelMembers().stream()
                .filter(ChannelMember::getIsManager)
                .collect(Collectors.toUnmodifiableList());

        channelMembers.forEach(channelMember -> channelRepository.delete(channelMember.getChannel()));

        memberRepository.delete(member);
    }

    private void deleteProfileInS3(String storeFileName) {
        if (!storeFileName.equals(S3FileStore.DEFAULT_IMAGE)) {
            fileStore.deleteS3File(storeFileName);
        }
    }

    private void deleteProfileInLocal(String storeFileName) {
        if (!storeFileName.equals(LocalFileStore.MEMBER_DEFAULT_IMAGE)) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));

        Collection<GrantedAuthority> authorities = new ArrayList<>(10);
        List<Role> roles = member.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getName())));

        return new User(member.getEmail(), member.getPassword(), true, true,
                true, true, authorities);
    }
}
