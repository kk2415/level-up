package com.levelup.core.domain.auth;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "email_auth")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "email_auth_id")
    private Long id;

    @Column(name = "email")
    private String email;

    private String securityCode;
    private Boolean isConfirmed;
    private LocalDateTime receivedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    public static EmailAuth createAuthEmail(String email) {
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

}
