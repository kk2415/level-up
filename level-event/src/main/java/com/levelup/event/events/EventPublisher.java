package com.levelup.event.events;

import org.springframework.context.ApplicationEventPublisher;

public class EventPublisher {

    private static ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        EventPublisher.publisher = publisher;
    }

    public static Object raise(Object event) {
        publisher.publishEvent(event);
        return event;
    }
}
