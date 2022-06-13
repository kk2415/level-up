package com.levelup.core.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberRequest {

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "유효한 이메일 형식이 아닙니다")
    private String email;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "비밀번호는 6자리이상, 영문자/숫자만 입력하세요")
    private String password;

    private String confirmPassword;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^[가-힣]{2,}$", message = "이름은 2자리 이상, 한글만 입력하세요")
//    @Size(min = 2)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
//    @Pattern(regexp = "^[0-9]{6,}$", message = "생년월일은 6자리 이상, 숫자만 입력하세요")
//    @Size(min = 6, max = 6, message = "생년월일은 6자리 이상 입력해주세요")
    private String birthday;

    @NotNull
//    @Pattern(regexp = "^0\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 형식이 아닙니다.")
    private String phone;

    private UploadFile uploadFile;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .gender(gender)
                .birthday(birthday)
                .phone(phone)
                .authority(Authority.ANONYMOUS)
                .profileImage(uploadFile)
                .channels(new ArrayList<>())
                .build();
    }

}
