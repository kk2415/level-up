package com.together.levelup.dto.post;
import com.together.levelup.domain.UploadFile;
import com.together.levelup.domain.post.PostCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdatePostRequest {

    @NotNull
    private String memberEmail;

    @NotNull
    private String title;

    @NotNull
    private String writer;

    @NotNull
    private String content;

    @NotNull
    private PostCategory category;

    private List<UploadFile> uploadFiles;

}
