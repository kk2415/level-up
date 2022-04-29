package com.together.levelup.service;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    private Member member1;
    private Member member2;

    private Long postId1;
    private Long postId2;
    private Long postId3;

    private Long commentId1;
    private Long commentId2;
    private Long commentId3;

    @BeforeEach
    public void 채널_생성_테스트() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960",null );
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        memberService.join(member1);
        memberService.join(member2);

        postId1 = postService.post(member1.getId(), "헬로 방가", "안녕하세요. 첫 게시글입니다");
        postId2 = postService.post(member1.getId(), "저녁 뭐 먹지?", "추천");
        postId3 = postService.post(member2.getId(), "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        commentId1 = commentService.comment(member2.getId(), postId1, "방가방가!");
        commentId2 = commentService.comment(member2.getId(), postId2, "불고기 추천함!");
        commentId3 = commentService.comment(member1.getId(), postId3, "오호...");
    }

    @Test
    void commenting() {
        Comment findComment1 = commentService.findOne(commentId1);
        Comment findComment2 = commentService.findOne(commentId2);
        Comment findComment3 = commentService.findOne(commentId3);

        Assertions.assertThat(findComment1.getContent()).isEqualTo("방가방가!");
        Assertions.assertThat(findComment2.getContent()).isEqualTo("불고기 추천함!");
        Assertions.assertThat(findComment3.getContent()).isEqualTo("오호...");
    }

    @Test
    void updateComment() {
        commentService.updateComment(commentId1, "안녕하세요!");

        Comment findComment1 = commentService.findOne(commentId1);
        Assertions.assertThat(findComment1.getContent()).isEqualTo("안녕하세요!");
    }

    @Test
    void deleteComment() {
        commentService.deleteComment(commentId1);

        List<Comment> commentList = commentService.findAll();
        Assertions.assertThat(commentList.size()).isEqualTo(2);
    }

    @Test
    void findByMemberId() {
        List<Comment> commentList = commentService.findByMemberId(member1.getId());
        Assertions.assertThat(commentList.size()).isEqualTo(1);
    }

    @Test
    void findByPostId() {
        List<Comment> commentList = commentService.findByPostId(postId1);
        Assertions.assertThat(commentList.size()).isEqualTo(1);
    }

}