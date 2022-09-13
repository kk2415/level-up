package com.levelup.api.service;

import com.levelup.api.dto.response.article.ArticlePagingResponse;
import com.levelup.api.dto.service.article.ArticleDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.file.ImageType;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.article.ArticlePagingDto;
import com.levelup.core.exception.AuthorityException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    private final MemberRepository memberRepository;
    private final LocalFileStore fileStore;
    private final ArticleRepository articleRepository;

    public ArticleDto save(ArticleDto dto, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        final Article article = Article.of(member, dto.getTitle(), dto.getContent(), dto.getArticleType());
        articleRepository.save(article);

        return ArticleDto.from(article);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        return fileStore.storeFile(ImageType.POST, file);
    }



    public ArticleDto get(Long articleId, boolean view) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view) article.addViews();

        return ArticleDto.from(article);
    }

    public Page<ArticlePagingResponse> getByPaging(ArticleType articleType, String field, String query, Pageable pageable) {
        Page<ArticlePagingDto> pages;

        switch (field) {
            case "title" :
                pages = articleRepository.findByTitleAndArticleType(query, articleType.name(), pageable);
                break;
            case "writer" :
                pages = articleRepository.findByNicknameAndArticleType(query, articleType.name(), pageable);
                break;
            default:
                pages = articleRepository.findByArticleType(articleType.name(), pageable);
        }

        return pages.map(ArticlePagingResponse::from);
    }

    public ArticleDto getNext(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findNextPageByArticleType(articleId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleDto.from(article);
    }

    public ArticleDto getPrev(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findPrevPageArticleType(articleId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleDto.from(article);
    }



    public ArticleDto update(ArticleDto request, Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("작성한 게시글이 없습니다"));

        if (!article.getMember().getId().equals(memberId)) {
            throw new AuthorityException("작성자만 게시글을 수정할 수 있습니다.");
        }

        article.modifyArticle(request.getTitle(), request.getContent());
        return ArticleDto.from(article);
    }

    public void delete(Long articleId) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }
}
