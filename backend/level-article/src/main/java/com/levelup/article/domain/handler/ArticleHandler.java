package com.levelup.article.domain.handler;

import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.WriterRepository;
import com.levelup.event.events.MemberCreatedEvent;
import com.levelup.event.events.MemberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ArticleHandler {

    private final WriterRepository writerRepository;

    @EventListener(MemberCreatedEvent.class)
    public void handleMemberCreatedEvent(MemberCreatedEvent event) {
        Writer writer = Writer.of(event.getMemberId(), event.getNickname(), event.getEmail());
        writerRepository.save(writer);
    }

    @EventListener(MemberUpdatedEvent.class)
    public void handleMemberUpdatedEvent(MemberUpdatedEvent event) {
        writerRepository.findByMemberId(event.getMemberId())
                .ifPresent(writer -> writer.update(event.getNickname(), event.getEmail()));
    }
}
