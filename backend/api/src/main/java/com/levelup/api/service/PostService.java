package com.levelup.api.service;

import com.levelup.api.config.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.FileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.PostCategory;
import com.levelup.core.dto.post.CreatePostRequest;
import com.levelup.core.dto.post.PostResponse;
import com.levelup.core.dto.post.SearchCondition;
import com.levelup.core.dto.post.UpdatePostResponse;
import com.levelup.core.exception.PostNotFoundException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final FileStore fileStore;


    /**
     * 게시글 등록
     * */
    public PostResponse create(CreatePostRequest postRequest, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Channel channel = channelRepository.findById(postRequest.getChannelId());

        Post findPost = postRequest.toEntity(channel, member);

        postRepository.save(findPost);

        return new PostResponse(
                findPost.getId(),
                findPost.getMember().getId(),
                findPost.getTitle(),
                findPost.getWriter(),
                findPost.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(),
                findPost.getViews(),
                findPost.getComments().size(),
                findPost.getPostCategory()
        );
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.POST, file);
    }


    /**
     * 게시글 수정
     * */
    public UpdatePostResponse updatePost(Long postId, Long memberId, String title, String content, PostCategory category) {
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

        Post findPost = findPosts.stream()
                .filter(p -> p.getId().equals(postId))
                .findAny()
                .orElse(null);

        if (findPost == null) {
            throw new PostNotFoundException("작성한 게시글이 없습니다");
        }

        findPost.changePost(title, content, category);

        return new UpdatePostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(),
                findPost.getPostCategory());
    }

    public void addVoteCount(Post findPost) {
        findPost.addVoteCount();
    }

    public void addViews(Post findPost) {
        findPost.addViews();
    }


    /**
     * 게시글 조회
     * */
    public PostResponse getPost(Long postId, String view) {
        Post findPost = postRepository.findById(postId);

        if (view.equals("true")) {
            findPost.addViews();;
        }

        return new PostResponse(findPost.getId(), findPost.getMember().getId(), findPost.getTitle(), findPost.getWriter(),
                findPost.getContent(), DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(),
                (int) findPost.getComments().stream().filter(c -> c.getParent() == null).count(), findPost.getPostCategory());
    }

    public List<PostResponse> getPosts(Long channelId, int page, int postCount, String field, String query) {
        SearchCondition searchCondition = new SearchCondition(field, query);
        List<Post> findPosts = postRepository.findByChannelId(channelId, page, postCount, searchCondition);

        return findPosts.stream()
                .map(p -> new PostResponse(p.getId(), p.getMember().getId(), p.getTitle(), p.getWriter(), p.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(p.getDateCreated()), p.getVoteCount(),
                        p.getViews(), (int) p.getComments().stream().filter(c -> c.getParent() == null).count(),
                        p.getPostCategory()))
                .collect(Collectors.toList());
    }

    public Long getPostsCount(Long channelId, String field, String query) {
        SearchCondition searchCondition = null;
        if (field != null && query != null) {
            searchCondition = new SearchCondition(field, query);
        }

        List<Post> post = postRepository.findByChannelId(channelId, searchCondition);

        return (long)post.size();
    }

    public PostResponse findNextPage(Long id) {
        Post findPost = postRepository.findNextPage(id);

        if (findPost == null) {
            throw new PostNotFoundException();
        }

        return new PostResponse(findPost.getId(), findPost.getMember().getId(), findPost.getTitle(), findPost.getWriter(),
                findPost.getContent(), DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(),
                (int) findPost.getComments().stream().filter(c -> c.getParent() == null).count(), findPost.getPostCategory());
    }

    public PostResponse findPrevPage(Long id) {
        Post findPost = postRepository.findPrevPage(id);

        if (findPost == null) {
            throw new PostNotFoundException();
        }

        return new PostResponse(findPost.getId(), findPost.getMember().getId(), findPost.getTitle(), findPost.getWriter(),
                findPost.getContent(), DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(),
                (int) findPost.getComments().stream().filter(c -> c.getParent() == null).count(), findPost.getPostCategory());
    }

    public List<Post> findByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByChannelId(Long channelId, SearchCondition postSearch) {
        return postRepository.findByChannelId(channelId, postSearch);
    }

    public List<Post> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch) {
        return postRepository.findByChannelId(channelId, page, postCount, postSearch);
    }


    /**
     * 게시글 삭제
     * */
    public void deletePost(Long postId) {
        postRepository.delete(postId);
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
