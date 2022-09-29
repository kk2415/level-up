package com.levelup.article.domain.service;

import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.article.domain.service.dto.CommentDto;
import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.article.exception.ArticleNotFoundException;
import com.levelup.article.exception.CommentNotFoundException;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.exception.MemberNotFoundException;
import com.levelup.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public CommentDto save(CommentDto dto, Long memberId) {
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
       final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
       final ArticleComment findComment = dto.toEntity(findMember, article);

       commentRepository.save(findComment);

       return CommentDto.from(findComment);
    }

    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ReplyCommentDto saveReply(ReplyCommentDto dto, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        final Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
        ArticleComment parentComment = commentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
        ArticleComment replyComment = dto.toEntity(member, article);

        commentRepository.save(replyComment);
        parentComment.addChildComment(replyComment);

        return ReplyCommentDto.from(replyComment);
    }



    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Long articleId) {
       final List<ArticleComment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(CommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getReplyComments(Long commentId) {
        final List<ArticleComment> reply = commentRepository.findReplyByParentId(commentId);

        return reply.stream()
                .map(CommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }



    public void update(Long commentId, String content) {
        ArticleComment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        findComment.changeComment(content);
    }



    public void delete(Long commentId, ArticleType articleType) {
        ArticleComment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(findComment);
    }
}
