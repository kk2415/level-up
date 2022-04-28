package com.together.levelup.domain.channel;

import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.member.UploadFile;
import com.together.levelup.exception.NoPlaceChnnelException;
import com.together.levelup.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
@Getter @Setter
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

    private String description;

    @Column(name = "member_count")
    private Long memberCount;

    @Enumerated(EnumType.STRING)
    private ChannelCategory category;

    @Embedded
    private UploadFile uploadFile;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers = new ArrayList<>();

    @OneToMany(mappedBy = "channel")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member member;

//    @OneToMany(mappedBy = "channel")
//    private List<CategoryChannel> categoryChannels = new ArrayList<>();

    /**
     * 연관관계 메서드는 한쪽에서만 해주면된다.
     * 연관관계 메서드로 각 테이블(객체)에 컬럼(변수)를 매핑시켜줘야된다.
     * 여기서는 지금 channel_member 테이블에 channel_id 컬럼에
     * 특정 channel 레코드를 매핑시키려는 것이다.
     * 만약 이렇게 연관관계 메서드를 호출하지 않으면 컬럼에 값이 들어가지 않는다.
     * */
    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getChannels().add(this);
    }

    public void setChannelMember(ChannelMember channelMember) {
        this.channelMembers.add(channelMember);
        channelMember.setChannel(this);
    }

    //==생성 메서드==//
    public static Channel createChannel(Member member, String name, Long limitNumber, String description, ChannelCategory category, UploadFile uploadFile) {
        Channel channel = new Channel();

        channel.setMember(member);
        channel.setName(name);
        channel.setManagerName(member.getName());
        channel.setLimitedMemberNumber(limitNumber);
        channel.setDescription(description);
        channel.setDateCreated(LocalDateTime.now());
        channel.setMemberCount(0L);
        channel.setCategory(category);
        channel.setUploadFile(uploadFile);

        return channel;
    }

    //==비즈니스 로직==//
    public void addMember(ChannelMember... channelMembers) {
        if (memberCount >= limitedMemberNumber ) {
            throw new NoPlaceChnnelException("채널 제한 멤버수가 다 찼습니다. 더 이상 가입할 수 없습니다");
        }

        for (ChannelMember channelMember : channelMembers) {
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
            this.getChannelMembers().add(channelMember);
            channelMember.setChannel(this);
            memberCount++;
        }
    }

    public void changeChannel(String name, Long limitNumber, String descript) {
        this.setName(name);
        this.setLimitedMemberNumber(limitNumber);
        this.setDescription(descript);
    }

}
