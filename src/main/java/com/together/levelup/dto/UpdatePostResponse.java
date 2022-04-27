package com.together.levelup.dto;

import com.together.levelup.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePostResponse {

    private String title;
    private String writer;
    private String content;
    private PostCategory category;

}
