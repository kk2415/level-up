package com.levelup.api.dto.channel;

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

    static public ChannelRequest of(
            String memberEmail, String name, Long limitedMemberNumber, String description, ChannelCategory category,
            String thumbnailDescription, UploadFile thumbnailImage, List<UploadFile> uploadFiles
    ) {
        return new ChannelRequest(
                memberEmail,
                name,
                limitedMemberNumber,
                description,
                category,
                thumbnailDescription,
                thumbnailImage,
                uploadFiles);
    }

    public Channel toEntity(String managerName) {
        return Channel.builder()
                .name(name)
                .managerName(managerName)
                .description(description)
                .memberMaxNumber(limitedMemberNumber)
                .mainDescription(thumbnailDescription)
                .thumbnailImage(thumbnailImage)
                .category(category)
                .channelMembers(new ArrayList<>((int) (limitedMemberNumber + 1)))
                .channelPosts(new ArrayList<>())
                .files(new ArrayList<>())
                .build();
    }
}
