package com.levelup.core.domain.channel;

import com.levelup.core.domain.file.File;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.exception.NoPlaceChnnelException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {

    @Id
    @GeneratedValue
    @Column(name = "channel_id")
    private Long id;

    private String name;

    @Column(name = "limited_mem_num")
    private Long limitedMemberNumber;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "manager_name")
    private String managerName;

    @Lob
    private String description;

    @Column(name = "member_count")
    private Long memberCount;

    @Column(name = "waiting_member_count")
    private Long waitingMemberCount;

    @Enumerated(EnumType.STRING)
    private ChannelCategory category;

    @Column(name = "thumbnail_description")
    private String thumbnailDescription;

    @Embedded
    private UploadFile thumbnailImage;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers = new ArrayList<>();

    @OneToMany(mappedBy = "channel")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member member;

    @OneToMany(mappedBy = "channel")
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "channel")
    private List<ChannelNotice> channelNotices = new ArrayList<>();

    /**
     * 연관관계 메서드는 한쪽에서만 해주면된다.
     * 연관관계 메서드로 각 테이블(객체)에 컬럼(변수)를 매핑시켜줘야된다.
     * 여기서는 지금 channel_member 테이블에 channel_id 컬럼에
     * 특정 channel 레코드를 매핑시키려는 것이다.
     * 만약 이렇게 연관관계 메서드를 호출하지 않으면 컬럼에 값이 들어가지 않는다.
     * */
    //==연관관계 메서드==//
    public void setMember(Member member) {
        if (member != null) {
            member.getChannels().remove(this);
        }

        this.member = member;
        member.getChannels().add(this);
    }

    public void setChannelMember(ChannelMember channelMember) {
        this.channelMembers.add(channelMember);
        channelMember.setChannel(this);
    }

    //==비즈니스 로직==//
    public void addMember(ChannelMember... channelMembers) {
        if (memberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
            if (memberCount >= limitedMemberNumber ) {
                break;
            }

            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            memberCount++;
        }
    }

    public void addMember(List<ChannelMember> channelMembers) {
        if (memberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
            if (memberCount >= limitedMemberNumber ) {
                break;
            }

            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            memberCount++;
        }
    }

    public void addWaitingMember(ChannelMember... channelMembers) {
        if (memberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            waitingMemberCount++;
        }
    }

    public void addWaitingMember(List<ChannelMember> channelMembers) {
        if (memberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            waitingMemberCount++;
        }
    }

    public void removeMember(ChannelMember... channelMembers) {
        if (memberCount <= 0 ) {
            throw new IllegalStateException("더이상 제거할 수 없습니다.");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.setChannel(null);

            channelMember.getMember().getChannelMembers().remove(channelMember);
            channelMember.setMember(null);
            memberCount--;
        }
    }

    public void removeMember(List<ChannelMember> channelMembers) {
        if (memberCount <= 0 ) {
            throw new IllegalStateException("더이상 제거할 수 없습니다.");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.getMember().getChannelMembers().remove(channelMember);
            memberCount--;
        }
    }

    public void removeWaitingMember(ChannelMember... channelMembers) {
        if (waitingMemberCount <= 0 ) {
            throw new IllegalStateException("더이상 제거할 수 없습니다.");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.getWaitingMember().getChannelMembers().remove(channelMember);
            waitingMemberCount--;
        }
    }

    public void removeWaitingMember(List<ChannelMember> channelMembers) {
        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.getWaitingMember().getChannelMembers().remove(channelMember);
            waitingMemberCount--;
        }
    }

    public void changeChannel(String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        this.name = name;
        this.limitedMemberNumber = limitNumber;
        this.description = description;
        this.thumbnailDescription = thumbnailDescription;
        this.thumbnailImage = thumbnailImage;
    }

    public void changeChannel(String name, Long limitNumber, String description, String thumbnailDescription, ChannelCategory category, UploadFile thumbnailImage) {
        this.name = name;
        this.limitedMemberNumber = limitNumber;
        this.description = description;
        this.thumbnailDescription = thumbnailDescription;
        this.thumbnailImage = thumbnailImage;
        this.category = category;
    }

}
