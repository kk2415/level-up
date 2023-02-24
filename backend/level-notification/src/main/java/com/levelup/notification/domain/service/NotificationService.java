package com.levelup.notification.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.levelup.notification.domain.constant.NotificationType;
import com.levelup.notification.domain.domain.Notification;
import com.levelup.notification.domain.entity.NotificationEntity;
import com.levelup.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    @Value("${project.properties.firebase-topic}")
    String topic;

    private final ObjectMapper objectMapper;
    /**
     * 이 클래스는 모든 서버측 Firebase 클라우드 메시징 작업의 시작점입니다.
     * */
    private FirebaseMessaging firebaseMessaging;

    private final NotificationRepository notificationRepository;

    @PostConstruct
    public void pushNotification() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase/level-up-516d8-firebase-adminsdk-2ffux-34f85d5ae0.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                        .fromStream(serviceAccount)
                        .createScoped(fireBaseCreateScoped))
                .setDatabaseUrl("https://fcm.googleapis.com/v1/projects/level-up-516d8/message:send")
                .setProjectId("level-up-516d8")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

    public Notification create(Notification notification) {
        NotificationEntity notificationEntity = notification.toEntity();
        notificationRepository.save(notificationEntity);

        if (!NotificationType.NONE.equals(notification.getNotificationType())) {
            //TODO:: 알림 발송 TO ANDROID, IOS, SMS, EMAIL
        }

        return Notification.from(notificationEntity);
    }
    
    public void push(Notification notification) {
        com.google.firebase.messaging.Notification firebaseMessagingNotification
                = com.google.firebase.messaging.Notification.builder().setTitle(notification.getTemplate().getTitle()).setBody(notification.getTemplate().getBody()).build();

        Message.Builder builder = Message.builder();
        Message message = builder.setTopic(topic).setNotification(firebaseMessagingNotification).build();

        try {
            String result = this.firebaseMessaging.send(message);//firebase에게 Message 전송
            System.out.println("result : " + result);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

//    public void sendMessageTo(String targetToken, String title, String body, String path) throws Exception {
//        String message = makeMessage(targetToken, title, body, path);
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//
//        Response response  = client.newCall(request).execute();
//
//        log.info(response.body().string());
//    }

    private String getAccessToken() throws Exception {
        // 2)
        InputStream serviceAccount = new ClassPathResource("firebase/level-up-516d8-firebase-adminsdk-2ffux-34f85d5ae0.json").getInputStream();

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // accessToken 생성
        googleCredentials.refreshIfExpired();

        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
