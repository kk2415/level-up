package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.comment.ChannelCommentRepository;
import com.levelup.channel.domain.service.dto.ChannelCommentDto;
import com.levelup.channel.domain.service.dto.ChannelReplyCommentDto;
import com.levelup.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelCommentService {

    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelCommentRepository commentRepository;
    private final ChannelArticleRepository articleRepository;

    public ChannelCommentDto save(ChannelCommentDto dto, Long memberId, Long articleId, Long channelId) {
        final ChannelArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        final ChannelMember channelMember
                = channelMemberRepository.findByChannelIdAndMemberId(channelId, memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        final ChannelComment comment = dto.toEntity(channelMember, article);
       commentRepository.save(comment);

       return ChannelCommentDto.from(comment);
    }

    public ChannelCommentDto saveReply(ChannelCommentDto dto, Long memberId, Long parentId) {
        final ChannelComment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        final ChannelMember channelMember
                = channelMemberRepository.findByChannelIdAndMemberId(
                        parentComment.getChannelMember().getChannel().getId(), memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        final ChannelComment replyComment = dto.toEntity(channelMember, parentComment.getArticle());

        commentRepository.save(replyComment);
        parentComment.addReply(replyComment);

        return ChannelCommentDto.from(replyComment);
    }


    @Transactional(readOnly = true)
    public List<ChannelCommentDto> getComments(Long articleId) {
       final List<ChannelComment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(ChannelCommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<ChannelCommentDto> getReplyComments(Long commentId) {
        final List<ChannelComment> reply = commentRepository.findReplyByParentId(commentId);

        return reply.stream()
                .map(ChannelCommentDto::from)
                .collect(Collectors.toUnmodifiableList());
    }


    public void update(Long commentId, String content) {
        final ChannelComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        comment.update(content);
    }


    public void delete(Long commentId) {
        final ChannelComment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(findComment);
    }
}
