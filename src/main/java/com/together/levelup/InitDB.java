package com.together.levelup;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
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

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initDb() {
            Member manager1 = Member.createMember("test0@naver.com", "000000", "테스트네임0",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
            Member manager2 = Member.createMember("test1@naver.com", "000000", "테스트네임1",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
            Member member1 = Member.createMember("test2@naver.com", "000000", "테스트네임2",
                    Gender.MALE, "19970927", "010-2354-9960", new UploadFile("내 이미지", "member/99D279435B3D788602.jfif"));
            em.persist(manager1);
            em.persist(manager2);
            em.persist(member1);

            Channel channel1 = Channel.createChannel(manager1, "축구를 x잘하고싶은 사람들", 20L,
                    "안녕하세요!", new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_IMAGE));
            Channel channel2 = Channel.createChannel(manager2, "스프링 x학습방", 20L,
                    "스프링 초보들 다 모여라!", new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_IMAGE));
            em.persist(channel1);
            em.persist(channel2);

            ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
            ChannelMember channelMember2 = ChannelMember.createChannelMember(member1);

            channel1.addMember(channelMember1);
            channel2.addMember(channelMember2);


            Post post1 = Post.createPost(member1, channel1, "안녕하세요1", "방갑습니다", PostCategory.INTRODUCE);
            Post post2 = Post.createPost(member1, channel1, "안녕하세요2", "방갑습니다", PostCategory.INTRODUCE);
            Post post3 = Post.createPost(member1, channel1, "안녕하세요3", "방갑습니다", PostCategory.INTRODUCE);
            Post post4 = Post.createPost(member1, channel1, "안녕하세요4", "방갑습니다", PostCategory.INTRODUCE);
            Post post5 = Post.createPost(member1, channel1, "안녕하세요5", "방갑습니다", PostCategory.INTRODUCE);
            Post post6 = Post.createPost(member1, channel1, "안녕하세요6", "방갑습니다", PostCategory.INTRODUCE);
            Post post7 = Post.createPost(member1, channel1, "안녕하세요7", "방갑습니다", PostCategory.INTRODUCE);
            Post post8 = Post.createPost(member1, channel1, "안녕하세요8", "방갑습니다", PostCategory.INTRODUCE);
            Post post9 = Post.createPost(member1, channel1, "안녕하세요9", "방갑습니다", PostCategory.INTRODUCE);
            Post post10 = Post.createPost(member1, channel1, "안녕하세요10", "방갑습니다", PostCategory.INTRODUCE);
            Post post11 = Post.createPost(member1, channel1, "꿀팁대공개1", "방갑습니다", PostCategory.INTRODUCE);
            Post post12 = Post.createPost(member1, channel1, "꿀팁대공개2", "방갑습니다", PostCategory.INTRODUCE);
            Post post13 = Post.createPost(member1, channel1, "꿀팁대공개3", "방갑습니다", PostCategory.INTRODUCE);
            Post post14 = Post.createPost(member1, channel1, "꿀팁대공개4", "방갑습니다", PostCategory.INTRODUCE);
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
            em.persist(post14);
        }
    }
}
