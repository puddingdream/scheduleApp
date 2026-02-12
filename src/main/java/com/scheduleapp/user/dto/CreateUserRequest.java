package com.scheduleapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

       @Size(min = 2 , max = 12 , message = "닉네임은 2자리이상 12자리 이하여야 합니다.")
       @NotBlank(message = "이름은 필수입니다.")
        String name,
        @Email(message = "올바른 이메일이 아닙니다.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,
       @Size(min = 8 , max = 20 , message = "비밀번호는 8자리이상 20자리 이하여야 합니다.")
       @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
