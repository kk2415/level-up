package com.levelup.core.dto.post;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.PostCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePostRequest {

    @NotNull
    private Long channelId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private PostCategory category;

    private List<UploadFile> uploadFiles;

    public Post toEntity(Channel channel, Member member) {
        return Post.builder()
                .member(member)
                .writer(member.getName())
                .channel(channel)
                .title(title)
                .content(content)
                .voteCount(0L)
                .views(0L)
                .postCategory(category)
                .comments(new ArrayList<>())
                .files(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();
    }

}
