package com.levelup.core.dto.channelNotice;

import lombok.Data;

import java.util.List;

@Data
public class DeleteChannelNoticeRequest {

    private List<Long> ids;

}
