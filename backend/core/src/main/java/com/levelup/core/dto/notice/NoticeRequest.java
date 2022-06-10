package com.levelup.core.dto.notice;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.Notice;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private List<UploadFile> uploadFiles;

    public Notice toEntity(Member member) {
        return Notice.builder()
                .member(member)
                .title(title)
                .writer(member.getName())
                .content(content)
                .views(0L)
                .build();
    }


}
