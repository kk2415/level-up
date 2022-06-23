package com.levelup.core.domain.channel;

import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.domain.post.Post;
import com.levelup.core.exception.NoPlaceChnnelException;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "channel")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "channel_id")
    private Long id;

    @Lob
    private String description;

    private String name;
    private String managerName;
    private Long limitedMemberNumber;

    private Long postCount;
    private Long memberCount;
    private Long waitingMemberCount;

    @Enumerated(EnumType.STRING)
    private ChannelCategory category;

    @Column(name = "thumbnail_description")
    private String thumbnailDescription;

    @Embedded
    private UploadFile thumbnailImage;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers;

    @OneToMany(mappedBy = "channel",  cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member manager;

    @OneToMany(mappedBy = "channel")
    private List<File> files;

    @OneToMany(mappedBy = "channel")
    private List<ChannelNotice> channelNotices;

    @OneToMany(mappedBy = "channel")
    private List<ChannelPost> channelPosts;

    /**
     * 연관관계 메서드는 한쪽에서만 해주면된다.
     * 연관관계 메서드로 각 테이블(객체)에 컬럼(변수)를 매핑시켜줘야된다.
     * 여기서는 지금 channel_member 테이블에 channel_id 컬럼에
     * 특정 channel 레코드를 매핑시키려는 것이다.
     * 만약 이렇게 연관관계 메서드를 호출하지 않으면 컬럼에 값이 들어가지 않는다.
     * */
    //==연관관계 메서드==//
    public void setManager(Member manager) {
        if (this.manager != null) {
            this.manager.getChannels().remove(this);
        }

        this.manager = manager;
        manager.getChannels().add(this);
    }

    public void setChannelMember(ChannelMember channelMember) {
        this.channelMembers.add(channelMember);
        channelMember.setChannel(this);
    }


    //==비즈니스 로직==//
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

    public void addWaitingMember(List<ChannelMember> channelMembers) {
        if (waitingMemberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            waitingMemberCount++;
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

    public void removeWaitingMember(List<ChannelMember> channelMembers) {
        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.getWaitingMember().getChannelMembers().remove(channelMember);
            waitingMemberCount--;
        }
    }

    public void modifyChannel(String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        this.name = name;
        this.limitedMemberNumber = limitNumber;
        this.description = description;
        this.thumbnailDescription = thumbnailDescription;
        this.thumbnailImage = thumbnailImage;
    }

    public void modifyThumbNail(UploadFile thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void addPostCount() {
        this.postCount++;
    }

    public void removePostCount() {
        if (this.postCount > 0) {
            this.postCount--;
        }
    }

}
