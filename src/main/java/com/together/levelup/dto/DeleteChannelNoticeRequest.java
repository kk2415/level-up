package com.together.levelup.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeleteChannelNoticeRequest {

    private List<Long> ids;

}
