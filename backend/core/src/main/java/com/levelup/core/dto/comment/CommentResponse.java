package com.levelup.core.dto.comment;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private Long memberId;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long replyCount;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(comment.getCreateAt());
        this.voteCount = comment.getVoteCount();
        this.replyCount = comment.getReplyCount();
    }

}
