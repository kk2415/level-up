package com.together.levelup.api;

import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.Post;
import com.together.levelup.dto.*;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.exception.PostNotFoundException;
import com.together.levelup.service.MemberService;
import com.together.levelup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;

    /**
     * 생성
     * */
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Validated @RequestBody CreatePostRequest postRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionName.SESSION_NAME);

        if (member == null) {
            throw new MemberNotFoundException("가입된 회원만 글을 작성할 수 있습니다");
        }

        Long postId = postService.post(member.getId(), postRequest.getChannelId(), postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());

        Post findPost = postService.findOne(postId);

        String dateTime = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated());

        return new PostResponse(findPost.getTitle(), findPost.getWriter(),
                findPost.getContent(), findPost.getPostCategory(), dateTime,
                findPost.getVoteCount(), findPost.getViews(), findPost.getComments().size());
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

    @GetMapping("/post/{postId}")
    public PostResponse readPost(@PathVariable Long postId,
                                 @RequestParam(required = false, defaultValue = "false") String view) {
        Post findPost;

        findPost = postService.findOne(postId);
        if (view.equals("true")) {
            findPost = postService.readPost(postId);
        }

        return new PostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getViews(), findPost.getComments().size());
    }

    @GetMapping("/{channelId}/posts/{page}")
    public Result listingChannelPosts(@PathVariable Long channelId, @PathVariable int page,
                                  @RequestParam(required = false) String field, @RequestParam(required = false) String query) {
        PostSearch postSearch = null;
        if (field != null && query != null) {
            postSearch = new PostSearch(field, query);
        }

        List<Post> findPosts = postService.findByChannelId(channelId, page, postSearch);
        List<PostResponse> postResponses = findPosts.stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getWriter(), p.getContent(), p.getPostCategory(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(p.getDateCreated()),
                        p.getVoteCount(), p.getViews(), p.getComments().size()))
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
        Post findPost = postService.findByMemberId(findMember.getId()).get(0);

        if (findPost == null || !findPost.getId().equals(postId)) {
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
    public PostResponse updatePost(@PathVariable Long postId, UpdatePostRequest postRequest) {
        Member findMember = memberService.findByEmail(postRequest.getMemberEmail());
        postService.updatePost(postId, findMember.getId(), postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());

        Post findPost = postService.findOne(postId);

        return new PostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getPostCategory(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findPost.getDateCreated()),
                findPost.getVoteCount(), findPost.getVoteCount(), findPost.getComments().size());
    }
//
//    /**
//     * 삭제
//     * */
//    @DeleteMapping("/post/{postId}")
//    public PostResponse deletePost() {
//    }

}
