package com.together.levelup.service;

import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.post.Post;
import com.together.levelup.exception.DuplicateEmailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class memberServiceTest {

    @Autowired
    private MemberService memberService;

    private Member member1;
    private Member member2;

    private Channel channel1;

    private Post post1;
    private Post post2;
    private Post post3;

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", new UploadFile("default", FileStore.MEMBER_DEFAULT_IMAGE));
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", new UploadFile("default", FileStore.MEMBER_DEFAULT_IMAGE));

        channel1 = Channel.createChannel(member1, "우당탕탕 스프링 개발기", 20L, "요리 친목도모",
                "요리 친목도모", ChannelCategory.STUDY,
                new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));

        post1 = Post.createPost(member1, channel1, "0", "안녕하세요. 첫 게시글입니다");
        post2 = Post.createPost(member1, channel1, "1", "추천 받음");
        post3 = Post.createPost(member2, channel1, "2", "천천히 생각해보니 인생이란...");

        comment1 = Comment.createComment(member1, post1, "안녕하세요~~");
        comment2 = Comment.createComment(member2, post2, "황금올리브 추천");
        comment3 = Comment.createComment(member2, post3, "그렇군요....");
    }

    @Test
    public void 회원가입() {
        memberService.join(member1);
        memberService.join(member2);

        Member findMember1 = memberService.findOne(member1.getId());
        Member findMember2 = memberService.findOne(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);
    }

    @Test
    public void 중복_회원_테스트() {
        memberService.join(member1);
        Member member2 = Member.createMember("test0",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", new UploadFile("default", FileStore.MEMBER_DEFAULT_IMAGE));

        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(DuplicateEmailException.class);
    }

}
