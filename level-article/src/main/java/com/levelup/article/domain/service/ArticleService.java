package com.levelup.article.domain.service;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.service.dto.SearchCondition;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.WriterRepository;
import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.article.exception.ArticleAuthorityException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final WriterRepository writerRepository;

    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ArticleDto save(ArticleDto dto, Long memberId) {
        final Writer writer = writerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        final Article article = dto.toEntity(writer);
        articleRepository.save(article);

        return ArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "article", key = "{#articleType + ':0'}")
    public ArticleDto get(Long articleId, ArticleType articleType, boolean view) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        if (view) article.addViews();

        return ArticleDto.from(article);
    }

    @Cacheable(
            cacheNames = "article",
            key = "{#articleType + ':' + #pageable.pageNumber}",
            condition = "#pageable.pageNumber == 0 AND #field == ''")
    public Page<ArticleDto> getArticles(
            ArticleType articleType,
            SearchCondition search,
            Pageable pageable)
    {
        Page<ArticleDto> pages;

        if (search.isTitleSearch()) {
            pages = articleRepository.findByTitleAndArticleType(search.getQuery(), articleType, pageable);
        } else if (search.isWriterSearch()) {
            pages = articleRepository.findByNicknameAndArticleType(search.getQuery(), articleType, pageable);
        } else {
            pages = articleRepository.findByArticleType(articleType, pageable);
        }

        return pages;
    }

    @Transactional(readOnly = true)
    public ArticleDto getNext(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findNextByIdAndArticleType(articleId, articleType.toString())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        return ArticleDto.from(article);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ArticleDto getPrev(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findPrevByIdAndArticleType(articleId, articleType.toString())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        return ArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ArticleDto update(ArticleDto dto, Long articleId, Long memberId) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        if (!article.isWriter(memberId)) {
            throw new ArticleAuthorityException(ErrorCode.ARTICLE_AUTHORITY_EXCEPTION);
        }

        article.update(dto.getTitle(), dto.getContent());
        return ArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "article", key = "{#articleType + ':0'}")
    public void delete(Long articleId, ArticleType articleType) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }
}
