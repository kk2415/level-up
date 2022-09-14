package com.levelup.api.dto.response.comment;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.comment.Comment;
import com.levelup.api.dto.service.comment.CommentDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
public class CommentResponse {

    private Long commentId;
    private Long memberId;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long replyCount;

    protected CommentResponse() {}

    private CommentResponse(Long commentId,
                            Long memberId,
                            String writer,
                            String content,
                            String dateCreated,
                            Long voteCount,
                            Long replyCount) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.writer = writer;
        this.content = content;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
    }

    public static CommentResponse from(CommentDto dto) {
        return new CommentResponse(
                dto.getCommentId(),
                dto.getMemberId(),
                dto.getWriter(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getReplyCount()
        );
    }
}
