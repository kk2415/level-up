package com.levelup.core.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSearch {

    private String field;
    private String query;

}
