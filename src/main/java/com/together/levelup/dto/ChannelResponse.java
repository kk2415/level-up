package com.together.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChannelResponse {

    private String name;
    private Long limitedMemberNumber;
    private LocalDateTime dateCreated;
    private String managerName;
    private String descript;
    private Long memberCount;

}
