package com.levelup.api.controller.v1.dto.request.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.service.dto.CreateChannelDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CreateChannelRequest {

    @NotNull
    private String name;

    @NotNull
    private String managerNickname;

    @NotNull
    private String managerEmail;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String description;

    @NotNull
    private ChannelCategory category;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedStartDate;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedEndDate;

    protected CreateChannelRequest() {}

    public CreateChannelDto toDto() {
        return CreateChannelDto.of(
                null,
                null,
                name,
                managerNickname,
                managerEmail,
                limitedMemberNumber,
                description,
                description,
        0L,
                category,
                expectedStartDate,
                expectedEndDate
        );
    }
}
