package com.together.levelup.service;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.UploadFile;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.PostCategory;
import com.together.levelup.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    void posting() {
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