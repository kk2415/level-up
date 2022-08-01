package com.levelup.api.service;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.article.ArticleRequest;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.ChannelPostRequest;
import com.levelup.core.dto.article.ChannelPostResponse;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final MemberRepository memberRepository;
    private final LocalFileStore fileStore;
    private final ArticleRepository articleRepository;


    /**
     * 게시글 등록
     */
    public ArticleResponse save(ArticleRequest articleRequest, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        final Article article = Article.createArticle(member, articleRequest.getTitle(), articleRequest.getContent(),
                articleRequest.getArticleType());
        articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.POST, file);
    }


    /**
     * 게시글 조회
     */
    public ArticleResponse getArticle(Long articleId, String view) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view.equals("true")) {
            article.addViews();
        }

        return ArticleResponse.from(article);
    }

    public Page<ArticleResponse> getArticles(ArticleType articleType, String field, String query, Pageable pageable) {
        Page<Article> pages = null;

        if (field == null || field.equals("")) {
            pages = articleRepository.findByArticleType(articleType, pageable);
        } else if (field.equals("title")) {
            pages = articleRepository.findByArticleTypeAndTitle(articleType, query, pageable);
        } else if (field.equals("writer")) {
            pages = articleRepository.findByArticleTypeAndWriter(articleType, query, pageable);
        }

        return pages.map(ArticleResponse::from);
    }

    public ChannelPostResponse getChannelPost(Long articleId, String view) {
        ChannelPost channelPost = articleRepository.findChannelPostById(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view.equals("true")) {
            channelPost.addViews();
            ;
        }

        return ChannelPostResponse.from(channelPost);
    }

    public Page<ChannelPostResponse> getChannelPosts(Long channelId, String field, String query, Pageable pageable) {
        Page<ChannelPost> pages = null;

        if (field == null || field.equals("")) {
            pages = articleRepository.findByChannelId(channelId, pageable);
        } else if (field.equals("title")) {
            pages = articleRepository.findByChannelIdAndTitle(channelId, query, pageable);
        } else if (field.equals("writer")) {
            pages = articleRepository.findByChannelIdAndWriter(channelId, query, pageable);
        }

        return pages.map(ChannelPostResponse::from);
    }

    public void getChannelPostsCount(Long channelId, String field, String query) {

    }

    public ArticleResponse getNextPageByArticleType(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findNextPageByArticleType(articleId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleResponse.from(article);
    }

    public ArticleResponse getPrevPageByArticleType(Long articleId, ArticleType articleType) {
        final Article article = articleRepository.findPrevPageArticleType(articleId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ArticleResponse.from(article);
    }

    public ChannelPostResponse getNextPageByChannelId(Long articleId, Long channelId) {
        final ChannelPost channelPost = articleRepository.findNextPageByChannelId(articleId, channelId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostResponse.from(channelPost);
    }

    public ChannelPostResponse getPrevPageByChannelId(Long articleId, Long channelId) {
        final ChannelPost channelPost = articleRepository.findPrevPageChannelId(articleId, channelId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostResponse.from(channelPost);
    }

    public List<ArticleResponse> getByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId).orElseThrow(
                () -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        return articles.stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * 게시글 수정
     */
    public ArticleResponse modify(Long articleId, Long memberId, ChannelPostRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("작성한 게시글이 없습니다"));

        article.modifyArticle(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }


    /**
     * 게시글 삭제
     */
    public void deleteArticle(Long articleId) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }


    /**
     * 게시글 권한
     */
    public Long articleOauth(Long articleId, Long memberId) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (!article.getMember().getId().equals(memberId)) {
            throw new PostNotFoundException("게시글의 대한 권한이 없습니다");
        }

        return articleId;
    }

}
