package com.together.levelup.dto;
import com.together.levelup.domain.member.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChannelRequest {

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

    UploadFile uploadFile;

}
