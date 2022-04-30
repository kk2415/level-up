package com.together.levelup;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
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
            Member manager1 = Member.createMember("test0@naver.com", "000000", "테스트네임0",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
            Member manager2 = Member.createMember("test1@naver.com", "000000", "테스트네임1",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
            Member member1 = Member.createMember("test2@naver.com", "000000", "테스트네임2",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
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

            Post post10 = Post.createPost(member1, channel1, "안녕하세요10", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post11 = Post.createPost(member1, channel1, "꿀팁대공개1", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post12 = Post.createPost(member1, channel1, "꿀팁대공개2", "방갑습니다", PostCategory.INTRODUCE);
            TimeUnit.MILLISECONDS.sleep(100);

            Post post13 = Post.createPost(member1, channel1, "꿀팁대공개3", "방갑습니다", PostCategory.INTRODUCE);
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
            em.persist(post10);
            em.persist(post11);
            em.persist(post12);
            em.persist(post13);
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개5", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개6", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개7", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개8", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개9", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개10", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개11", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개12", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개13", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개14", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개15", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개16", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개17", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개18", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개19", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개20", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개21", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개22", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개23", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개24", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개25", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개26", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개27", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개28", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개29", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개30", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개31", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개32", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개33", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개34", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개35", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개36", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개37", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개38", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개39", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개40", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개41", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개42", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개43", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개44", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개45", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개46", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개47", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개48", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개49", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개50", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개51", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개52", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
            em.persist(Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE));
        }
    }
}
