package com.levelup.api.service;


import com.levelup.core.domain.comment.ArticleIdentity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.domain.vote.Vote;
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

    public Long save(ArticleIdentity identity, Long articleId, Long memberId) {
        Object article = identifyArticle(identity, articleId);
        Member findMember = memberRepository.findById(memberId);

        Vote vote = Vote.createVote(findMember, article);

        voteRepository.save(vote);
        addVoteCount(article, articleId);

        return vote.getId();
    }

    private Object identifyArticle(ArticleIdentity identity, Long articleId) {
        switch (identity) {
            case POST: return postRepository.findById(articleId);
            case QNA: return qnaRepository.findById(articleId);
            case COMMENT: return commentRepository.findById(articleId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }
    }

    private void addVoteCount (Object article, Long articleId) {
        if (article instanceof Post) {
            postRepository.findById(articleId).addVoteCount();
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

    public List<Vote> findByPostId(Long postId) {
        return voteRepository.findByPostId(postId);
    }

    public List<Vote> findByQnaId(Long qnaId) {
        return voteRepository.findByQnaId(qnaId);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public void delete(Long id) {
        voteRepository.delete(id);
    }


}
