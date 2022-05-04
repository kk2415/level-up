package com.together.levelup.repository;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.dto.post.PostSearch;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.JpaPostRepository;
import com.together.levelup.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Transactional
public class JpaPostRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private JpaPostRepository jpaPostRepository;

    private Member member1;
    private Member member2;
    private Channel channel0;
    private Channel channel1;
    private Channel channel2;
    private Channel channel3;

    Post post1;
    Post post2;
    Post post3;
    Post post4;
    Post post5;
    Post post6;
    Post post7;
    Post post8;
    Post post9;
    Post post10;
    Post post11;
    Post post12;
    Post post13;
    Post post14;
    Post post15;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        channel0 = Channel.createChannel(member1, "우당탕탕 스프링 개발기", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
        channel1 = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
        channel2 = Channel.createChannel(member2, "스프링 프로젝트1", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.PROJECT, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
        channel3 = Channel.createChannel(member2, "스프링 프로젝트2", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.PROJECT, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));

        post1 = Post.createPost(member1, channel1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        post2 = Post.createPost(member1, channel1, "0", "추천 받음");
        post3 = Post.createPost(member1, channel1, "1", "천천히 생각해보니 인생이란...");
        post4 = Post.createPost(member1, channel1, "2", "천천히 생각해보니 인생이란...");
        post5 = Post.createPost(member1, channel1, "3", "천천히 생각해보니 인생이란...");
        post6 = Post.createPost(member1, channel1, "4", "천천히 생각해보니 인생이란...");
        post7 = Post.createPost(member1, channel1, "5", "천천히 생각해보니 인생이란...");
        post8 = Post.createPost(member1, channel1, "6", "천천히 생각해보니 인생이란...");
        post9 = Post.createPost(member2, channel1, "7", "천천히 생각해보니 인생이란...");
        post10 = Post.createPost(member2, channel1, "8", "천천히 생각해보니 인생이란...");
        post11 = Post.createPost(member2, channel1, "9", "천천히 생각해보니 인생이란...");
        post12 = Post.createPost(member2, channel1, "10", "천천히 생각해보니 인생이란...");
        post13 = Post.createPost(member2, channel1, "11", "천천히 생각해보니 인생이란...");
        post14 = Post.createPost(member2, channel1, "12", "천천히 생각해보니 인생이란...");
        post15 = Post.createPost(member2, channel1, "울랄라", "천천히 생각해보니 인생이란...");
    }

    @Test
    public void 게시글_레포_테스트() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Post findPost1 = postRepository.findById(post1.getId());
        Post findPost2 = postRepository.findById(post2.getId());
        Post findPost3 = postRepository.findById(post3.getId());

        Assertions.assertThat(post1).isEqualTo(findPost1);
        Assertions.assertThat(post2).isEqualTo(findPost2);
        Assertions.assertThat(post3).isEqualTo(findPost3);
    }

    @Test
    public void 멤버_포스트_조회() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        List<Post> memberList = postRepository.findByMemberId(member1.getId());
        Assertions.assertThat(memberList.size()).isEqualTo(3);
    }

    @Test
    public void 채널별_게시글_조회() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);
        channelRepository.save(channel2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        List<Post> findPosts = postRepository.findByChannelId(channel1.getId(), 1, null);
        Assertions.assertThat(findPosts.size()).isEqualTo(3);
    }

    @Test
    public void 채널게시글_페이징_테스트() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);
        postRepository.save(post8);
        postRepository.save(post9);
        postRepository.save(post10);
        postRepository.save(post11);
        postRepository.save(post12);
        postRepository.save(post13);
        postRepository.save(post14);
        postRepository.save(post15);

        List<Post> findPosts = postRepository.findByChannelId(channel1.getId(), 1, null);
        Assertions.assertThat(findPosts.size()).isEqualTo(10);

        List<Post> findPosts2 = postRepository.findByChannelId(channel1.getId(), 1, new PostSearch("writer", "이예지"));
        Assertions.assertThat(findPosts2.size()).isEqualTo(7);

        List<Post> findPosts3 = postRepository.findByChannelId(channel1.getId(), 1, new PostSearch("title", "헬로"));
        Assertions.assertThat(findPosts3.size()).isEqualTo(1);
    }

    @Test
    public void queryDsl테스트() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);

        List<Post> findPosts = jpaPostRepository.findByChannelId(channel1.getId(), 1, null);
        Assertions.assertThat(findPosts.size()).isEqualTo(7);

        List<Post> findPosts2 = jpaPostRepository.findByChannelId(channel1.getId(), 1, new PostSearch("writer", "김경희"));
        Assertions.assertThat(findPosts2.size()).isEqualTo(7);

        List<Post> findPosts3 = jpaPostRepository.findByChannelId(channel1.getId(), 1, new PostSearch("title", "헬로"));
        Assertions.assertThat(findPosts3.size()).isEqualTo(1);
    }

    @Test
    public void next_page_test() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        Post post1 = Post.createPost(member1, channel1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post2 = Post.createPost(member1, channel1, "저녁 뭐 먹지?", "추천 받음");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post3 = Post.createPost(member1, channel1, "1", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post4 = Post.createPost(member1, channel1, "2", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post5 = Post.createPost(member1, channel1, "3", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post6 = Post.createPost(member1, channel1, "4", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post7 = Post.createPost(member1, channel1, "5", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);

        Post findPost = postRepository.findNextPage(post4.getId());
        Assertions.assertThat(findPost.getId()).isEqualTo(post5.getId());
    }

    @Test
    public void prev_page_test() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        Post post1 = Post.createPost(member1, channel1, "-1", "안녕하세요. 첫 게시글입니다");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post2 = Post.createPost(member1, channel1, "0", "추천 받음");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post3 = Post.createPost(member1, channel1, "1", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post4 = Post.createPost(member1, channel1, "2", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post5 = Post.createPost(member1, channel1, "3", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post6 = Post.createPost(member1, channel1, "4", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);
        Post post7 = Post.createPost(member1, channel1, "5", "천천히 생각해보니 인생이란...");
        TimeUnit.MILLISECONDS.sleep(100);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);

        List<Post> all = postRepository.findAll();

        Post findPost = postRepository.findPrevPage(post4.getId());
        Assertions.assertThat(findPost.getId()).isEqualTo(post3.getId());
    }

}
