package com.levelup.core.dto.notice;

import com.levelup.core.domain.file.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NoticeRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private List<UploadFile> uploadFiles;

}
