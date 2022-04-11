package com.together.community.service;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MemberService memberService;

    @Test
    void 채널_생성_테스트() {
        Member manager = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        memberService.join(manager);

        Long channelId = channelService.create(manager.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        Channel findChannel = channelService.findOne(channelId);

        Assertions.assertThat(findChannel.getName()).isEqualTo("맨유팬 모임");
    }

    @Test
    public void 채널명_중복_테스트() {
        Member manager1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member manager2 = getMember("test1", "1997", "kkh2415@naver.com", "매니저", Gender.MAIL);
        memberService.join(manager1);
        memberService.join(manager2);

        Long channelId = channelService.create(manager1.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        Assertions.assertThatThrownBy(() -> channelService.create(manager2.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 멤버_추가_테스트() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        Member manager = getMember("test2", "1999", "kkh2415@naver.com", "이승호", Gender.MAIL);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(manager);

        Long channelId = channelService.create(manager.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        channelService.addMember(channelId, member1.getId(), member2.getId());
        Assertions.assertThat(channelService.findOne(channelId).getMemberCount()).isEqualTo(2);
    }

    @Test
    void 채널_수정_테스트() {
        Member manager = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        memberService.join(manager);

        Long channelId = channelService.create(manager.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        Channel findChannel = channelService.findOne(channelId);
        channelService.update(channelId, "맨유팬 모여라", findChannel.getLimitedMemberNumber(), findChannel.getDescript());
        Assertions.assertThat(findChannel.getName()).isEqualTo("맨유팬 모여라");
    }

    @Test
    void deleteChannel() {
        Member manager = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        memberService.join(manager);

        Long channelId = channelService.create(manager.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        channelService.deleteChannel(channelId);
        List<Channel> findChannels = channelService.findAll();
        Assertions.assertThat(findChannels.size()).isEqualTo(0);
    }

    @Test
    void findByMemberId() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        Member manager = getMember("test2", "1999", "kkh2415@naver.com", "이승호", Gender.MAIL);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(manager);

        Long channelId1 = channelService.create(manager.getId(), "맨유팬 모임", 30L, "맨유를 사랑하는 사람들의 모임");
        Long channelId2 = channelService.create(manager.getId(), "리버풀팬 모임", 30L, "리버풀을 사랑하는 사람들의 모임");
        channelService.addMember(channelId1, member1.getId(), member2.getId());
        channelService.addMember(channelId2, member1.getId());

        List<Channel> findChannels = channelService.findByMemberId(member1.getId());
        Assertions.assertThat(findChannels.size()).isEqualTo(2);
    }

    private Member getMember(String loginId, String birthday, String email, String name, Gender gender) {
        Member member1 = new Member();
        member1.setLoginId(loginId);
        member1.setPassword("0000");
        member1.setBirthday(birthday);
        member1.setEmail(email);
        member1.setDateCreated(LocalDateTime.now());
        member1.setGender(gender);
        member1.setPhone("010-2354-9960");
        member1.setName(name);
        return member1;
    }

}