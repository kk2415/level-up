package com.together.levelup.service;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired private CommentService commentService;
    @Autowired private MemberService memberService;
    @Autowired private PostService postService;

    @Test
    void commenting() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Long postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        Long postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        Long commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        Long commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        Long commentId3 = commentService.comment(member1.getId(), postId3, "오호...");

        Comment findComment1 = commentService.findOne(commentId1);
        Comment findComment2 = commentService.findOne(commentId2);
        Comment findComment3 = commentService.findOne(commentId3);

        Assertions.assertThat(findComment1.getContent()).isEqualTo("방가방가!");
        Assertions.assertThat(findComment2.getContent()).isEqualTo("불고기 추천함!");
        Assertions.assertThat(findComment3.getContent()).isEqualTo("오호...");
    }

    @Test
    void updateComment() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Long postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        Long postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        Long commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        Long commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        Long commentId3 = commentService.comment(member1.getId(), postId3, "오호...");

        commentService.updateComment(commentId1, "안녕하세요!");

        Comment findComment1 = commentService.findOne(commentId1);
        Assertions.assertThat(findComment1.getContent()).isEqualTo("안녕하세요!");
    }

    @Test
    void deleteComment() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Long postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        Long postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        Long commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        Long commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        Long commentId3 = commentService.comment(member1.getId(), postId3, "오호...");

        commentService.deleteComment(commentId1);

        List<Comment> commentList = commentService.findAll();
        Assertions.assertThat(commentList.size()).isEqualTo(2);
    }

    @Test
    void findByMemberId() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Long postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        Long postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        Long commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        Long commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        Long commentId3 = commentService.comment(member1.getId(), postId3, "오호...");

        List<Comment> commentList = commentService.findByMemberId(member1.getId());
        Assertions.assertThat(commentList.size()).isEqualTo(1);
    }

    @Test
    void findByPostId() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");
        memberService.join(member1);
        memberService.join(member2);

        Long postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Long postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        Long postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        Long commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        Long commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        Long commentId3 = commentService.comment(member1.getId(), postId3, "오호...");

        List<Comment> commentList = commentService.findByPostId(postId1);
        Assertions.assertThat(commentList.size()).isEqualTo(1);
    }

}