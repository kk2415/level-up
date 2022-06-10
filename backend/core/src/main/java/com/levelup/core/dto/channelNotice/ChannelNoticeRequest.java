package com.levelup.core.dto.channelNotice;

import com.levelup.core.domain.file.UploadFile;
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
