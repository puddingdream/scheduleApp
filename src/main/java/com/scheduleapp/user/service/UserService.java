package com.scheduleapp.user.service;

import com.scheduleapp.exception.PasswordMissException;
import com.scheduleapp.exception.UserNullException;
import com.scheduleapp.user.dto.*;
import com.scheduleapp.user.entity.User;
import com.scheduleapp.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입(생성)
    @Transactional
    public CreateUserResponse crateAccount(@Valid CreateUserRequest request) {
        return CreateUserResponse.from(userRepository.save(User.from(request)));

    }

    //회원 전체조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(GetUserResponse::from).toList();
    }

    //회원 단건조회
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNullException("존재하지 않는 유저입니다.")
        );
        return GetUserResponse.from(user);
    }


    //회원정보수정
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNullException("존재하지 않는 유저입니다.")
        );
        if(!user.getPassword().equals(request.password())){
            throw new PasswordMissException("비밀번호를 틀렸습니다.");
        }
        String name = request.name() != null ? request.name() : user.getName();
        String email = request.email() != null ? request.email() : user.getEmail();
        user.update(name, email);
        return UpdateUserResponse.from(user);

    }

    //삭제
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNullException("존재하지 않는 유저입니다.")
        );
        user.delete();


    }

    // 로그인전용 검증만
    @Transactional(readOnly = true)
    public User login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new PasswordMissException("이메일 또는 비밀번호가 일치하지 않습니다.")
        );
        if (!request.password().equals(user.getPassword())) {
            throw new PasswordMissException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
}
