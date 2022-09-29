package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("채널 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest extends TestSupporter {

    @Mock private MemberRepository memberRepository;
    @Mock private ChannelRepository channelRepository;

    @InjectMocks private ChannelService channelService;

    @DisplayName("채널 생성시 유저에 매니저 권한 부여 and 생성된 채널에 유저가 자동 등록됨")
    @Test
    void save() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        ChannelMember manager1 = createChannelMember(member1, true, false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        ChannelDto channelDto = ChannelDto.builder()
                .channelId(1L)
                .managerId(manager1.getId())
                .name(channel1.getName())
                .managerNickname(member1.getNickname())
                .description(channel1.getDescription())
                .limitedMemberNumber(channel1.getMemberMaxNumber())
                .thumbnailImage(channel1.getThumbnail())
                .category(channel1.getCategory())
                .expectedStartDate(LocalDate.of(1997, 9, 27))
                .expectedEndDate(LocalDate.of(1997, 9, 27))
                .build();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member1));
//        given(channelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.of(manager1));
        given(channelRepository.save(any(Channel.class))).willReturn(channelDto.toEntity(manager1));

        // When
        ChannelDto newChannelDto = channelService.save(channelDto, member1.getId());

        List<Role> roles = member1.getRoles();
        for (Role role : roles) {
            System.out.println(role.getRoleName());
        }

        // Then
        assertThat(newChannelDto.getMemberCount()).isEqualTo(1L);
        assertThat(manager1.getMember().getRoles().size()).isEqualTo(2L);
        assertThat(manager1.getMember().getRoles().get(manager1.getMember().getRoles().size() - 1).getRoleName()).isEqualTo(RoleName.CHANNEL_MANAGER);
    }
}