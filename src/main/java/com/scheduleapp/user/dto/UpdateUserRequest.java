package com.scheduleapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(min = 2 , max = 12 , message = "닉네임은 2자리이상 12자리 이하여야 합니다.")
        String name,    // 안바꾸면 그대로하는 로직만들기
        @Email(message = "올바른 이메일이 아닙니다.")
        String email,   // 안바꾸면 그대로하는 로직만들기
        String password //검증해야함
) {
}
