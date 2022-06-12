package com.levelup.core.service;

import com.levelup.core.CoreApplication;
import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleCategory;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.PostCategory;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Transactional
@SpringBootTest(classes = CoreApplication.class)
public class ArticleServiceTest {

    private MemberRepository memberRepository;
    private ArticleRepository articleRepository;
    private ChannelRepository channelRepository;

    @Autowired
    public ArticleServiceTest(MemberRepository memberRepository, ArticleRepository articleRepository, ChannelRepository channelRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.channelRepository = channelRepository;
    }

    Member member1;
    Member member2;
    Member member3;

    Article article1;
    Article article2;
    Article article3;
    Article article4;

    Channel channel1;

    @BeforeEach
    public void before() {
        member1 = createMember("test1@naver.com", "test1");
        member2 = createMember("test2@naver.com", "test2");
        member3 = createMember("test3@naver.com", "test3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        article1 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
        article2 = createArticle(member1, "testTitle", member1.getName(), "tset contents", ArticleCategory.CHANNEL_POST);
        article3 = createArticle(member2, "testTitle", member2.getName(), "tset contents", ArticleCategory.CHANNEL_NOTICE);
        article4 = createArticle(member3, "testTitle", member3.getName(), "tset contents", ArticleCategory.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        channel1 = createChannel(member1, "test Channel", "this is tset channel");
        channelRepository.save(channel1);
    }

    @Test
    public void 생성() {
    }

    private Member createMember(String email, String name) {
        return Member.builder()
                .email(email)
                .password("password")
                .name(name)
                .gender(Gender.MALE)
                .birthday("970927")
                .phone("010-2354-9960")
                .authority(Authority.NORMAL)
                .profileImage(null)
                .build();
    }

    private Article createArticle(Member member, String title, String writer, String contents, ArticleCategory category) {
        return Article.builder()
                .title(title)
                .writer(writer)
                .content(contents)
                .voteCount(0L)
                .views(0L)
                .category(category)
                .member(member)
                .postCategory(PostCategory.INFO)
                .comments(new ArrayList<>())
                .files(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();
    }

    private Channel createChannel(Member member, String name, String description) {
        return Channel.builder()
                .name(name)
                .category(ChannelCategory.STUDY)
                .managerName(member.getName())
                .description(description)
                .memberCount(0L)
                .manager(member)
                .limitedMemberNumber(10L)
                .articles(new ArrayList<>())
                .build();
    }

}
