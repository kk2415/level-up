package com.levelup.api.controller.v1.notification;

import com.levelup.api.controller.v1.dto.ChannelJoinRequestNotificationDto;
import com.levelup.api.controller.v1.dto.ChannelJoinRequestNotificationDto.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@RestController
public class NotificationApiController {

    //필요한 데이터:
    //댓글 작성자, 댓글 내용 - NEW_COMMENT_ON_FEED
    //답변 작성자, 답변 내용 - NEW_ANSWER_ON_QNA
    //신청자 이름, 채널 이름 - NEW_REQUEST_ON_CHANNEL
    //좋아요 누른 사람, 채널 이름 - NEW_LIKE_ON_FEED

    @PostMapping("")
    public void sendRequestJoinChannel(@RequestBody Request request) {

    }

    @PostMapping("")
    public void sendNewComment(@RequestBody Request request) {

    }

    @PostMapping("/job")
    public void sendNewJob() {

    }
}
