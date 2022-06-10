//package com.levelup.api.service;
//
//import com.levelup.api.ApiApplication;
//import com.levelup.core.domain.Article.Article;
//import com.levelup.core.domain.Article.ArticleCategory;
//import com.levelup.core.domain.channel.Channel;
//import com.levelup.core.domain.channel.ChannelCategory;
//import com.levelup.core.domain.channel.ChannelPost;
//import com.levelup.core.domain.member.Authority;
//import com.levelup.core.domain.member.Gender;
//import com.levelup.core.domain.member.Member;
//import com.levelup.core.domain.post.PostCategory;
//import com.levelup.core.dto.article.CreateArticleRequest;
//import com.levelup.core.dto.channel.ChannelRequest;
//import com.levelup.core.dto.member.CreateMemberRequest;
//import com.levelup.core.dto.member.CreateMemberResponse;
//import com.levelup.core.dto.member.MemberResponse;
//import com.levelup.core.repository.article.ArticleRepository;
//import com.levelup.core.repository.channel.ChannelPostRepository;
//import com.levelup.core.repository.channel.ChannelRepository;
//import com.levelup.core.repository.member.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Transactional
//@SpringBootTest(classes = ApiApplication.class)
//public class ArticleServiceTest {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private ArticleService articleService;
//
//    @Autowired
//    private ChannelService channelService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private ChannelRepository channelRepository;
//
//    Member member1;
//    Member member2;
//    Member member3;
//
//    Article article1;
//    Article article2;
//    Article article3;
//    Article article4;
//
//    Channel channel1;
//
//    ChannelPost channelPost1;
//    ChannelPost channelPost2;
//
//    @BeforeEach
//    public void before() {
//        member1 = createMember("tesfasdasdfasdft1@naver.com", "test1");
//        member2 = createMember("teasdasdst2@navfasdfer.com", "test2");
//        member3 = createMember("tasdestaa3@asdfnaver.com", "test3");
//        memberService.create(new CreateMemberRequest(member1));
//        memberService.create(new CreateMemberRequest(member2));
//        memberService.create(new CreateMemberRequest(member3));
//
//        article1 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
//        article2 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
//        article3 = createArticle(member2, "testTitle", member2.getName(), "tset contents", ArticleCategory.CHANNEL_NOTICE);
//        article4 = createArticle(member3, "testTitle", member3.getName(), "tset contents", ArticleCategory.NOTICE);
//
//        channel1 = createChannel(member1, "test Channel", "this is tset channel");
//        channelService.create(new ChannelRequest(member1.getEmail(), channel1.getName(), channel1.getLimitedMemberNumber(),
//                channel1.getDescription(), channel1.getCategory(), channel1.getThumbnailDescription(),
//                channel1.getThumbnailImage(), null));
//    }
//
//    @Test
//    public void 생성() {
//        CreateArticleRequest request = CreateArticleRequest.builder()
//                .title(article1.getTitle())
//                .writer(article1.getWriter())
//                .content(article1.getContent())
//                .category(article1.getCategory())
//                .postCategory(article1.getPostCategory())
//                .build();
//
//        MemberResponse findMember = memberService.findByEmail(member1.getEmail());
//        articleService.create(request, channel1.getId(), findMember.getId());
////        List<Article> articles = articleRepository.findByChannelId(channel1.getId()).get();
////        Assertions.assertThat(articles.size()).isEqualTo(1);
//    }
//
//    private Article createArticle(Member member, String title, String writer, String contents, ArticleCategory category) {
//        return Article.builder()
//                .title(title)
//                .writer(writer)
//                .content(contents)
//                .voteCount(0L)
//                .views(0L)
//                .category(category)
//                .member(member)
//                .comments(new ArrayList<>())
//                .files(new ArrayList<>())
//                .votes(new ArrayList<>())
//                .build();
//    }
//
//    private Member createMember(String email, String name) {
//        return Member.builder()
//                .email(email)
//                .password("password")
//                .name(name)
//                .gender(Gender.MALE)
//                .birthday("970927")
//                .phone("010-2354-9960")
//                .authority(Authority.NORMAL)
//                .dateCreated(LocalDateTime.now())
//                .profileImage(null)
//                .channels(new ArrayList<>())
//                .channelMembers(new ArrayList<>())
//                .build();
//    }
//
//    private Channel createChannel(Member member, String name, String description) {
//        return Channel.builder()
//                .name(name)
//                .category(ChannelCategory.STUDY)
//                .dateCreated(LocalDateTime.now())
//                .managerName(member.getName())
//                .description(description)
//                .memberCount(0L)
//                .limitedMemberNumber(10L)
//                .articles(new ArrayList<>())
//                .build();
//    }
//
//}
