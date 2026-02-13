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
# 📌 API 명세서 (최신화 버전)

---

# 📊 데이터 정의

---

## 👤 유저 (User)

| 데이터명 | 키 | 예시값 |
|-----------|------|-----------|
| 유저 ID | userId | 1 |
| 유저명 | name | "철수" |
| 이메일 | email | "test@test.com" |
| 비밀번호 | password | (BCrypt 암호화 저장) |
| 회원가입일 | createdAt | 2026-02-05T19:13:35 |
| 회원수정일 | modifiedAt | 2026-02-05T19:13:35 |

---

## 📅 일정 (Schedule)

| 데이터명 | 키 | 예시값 |
|-----------|------------|------------|
| 스케줄 번호 | scheduleId | 1 |
| 제목 | title | "과제하기" |
| 내용 | content | "JPA 공부하기" |
| 작성 유저 ID | userId | 1 |
| 작성 시간 | createdAt | 2026-02-05T19:13:35 |
| 수정 시간 | modifiedAt | 2026-02-05T19:13:35 |

---

## 💬 댓글 (Comment)

| 데이터명 | 키 | 예시값 |
|-----------|------------|------------|
| 댓글 번호 | commentId | 1 |
| 댓글 내용 | comment | "좋아요" |
| 일정 ID | scheduleId | 1 |
| 작성 유저 ID | userId | 2 |
| 작성 시간 | createdAt | 2026-02-05T19:15:35 |
| 수정 시간 | modifiedAt | 2026-02-05T19:15:35 |

---

# 👤 회원가입

## 🟢 유저 생성

### Request

- Method: POST
- URL: `/users`
- Content-Type: application/json

```json
{
  "name": "철수",
  "email": "test@test.com",
  "password": "12345678"
}
```

### Response

Status Code:
- 201 CREATED
- 400 BAD REQUEST

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

## 🟢 로그인

### Request

- Method: POST
- URL: `/login`
- Content-Type: application/json

```json
{
  "email": "test@test.com",
  "password": "12345678"
}
```

### Response

Status Code:
- 200 OK
- 401 UNAUTHORIZED

✔ 로그인 성공 시 Session 생성

---

# 📅 일정 CRUD

---

## 🟢 일정 생성

### Request

- Method: POST
- URL: `/schedules`
- Session: 필요
- Content-Type: application/json

```json
{
  "title": "과제하기",
  "content": "JPA 공부"
}
```

### Response

Status Code:
- 201 CREATED
- 400 BAD REQUEST
- 401 UNAUTHORIZED

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "JPA 공부",
  "userName": "철수",
  "createdAt": "2026-02-05T19:13:35",
  "modifiedAt": "2026-02-05T19:13:35"
}
```

---

## 🟢 일정 단건 조회

### Request

- Method: GET
- URL: `/schedules/{scheduleId}`

### Response

Status Code:
- 200 OK
- 404 NOT FOUND

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "JPA 공부",
  "userName": "철수",
  "createdAt": "2026-02-05T19:13:35",
  "modifiedAt": "2026-02-05T19:13:35",
  "comments": [
    {
      "commentId": 1,
      "comment": "좋아요",
      "userName": "영희",
      "createdAt": "2026-02-05T19:15:35",
      "modifiedAt": "2026-02-05T19:15:35"
    }
  ]
}
```

---

## 🟢 일정 전체 조회 (페이징)

### Request

- Method: GET
- URL: `/schedules`
- Query Parameter:

| 키 | 설명 |
|----|------|
| page | 페이지 번호 (기본 0) |
| size | 페이지 크기 (기본 10) |

예시:
```
/schedules?page=0&size=10
```

### Response

Status Code:
- 200 OK

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

✔ 수정일 기준 내림차순 정렬

---

## 🟢 일정 수정

### Request

- Method: PATCH
- URL: `/schedules/{scheduleId}`
- Session: 필요

```json
{
  "title": "과제 끝내기",
  "content": "완료"
}
```

### Response

- 200 OK
- 400 BAD REQUEST
- 401 UNAUTHORIZED
- 404 NOT FOUND

---

## 🟢 일정 삭제

### Request

- Method: DELETE
- URL: `/schedules/{scheduleId}`
- Session: 필요

### Response

- 204 NO CONTENT
- 401 UNAUTHORIZED
- 404 NOT FOUND

---

# 💬 댓글 CRUD

---

## 🟢 댓글 생성

### Request

- Method: POST
- URL: `/schedules/{scheduleId}/comments`
- Session: 필요

```json
{
  "comment": "좋은 일정입니다."
}
```

### Response

- 201 CREATED
- 400 BAD REQUEST
- 401 UNAUTHORIZED

---

## 🟢 댓글 전체 조회

### Request

- Method: GET
- URL: `/schedules/{scheduleId}/comments`

### Response

- 200 OK
- 404 NOT FOUND

---

## 🟢 댓글 수정

### Request

- Method: PATCH
- URL: `/schedules/{scheduleId}/comments/{commentId}`
- Session: 필요

```json
{
  "comment": "수정된 댓글"
}
```

---

## 🟢 댓글 삭제

### Request

- Method: DELETE
- URL: `/schedules/{scheduleId}/comments/{commentId}`
- Session: 필요

- 204 NO CONTENT
- 401 UNAUTHORIZED
- 404 NOT FOUND


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
