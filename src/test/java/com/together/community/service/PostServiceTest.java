package com.together.community.service;

import com.together.community.domain.Post;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

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
    @Commit
    void updatePost() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

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
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

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