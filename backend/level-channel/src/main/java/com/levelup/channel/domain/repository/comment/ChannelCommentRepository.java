package com.levelup.channel.domain.repository.comment;

import com.levelup.channel.domain.entity.ChannelComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelCommentRepository extends JpaRepository<ChannelComment, Long> {

    List<ChannelComment> findByArticleId(Long articleId);
    List<ChannelComment> findReplyByParentId(Long commentId);
}
