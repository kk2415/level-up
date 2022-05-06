package com.together.levelup.dto.notice;

import com.together.levelup.domain.file.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class UpdateNoticeRequest {

    private String title;

    private String content;

    private List<UploadFile> uploadFiles;

}
