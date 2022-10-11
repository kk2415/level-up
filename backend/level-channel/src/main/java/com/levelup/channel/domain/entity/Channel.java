package com.levelup.channel.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Member;
import lombok.*;
import org.jsoup.Jsoup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@Table(name = "channel")
@AllArgsConstructor
public class Channel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
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

    @Embedded
    private UploadFile thumbnail;

    @Column(nullable = false)
    private LocalDate expectedStartDate;

    @Column(nullable = false)
    private LocalDate expectedEndDate;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<ChannelArticle> channelArticles;

    protected Channel() {}

    public long getMemberCount() {
        return this.getChannelMembers().stream()
                .filter(member -> !member.getIsWaitingMember())
                .count();
    }

    public Long getManagerId() {
        return this.getChannelMembers().stream()
                .filter(ChannelMember::getIsManager)
                .map(ChannelMember::getMemberId)
                .findAny()
                .orElse(null);
    }

    public String getManagerNickname() {
        return this.getChannelMembers().stream()
                .filter(ChannelMember::getIsManager)
                .map(ChannelMember::getNickname)
                .findAny().orElse("none");
    }

    public String getDescriptionSummary() {
        String descriptionSummary = Jsoup.parse(this.description).text();

        if (descriptionSummary.length() > 20) {
            descriptionSummary = descriptionSummary.substring(0, 20) + "...";
        }
        return descriptionSummary;
    }

    public void addChannelMember(ChannelMember channelMember) {
        this.getChannelMembers().add(channelMember);
        channelMember.setChannel(this);
    }

    public void addChannelMembers(ChannelMember... channelMembers) {
        this.getChannelMembers().addAll(List.of(channelMembers));

        for (ChannelMember channelMember : channelMembers) {
            channelMember.setChannel(this);
        }
    }

    public void updateChannel(String name,
                              ChannelCategory category,
                              Long memberMaxNumber,
                              String description,
                              UploadFile thumbnail) {
        this.name = name == null ? this.name : name;
        this.category = category == null ? this.category : category;
        this.memberMaxNumber = memberMaxNumber == null ? this.memberMaxNumber : memberMaxNumber;
        this.description = description == null ? this.description : description;
        this.thumbnail = thumbnail == null ? this.thumbnail : thumbnail;
    }

    public void updateThumbnail(UploadFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void removeMembers(ChannelMember channelMember) {
        this.getChannelMembers().remove(channelMember);
    }

    public void removeMembers(List<ChannelMember> channelMembers) {
        for (ChannelMember channelMember : channelMembers) {
            this.getChannelMembers().remove(channelMember);
        }
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Channel)) return false;
        return id != null && id.equals(((Channel) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
