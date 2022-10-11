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

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    String profileImage;

    @Setter
    @Column(nullable = false)
    private Boolean isManager;

    @Setter
    @Column(nullable = false)
    private Boolean isWaitingMember;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "channelMember")
    private List<ChannelComment> comments;

    protected ChannelMember() {}

    public static ChannelMember of(Long memberId, String email, String nickname, String profileImage, Boolean isManager, Boolean isWaitingMember) {
        return ChannelMember.builder()
                .memberId(memberId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .isManager(isManager)
                .isWaitingMember(isWaitingMember)
                .comments(new ArrayList<>())
                .build();
    }

    public void update(String email, String nickname, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
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
