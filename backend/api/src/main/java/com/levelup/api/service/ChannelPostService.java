package com.levelup.api.service;

import com.levelup.api.dto.service.channelPost.ChannelPostDto;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.ImageType;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.request.channelPost.ChannelPostRequest;
import com.levelup.core.exception.channel.ChannelNotFountExcpetion;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.ChannelPost.ChannelPostRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelPostService {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final LocalFileStore fileStore;
    private final ArticleRepository articleRepository;
    private final ChannelPostRepository channelPostRepository;

    public ChannelPostDto save(ChannelPostDto dto, Long memberId, Long channelId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        ChannelPost channelPost = dto.toEntity(member, channel);

        channelPostRepository.save(channelPost);
        return ChannelPostDto.from(channelPost);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.POST, file);
    }

    public ChannelPostDto get(Long articleId, boolean view) {
        ChannelPost channelPost = channelPostRepository.findByArticleId(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view) {
            channelPost.addViews();;
        }

        return ChannelPostDto.from(channelPost);
    }

    public Page<ChannelPostDto> getByPaging(Long channelId, ArticleType articleType, String field,
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

        return pages.map(ChannelPostDto::from);
    }

    public ChannelPostDto getNext(Long articleId, ArticleType articleType, Long channelId) {
       final ChannelPost channelPost = channelPostRepository.findNextByChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostDto.from(channelPost);
    }

    public ChannelPostDto getPrev(Long articleId, ArticleType articleType, Long channelId) {
        final ChannelPost channelPost = channelPostRepository.findPrevChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostDto.from(channelPost);
    }

    public ChannelPostDto update(ChannelPostDto dto, Long articleId, Long memberId) {
        ChannelPost channelPost = channelPostRepository.findByArticleId(articleId)
                .orElseThrow(() -> new PostNotFoundException("작성한 게시글이 없습니다"));

        if (!channelPost.getMember().getId().equals(memberId)) {
            throw new MemberNotFoundException("권한이 없습니다.");
        }

        channelPost.modifyChannelPost(dto.getTitle(), dto.getContent(), dto.getPostCategory());

        return ChannelPostDto.from(channelPost);
    }

    public void delete(Long articleId) {
        channelPostRepository.findByArticleId(articleId).ifPresent(articleRepository::delete);
    }
}
