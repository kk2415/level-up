package com.levelup.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_NOT_FOUND(400, "존재하지 않는 회원입니다."),
    ARTICLE_NOT_FOUND(400, "존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(400, "존재하지 않는 댓글입니다."),
    CHANNEL_NOT_FOUND(400, "존재하지 않는 채널입니다."),
    CHANNEL_MEMBER_NOT_FOUND(400, "존재하지 않는 채널 회원입니다."),
    IMAGE_NOT_FOUND(400, "이미지를 찾을 수 없습니다."),

    VOTE_DUPLICATION(400, "추천은 한 번만 가능합니다."),
    EMAIL_DUPLICATION(400, "이미 존재하는 이메일입니다."),
    NICKNAME_DUPLICATION(400, "이미 존재하는 닉네임입니다."),
    CHANNEL_MEMBER_DUPLICATION(400, "이미 채널에 등록된 회원입니다."),

    SECURITY_CODE_EXPIRED(400, "인증코드가 만료되었습니다."),
    NOT_MATCH_SECURITY_CODE(400, "인증코드가 일치하지 않습니다."),

    NO_PLACE_CHANNEL(400, "스터디/프로젝트 정원이 다 찼습니다."),

    AUTHORITY_EXCEPTION(403, "작성자 본인이 아닙니다."),

    INVALID_REQUEST_BODY(400, "HTTP 리퀘스트 바디 유효성 체크 에러"),
    BAD_REQUEST(400, "잘못된 접근입니다");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
