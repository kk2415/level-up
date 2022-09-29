package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.member.domain.entity.Member;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "channel_member")
@Entity
public class ChannelMember extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_member_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private Boolean isManager;

    @Setter
    @Column(nullable = false)
    private Boolean isWaitingMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "channelMember")
    private List<ChannelComment> comments;

    protected ChannelMember() {}

    public void setMember(Member member) {
        this.member = member;
    }

    public static ChannelMember of(Member member, Boolean isManager, Boolean isWaitingMember) {
        ChannelMember channelMember = ChannelMember.builder()
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();

        channelMember.setMember(member);
        return channelMember;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChannelMember)) return false;
        return id != null && id.equals(((ChannelMember) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
