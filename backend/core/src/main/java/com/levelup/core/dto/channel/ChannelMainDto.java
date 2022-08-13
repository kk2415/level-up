package com.levelup.core.dto.channel;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
public class ChannelMainDto implements Serializable {

    private BigInteger channelId;
    private String channelName;
    private BigInteger memberMaxNumber;
    private String mainDescription;
    private String managerName;
    private String storeFileName;
    private Timestamp createdAt;
    private BigInteger memberCount;
    private BigInteger memberId;

    protected ChannelMainDto() {}

    public ChannelMainDto(BigInteger channelId,
                          String channelName,
                          BigInteger memberMaxNumber,
                          String mainDescription,
                          String managerName,
                          String storeFileName,
                          Timestamp createdAt,
                          BigInteger memberCount,
                          BigInteger memberId) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.memberMaxNumber = memberMaxNumber;
        this.mainDescription = mainDescription;
        this.managerName = managerName;
        this.storeFileName = storeFileName;
        this.createdAt = createdAt;
        this.memberCount = memberCount;
        this.memberId = memberId;
    }
}
