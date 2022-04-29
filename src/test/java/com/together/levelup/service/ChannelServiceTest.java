package com.together.levelup.service;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MemberService memberService;

    Member member1;
    Member member2;
    Member manager;
    Member manager1;
    Member manager2;

    private Long channelId;
    private Long channelId1;
    private Long channelId2;

    @BeforeEach
    public void 채널_생성_테스트() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960",null );
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        manager = Member.createMember("test2",
                "0000", "박문자", Gender.FEMALE, "020509", "010-5874-3699", null);
        manager1 = Member.createMember("test3",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
        manager2 = Member.createMember("test4",
                "0000", "박문자", Gender.FEMALE, "020509", "010-5874-3699", null);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(manager);
        memberService.join(manager1);
        memberService.join(manager2);

        channelId = channelService.create(manager.getId(), "맨유팬1 모임", 30L,
                "맨유를 사랑하는 사람들의 모임", "맨유를 사랑하는 사람들의 모임", ChannelCategory.STUDY,
                new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        channelId1 = channelService.create(manager1.getId(), "맨유팬2 모임", 30L,
                "맨유를 사랑하는 사람들의 모임", "맨유를 사랑하는 사람들의 모임", ChannelCategory.STUDY,
                new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        channelId2 = channelService.create(manager2.getId(), "리버풀팬3 모임", 30L,
                "리버풀을 사랑하는 사람들의 모임", "맨유를 사랑하는 사람들의 모임", ChannelCategory.STUDY,
                new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
    }

    @Test
    public void 채널명_중복_테스트() {
        Long channelId = channelService.create(manager1.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임", "맨유를 사랑하는 사람들의 모임", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        Assertions.assertThatThrownBy(() -> channelService.create(manager2.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임", "맨유를 사랑하는 사람들의 모임", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 멤버_추가_테스트() {
        channelService.addMember(channelId, member1.getId(), member2.getId());
        Assertions.assertThat(channelService.findOne(channelId).getMemberCount()).isEqualTo(2);
    }

    @Test
    void 채널_수정_테스트() {
        Channel findChannel = channelService.findOne(channelId);
        channelService.update(channelId, "맨유팬 모여라", findChannel.getLimitedMemberNumber(), findChannel.getDescription());
        Assertions.assertThat(findChannel.getName()).isEqualTo("맨유팬 모여라");
    }

    @Test
    void 채널_삭제_테스트() {
        channelService.deleteChannel(channelId);
        List<Channel> findChannels = channelService.findAll();
        Assertions.assertThat(findChannels.size()).isEqualTo(2);
    }

    @Test
    void findByMemberId() {
        channelService.addMember(channelId, member1.getId(), member2.getId());
        channelService.addMember(channelId2, member1.getId());

        List<Channel> findChannels = channelService.findByMemberId(member1.getId());
        Assertions.assertThat(findChannels.size()).isEqualTo(2);
    }

}