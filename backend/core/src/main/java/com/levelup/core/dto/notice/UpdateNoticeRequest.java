package com.levelup.core.dto.notice;

import com.levelup.core.domain.file.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class UpdateNoticeRequest {

    private String title;

    private String content;

    private List<UploadFile> uploadFiles;

}
