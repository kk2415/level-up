package com.levelup.core.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerPostResponse{
    private Long id;
    private String title;
    private String writer;
}
