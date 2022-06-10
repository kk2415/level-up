package com.levelup.api.service;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.article.ArticleRequest;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.CreateArticleRequest;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;


    /**
     * 생성
     * */
    public ArticleResponse create(CreateArticleRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId);

        Article article = request.toEntity(member);
        articleRepository.save(article);

        return new ArticleResponse(article);
    }

    public ArticleResponse create(CreateArticleRequest request, Long channelId, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Channel channel = channelRepository.findById(channelId);

        Article article = request.toEntity(member);

        articleRepository.save(article);
        channel.addArticle(article);

        return new ArticleResponse(article);
    }


    /**
     * 조회
     * */
    public ArticleResponse get(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 article입니다."));

        return new ArticleResponse(article);
    }

    public Page<ArticleResponse> getByChannelId(Long channelId, Pageable pageable) {
        Page<Article> articles = articleRepository.findByChannelId(channelId, pageable)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 article입니다."));

       return articles.map(ArticleResponse::new);
    }


    /**
     * 수정
     * */
    public ArticleResponse modifyArticle(ArticleRequest request, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이디"));

        article.modifyArticle(article.getTitle(), article.getContent(), article.getPostCategory());

        return new ArticleResponse(article);
    }

}
