package com.levelup.article.domain.service;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.article.exception.ArticleAuthorityException;
import com.levelup.article.exception.ArticleNotFoundException;
import com.levelup.common.domain.FileType;
import com.levelup.common.util.file.LocalFileStore;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.exception.MemberNotFoundException;
import com.levelup.member.domain.repository.MemberRepository;
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


    public ArticleDto get(Long articleId, ArticleType articleType, boolean view) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("존재하는 게시글이 없습니다."));

        if (view) addViews(article);

        return ArticleDto.from(article);
    }

    @CacheEvict(cacheNames = "article", key = "{#article.articleType + ':0'}")
    public void addViews(Article article) {
        article.addViews();
    }

    @Cacheable(
            cacheNames = "article",
            key = "{#articleType + ':' + #pageable.pageNumber}",
            condition = "#pageable.pageNumber == 0 AND #field == ''")
    public Page<ArticleDto> getArticles(ArticleType articleType, String field, String query, Pageable pageable) {
        Page<ArticleDto> pages;

        if ("title".equals(field) && !("".equals(query))) {
            pages = articleRepository.findByTitleAndArticleType(query, articleType, pageable);
        } else if ("writer".equals(field) && !("".equals(query))) {
            pages = articleRepository.findByNicknameAndArticleType(query, articleType, pageable);
        } else {
            pages = articleRepository.findByArticleType(articleType, pageable);
        }

        return pages;
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
            throw new ArticleAuthorityException("작성자만 게시글을 수정할 수 있습니다.");
        }

        article.update(dto.getTitle(), dto.getContent());
        return ArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "article", key = "{#articleType + ':0'}")
    public void delete(Long articleId, ArticleType articleType) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }
}
