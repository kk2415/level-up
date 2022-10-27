package com.levelup.api.controller.v1.dto.request.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.entity.ChannelCategory;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ChannelRequest {

    @NotNull
    private String name;

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

    protected ChannelRequest() {}

    private ChannelRequest(
            String name,
            Long limitedMemberNumber,
            String description,
            ChannelCategory category,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        this.name = name;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.category = category;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
    }

    public static ChannelRequest of(
            String name,
            Long limitedMemberNumber,
            String description,
            ChannelCategory category,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        return new ChannelRequest(
                name,
                limitedMemberNumber,
                description,
                category,
                expectedStartDate,
                expectedEndDate);
    }

    public ChannelDto toDto(Long managerId) {
        return ChannelDto.of(
                null,
                managerId,
                name,
                "unknown",
                limitedMemberNumber,
                description,
                "unknown",
        0L,
                category,
                LocalDateTime.now(),
                expectedStartDate,
                expectedEndDate
        );
    }

    public ChannelDto toDto() {
        return ChannelDto.of(
                null,
                null,
                name,
                "unknown",
                limitedMemberNumber,
                description,
                "unknown",
                0L,
                category,
                LocalDateTime.now(),
                expectedStartDate,
                expectedEndDate
        );
    }
}
