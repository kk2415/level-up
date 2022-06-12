//package com.levelup.core.repository;
//
//import com.levelup.core.CoreApplication;
//import com.levelup.core.domain.Article.Article;
//import com.levelup.core.domain.Article.ArticleCategory;
//import com.levelup.core.domain.channel.Channel;
//import com.levelup.core.domain.channel.ChannelCategory;
//import com.levelup.core.domain.member.Authority;
//import com.levelup.core.domain.member.Gender;
//import com.levelup.core.domain.member.Member;
//import com.levelup.core.repository.article.ArticleRepository;
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
//
//@Transactional
//@SpringBootTest(classes = CoreApplication.class)
//class ArticleRepositoryTest {
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
//    @BeforeEach
//    public void before() {
//        member1 = createMember("test1@naver.com", "test1");
//        member2 = createMember("test2@naver.com", "test2");
//        member3 = createMember("test3@naver.com", "test3");
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        article1 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
//        article2 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
//        article3 = createArticle(member2, "testTitle", member2.getName(), "tset contents", ArticleCategory.CHANNEL_NOTICE);
//        article4 = createArticle(member3, "testTitle", member3.getName(), "tset contents", ArticleCategory.NOTICE);
//
//        articleRepository.save(article1);
//        articleRepository.save(article2);
//        articleRepository.save(article3);
//        articleRepository.save(article4);
//
//        channel1 = createChannel(member1, "test Channel", "this is tset channel");
//        channelRepository.save(channel1);
//    }
//
//    @Test
//    public void 생성() {
//        articleRepository.save(article1);
//        articleRepository.save(article2);
//        Article findArticle3 = articleRepository.save(article3);
//        Article findArticle4 = articleRepository.save(article4);
//
//        Assertions.assertThat(findArticle3).isEqualTo(article3);
//        Assertions.assertThat(findArticle4).isEqualTo(article4);
//    }
//
//    @Test
//    public void 카테고리별_검색() {
//        Article findArticle1 = articleRepository.save(article1);
//        Article findArticle2 = articleRepository.save(article2);
//        Article findArticle3 = articleRepository.save(article3);
//        Article findArticle4 = articleRepository.save(article4);
//
////        List<Article> articles = articleRepository.findByCategory(ArticleCategory.CHANNEL_POST).get();
////        Assertions.assertThat(articles.size()).isEqualTo(2);
//    }
//
//    @Test
//    @Commit
//    public void 채널_게시글_조회() {
////        channel1.addArticle(article1);
////        channel1.addArticle(article2);
//
////        List<Article> articles = articleRepository.findByChannelId(channel1.getId()).get();
////        Assertions.assertThat(articles.size()).isEqualTo(2);
//    }
//
////    @Test
//    public void 채널_게시글_페이징() {
//        Article article5 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
//        Article article6 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//        Article article7 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//        Article article8 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//        Article article9 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//        Article article10 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//        Article article11 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);;
//
//        articleRepository.save(article5);
//        articleRepository.save(article6);
//        articleRepository.save(article7);
//        articleRepository.save(article8);
//        articleRepository.save(article9);
//        articleRepository.save(article10);
//        articleRepository.save(article11);
//
//
//
////        channelPostRepository.save(channelPost1);
////        channelPostRepository.save(channelPost2);
////        channelPostRepository.save(channelPost3);
////        channelPostRepository.save(channelPost4);
////        channelPostRepository.save(channelPost5);
////        channelPostRepository.save(channelPost6);
////        channelPostRepository.save(channelPost7);
////        channelPostRepository.save(channelPost8);
////        channelPostRepository.save(channelPost9);
////        channelPostRepository.save(channelPost10);
////        channelPostRepository.save(channelPost11);
////
////        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "createdDate"));
////
////        Page<Article> articles = articleRepository.findByChannelId(channel1.getId(), pageRequest).get();
////        for (Article article : articles) {
////            System.out.println(article);
////        }
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
//                .manager(member)
//                .limitedMemberNumber(10L)
//                .articles(new ArrayList<>())
//                .build();
//    }
//
//}