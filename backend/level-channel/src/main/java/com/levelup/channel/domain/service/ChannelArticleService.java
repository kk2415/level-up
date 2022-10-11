package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.common.domain.FileType;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.util.file.LocalFileStore;
import com.levelup.common.util.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelArticleService {

    private final LocalFileStore fileStore;
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

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(FileType.POST, file);
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
            String field,
            String query,
            Pageable pageable)
    {
        Page<ChannelArticle> pages = null;

        if (field == null || field.equals("")) {
            pages = channelArticleRepository.findByChannelId(channelId, pageable);
        }
        else if (field.equals("title")) {
            pages = channelArticleRepository.findByChannelIdAndTitle(channelId, query, pageable);
        }
        else if (field.equals("writer")) {
            pages = channelArticleRepository.findByChannelIdAndNickname(channelId, query, pageable);
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

        if (!article.getChannelMember().getMember().getId().equals(memberId)) {
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }

        article.update(dto.getTitle(), dto.getContent(), dto.getPostCategory());

        return ChannelArticleDto.from(article);
    }


    @CacheEvict(cacheNames = "channelArticle", key = "{#channelId + ':0'}")
    public void delete(Long articleId, Long channelId) {
        channelArticleRepository.findById(articleId).ifPresent(channelArticleRepository::delete);
    }
}
