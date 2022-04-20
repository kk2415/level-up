package com.together.levelup.domain;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private EntityManager em;

    @Test
    public void memberTest() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Member findMember1 = em.find(Member.class, member1.getId());
        Member findMember2 = em.find(Member.class, member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);

    }

    @Test
    public void postTest() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Post findPost1 = em.find(Post.class, post1.getId());
        Post findPost2 = em.find(Post.class, post2.getId());
        Post findPost3 = em.find(Post.class, post3.getId());

        Assertions.assertThat(post1).isEqualTo(findPost1);
        Assertions.assertThat(post2).isEqualTo(findPost2);
        Assertions.assertThat(post3).isEqualTo(findPost3);
    }

    @Test
    public void commentTest() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Post post1 = Post.createPost(member1, "헬로 방가", "안녕하세요. 첫 게시글입니다");
        Post post2 = Post.createPost(member1, "저녁 뭐 먹지?", "추천 받음");
        Post post3 = Post.createPost(member2, "인생에 대한 고찰", "천천히 생각해보니 인생이란...");

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Comment comment1 = Comment.createComment(member2, post1, "안녕하세요 ㅎㅎ");
        Comment comment2 = Comment.createComment(member2, post2, "뿌링클 추천!");
        Comment comment3 = Comment.createComment(member1, post3, "그렇군요...");

        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);

        Comment findComment1 = em.find(Comment.class, comment1.getId());
        Comment findComment2 = em.find(Comment.class, comment2.getId());
        Comment findComment3 = em.find(Comment.class, comment3.getId());

        Assertions.assertThat(comment1).isEqualTo(findComment1);
        Assertions.assertThat(comment2).isEqualTo(findComment2);
        Assertions.assertThat(comment3).isEqualTo(findComment3);
    }

}
