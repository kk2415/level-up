package com.together.levelup.repository;

import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.dto.PostSearch;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    public void 게시글_레포_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699",null );
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
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
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모");
        channelRepository.save(channel);

        Post post1 = Post.createPost(member1, channel, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, channel, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, channel, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        List<Post> memberList = postRepository.findByMemberId(member1.getId());
        Assertions.assertThat(post3).isNotIn(memberList);
    }

    @Test
    public void 채널별_게시글_조회() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel1 = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모");
        Channel channel2 = Channel.createChannel(member2, "철학토크", 20L, "철학쓰");
        channelRepository.save(channel1);
        channelRepository.save(channel2);

        Post post1 = Post.createPost(member1, channel1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, channel1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, channel2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        List<Post> findPosts = postRepository.findByChannelId(channel1.getId());
        Assertions.assertThat(findPosts.size()).isEqualTo(2);
    }

    @Test
    public void 채널게시글_페이징_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel1 = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모");
        channelRepository.save(channel1);

        Post post1 = Post.createPost(member1, channel1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, channel1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post4 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post5 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post6 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post7 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post8 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post9 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post10 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post11 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post12 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post13 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post14 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        Post post15 = Post.createPost(member2, channel1, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

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

        List<Post> findPosts2 = postRepository.findByChannelId(channel1.getId(), 1, new PostSearch("writer", "김경희"));
        Assertions.assertThat(findPosts2.size()).isEqualTo(2);
    }

    @Test
    public void 게시글_전체_개수() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699",null );
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Long count = postRepository.countAll();
        Assertions.assertThat(count).isEqualTo(3L);
    }

}
