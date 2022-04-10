package com.together.community.repository.comment;

import com.together.community.domain.Comment;
import com.together.community.domain.Post;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.repository.member.MemberRepository;
import com.together.community.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Test
    void save() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Comment comment1 = Comment.createComment(member2, post1, "안녕하세요~~");
        Comment comment2 = Comment.createComment(member2, post2, "황금올리브 추천");
        Comment comment3 = Comment.createComment(member1, post3, "그렇군요....");

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

        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Comment comment1 = Comment.createComment(member2, post1, "안녕하세요~~");
        Comment comment2 = Comment.createComment(member2, post2, "황금올리브 추천");
        Comment comment3 = Comment.createComment(member1, post3, "그렇군요....");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        List<Comment> findMembers1 = commentRepository.findByMemberId(member1.getId());
        List<Comment> findMembers2 = commentRepository.findByMemberId(member2.getId());

        Assertions.assertThat(findMembers1.size()).isEqualTo(1);
        Assertions.assertThat(findMembers2.size()).isEqualTo(2);
    }

    @Test
    void findByPostId() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Comment comment1 = Comment.createComment(member2, post1, "안녕하세요~~");
        Comment comment2 = Comment.createComment(member2, post2, "황금올리브 추천");
        Comment comment3 = Comment.createComment(member1, post3, "그렇군요....");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        List<Comment> findPosts = commentRepository.findByPostId(post1.getId());
        Assertions.assertThat(findPosts.size()).isEqualTo(1);
    }

    @Test
    void delete() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Comment comment1 = Comment.createComment(member2, post1, "안녕하세요~~");
        Comment comment2 = Comment.createComment(member2, post2, "황금올리브 추천");
        Comment comment3 = Comment.createComment(member1, post3, "그렇군요....");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        commentRepository.delete(comment1.getId());
        Long count = commentRepository.countAll();
        Assertions.assertThat(count).isEqualTo(2);
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