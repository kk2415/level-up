package com.levelup.api.service;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.ImageType;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.channelPost.ChannelPostRequest;
import com.levelup.api.dto.channelPost.ChannelPostResponse;
import com.levelup.core.exception.channel.ChannelNotFountExcpetion;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.article.ChannelPost.ChannelPostRepository;
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

    public ChannelPostResponse save(ChannelPostRequest channelPostRequest, Long memberId, Long channelId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        ChannelPost channelPost = channelPostRequest.toEntity(member, channel);

        channelPostRepository.save(channelPost);
        return ChannelPostResponse.from(channelPost);
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.POST, file);
    }

    public ChannelPostResponse getChannelPost(Long articleId, boolean view) {
        ChannelPost channelPost = channelPostRepository.findByArticleId(articleId)
                .orElseThrow(() -> new PostNotFoundException("존재하는 게시글이 없습니다."));

        if (view) {
            channelPost.addViews();;
        }

        return ChannelPostResponse.from(channelPost);
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

        return pages.map(ChannelPostResponse::from);
    }

    public ChannelPostResponse getNextPage(Long articleId, ArticleType articleType, Long channelId) {
       final ChannelPost channelPost = channelPostRepository.findNextByChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostResponse.from(channelPost);
    }

    public ChannelPostResponse getPrevPage(Long articleId, ArticleType articleType, Long channelId) {
        final ChannelPost channelPost = channelPostRepository.findPrevChannelIdAndArticleType(articleId, channelId, articleType)
                .orElseThrow(() -> new PostNotFoundException("존재하는 페이지가 없습니다."));

        return ChannelPostResponse.from(channelPost);
    }

    public ChannelPostResponse modify(Long articleId, Long memberId, ChannelPostRequest request) {
        ChannelPost channelPost = articleRepository.findChannelPostById(articleId)
                .orElseThrow(() -> new PostNotFoundException("작성한 게시글이 없습니다"));

        if (!channelPost.getMember().getId().equals(memberId)) {
            throw new MemberNotFoundException("권한이 없습니다.");
        }

        channelPost.modifyChannelPost(request.getTitle(), request.getContent(), request.getPostCategory());

        return ChannelPostResponse.from(channelPost);
    }

    public void delete(Long articleId) {
        Channel channel = channelRepository.findByArticleId(articleId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        articleRepository.findById(articleId).ifPresent(articleRepository::delete);
    }
}
