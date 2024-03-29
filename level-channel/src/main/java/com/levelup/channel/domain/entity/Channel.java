package com.levelup.channel.domain.entity;

import com.levelup.channel.domain.constant.ChannelCategory;
import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.*;
import org.jsoup.Jsoup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "channel")
@Entity
public class Channel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @Lob
    private String description;

    @Column(name = "channel_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Long memberMaxNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_category", nullable = false)
    private ChannelCategory category;

    @Column(nullable = false)
    private LocalDate expectedStartDate;

    @Column(nullable = false)
    private LocalDate expectedEndDate;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelMember> channelMembers;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.REMOVE)
    private List<ChannelArticle> channelArticles;

    protected Channel() {}

    public static Channel of(
            Long id,
            String description,
            String name,
            Long memberMaxNumber,
            ChannelCategory category,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        return new Channel(
                id,
                description,
                name,
                memberMaxNumber,
                category,
                expectedStartDate,
                expectedEndDate,
                new ArrayList<>(),
                new ArrayList<>());
    }

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

    /**
     * Jsoup 라이브러리 활용하여 description 데이터에서 HTML 태그 제거하였음.
     * HTML 태그 제거 된 문자열을 20자리까지만 자르고 리턴.
     * */
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
                              String description) {
        this.name = name == null ? this.name : name;
        this.category = category == null ? this.category : category;
        this.memberMaxNumber = memberMaxNumber == null ? this.memberMaxNumber : memberMaxNumber;
        this.description = description == null ? this.description : description;
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
