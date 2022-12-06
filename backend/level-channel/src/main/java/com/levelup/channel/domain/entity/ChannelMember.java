package com.levelup.channel.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Setter
    @Column(nullable = false)
    private Boolean isManager;

    @Setter
    @Column(nullable = false)
    private Boolean isWaitingMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "channelMember", cascade = CascadeType.REMOVE)
    private List<ChannelArticle> articles = new ArrayList<>();

    protected ChannelMember() {}

    public static ChannelMember of(
            Long id,
            Long memberId,
            String email,
            String nickname,
            Boolean isManager,
            Boolean isWaitingMember)
    {
        return new ChannelMember(
                id,
                memberId,
                email,
                nickname,
                isManager,
                isWaitingMember,
                null,
                new ArrayList<>());
    }

    public void setChannel(Channel channel) {
        if (this.channel != null) {
            this.channel.getChannelMembers().remove(this);
        }

        this.channel = channel;
    }

    public void update(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
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
