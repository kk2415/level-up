package com.levelup.event.events;

import com.levelup.common.util.file.UploadFile;
import lombok.Getter;

@Getter
public class MemberUpdatedEvent {

    private Long memberId;
    private String email;
    private String nickname;
    private UploadFile profileImage;

    protected MemberUpdatedEvent() {}

    private MemberUpdatedEvent(Long memberId, String email, String nickname, UploadFile profileImage) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static MemberUpdatedEvent of(Long memberId, String email, String nickname, UploadFile profileImage) {
        return new MemberUpdatedEvent(memberId, email, nickname, profileImage);
    }
}
