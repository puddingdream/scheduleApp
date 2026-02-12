package com.scheduleapp.user.controller;

import com.scheduleapp.exception.NoSessionLogOutException;
import com.scheduleapp.user.dto.*;
import com.scheduleapp.user.entity.User;
import com.scheduleapp.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserContorller {

    private final UserService userService;

    //생성(회원가입)
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createAccount(
            @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.crateAccount(request));
    }

    //전체조회
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    //단건조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    //수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> update(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    //삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId
    ){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {
        User user = userService.login(request);
        SessionUser sessionUser = new SessionUser(
                user.getUserId(),
                user.getName(),
                user.getEmail());
        session.setAttribute("loginUser", sessionUser);

        LoginResponse response = new LoginResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session) {
        if (sessionUser == null) {
            throw new NoSessionLogOutException("SessionUser is null");
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
