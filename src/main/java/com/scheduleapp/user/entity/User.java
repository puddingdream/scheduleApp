package com.scheduleapp.user.entity;

import com.scheduleapp.baseEntity.SoftDeleteEntity;
import com.scheduleapp.user.dto.CreateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "users")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User from(CreateUserRequest request) {
        return new User(request.email(),
                request.name(),
                request.password());
    }

}
