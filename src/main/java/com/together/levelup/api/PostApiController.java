package com.together.levelup.api;

import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.Post;
import com.together.levelup.dto.*;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/posts")
    public Result findAllPost() {
        List<Post> findPosts = postService.findAll();

        List<PostResponse> postResponses = findPosts.stream()
                .map(p -> new PostResponse(p.getTitle(), p.getWriter(), p.getContent(),
                        p.getDateCreated(), p.getVoteCount(), p.getComments().size()))
                .collect(Collectors.toList());

        return new Result(postResponses, postResponses.size());
    }

    @GetMapping("/{channelId}/posts/{page}")
    public Result findByChannelId(@PathVariable Long channelId, @PathVariable int page,
                                  @RequestBody PostSearch postSearch) {
        List<Post> findPosts = postService.findByChannelId(channelId, page, postSearch);

        List<PostResponse> postResponses = findPosts.stream()
                .map(p -> new PostResponse(p.getTitle(), p.getWriter(), p.getContent(),
                        p.getDateCreated(), p.getVoteCount(), p.getComments().size()))
                .collect(Collectors.toList());

        return new Result(postResponses, postResponses.size());
    }

    @PostMapping("/post")
    public PostResponse posting(@Validated @RequestBody CreatePostRequest postRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionName.SESSION_NAME);

        if (member == null) {
            throw new MemberNotFoundException("가입된 회원만 글을 작성할 수 있습니다");
        }

        Long postId = postService.post(member.getId(), postRequest.getChannelId(), postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());

        Post findPost = postService.findOne(postId);
        return new PostResponse(findPost.getTitle(), findPost.getWriter(), findPost.getContent(), findPost.getDateCreated(),
                findPost.getVoteCount(), findPost.getComments().size());
    }

}
