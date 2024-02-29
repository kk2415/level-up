package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.channel.domain.service.dto.SearchCondition;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelArticleService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelArticleRepository channelArticleRepository;

    @CacheEvict(cacheNames = "channelArticle", key = "{#channelId + ':0'}")
    public ChannelArticleDto save(ChannelArticleDto dto, Long memberId, Long channelId) {
        final ChannelMember channelMember = channelMemberRepository.findByChannelIdAndMemberId(channelId, memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_MEMBER_NOT_FOUND));
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        ChannelArticle channelArticle = dto.toEntity(channelMember, channel);

        channelArticleRepository.save(channelArticle);
        return ChannelArticleDto.from(channelArticle);
    }


    @CacheEvict(cacheNames = "channelArticle", key = "{#channelId + ':0'}")
    public ChannelArticleDto get(Long articleId, Long channelId, boolean view) {
        final ChannelArticle article = channelArticleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        if (view) article.addViews();;

        return ChannelArticleDto.from(article);
    }

    @Cacheable(
            cacheNames = "channelArticle",
            key = "{#channelId + ':' + #pageable.pageNumber}",
            condition = "#pageable.pageNumber == 0 AND #field == ''")
    public Page<ChannelArticleDto> getChannelArticles(
            Long channelId,
            SearchCondition search,
            Pageable pageable)
    {
        Page<ChannelArticle> pages;

        if (search.isTitleSearch()) {
            pages = channelArticleRepository.findByChannelIdAndTitle(channelId, search.getQuery(), pageable);
        } else if (search.isWriterSearch()) {
            pages = channelArticleRepository.findByChannelIdAndNickname(channelId, search.getQuery(), pageable);
        } else {
            pages = channelArticleRepository.findByChannelId(channelId, pageable);
        }

        return pages.map(ChannelArticleDto::from);
    }

    public ChannelArticleDto getNext(Long articleId, Long channelId) {
       final ChannelArticle channelPost = channelArticleRepository.findNextByChannelId(articleId, channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        return ChannelArticleDto.from(channelPost);
    }

    public ChannelArticleDto getPrev(Long articleId, Long channelId) {
        final ChannelArticle channelPost = channelArticleRepository.findPrevByChannelId(articleId, channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        return ChannelArticleDto.from(channelPost);
    }



    @CacheEvict(cacheNames = "channelArticle", key = "{#channelId + ':0'}")
    public ChannelArticleDto update(ChannelArticleDto dto, Long articleId, Long memberId, Long channelId) {
        final ChannelArticle article = channelArticleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        if (!article.getChannelMember().getMemberId().equals(memberId)) {
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }

        article.update(dto.getTitle(), dto.getContent(), dto.getCategory());

        return ChannelArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "channelArticle", key = "{#channelId + ':0'}")
    public void delete(Long articleId, Long channelId) {
        channelArticleRepository.findById(articleId).ifPresent(channelArticleRepository::delete);
    }
}
