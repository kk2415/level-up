package com.levelup.api.service;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.PostCategory;
import com.levelup.core.dto.post.PostSearch;
import com.levelup.core.exception.PostNotFoundException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.post.PostRepository;
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
    private final ChannelRepository channelRepository;

    /**
     * 게시글 등록
     * */
    public Long create(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId);
        Post post = Post.createPost(member, title, content);
        postRepository.save(post);
        return post.getId();
    }

    public Long create(Long memberId, Long channelId, String title, String content, PostCategory postCategory) {
        Member member = memberRepository.findById(memberId);
        Channel channel = channelRepository.findById(channelId);

        Post post = Post.createPost(member, channel, title, content, postCategory);
        postRepository.save(post);
        return post.getId();
    }


    /**
     * 게시글 수정
     * */
    public void updatePost(Long postId, Long memberId, String title, String content, PostCategory category) {
        /**
         * @Transactional에 의해서 commit이 진행됨
         * 커밋이 딱 되면 jpa가 플러쉬를 날린다.
         * 그러면 엔티티 중에서 변경감지가 일어난 애를 다 찾는다.
         * 그러면 UPDATE 쿼리를 바뀐 엔티티에 맞게 DB에 알아서 날려준다
         * */
        List<Post> findPosts = postRepository.findByMemberId(memberId);
        if (findPosts == null) {
            throw new PostNotFoundException("작성한 게시글이 없습니다");
        }

        Post post = findPosts.stream()
                .filter(p -> p.getId().equals(postId))
                .findAny()
                .orElse(null);

        if (post == null) {
            throw new PostNotFoundException("작성한 게시글이 없습니다");
        }

        post.changePost(title, content, category);
    }

    public void addVoteCount(Post findPost) {
        findPost.addVoteCount();
    }

    public void addViews(Post findPost) {
        findPost.addViews();
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
    public Post findById(Long postId) {
        return postRepository.findById(postId);
    }

    public Post readPost(Long postId) {
        Post findPost = postRepository.findById(postId);
        findPost.addViews();
        return  findPost;
    }

    public List<Post> findByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByChannelId(Long channelId, PostSearch postSearch) {
        return postRepository.findByChannelId(channelId, postSearch);
    }

    public List<Post> findByChannelId(Long channelId, int page, int postCount, PostSearch postSearch) {
        return postRepository.findByChannelId(channelId, page, postCount, postSearch);
    }

    public Post findNextPage(Long id) {
        return postRepository.findNextPage(id);
    }

    public Post findPrevPage(Long id) {
        return postRepository.findPrevPage(id);
    }



    /**
     * 게시글 권한
     * */
    public Long oauth(Long postId, String email) {
        Member findMember = memberRepository.findByEmail(email);
        List<Post> findPosts = findByMemberId(findMember.getId());

        long count = findMember.getPosts().stream().map(p -> p.getId().equals(postId)).count();
        if (count <= 0) {
            throw new PostNotFoundException("게시글의 대한 권한이 없습니다");
        }
        return postId;
    }

}
