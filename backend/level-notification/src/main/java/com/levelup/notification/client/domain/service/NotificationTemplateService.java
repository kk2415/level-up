package com.levelup.notification.client.domain.service;

import com.levelup.notification.client.domain.constant.NotificationTemplateType;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationTemplateService {

    public void create(NotificationTemplateType templateType) {

    }

    public void createJobNotificationTemplate(List<String> jobTitles) {

        //필요한 데이터:
        //댓글 작성자, 댓글 내용 - NEW_COMMENT_ON_FEED
        //답변 작성자, 답변 내용 - NEW_ANSWER_ON_QNA
        //신청자 이름, 채널 이름 - NEW_REQUEST_ON_CHANNEL
        //좋아요 누른 사람, 채널 이름 - NEW_LIKE_ON_FEED

        //떙떙님이 게시글에 좋아요를 눌렀습니다.
        //떙떙님이 님긴 답글: "ㅇㅇㅇㅇ"
        //떙떙님이 떙떙채널에 신청하였습니다.
        //떙떙 채널 가입이 승인되셨습니다.


        String title = "새로운 채용 공고가 올라왔습니다!";
        String body = jobTitles.stream().map(jobTitle -> jobTitle + "\n").collect(Collectors.joining());

        //NotificationTemplate
    }
}
