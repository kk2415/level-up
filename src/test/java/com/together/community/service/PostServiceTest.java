package com.together.community.service;

import com.together.community.domain.Post;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    void posting() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "제목1", "내용1");
        Long postId2 = postService.post(member2.getId(), "제목2", "내용2");

        Post findPost1 = postService.findOne(postId1);
        Post findPost2 = postService.findOne(postId2);

        Assertions.assertThat(findPost1.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(findPost2.getTitle()).isEqualTo("제목2");
    }

    @Test
    void updatePost() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "제목1", "내용1");
        Long postId2 = postService.post(member2.getId(), "제목2", "내용2");

        Post findPost1 = postService.findOne(postId1);
        Post findPost2 = postService.findOne(postId2);

        postService.updatePost(postId1, "수정1", "수정 내용1");
        Assertions.assertThat(findPost1.getTitle()).isEqualTo("수정1");
        Assertions.assertThat(findPost1.getContent()).isEqualTo("수정 내용1");
    }

    @Test
    void deletePost() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "제목1", "내용1");
        Long postId2 = postService.post(member2.getId(), "제목2", "내용2");

        Post findPost1 = postService.findOne(postId1);
        Post findPost2 = postService.findOne(postId2);

        postService.deletePost(postId1);
        Long count = postRepository.countAll();
        Assertions.assertThat(count).isEqualTo(1L);

    }

}