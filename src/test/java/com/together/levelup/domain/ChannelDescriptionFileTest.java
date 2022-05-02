package com.together.levelup.domain;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.file.ChannelDescriptionFile;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ChannelDescriptionFileTest {

    @Autowired
    private EntityManager em;

    private Member member1;
    private Channel channel;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        em.persist(member1);

        channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE));
        em.persist(channel);
    }

    @Test
    @Commit
    public void 생성_테스트() {
        UploadFile uploadFile = new UploadFile("channelImage", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE);

        ChannelDescriptionFile channelDescriptionFile = ChannelDescriptionFile.createChannelDescriptionFile(uploadFile);

        channel.addDescriptionFile(channelDescriptionFile);
        Assertions.assertThat(channel.getChannelDescriptionFiles().get(0).getChannel()).isEqualTo(channel);
    }
}
