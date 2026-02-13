# 📅 Schedule Application (Advanced Version)

## 📌 프로젝트 개요

Spring Boot 기반 일정 관리 애플리케이션입니다.

기존 CRUD 기반 일정 앱에서 확장되어 아래 기능이 추가되었습니다:

- ✅ 유저 CRUD
- ✅ 일정 - 유저 연관관계 (ManyToOne)
- ✅ 회원가입 / 로그인 (Session 기반 인증)
- ✅ 비밀번호 암호화 (BCrypt)
- ✅ 댓글 CRUD
- ✅ 일정 페이징 조회 (Pageable)
- ✅ Validation + Global Exception 처리
- ✅ JPA Auditing 적용

---

# 📌 ERD 

<img width="1901" height="5263" alt="image" src="https://github.com/user-attachments/assets/8faa290e-0251-4bf9-9260-5e15eb2d1875" />


# 📌 데이터 정의

## 👤 User

| 데이터명 | 키 | 설명 |
|----------|----|------|
| 유저 ID | userId | PK |
| 유저명 | name | 최대 4글자 |
| 이메일 | email | 로그인 ID |
| 비밀번호 | password | BCrypt 암호화 저장 |
| 생성일 | createdAt | JPA Auditing |
| 수정일 | modifiedAt | JPA Auditing |

---

## 📅 Schedule

| 데이터명 | 키 | 설명 |
|----------|----|------|
| 일정 ID | scheduleId | PK |
| 제목 | title | 최대 10글자 |
| 내용 | content | |
| 유저 ID | userId | FK |
| 생성일 | createdAt | JPA Auditing |
| 수정일 | modifiedAt | JPA Auditing |

---

## 💬 Comment

| 데이터명 | 키 | 설명 |
|----------|----|------|
| 댓글 ID | commentId | PK |
| 댓글 내용 | comment | |
| 일정 ID | scheduleId | FK |
| 유저 ID | userId | FK |
| 생성일 | createdAt | JPA Auditing |
| 수정일 | modifiedAt | JPA Auditing |

---

# 📌 인증 방식

- 로그인 시 Session 생성
- 이후 수정/삭제 API는 세션 기반 인증
- 비밀번호는 BCrypt 암호화 저장
- 로그인 시 PasswordEncoder.matches() 사용

---

# 📌 API 명세서

---

# 👤 회원가입

### POST /users

```json
{
  "name": "철수",
  "email": "test@test.com",
  "password": "12345678"
}
```

### Response (201)

```json
{
  "userId": 1,
  "name": "철수",
  "email": "test@test.com",
  "createdAt": "2026-02-05T19:13:35",
  "modifiedAt": "2026-02-05T19:13:35"
}
```

---

# 🔐 로그인

### POST /login

```json
{
  "email": "test@test.com",
  "password": "12345678"
}
```

### Response

- 200 OK
- Session 발급

---

# 📅 일정 생성

### POST /schedules

```json
{
  "title": "과제하기",
  "content": "JPA 연관관계 공부",
  "userId": 1
}
```

### Response (201)

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "JPA 연관관계 공부",
  "userName": "철수",
  "createdAt": "2026-02-05T19:13:35",
  "modifiedAt": "2026-02-05T19:13:35"
}
```

---

# 📅 일정 단건 조회

### GET /schedules/{scheduleId}

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "JPA 연관관계 공부",
  "userName": "철수",
  "createdAt": "2026-02-05T19:13:35",
  "modifiedAt": "2026-02-05T19:13:35",
  "comments": [
    {
      "commentId": 1,
      "comment": "좋아요",
      "userName": "영희",
      "createdAt": "2026-02-05T19:14:35",
      "modifiedAt": "2026-02-05T19:14:35"
    }
  ]
}
```

---

# 📅 일정 전체 조회 (페이징)

### GET /schedules?page=0&size=10

- 기본 size = 10
- 수정일 기준 내림차순 정렬

### Response

```json
{
  "content": [
    {
      "scheduleId": 1,
      "title": "과제하기",
      "content": "JPA 공부",
      "commentCount": 2,
      "userName": "철수",
      "createdAt": "2026-02-05T19:13:35",
      "modifiedAt": "2026-02-05T19:13:35"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

---

# 📅 일정 수정

### PATCH /schedules/{scheduleId}

```json
{
  "title": "과제 끝내기"
}
```

---

# 📅 일정 삭제

### DELETE /schedules/{scheduleId}

- 세션 인증 필요

---

# 💬 댓글 생성

### POST /schedules/{scheduleId}/comments

```json
{
  "comment": "좋은 일정입니다."
}
```

---

# 💬 댓글 전체 조회

### GET /schedules/{scheduleId}/comments

---

# 💬 댓글 수정

### PATCH /schedules/{scheduleId}/comments/{commentId}

---

# 💬 댓글 삭제

### DELETE /schedules/{scheduleId}/comments/{commentId}

---

# 📌 예외 처리

- @RestControllerAdvice 적용
- Validation 에러 메시지 커스터마이징
- 존재하지 않는 리소스 → 404
- 권한 없음 → 403
- 입력 오류 → 400

---

# 📌 기술 스택

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- BCrypt
- Lombok

---

# 📌 학습 포인트

- JPA 연관관계 매핑 (ManyToOne)
- N+1 문제 인식
- Pageable + Page 구조 이해
- DTO 분리 설계
- 세션 기반 인증 흐름
- 비밀번호 암호화 전략
- Global Exception Handling

---

# ✅ 최종 정리

이 프로젝트는 단순 CRUD 과제가 아니라

- 계층형 아키텍처 이해
- ORM 사용 감각
- 인증 흐름 설계
- API 설계 경험
- 실무형 페이징 처리

를 경험하기 위한 확장형 일정 관리 애플리케이션입니다.
