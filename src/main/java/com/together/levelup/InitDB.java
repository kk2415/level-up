package com.together.levelup;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Authority;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.domain.notice.Notice;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.post.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() throws InterruptedException {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initDb() throws InterruptedException {
            Member manager1 = Member.createMember(Authority.ADMIN, "test0@naver.com", "000000", "테스트네임0",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "/images/member/AFF947XXQ-5554WSDQ12.png"));
            Member manager2 = Member.createMember("test1@naver.com", "000000", "테스트네임1",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "/images/member/AFF947XXQ-5554WSDQ12.png"));
            Member member1 = Member.createMember("test2@naver.com", "000000", "테스트네임2",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "/images/member/AFF947XXQ-5554WSDQ12.png"));
            em.persist(manager1);
            em.persist(manager2);
            em.persist(member1);

            Channel channel1 = Channel.createChannel(manager1, "리액트 기초", 20L,
                    "리액트에 대한 전반적인 개념을 공부하는 스터디", "데이터베이스 기초에 대한 스터디", ChannelCategory.STUDY, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
            Channel channel2 = Channel.createChannel(manager2, "스프링 기본개념", 20L,
                    "스프링 초보들 다 모여라!", "데이터베이스 기초에 대한 스터디", ChannelCategory.STUDY, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
            Channel channel3 = Channel.createChannel(manager2, "HTTP 완전 정복", 20L,
                    "HTTP 완전 정복 가이드 책을 따라 읽는 스터디", "데이터베이스 기초에 대한 스터디", ChannelCategory.STUDY, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
            Channel channel4 = Channel.createChannel(manager2, "데이터베이스 기본반", 20L,
                    "데이터베이스 기초에 대한 스터디", "데이터베이스 기초에 대한 스터디", ChannelCategory.STUDY, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
            Channel channel5 = Channel.createChannel(manager2, "자바 코테 스터디", 20L,
                    "자바 코테 준비 스터디입니다", "데이터베이스 기초에 대한 스터디", ChannelCategory.STUDY, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
            Channel channel6 = Channel.createChannel(manager2, "스프링 기반 프로젝트", 20L,
                    "스프링 기반 프로젝트입니다", "데이터베이스 기초에 대한 스터디", ChannelCategory.PROJECT, new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));

            em.persist(channel1);
            em.persist(channel2);
            em.persist(channel3);
            em.persist(channel4);
            em.persist(channel5);
            em.persist(channel6);

            ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
            ChannelMember channelMember2 = ChannelMember.createChannelMember(member1);

            channel1.addMember(channelMember1);
            channel2.addMember(channelMember2);


            Post post1 = Post.createPost(member1, channel1, "안녕하세요1", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post2 = Post.createPost(member1, channel1, "안녕하세요2", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post3 = Post.createPost(member1, channel1, "안녕하세요3", "방갑습니다", PostCategory.INTRODUCE);            TimeUnit.MILLISECONDS.sleep(100);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post4 = Post.createPost(member1, channel1, "안녕하세요4", "방갑습니다", PostCategory.INTRODUCE);            TimeUnit.MILLISECONDS.sleep(100);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post5 = Post.createPost(member1, channel1, "안녕하세요5", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post6 = Post.createPost(member1, channel1, "안녕하세요6", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post7 = Post.createPost(member1, channel1, "안녕하세요7", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post8 = Post.createPost(member1, channel1, "안녕하세요8", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post9 = Post.createPost(member1, channel1, "안녕하세요9", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);
            em.persist(post9);
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개5", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개6", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개7", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개8", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개9", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개10", "방갑습니다", PostCategory.INTRODUCE));


            ChannelNotice channelNotice1 = ChannelNotice.createChannelNotice(channel1, "11", channel1.getManagerName(), "11");
            TimeUnit.MILLISECONDS.sleep(100);
            ChannelNotice channelNotice2 = ChannelNotice.createChannelNotice(channel1, "22", channel1.getManagerName(), "11");
            TimeUnit.MILLISECONDS.sleep(100);
            ChannelNotice channelNotice3 = ChannelNotice.createChannelNotice(channel1, "33", channel1.getManagerName(), "11");
            TimeUnit.MILLISECONDS.sleep(100);
            ChannelNotice channelNotice4 = ChannelNotice.createChannelNotice(channel1, "44", channel1.getManagerName(), "11");
            TimeUnit.MILLISECONDS.sleep(100);
            ChannelNotice channelNotice5 = ChannelNotice.createChannelNotice(channel1, "55", channel1.getManagerName(), "11");
            TimeUnit.MILLISECONDS.sleep(100);
            ChannelNotice channelNotice6 = ChannelNotice.createChannelNotice(channel1, "66", channel1.getManagerName(), "11");

            em.persist(channelNotice1);
            em.persist(channelNotice2);
            em.persist(channelNotice3);
            em.persist(channelNotice4);
            em.persist(channelNotice5);
            em.persist(channelNotice6);

            Notice notice1 = Notice.createNotice(member1, "공지사항 1", member1.getName(), "공지시항 1입니다.");
            Notice notice2 = Notice.createNotice(member1, "공지사항 2", member1.getName(), "공지시항 2입니다.");
            Notice notice3 = Notice.createNotice(member1, "공지사항 3", member1.getName(), "공지시항 3입니다.");
            Notice notice4 = Notice.createNotice(member1, "공지사항 4", member1.getName(), "공지시항 4입니다.");

            em.persist(notice1);
            em.persist(notice2);
            em.persist(notice3);
            em.persist(notice4);
        }
    }
}
