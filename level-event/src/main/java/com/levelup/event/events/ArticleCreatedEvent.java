package com.levelup.event.events;

import lombok.Getter;

@Getter
public class ArticleCreatedEvent {

    private String memberEmail;
    private String memberNickname;

    protected ArticleCreatedEvent() {}

    private ArticleCreatedEvent(String memberEmail, String memberNickname) {
        this.memberEmail = memberEmail;
        this.memberNickname = memberNickname;
    }

    public static ArticleCreatedEvent of(String memberEmail, String memberNickname) {
        return new ArticleCreatedEvent(memberEmail,memberNickname);
    }
}
