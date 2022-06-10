package com.levelup.core.dto.article;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleCategory;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {

    private String title;
    private String writer;
    private String content;
    private ArticleCategory category;
    private PostCategory postCategory;

    public Article toEntity(Member member) {
        return Article.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .voteCount(0L)
                .views(0L)
                .category(category)
                .postCategory(postCategory)
                .member(member)
                .build();
    }

}
