package com.together.levelup.repository;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.UploadFile;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.comment.CommentRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ChannelRepository channelRepository;

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
    void save() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Comment findComment1 = commentRepository.findById(comment1.getId());
        Comment findComment2 = commentRepository.findById(comment2.getId());
        Comment findComment3 = commentRepository.findById(comment3.getId());

        Assertions.assertThat(findComment1).isEqualTo(comment1);
        Assertions.assertThat(findComment2).isEqualTo(comment2);
        Assertions.assertThat(findComment3).isEqualTo(comment3);
    }

    @Test
    void findByMemberId() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        List<Comment> findComments1 = commentRepository.findByMemberId(member1.getId());
        List<Comment> findComments2 = commentRepository.findByMemberId(member2.getId());

        Assertions.assertThat(findComments1.size()).isEqualTo(1);
        Assertions.assertThat(findComments2.size()).isEqualTo(2);
    }

    @Test
    void findByPostId() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        List<Comment> findPosts = commentRepository.findByPostId(post1.getId());
        Assertions.assertThat(findPosts.size()).isEqualTo(1);
    }

    @Test
    void delete() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        channelRepository.save(channel1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        commentRepository.delete(comment1.getId());
        Long count = commentRepository.countAll();
        Assertions.assertThat(count).isEqualTo(2);
    }

}