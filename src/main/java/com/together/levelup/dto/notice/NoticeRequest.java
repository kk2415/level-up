package com.together.levelup.dto.notice;

import com.together.levelup.domain.file.UploadFile;
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
