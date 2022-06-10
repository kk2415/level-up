package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    public Channel toEntity() {
        return Channel.builder()
                .name(name)
                .managerName(memberEmail)
                .limitedMemberNumber(limitedMemberNumber)
                .description(description)
                .thumbnailDescription(thumbnailDescription)
                .memberCount(0L)
                .waitingMemberCount(0L)
                .category(category)
                .thumbnailImage(thumbnailImage)
                .build();
    }

}
