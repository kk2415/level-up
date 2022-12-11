package com.levelup.notification.client.domain.service;

import com.levelup.notification.client.domain.constant.NotificationType;
import com.levelup.notification.client.domain.domain.Notification;

import java.util.List;

public class JobNotificationService {

    public void createJobNotification(Notification notification, List<String> jobTitles) {
        //채용 정보
        //채용 건수
        // param : List<채용> jobs
        //String title = "새로운 채용 공고가 올라왔습니다! 카카오 백엔드 엔지니어 채용 외 4건의 채용을 시작되었습니다.";

        if (!NotificationType.NONE.equals(notification.getNotificationType())) {
            //TODO:: 알림 발송 TO ANDROID, IOS, SMS, EMAIL
        }
    }
}
