package com.levelup.api.controller.v1.channel;

import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.channel.domain.service.ChannelArticleService;
import com.levelup.api.controller.v1.dto.request.channel.ChannelArticleRequest;
import com.levelup.api.controller.v1.dto.response.channel.ChannelArticleResponse;
import com.levelup.channel.domain.service.dto.SearchCondition;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "채널 게시글 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel-articles")
public class ChannelArticleApiController {

    private final ChannelArticleService channelArticleService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelArticleResponse> create(
            @Valid @RequestBody ChannelArticleRequest request,
            @RequestParam("channel") Long channelId,
            @RequestParam("member") Long memberId)
    {
        ChannelArticleDto dto = channelArticleService.save(request.toDto(), memberId, channelId);

        return ResponseEntity.ok().body(ChannelArticleResponse.from(dto));
    }


    @GetMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<ChannelArticleResponse> get(
            @PathVariable Long articleId,
            @RequestParam("channel") Long channelId,
            @RequestParam(required = false, defaultValue = "false") boolean view)
    {
        ChannelArticleDto dto = channelArticleService.get(articleId, channelId, view);

        return ResponseEntity.ok().body(ChannelArticleResponse.from(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ChannelArticleResponse>> getChannelPosts(
            @RequestParam("channel") Long channelId,
            Pageable pageable,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String query)
    {
        Page<ChannelArticleResponse> response
                = channelArticleService.getChannelArticles(channelId, SearchCondition.of(field, query), pageable)
                            .map(ChannelArticleResponse::from);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping({"/{articleId}/next-article", "/{articleId}/next-article/"})
    public ResponseEntity<ChannelArticleResponse> getNext(
            @PathVariable Long articleId,
            @RequestParam("channel") Long channelId)
    {
        ChannelArticleDto dto = channelArticleService.getNext(articleId, channelId);

        return ResponseEntity.ok().body(ChannelArticleResponse.from(dto));
    }

    @GetMapping({"/{articleId}/prev-article", "/{articleId}/prev-article/"})
    public ResponseEntity<ChannelArticleResponse> getPrev(
            @PathVariable Long articleId,
            @RequestParam("channel") Long channelId)
    {
        ChannelArticleDto dto = channelArticleService.getPrev(articleId, channelId);

        return ResponseEntity.ok().body(ChannelArticleResponse.from(dto));
    }


    @PatchMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<ChannelArticleResponse> update(
            @PathVariable Long articleId,
            @RequestParam("member") Long memberId,
            @RequestParam("channel") Long channelId,
            @Valid @RequestBody ChannelArticleRequest request)
    {
        ChannelArticleDto dto = channelArticleService.update(request.toDto(), articleId, memberId, channelId);

        return ResponseEntity.ok().body(ChannelArticleResponse.from(dto));
    }


    @DeleteMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long articleId,
            @RequestParam("channel") Long channelId)
    {
        channelArticleService.delete(articleId, channelId);

        return ResponseEntity.ok().build();
    }
}
