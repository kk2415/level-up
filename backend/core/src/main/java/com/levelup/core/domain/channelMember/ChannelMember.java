package com.levelup.core.domain.channelMember;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "channel_member")
@Entity
public class ChannelMember extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "channel_member_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private Boolean isManager;

    @Setter
    @Column(nullable = false)
    private Boolean isWaitingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    protected ChannelMember() {}

    public void setMember(Member member) {
        this.member = member;
        member.getChannelMembers().add(this);
    }

    public static ChannelMember of(Member member, Boolean isManager, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.builder()
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .build();

        channelMember.setMember(member);
        return channelMember;
    }
}
