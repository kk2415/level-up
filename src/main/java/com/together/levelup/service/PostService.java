package com.together.levelup.service;

import com.together.levelup.domain.Post;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 등록
     * */
    public Long post(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId);
        Post post = Post.createPost(member, title, content);
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 수정
     * */
    public void updatePost(Long postId, String title, String content) {
        /**
         * @Transactional에 의해서 commit이 진행됨
         * 커밋이 딱 되면 jpa가 플러쉬를 날린다.
         * 그러면 엔티티 중에서 변경감지가 일어난 애를 다 찾는다.
         * 그러면 UPDATE 쿼리를 바뀐 엔티티에 맞게 DB에 알아서 날려준다
         * */
        Post post = postRepository.findById(postId);
        post.changePost(title, content);
    }

    /**
     * 게시글 삭제
     * */
    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    /**
     * 게시글 조회
     * */
    public Post findOne(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

}
