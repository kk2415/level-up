package com.levelup.api.controller.v1.dto.request.channel;

import com.levelup.channel.domain.service.dto.ChannelCommentDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ChannelCommentRequest {

    @NotNull
    @NotBlank
    private String content;

    protected ChannelCommentRequest() {}

    private ChannelCommentRequest(String content) {
        this.content = content;
    }

    public static ChannelCommentRequest of(String content) {
        return new ChannelCommentRequest(content);
    }

    public ChannelCommentDto toDto() {
        return ChannelCommentDto.from(content);
    }
}
