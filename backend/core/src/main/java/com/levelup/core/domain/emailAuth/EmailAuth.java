package com.levelup.core.domain.emailAuth;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "email_auth")
@Entity
public class EmailAuth extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "email_auth_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String securityCode;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @Column(nullable = false)
    private LocalDateTime receivedDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    protected EmailAuth() {}

    public void setMember(Member member) {
        this.member = member;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public static EmailAuth from(String email) {
        return EmailAuth.builder()
                .email(email)
                .securityCode(createSecurityCode())
                .isConfirmed(false)
                .receivedDate(LocalDateTime.now())
                .build();
    }

    public static String createSecurityCode() {
        Random rand = new Random();
        String securityCode = "";

        int idx = 0;
        while (idx < 6) {
            String randomInt = Integer.toString(rand.nextInt(10));
            if (!securityCode.contains(randomInt)) {
                securityCode += randomInt;
                idx++;
            }
        }

        return securityCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmailAuth)) return false;
        return id != null && id.equals(((EmailAuth) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
