package com.levelup.event.events;

import lombok.Getter;

@Getter
public class MemberProfileImageUpdatedEvent {

    private Long memberId;
    private String uploadFileName;
    private String storeFileName;

    protected MemberProfileImageUpdatedEvent() {}

    private MemberProfileImageUpdatedEvent(Long memberId, String uploadFileName, String storeFileName) {
        this.memberId = memberId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public static MemberProfileImageUpdatedEvent of(Long memberId, String uploadFileName, String storeFileName) {
        return new MemberProfileImageUpdatedEvent(memberId, uploadFileName, storeFileName);
    }
}
