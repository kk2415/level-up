package com.together.levelup.api;

import com.together.levelup.domain.comment.ArticleIdentity;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.vote.Vote;
import com.together.levelup.dto.CreateVoteRequest;
import com.together.levelup.dto.Result;
import com.together.levelup.exception.DuplicateVoteException;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
        return new ResponseEntity(new Result("추천되었습니다", 0), HttpStatus.CREATED);
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
