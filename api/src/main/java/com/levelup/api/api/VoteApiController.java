package com.levelup.api.api;


import com.levelup.api.service.VoteService;
import com.levelup.core.domain.comment.ArticleIdentity;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import com.levelup.core.dto.CreateVoteRequest;
import com.levelup.core.dto.Result;
import com.levelup.core.exception.DuplicateVoteException;
import com.levelup.core.exception.MemberNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Tag(name = "추천 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteApiController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity create(@RequestBody @Validated CreateVoteRequest voteRequest,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionName.SESSION_NAME) == null) {
            throw new MemberNotFoundException("가입된 회원만 추천 기능을 사용할 수 있습니다.");
        }
        Member member = (Member) session.getAttribute(SessionName.SESSION_NAME);

        List<Vote> findVotes = identifyArticle(voteRequest.getIdentity(), voteRequest.getArticleId(),
                member.getId());

        if (!findVotes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 게시글에 한 번만 할 수 있습니다.");
        }

        voteService.save(voteRequest.getIdentity(), voteRequest.getArticleId(), member.getId());
        return new ResponseEntity(new Result<>("추천되었습니다", 0), HttpStatus.CREATED);
    }

    private List<Vote> identifyArticle(ArticleIdentity identity, Long articleId, Long memberId) {
        switch (identity) {
            case POST: return voteService.findByPostAndMember(articleId, memberId);
            case QNA: return voteService.findByQnaAndMember(articleId, memberId);
            case COMMENT: return voteService.findByCommentAndMember(articleId, memberId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }
    }

}
