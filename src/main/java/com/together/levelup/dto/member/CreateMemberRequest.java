package com.together.levelup.dto.member;

import com.together.levelup.domain.member.Authority;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@ToString
public class CreateMemberRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
            message = "유효한 이메일 형식이 아닙니다")
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "비밀번호는 6자리이상, 영문자/숫자만 입력하세요")
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[가-힣]{2,}$", message = "이름은 2자리 이상, 한글만 입력하세요")
    @Size(min = 2)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Pattern(regexp = "^[0-9]{6,}$", message = "생년월일은 6자리 이상, 숫자만 입력하세요")
    @Size(min = 6, max = 6, message = "생년월일은 6자리 이상 입력해주세요")
    private String birthday;

    @NotNull
    @Pattern(regexp = "^0\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 형식이 아닙니다.")
    private String phone;

    private UploadFile uploadFile;

    private Authority authority;

}
