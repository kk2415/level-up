package com.together.levelup.repository;

import com.together.levelup.domain.Post;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
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

    @Test
    public void 게시글_레포_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
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
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        List<Post> memberList = postRepository.findByMemberId(member1.getId());
        Assertions.assertThat(post3).isNotIn(memberList);
    }

    @Test
    public void 게시글_전체_개수() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
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
