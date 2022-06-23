package com.levelup.api.service;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.article.ArticleRequest;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.ChannelPostRequest;
import com.levelup.core.dto.article.ChannelPostResponse;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.exception.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.article.ChannelPostRepository;
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
public class ChannelPostService {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final LocalFileStore fileStore;
    private final ArticleRepository articleRepository;
    private final ChannelPostRepository channelPostRepository;


    /**
     * 게시글 등록
     * */
    public ChannelPostResponse create(ChannelPostRequest channelPostRequest, Long memberId, Long channelId) {
        Member member = memberRepository.findById(memberId);
        Channel channel = channelRepository.findById(channelId);

        ChannelPost channelPost = channelPostRequest.toEntity(member, channel);

        System.out.println(channelPost.getPostCategory());
        articleRepository.save(channelPost);

        return new ChannelPostResponse(channelPost);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.POST, file);
    }


    /**
     * 게시글 조회
     * */
    public ChannelPostResponse getChannelPost(Long articleId, String view) {
        ChannelPost channelPost = channelPostRepository.findByArticleId(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view.equals("true")) {
            channelPost.addViews();;
        }

        return new ChannelPostResponse(channelPost);
    }

    public Page<ChannelPostResponse> getChannelPosts(Long channelId, ArticleType articleType, String field,
                                                     String query, Pageable pageable) {
        Page<ChannelPost> pages = null;

        if (field == null || field.equals("")) {
            pages = channelPostRepository.findByChannelIdAndArticleType(channelId, articleType, pageable);
        }
        else if (field.equals("title")) {
            pages = channelPostRepository.findByChannelIdAndTitleAndArticleType(channelId, articleType, query, pageable);
        }
        else if (field.equals("writer")) {
            pages = channelPostRepository.findByChannelIdAndWriterAndArticleType(channelId, articleType, query, pageable);
        }

        return pages.map(ChannelPostResponse::new);
    }

    public ChannelPostResponse findNextPage(Long articleId, ArticleType articleType, Long channelId) {
        ChannelPost channelPost = channelPostRepository.findNextByChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return new ChannelPostResponse(channelPost);
    }

    public ChannelPostResponse findPrevPage(Long articleId, ArticleType articleType, Long channelId) {
        ChannelPost channelPost = channelPostRepository.findPrevChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return new ChannelPostResponse(channelPost);
    }

    public List<ArticleResponse> findByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId).orElseThrow(
                () -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        return articles.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());
    }


    /**
     * 게시글 수정
     * */
    public ChannelPostResponse modify(Long articleId, Long memberId, ChannelPostRequest request) {
        ChannelPost channelPost = articleRepository.findChannelPostById(articleId)
                .orElseThrow(() -> new PostNotFoundException("작성한 게시글이 없습니다"));

        if (!channelPost.getMember().getId().equals(memberId)) {
            throw new MemberNotFoundException("권한이 없습니다.");
        }

        channelPost.modifyChannelPost(request.getTitle(), request.getContent(), request.getPostCategory());

        return new ChannelPostResponse(channelPost);
    }


    /**
     * 게시글 삭제
     * */
    public void delete(Long articleId) {
        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }



    /**
     * 게시글 권한
     * */
    public Long articleOauth(Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (!article.getMember().getId().equals(memberId)) {
            throw new PostNotFoundException("게시글의 대한 권한이 없습니다");
        }

        return articleId;
    }

}
