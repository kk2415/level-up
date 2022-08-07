package com.levelup.core.domain.channel;

import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.file.File;
import com.levelup.core.domain.file.UploadFile;
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

    @Column(name = "channel_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false)
    private Long memberMaxNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_category", nullable = false)
    private ChannelCategory category;

    @Column(nullable = false)
    private String mainDescription;

    @Embedded
    private UploadFile thumbnailImage;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers;

    @OneToMany(mappedBy = "channel")
    private List<File> files;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<ChannelPost> channelPosts;


    /**
     * 연관관계 메서드는 한쪽에서만 해주면된다.
     * 연관관계 메서드로 각 테이블(객체)에 컬럼(변수)를 매핑시켜줘야된다.
     * 여기서는 지금 channel_member 테이블에 channel_id 컬럼에
     * 특정 channel 레코드를 매핑시키려는 것이다.
     * 만약 이렇게 연관관계 메서드를 호출하지 않으면 컬럼에 값이 들어가지 않는다.
     * */
    //==연관관계 메서드==//
    public void setChannelMember(ChannelMember channelMember) {
        this.getChannelMembers().add(channelMember);
        channelMember.setChannel(this);
    }


    //==비즈니스 로직==//
    public void removeMember(List<ChannelMember> channelMembers) {
        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
            channelMember.getMember().getChannelMembers().remove(channelMember);
        }
    }

    public void modifyChannel(String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        this.name = name;
        this.memberMaxNumber = limitNumber;
        this.description = description;
        this.mainDescription = thumbnailDescription;
        this.thumbnailImage = thumbnailImage;
    }

    public void modifyThumbNail(UploadFile thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}
