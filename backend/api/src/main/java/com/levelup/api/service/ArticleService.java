package com.levelup.api.service;

import com.levelup.api.controller.v1.dto.response.article.ArticlePagingResponse;
import com.levelup.api.service.dto.article.ArticleDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.file.FileType;
import com.levelup.api.util.file.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.ArticlePagingDto;
import com.levelup.api.exception.AuthorityException;
import com.levelup.api.exception.member.MemberNotFoundException;
import com.levelup.api.exception.article.ArticleNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final LocalFileStore fileStore;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ArticleDto save(ArticleDto dto, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        final Article article = dto.toEntity(member);
        articleRepository.save(article);

        return ArticleDto.from(article);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        return fileStore.storeFile(FileType.POST, file);
    }



    @CacheEvict(cacheNames = "article", key = "{#articleType + ':0'}")
    public ArticleDto get(Long articleId, ArticleType articleType, boolean view) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("존재하는 게시글이 없습니다."));

        if (view) article.addViews();

        return ArticleDto.from(article);
    }

    @Cacheable(cacheNames = "article",
            key = "{#articleType + ':' + #pageable.pageNumber}",
            condition = "#pageable.pageNumber == 0 AND #field == ''")
    public Page<ArticlePagingResponse> getArticles(ArticleType articleType, String field, String query, Pageable pageable) {
        Page<ArticlePagingDto> pages;

        if ("title".equals(field) && !("".equals(query))) {
            pages = articleRepository.findByTitleAndArticleType(query, articleType.name(), pageable);
        } else if ("writer".equals(field) && !("".equals(query))) {
            pages = articleRepository.findByNicknameAndArticleType(query, articleType.name(), pageable);
        } else {
            pages = articleRepository.findByArticleType(articleType.name(), pageable);
        }

        return pages.map(ArticlePagingResponse::from);
    }

    public ArticleDto getNext(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findNextByIdAndArticleType(articleId, articleType.toString())
                .orElseThrow(() -> new ArticleNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleDto.from(article);
    }

    public ArticleDto getPrev(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findPrevByIdAndArticleType(articleId, articleType.toString())
                .orElseThrow(() -> new ArticleNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleDto.from(article);
    }



    @CacheEvict(cacheNames = "article", key = "{#dto.articleType + ':0'}")
    public ArticleDto update(ArticleDto dto, Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("작성한 게시글이 없습니다"));

        if (!article.getMember().getId().equals(memberId)) {
            throw new AuthorityException("작성자만 게시글을 수정할 수 있습니다.");
        }

        article.update(dto.getTitle(), dto.getContent());
        return ArticleDto.from(article);
    }



    public void delete(Long articleId) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }
}
