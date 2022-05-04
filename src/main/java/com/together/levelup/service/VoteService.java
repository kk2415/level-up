package com.together.levelup.service;

import com.together.levelup.domain.vote.Vote;
import com.together.levelup.domain.comment.ArticleIdentity;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.PostRepository;
import com.together.levelup.repository.qna.QnaRepository;
import com.together.levelup.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final QnaRepository qnaRepository;

    public void save(ArticleIdentity identity, Long articleId, Long memberId) {
        Member findMember = memberRepository.findById(memberId);
        Object article = identifyArticle(identity, articleId);

        Vote vote = Vote.createVote(findMember, article);
        voteRepository.save(vote);
    }

    private Object identifyArticle(ArticleIdentity identity, Long articleId) {
        switch (identity) {
            case POST: return postRepository.findById(articleId);
            case QNA: return qnaRepository.findById(articleId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
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

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public void delete(Long id) {
        voteRepository.delete(id);
    }


}
