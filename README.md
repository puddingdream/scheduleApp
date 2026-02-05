
# 📅 Schedule Application

## 📌 ERD (Entity Relationship Diagram)

```mermaid
erDiagram
    SCHEDULE {
        Long schedule_id PK
        String title
        String content
        String writer
        String password
        LocalDateTime created_at
        LocalDateTime modified_at
    }

    COMMENT {
        Long comment_id PK
        String comment
        String writer
        String password
        LocalDateTime created_at
        LocalDateTime modified_at
        Long schedule_id FK
    }

    SCHEDULE ||--o{ COMMENT : "has"
    %% 비즈니스 제약사항: 하나의 일정에는 최대 10개의 댓글만 존재할 수 있음
````

---

## 📌 API 명세서

### 🔹 데이터 정의

#### 📅 일정(Schedule)

| 데이터 명  | 키          | 예시 값        |
| ------ | ---------- | ----------- |
| 스케줄 번호 | scheduleId | 1           |
| 제목     | title      | "과제하기"      |
| 내용     | content    | "스케줄 앱 만들기" |
| 작성자    | writer     | "배철수"       |
| 패스워드   | password   | "12345678"  |
| 작성 시간  | createdAt  | 시간          |
| 수정 시간  | modifiedAt | 시간          |

#### 💬 댓글(Comment)

| 데이터 명 | 키          | 예시 값       |
| ----- | ---------- | ---------- |
| 댓글 번호 | commentId  | 1          |
| 댓글 내용 | comment    | "히히"       |
| 작성자   | writer     | "김철수"      |
| 패스워드  | password   | "12345678" |
| 작성 시간 | createdAt  | 시간         |
| 수정 시간 | modifiedAt | 시간         |

---

## 🟢 일정 생성

### Request

* **Method:** POST
* **URL:** `/schedules`
* **Content-Type:** application/json

```json
{
  "title": "과제하기",
  "content": "일정 관리 앱 구현하기",
  "writer": "배철수",
  "password": "12345678"
}
```

### Response

* **Status Code:**

  * 201 CREATED
  * 400 BAD REQUEST

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "일정 관리 앱 구현하기",
  "writer": "배철수",
  "createdAt": "2026-02-04T19:13:35.036846",
  "modifiedAt": "2026-02-04T19:13:35.036846"
}
```

---

## 🟢 댓글 생성

### Request

* **Method:** POST
* **URL:** `/schedules/{scheduleId}/comments`
* **Content-Type:** application/json

```json
{
  "comment": "히히",
  "writer": "김철수",
  "password": "12345678"
}
```

### Response

* **Status Code:**

  * 201 CREATED
  * 400 BAD REQUEST

```json
{
  "commentId": 1,
  "comment": "히히",
  "writer": "김철수",
  "createdAt": "2026-02-04T19:15:30.397854",
  "modifiedAt": "2026-02-04T19:15:30.397854"
}
```

---

## 🟢 일정 전체 조회

### Request

* **Method:** GET
* **URL:** `/schedules`
* **Query Parameter (선택):**

| 키      | 값   |
| ------ | --- |
| writer | 배철수 |

### Response

* **Status Code:** 200 OK

```json
[
  {
    "scheduleId": 1,
    "title": "스프링 과제 제출",
    "content": "일정 관리 앱 구현하기",
    "writer": "배철수",
    "createdAt": "2026-02-04T19:15:30.397854",
    "modifiedAt": "2026-02-04T19:15:30.397854"
  },
  {
    "scheduleId": 2,
    "title": "자바과제",
    "content": "커머스과제하기",
    "writer": "배철수",
    "createdAt": "2026-02-02T19:15:30.397854",
    "modifiedAt": "2026-02-02T19:15:30.397854"
  }
]
```

* 작성자가 있으면 해당 작성자의 일정만 조회
* 작성자가 없으면 전체 일정 조회

---

## 🟢 일정 단건 조회 (댓글 포함)

### Request

* **Method:** GET
* **URL:** `/schedules/{scheduleId}`

### Response

* **Status Code:** 200 OK

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "일정 관리 앱 구현하기",
  "writer": "배철수",
  "createdAt": "2026-02-04T19:13:35.036846",
  "modifiedAt": "2026-02-04T19:13:35.036846",
  "comments": [
    {
      "commentId": 1,
      "comment": "히히",
      "writer": "김철수",
      "createdAt": "2026-02-04T19:15:30.397854",
      "modifiedAt": "2026-02-04T19:15:30.397854"
    }
  ]
}
```

---

## 🟢 일정 수정

### Request

* **Method:** PATCH
* **URL:** `/schedules/{scheduleId}`

```json
{
  "title": "과제하기",
  "writer": "배철수",
  "password": "12345678"
}
```

### Response

* **Status Code:**

  * 200 OK
  * 400 BAD REQUEST
  * 404 NOT FOUND

```json
{
  "scheduleId": 1,
  "title": "과제하기",
  "content": "일정 관리 앱 구현하기",
  "writer": "배철수",
  "createdAt": "2026-02-04T19:13:35.036846",
  "modifiedAt": "2026-02-05T19:13:35.036846"
}
```

---

## 🟢 일정 삭제

### Request

* **Method:** DELETE
* **URL:** `/schedules/{scheduleId}`

```json
{
  "password": "12345678"
}
```

### Response

* **Status Code:**

  * 204 NO CONTENT
  * 400 BAD REQUEST
  * 404 NOT FOUND

---

## 🧠 프로젝트 설명

이 프로젝트는 **Spring Boot 기반의 일정 관리 애플리케이션**으로,
일정을 생성·조회·수정·삭제할 수 있으며 각 일정에는 댓글을 작성할 수 있습니다.

### ✨ 주요 특징

* 일정과 댓글의 1:N 관계 모델링
* 비밀번호 기반 수정/삭제 검증
* 일정 단건 조회 시 댓글 목록 포함
* 댓글 최대 10개 제한 (비즈니스 로직)
* 전역 예외 처리(Global Exception Handler) 적용
* 입력값 검증 로직을 Service 계층에서 명시적으로 처리

### 🛠 기술 스택

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok

---

## ✅ 정리

> 이 프로젝트는
> **CRUD 기본기 + REST API 설계 + 예외 처리 + 비즈니스 규칙 처리**를
> 종합적으로 연습하기 위한 일정 관리 애플리케이션입니다.

```

---

