package com.levelup.api.dto.comment;

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

    private CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.writer = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(comment.getCreatedAt());
        this.voteCount = (long) comment.getCommentVotes().size();
        this.replyCount = (long) comment.getChild().size();
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment);
    }
}
