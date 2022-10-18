package com.levelup.member.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "email_auth")
@Entity
public class EmailAuth extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "email_auth_id")
    private Long id;

    @Column(nullable = false, name = "email_auth_type")
    @Enumerated(EnumType.STRING)
    private EmailAuthType authType;

    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String securityCode;

    @Setter
    @Column(nullable = false)
    private Boolean isAuthenticated;

    @Setter
    @Column(nullable = false)
    private LocalDateTime receivedDate;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    protected EmailAuth() {}

    public static EmailAuth of(
            EmailAuthType authType,
            String email,
            Boolean isAuthenticated,
            LocalDateTime receivedDate,
            LocalDateTime expireDate,
            Member member)
    {
        return new EmailAuth(
                null,
                authType,
                email,
                createSecurityCode(),
                isAuthenticated,
                receivedDate,
                expireDate,
                member);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public static String createSecurityCode() {
        Random rand = new Random();
        StringBuilder securityCode = new StringBuilder();

        int idx = 0;
        while (idx < 6) {
            String randomInt = Integer.toString(rand.nextInt(10));
            if (!securityCode.toString().contains(randomInt)) {
                securityCode.append(randomInt);
                idx++;
            }
        }

        return securityCode.toString();
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
