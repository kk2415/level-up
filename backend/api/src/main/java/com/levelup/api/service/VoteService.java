package com.levelup.api.service;


import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.domain.vote.Vote;
import com.levelup.core.exception.DuplicateVoteException;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.post.PostRepository;
import com.levelup.core.repository.qna.QnaRepository;
import com.levelup.core.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final QnaRepository qnaRepository;

    public Long create(ArticleType identity, Long articleId, Long memberId) {
        Object article = identifyArticle(identity, articleId);
        Member findMember = memberRepository.findById(memberId);

        validDuplicateVote(identity, articleId, memberId);

        Vote vote = Vote.createVote(findMember, article);
        voteRepository.save(vote);

        addVoteCount(article, articleId);
        return vote.getId();
    }

    private void validDuplicateVote(ArticleType identity, Long articleId, Long memberId) {
        List<Vote> votes;

        switch (identity) {
            case POST: votes = voteRepository.findByPostAndMember(articleId, memberId); break;
            case QNA: votes = voteRepository.findByQnaAndMember(articleId, memberId); break;
            case COMMENT: votes = voteRepository.findByCommentAndMember(articleId, memberId); break;
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }

        if (!votes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 번만 할 수 있습니다!");
        }
    }

    private Object identifyArticle(ArticleType identity, Long articleId) {
        switch (identity) {
            case POST: return postRepository.findById(articleId);
            case QNA: return qnaRepository.findById(articleId);
            case COMMENT: return commentRepository.findById(articleId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }
    }

    private void addVoteCount (Object article, Long articleId) {
        if (article instanceof Post) {
            postRepository.findById(articleId).get().addVoteCount();
        }
        else if (article instanceof Qna) {
            qnaRepository.findById(articleId).addVoteCount();
        }
        else if (article instanceof Comment) {
            commentRepository.findById(articleId).addVoteCount();
        }
    }

    public Vote findById(Long id) {
        return voteRepository.findById(id);
    }

    public List<Vote> findByPostAndMember(Long pottId, Long memberId) {
        return voteRepository.findByPostAndMember(pottId, memberId);
    }

    public List<Vote> findByQnaAndMember(Long qnaId, Long memberId) {
        return voteRepository.findByQnaAndMember(qnaId, memberId);
    }

    public List<Vote> findByCommentAndMember(Long commentId, Long memberId) {
        return voteRepository.findByCommentAndMember(commentId, memberId);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public void delete(Long id) {
        voteRepository.delete(id);
    }


}
