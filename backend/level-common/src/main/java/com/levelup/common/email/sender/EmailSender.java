package com.levelup.common.email.sender;

public interface EmailSender {

    void send(String receiver, String subject, String body);
    void asyncSend(String receiver, String subject, String body);
}
