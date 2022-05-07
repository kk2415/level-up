package com.together.levelup.api;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.file.ImageType;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.Post;
import com.together.levelup.dto.*;
import com.together.levelup.dto.post.*;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.exception.NotLoggedInException;
import com.together.levelup.exception.PostNotFoundException;
import com.together.levelup.service.FileService;
import com.together.levelup.service.MemberService;
import com.together.levelup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;
    private final FileStore fileStore;
    private final FileService fileService;

    /**
     * 생성
     * */
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Validated @RequestBody CreatePostRequest postRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionName.SESSION_NAME) == null) {
            throw new NotLoggedInException("가입된 회원만 글을 작성할 수 있습니다");
        }

        Member member = (Member) session.getAttribute(SessionName.SESSION_NAME);

        Long postId = postService.post(member.getId(), postRequest.getChannelId(), postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());
        Post findPost = postService.findById(postId);

        for (UploadFile uploadFile : postRequest.getUploadFiles()) {
            fileService.create(findPost, uploadFile);
        }

        String dateTime = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated());
        return new PostResponse(findPost.getTitle(), findPost.getWriter(),
                findPost.getContent(), findPost.getPostCategory(), dateTime,
                findPost.getVoteCount(), findPost.getViews(), findPost.getComments().size());
    }

    @PostMapping("/post/files")
    public ResponseEntity storeFiles(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return new ResponseEntity("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        UploadFile uploadFiles = fileStore.storeFile(ImageType.POST, file);

        String storeFileName = uploadFiles.getStoreFileName();
        return new ResponseEntity(storeFileName, HttpStatus.OK);
    }

    /**
     * 조회
     * */
    @GetMapping("/posts")
    public Result findAllPost() {
        List<Post> findPosts = postService.findAll();

        List<PostResponse> postResponses = findPosts.stream()
                .map(p -> new PostResponse(p.getTitle(), p.getWriter(), p.getContent(), p.getPostCategory(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(p.getDateCreated()),
                        p.getVoteCount(), p.getViews(), p.getComments().size()))
                .collect(Collectors.toList());

        return new Result(postResponses, postResponses.size());
    }

    @GetMapping("/{channelId}/search/posts-size")
    public int findAllPostWithQuery(@PathVariable Long channelId,
                                       @RequestParam(required = false) String field,
                                       @RequestParam(required = false) String query) {
        PostSearch postSearch = null;
        if (field != null && query != null) {
            postSearch = new PostSearch(field, query);
        }
        List<Post> findPosts = postService.findByChannelId(channelId, postSearch);

        return findPosts.size();
    }

    @GetMapping("/post/{postId}")
    public PostResponse readPost(@PathVariable Long postId,
                                 @RequestParam(required = false, defaultValue = "false") String view) {
        Post findPost = postService.findById(postId);

        if (view.equals("true")) {
            postService.addViews(findPost);
        }

        return new PostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(),
                (int) findPost.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/{channelId}/posts/{page}")
    public Result listingChannelPosts(@PathVariable Long channelId,
                                      @PathVariable int page,
                                      @RequestParam(required = false) String field,
                                      @RequestParam(required = false) String query) {

        PostSearch postSearch = null;
        if (field != null && query != null) {
            postSearch = new PostSearch(field, query);
        }

        List<Post> findPosts = postService.findByChannelId(channelId, page, postSearch);
        List<PostResponse> postResponses = findPosts.stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getWriter(), p.getContent(), p.getPostCategory(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(p.getDateCreated()),
                        p.getVoteCount(), p.getViews(),
                        (int) p.getComments().stream().filter(c -> c.getParent() == null).count()))
                .collect(Collectors.toList());

        return new Result(postResponses, postResponses.size());
    }

    @GetMapping("/post/{postId}/nextPost")
    public PostResponse findNextPost(@PathVariable Long postId) {
        Post nextPage = postService.findNextPage(postId);

        return new PostResponse(nextPage.getId(), nextPage.getTitle(), nextPage.getWriter(), nextPage.getContent(), nextPage.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getDateCreated()),
                nextPage.getVoteCount(), nextPage.getViews(), nextPage.getComments().size());
    }

    @GetMapping("/post/{postId}/prevPost")
    public PostResponse findPrevPost(@PathVariable Long postId) {
        Post prevPage = postService.findPrevPage(postId);

        if (prevPage == null) {
            throw new PostNotFoundException();
        }

        return new PostResponse(prevPage.getId(), prevPage.getTitle(), prevPage.getWriter(), prevPage.getContent(), prevPage.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getDateCreated()),
                prevPage.getVoteCount(), prevPage.getViews(), prevPage.getComments().size());
    }

    @GetMapping("/post/{postId}/check-member")
    public PostResponse findPostByMemberId(@PathVariable Long postId, @RequestParam String email) {
        Member findMember = memberService.findByEmail(email);
        Post findPost = postService.findById(postId);
        List<Post> findPosts = postService.findByMemberId(findMember.getId());

        if (findPosts == null) {
            throw new PostNotFoundException("게시글의 대한 권한이 없습니다");
        }

        long count = findPosts.stream().map(p -> p.getId().equals(postId)).count();

        if (count <= 0) {
            throw new PostNotFoundException("게시글의 대한 권한이 없습니다");
        }

        return new PostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(), findPost.getComments().size());
    }

    /**
     * 수정
     * */
    @PatchMapping("/post/{postId}")
    public UpdatePostResponse updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest postRequest) {
        Member findMember = memberService.findByEmail(postRequest.getMemberEmail());

        postService.updatePost(postId, findMember.getId(), postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());
        Post findPost = postService.findById(postId);

        fileService.deleteByPostId(findPost.getId());
        for (UploadFile uploadFile : postRequest.getUploadFiles()) {
            fileService.create(findPost, uploadFile);
        }

        return new UpdatePostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getPostCategory());
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return new ResponseEntity(HttpStatus.OK);
    }

}
