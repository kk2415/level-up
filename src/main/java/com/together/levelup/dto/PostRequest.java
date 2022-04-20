package com.together.levelup.dto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostRequest {

    @NotNull
    private String memberEmail;

    @NotNull
    private String name;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String managerName;

    @NotNull
    private String description;

}
