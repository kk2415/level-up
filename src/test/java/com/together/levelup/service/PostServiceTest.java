package com.together.levelup.service;

import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.PostCategory;
import com.together.levelup.repository.post.PostRepository;
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
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

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
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960",null );
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699",null );

        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "제목1", "내용1");
        Long postId2 = postService.post(member2.getId(), "제목2", "내용2");

        Post findPost1 = postService.findOne(postId1);
        Post findPost2 = postService.findOne(postId2);

        postService.updatePost(postId1, member1.getId(), "수정1", "수정 내용1", PostCategory.INFO);
        Assertions.assertThat(findPost1.getTitle()).isEqualTo("수정1");
        Assertions.assertThat(findPost1.getContent()).isEqualTo("수정 내용1");
        Assertions.assertThat(findPost1.getPostCategory()).isEqualTo(PostCategory.INFO);
    }

    @Test
    void deletePost() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "제목1", "내용1");
        Long postId2 = postService.post(member2.getId(), "제목2", "내용2");

        Post findPost1 = postService.findOne(postId1);
        Post findPost2 = postService.findOne(postId2);

        postService.deletePost(postId1);
        int count = postService.findAll().size();
        Assertions.assertThat(count).isEqualTo(1);
    }

}