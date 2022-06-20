package com.levelup.core.post;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.PostCategory;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    private Member member1;
    private Channel channel1;
    private Post post1;

    @BeforeEach
    public void before() {
        member1 = createMember("taest@test.com");
        memberRepository.save(member1);

        channel1 = createChannel(member1, "testChannel");
        channelRepository.save(channel1);

        post1 = createPost(member1, channel1, "test");
        postRepository.save(post1);
    }

    @Test
    public void 조회() {
    }

    private Member createMember(String email) {
        return Member.builder()
                .email(email)
                .gender(Gender.MALE)
                .password("123")
                .name("test")
                .birthday("970927")
                .phone("010-0000-0000")
                .profileImage(new UploadFile("a", "A"))
                .articles(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private Channel createChannel(Member member, String name) {
        return Channel.builder()
                .name(name)
                .limitedMemberNumber(10L)
                .managerName(member.getName())
                .description("test")
                .memberCount(0L)
                .waitingMemberCount(0L)
                .category(ChannelCategory.STUDY)
                .thumbnailImage(new UploadFile("a", "a"))
                .articles(new ArrayList<>())
                .channelMembers(new ArrayList<>())
                .posts(new ArrayList<>())
                .files(new ArrayList<>())
                .channelNotices(new ArrayList<>())
                .manager(member)
                .build();
    }

    private Post createPost(Member member, Channel channel, String title) {
        return Post.builder()
                .title(title)
                .writer(member.getName())
                .content("test")
                .voteCount(0L)
                .views(0L)
                .postCategory(PostCategory.INFO)
                .member(member)
                .comments(new ArrayList<>())
                .channel(channel)
                .files(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();
    }

}
