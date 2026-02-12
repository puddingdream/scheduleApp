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
            @PathVariable Long userId,
            @RequestBody String password
    ){
        userService.deleteUser(userId, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {
        User user = userService.login(request);
        SessionUser sessionUser =  SessionUser.from(user); // 세션에 넣을 정보

        session.setAttribute("loginUser", sessionUser); // 세션만드는 메소드

        return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.from(user)); //유저한테 간단한정보주기
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, // 세션의 값 가져오기
            HttpSession session) { // 세션그자체
        if (sessionUser == null) { // 가져온 세션값을보고 로그인중이아니면 로그아웃하지않고 오류던지기
            throw new NoSessionLogOutException("SessionUser is null");
        }
        session.invalidate();  // 세션삭제
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
