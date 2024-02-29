package com.levelup.article.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@Getter
@Table(name = "writer")
@Entity
public class Writer extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "writer_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    protected Writer() {}

    public static Writer of(Long memberId, String nickname, String email) {
        return Writer.builder()
                .memberId(memberId)
                .nickname(nickname)
                .email(email)
                .build();
    }

    public void update(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
