package com.together.levelup.dto.post;
import com.together.levelup.domain.post.PostCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreatePostRequest {

    @NotNull
    private Long channelId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private PostCategory category;

}
