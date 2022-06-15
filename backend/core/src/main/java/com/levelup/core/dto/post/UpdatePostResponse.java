package com.levelup.core.dto.post;

import com.levelup.core.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostResponse {

    private String title;
    private String writer;
    private String content;
    private PostCategory category;

}
