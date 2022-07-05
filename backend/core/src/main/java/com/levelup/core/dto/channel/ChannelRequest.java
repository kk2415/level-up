package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelRequest {

    @NotNull
    private String memberEmail;

    @NotNull
    private String name;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String description;

    @NotNull
    private ChannelCategory category;

    @NotNull
    private String thumbnailDescription;

    @NotNull
    private UploadFile thumbnailImage;

    private List<UploadFile> uploadFiles;

    public Channel toEntity(String managerName) {
        return Channel.builder()
                .name(name)
                .managerName(managerName)
                .description(description)
                .limitedMemberNumber(limitedMemberNumber)
                .thumbnailDescription(thumbnailDescription)
                .thumbnailImage(thumbnailImage)
                .category(category)
                .memberCount(0L)
                .postCount(0L)
                .channelMembers(new ArrayList<>())
                .channelPosts(new ArrayList<>())
                .files(new ArrayList<>())
                .build();
    }

}
