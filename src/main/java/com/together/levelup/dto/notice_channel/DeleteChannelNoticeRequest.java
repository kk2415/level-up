package com.together.levelup.dto.notice_channel;

import lombok.Data;

import java.util.List;

@Data
public class DeleteChannelNoticeRequest {

    private List<Long> ids;

}
