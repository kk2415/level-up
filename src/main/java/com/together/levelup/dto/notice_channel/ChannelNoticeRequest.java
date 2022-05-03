package com.together.levelup.dto.notice_channel;

import com.together.levelup.domain.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChannelNoticeRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<UploadFile> uploadFiles;

}
