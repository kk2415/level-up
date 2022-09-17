package com.levelup.api.service;

import com.levelup.api.service.dto.comment.ReplyCommentDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.api.service.dto.comment.CommentDto;
import com.levelup.api.exception.comment.CommentNotFoundException;
import com.levelup.api.exception.member.MemberNotFoundException;
import com.levelup.api.exception.article.ArticleNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    public CommentDto save(CommentDto dto, Long memberId) {
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
       final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
       final Comment findComment = dto.toEntity(findMember, article);

       commentRepository.save(findComment);

       return CommentDto.from(findComment);
    }

    public ReplyCommentDto saveReply(ReplyCommentDto dto, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        final Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
        Comment parentComment = commentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
        Comment replyComment = dto.toEntity(member, article);

        commentRepository.save(replyComment);
        parentComment.addChildComment(replyComment);

        return ReplyCommentDto.from(replyComment);
    }



    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Long articleId) {
       final List<Comment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(CommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getReplyComments(Long commentId) {
        final List<Comment> reply = commentRepository.findReplyByParentId(commentId);

        return reply.stream()
                .map(CommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }



    public void update(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        findComment.changeComment(content);
    }



    public void delete(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(findComment);
    }
}
