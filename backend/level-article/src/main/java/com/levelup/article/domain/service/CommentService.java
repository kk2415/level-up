package com.levelup.article.domain.service;

import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.article.domain.service.dto.CommentDto;
import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.member.domain.entity.Member;
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
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
       final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
       final ArticleComment findComment = dto.toEntity(findMember, article);

       commentRepository.save(findComment);

       return CommentDto.from(findComment);
    }

    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ReplyCommentDto saveReply(ReplyCommentDto dto, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        final Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
        ArticleComment parentComment = commentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
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
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        findComment.changeComment(content);
    }



    public void delete(Long commentId, ArticleType articleType) {
        ArticleComment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(findComment);
    }
}
