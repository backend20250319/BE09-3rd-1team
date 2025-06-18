# ✅ 프로젝트 기획서
---

## **1. 프로젝트 개요**

본 프로젝트는 야놀자와 에어비앤비와 같은 숙박 예약 플랫폼을 참고하여,
Spring Framework 기반으로 MSA 방식으로 설계 및 구현한 숙박 예약 시스템입니다.

각 기능(회원, 숙소, 예약, 결제, 리뷰 등)은 독립적인 마이크로서비스로 나누어 개발되었으며,
서비스 간 통신은 REST API와 Eureka 기반의 서비스 디스커버리, Spring Cloud Gateway를 통한 API 라우팅 통해 수행됩니다.

인증 및 인가 기능은 JWT를 활용하여 구현하였으며, 전체 시스템은 유지보수성과 확장성을 고려하여 구성하였습니다.

---

## **2. 프로젝트 목표 및 범위**

### **목표**

- MSA 구조에 대한 이해와 실습을 통해 대규모 시스템 설계 역량 강화
- Spring Boot 기반의 마이크로서비스 구현 및 서비스 간 통신 기술 습득
- JWT 기반의 인증/인가 및 보안 처리에 대한 경험
- 실제 서비스 운영을 고려한 RESTful API 설계 및 예외 처리 방식 적용

### **범위**

- **회원 서비스 (User-Service)**
회원가입, 로그인, 사용자 정보 조회 등 사용자 관련 기능 제공
- **숙소 서비스 (Room-Service)**
숙소 등록, 수정, 삭제, 목록 및 상세 조회 기능 구현
- **예약 서비스 (Reservation-Service)**
숙소 예약 및 예약 내역 관리 기능 제공
- **결제 서비스 (Payment-Service)**
예약에 대한 결제 처리 및 결제 내역 관리 기능 구현
- **리뷰 서비스 (Review-Service)**
이용자 후기 등록 및 조회 기능 제공
- **API Gateway**
클라이언트와 각 마이크로서비스 간의 요청 라우팅 처리
- **인증/인가 처리**
JWT 기반의 사용자 인증 및 권한 분기 처리

---

## **3. Target**

- **일반 사용자 (소비자)**
숙소 검색, 예약, 결제, 리뷰 작성 등 숙박 서비스를 이용하고자 하는 고객
- **관리자 (판매자 역할)**
숙소 등록/수정/삭제 등 숙박 관련 CRUD 기능을 수행하며 플랫폼 내 숙소를 관리하는 주체

---

## **4. Skill Stack**

- **Back-end**
    - Java 17 - 안정성과 최신 문법 지원을 위한 언어 선택
    - Spring Boot 3.4.6 - RESTFUL API 개발을 위한 핵심 프레임워크
    - Spring Security  - 인증 및 권한 부여 기능 구현
    - Spring DATA JPA - ORM 기반 데이터베이스 연동
    - MySQL - 주요 데이터 저장용 RDBMS
    - JWT - 사용자 인증을 위한 토큰 기반 인증
    - ModelMapper - DTO 변환을 위한 객체 매핑 도구
- **Microservices & Infra**
    - Spring Cloud Gateway - API Gateway 역할 수행
    - Spring Cloud Netflix Eureka - 서비스 등록 및 디스커버리
    - Spring Boot Actuator - 어플리케이션 모니터링
    - Lombok - 코드 간결화를 위한 어노테이션 활용
- **API 문서화**
    - Springdoc OpenAPI (Swagger) - 자동 API 문서화 및 테스트 UI 제공
- **DEV Tools & etc**
    - IntelliJ IDEA - Main Developement Tool
    - Gradle - Build Tool
    - Git - Version management Tool

---

## **5. MSA**

본 프로젝트는 도메인별로 Service의 책임을 분리하여 MSA 방식으로 구축되었습니다.

각 Service는 독립적으로 배포 및 확장이 가능하며, 아래와 같은 구성으로 이루어져 있습니다.

1. **Gateway Service**
- 클라이언트와 각 마이크로서비스 간의 진입 지점 역할
- 요청 라우팅, 필터링, 인증 토큰 전달 등을 담당
1. **User Service**
- 회원가입, 로그인, 사용자 정보 조회 등의 사용자 관련 기능 처리
- JWT 토큰 발급 및 검증 기능 포함
1. **Room Service**
- 숙박 시설 등록, 수정, 삭제, 조회 기능 제공
- 관리자 권한 기반 CRUD 제한 적용
1. **Reservation Service**
- 사용자의 예약 생성, 조회, 취소 기능 제공
- 방 정보와 사용자 정보 연동
1. **Review Service**
- 사용자 후기 등록, 수정, 삭제, 조회 기능 제공
- 방 ID 및 사용자 ID를 기반으로 연결
1. **Payment Service**
- 예약에 대한 결제 요청, 결제 완료, 환불 처리 기능 제공
- 외부 결제 API 연동을 고려한 구조
1. **Eureka Server**
- 각 마이크로서비스의 위치를 관리하는 서비스 Discovery Server
- Service 간의 동적 등록 및 탐색 지원

---

## **6. 개발 방식 및 전략**

- **Domain - Driven Design**
    - 각 Service 는 명확한 Domain 책임을 기준으로 분리되어 독립적인 기능을 수행합니다.
- **RESTFUL API 설계 원칙 준수**
    - HTTP method 와 URI 구조를 명확하게 정의하여 일관성 있는 API 제공
- **JWT 기반 인증 및 인가**
    - 인증은 User Service에서 JWT 토큰을 발급하고, 각 Service 에서는 해당 토큰을 검증하여 사용자 권한을 판단합니다.
- **Spring Cloud Gateway 도입**
    - Gateway에서 모든 요청을 통제하여 각 마이크로서비스로 안전하게 전달하고, 인증 필터를 통해 사용자 검증을 사전 수행합니다.
- **Git Flow 전략 기반 협업**
    - main, develop, feature 브랜치 전략을 통해 안정적인 배포 및 기능 개발을 병행
- **무중단 배포 고려한 Service 구조**
    - 각 Service는 Eureka를 통해 동적으로 등록 및 탐색되며, 장애 발생 시 빠른 대체가 가능하도록 구성


<br>

# ✅ 요구사항 정의서
---
## 담당 기능
| 담당자 이름     | 담당 서버               | 담당 기능 요약                              |
| ---------- | ------------------- | ------------------------------------- |
| **이지용**  | Gateway Service     | 요청 라우팅, 인증 토큰 전달                      |
|            | User Service        | 회원가입, 로그인, 사용자 정보 조회/수정, 관리자 회원 정보 조회 |
| **박경빈**  | Room Service        | 방 등록, 수정, 삭제, 전체 조회, 상세 조회, 여러 방 조회   |
| **이정아**  | Reservation Service | 예약 생성, 예약된 날짜 조회, 예약 내역 조회, 예약 취소     |
| **배기열**  | Review Service      | 후기 등록, 수정, 삭제, 조회                     |
| **박범석** | Payment Service     | 결제 요청, 결제 완료 처리, 결제 정보 조회                |


---
## **1. 기능 요구 사항**

---

### 공통 마이크로서비스 기능 요구 사항

### Gateway Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| gateway-service | `ALL` | `NULL` | GW-001 | 요청 라우팅 | 클라이언트 요청을 각 서비스로 전달 | 필수 |
| gateway-service | `ALL` | `NULL` | GW-002 | 인증 토큰 전달 | 인증 헤더를 포함한 요청 전달 | 필수 |

### User Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| user-service | `POST` | `NULL` | US-001 | 회원가입 | 이메일, 비밀번호 등으로 사용자 등록 | 필수 |
| user-service | `POST` | `NULL` | US-002 | 로그인 | 사용자 인증 및 JWT 발급 | 필수 |
| user-service | `GET/PUT` | `CUSTOMER` | US-003 | 사용자 정보 조회/수정 | 사용자 본인의 정보 관리 | 중간 |
| user-service | `GET` | `ADMIN` | US-004 | 관리자 회원 정보 조회 | 전체 사용자 목록 확인 | 중간 |

### Room Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| room-service | `POST` | `SELLER` | RS-001 | 방 등록 | 관리자가 방을 등록할 수 있음 | 필수 |
| room-service | `PUT` | `SELLER` | RS-002 | 방 수정 | 등록된 방 정보를 수정 | 필수 |
| room-service | `DELETE` | `SELLER` | RS-003 | 방 삭제 | 등록된 방을 삭제 | 필수 |
| room-service | `GET` | `NULL` | RS-004 | 방 전체 조회 | 전체 방 목록 조회 | 필수 |
| room-service | `GET` | `NULL` | RS-005 | 방 상세 조회 | ID 기반 단일 방 조회 | 필수 |
| room-service | `POST` | `NULL` | RS-006 | 여러 방 조회 | ID 리스트 기반 Batch 조회 | 중간 |

### Reservation Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| reservation-service | `POST` | `CUSTOMER` | RV-001 | 예약 생성 | 숙소 예약 | 필수 |
| reservation-service | `GET` | `NONE` | RV-002 | 숙소에 이미 예약된 날짜 조회(리스트) | 예약 가능일 판별용 | 필수 |
| reservation-service | `GET` | `CUSTOMER` | RV-003 | 예약 내역 조회 | 사용자 본인의 예약 내역 조회 | 필수 |
| reservation-service | `PATCH` | `CUSTOMER` | RV-003 | 예약 취소 | 사용자 본인의 예약 취소 가능 | 필수 |

### Review Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| review-service | `POST` | `CUSTOMER` | RW-001 | 후기 등록 | 사용자가 방에 대한 후기 작성 | 필수 |
| review-service | `PUT` | `CUSTOMER` | RW-002 | 후기 수정 | 후기 작성자만 수정 가능 | 중간 |
| review-service | `DELETE` | `CUSTOMER` | RW-003 | 후기 삭제 | 후기 작성자만 삭제 가능 | 중간 |
| review-service | `GET` | `NULL` | RW-004 | 후기 조회 | 특정 방의 후기 목록 조회 | 필수 |

### Payment Service

| 서버 | HTTP Method | 권한 | 요구사항 ID | 기능명 | 설명 | 우선순위 |
| --- | --- | --- | --- | --- | --- | --- |
| payment-service | `POST` | `CUSTOMER` | PM-001 | 결제 요청 | 사용자가 예약에 대해 결제 요청 | 필수 |
| payment-service | `PATCH` | `CUSTOMER` | PM-002 | 결제 완료 처리 | 결제 성공 시 상태 변경 | 필수 |
| payment-service | `POST` | `CUSTOMER` | PM-003 | 환불 처리 | 사용자 요청 시 환불 처리 | 중간 |

---

## **2. 비 기능 요구 사항**

| ID | 설명 | 우선순위 |
| --- | --- | --- |
| NF-001 | 모든 사용자 인증은 JWT 기반으로 처리되어야 함 | 높음 |
| NF-002 | 비밀번호는 BCrypt로 암호화하여 저장해야 함 | 높음 |
| NF-003 | 각 API는 Swagger 기반 문서 제공 | 중간 |
| NF-004 | Eureka를 통한 서비스 등록/탐색 필수 | 높음 |

---

## **3. 제약 사항**

- 모든 서비스는 Spring Boot 기반으로 구성됨
- MSA 구조로 서비스별 프로젝트 분리
- 서비스 간 통신은 Feign Client 사용
- API Gateway는 요청 인증 및 라우팅을 담당
- 데이터베이스는 MySQL 사용
- 인증은 JWT 기반의 Stateless 인증 적용


# ✅ 인터페이스 설계서 
---
https://docs.google.com/spreadsheets/d/1nJpWwDPFVh1lToGeEJRUaSdLpMkTaEShcG1omECfzTU/edit?gid=0#gid=0


<br>

# ✅ 테스트 케이스 
---

### **User Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC-USER-001 | user-service | 회원가입 | `NONE` | 유효한 정보로 회원가입 성공 | 이메일, 비밀번호 | 회원가입됨 | `201 Created` |
| TC-USER-002 | user-service | 로그인 | `NONE` | 유효한 정보로 로그인 성공 | 이메일, 비밀번호 | 로그인됨 | `200 OK` |
| TC-USER-003 | user-service | 로그인 | `NONE` | 잘못된 비밀번호로 로그인 실패 | 이메일, 틀린 비밀번호 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-USER-004 | user-service | 내 정보 조회 | `CUSTOMER` | 로그인한 사용자 자신의 정보 조회 | JWT 포함 | 정보 조회됨 | `200 OK` |
| TC-USER-005 | user-service | 내 정보 조회 | `NONE` | 로그인 없이 정보 조회 요청 실패 | 없음 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-USER-006 | user-service | 전체 회원 조회 | `SELLER` | 관리자 권한으로 전체 사용자 조회 | JWT(admin) 포함 | 목록 조회됨 | `200 OK` |
| TC-USER-007 | user-service | 전체 회원 조회 | `CUSTOMER` | 일반 사용자가 전체 사용자 조회 요청 시 실패 | JWT(customer) 포함 | 권한 부족 메시지 반환 | `403 Forbidden` |

---

### **Room Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC-ROOM-001 | room-service | 방 등록 | `SELLER` | 유효한 정보로 숙소 등록 | 제목, 가격 | 등록됨 | `201 Created` |
| TC-ROOM-002 | room-service | 방 등록 | `NONE` | 로그인 없이 숙소 등록 요청 실패 | 제목, 가격 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-ROOM-003 | room-service | 방 등록 | `CUSTOMER` | 권한 없는 사용자 등록 요청 실패 | 제목, 가격 | 권한 부족 메시지 반환 | `403 Forbidden` |
| TC-ROOM-004 | room-service | 방 수정 | `SELLER` | 유효한 정보로 숙소 수정 | 방 ID, 제목, 가격 | 수정됨 | `200 OK` |
| TC-ROOM-005 | room-service | 방 삭제 | `SELLER` | SELLER 권한으로 숙소 삭제 | 방 ID | 삭제됨 | `204 No Content` |
| TC-ROOM-006 | room-service | 전체 조회 | `NONE` | 전체 숙소 리스트 보기 | 없음 | 목록 조회됨 | `200 OK` |
| TC-ROOM-007 | room-service | 상세 조회 | `NONE` | 특정 숙소 상세 정보 조회 | 방 ID | 상세 조회됨 | `200 OK` |

---

### **Reservation Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC-RESERVATION-001 | reservation-service | 예약하기 | `CUSTOMER` | 유효한 예약 정보로 예약 성공 | 숙소 ID, 예약 시작일, 예약 종료일, 금액 | 생성한 예약 ID 반환 | `201 Created` |
| TC-RESERVATION-002 | reservation-service | 예약하기 | `CUSTOMER` | 로그인 없이 예약 요청 시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 금액 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-RESERVATION-003 | reservation-service | 예약하기 | `CUSTOMER` | 필수 입력값 누락 시 실패 | 숙소 ID만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` |
| TC-RESERVATION-004 | reservation-service | 예약하기 | `CUSTOMER` | 날짜 형식 오류 등 잘못된 입력값 시 실패 | 잘못된 날짜 형식 입력 | 잘못된 입력 메시지 반환 | `400 Bad Request` |
| TC-RESERVATION-005 | reservation-service | 예약하기 | `CUSTOMER` | 과거 날짜로 예약 요청 시 실패 | 체크인 날짜가 오늘보다 이전 | 날짜 오류 메시지 반환 | `400 Bad Request` |
| TC-RESERVATION-006 | reservation-service | 예약하기 | `CUSTOMER` | 시작일이 종료일 이후인 경우 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 잘못된 입력값 메세지 반환 | `400 Bad Request` |
| TC-RESERVATION-007 | reservation-service | 예약하기 | `CUSTOMER` | 존재하지 않는 숙소 ID 입력시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 잘못된 입력값 메세지 반환 | `404 Not Found` |
| TC-RESERVATION-008 | reservation-service | 예약하기 | `CUSTOMER` | 이미 예약된 기간으로 예약 요청 시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 중복 예약 메시지 반환 | `400 Bad Request` |
| TC-RESERVATION-009 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 유효한 숙소 ID와 년도/월 입력시 예약 날짜 리스트 조회 성공 | 숙소 ID, 연도/월 | 숙소의 예약 날짜 리스트 반환 | `200 OK` |
| TC-RESERVATION-010 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 존재하지 않는 숙소 ID 입력시 실패 | 숙소 ID, 연도/월 | 존재하지 않는 숙소 메세지 반환 | `404 Not Found` |
| TC-RESERVATION-011 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 입력값 형식이 잘못되어 실패 | 숙소 ID, 연도/월 | 잘못된 입력값 메세지 반환 | `*400 Bad Request*` |
| TC-RESERVATION-012 | reservation-service | 예약 목록 조회 | `CUSTOMER` | 입력값 없이 요청 | - | 숙소 예약 목록 반환 | `200 OK` |
| TC-RESERVATION-013 | reservation-service | 예약 목록 조회 | `CUSTOMER` | 로그인 없이 요청 |  | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-RESERVATION-014 | reservation-service | 예약 취소 | `CUSTOMER` | 유효한 예약 ID 입력시 성공 | 예약 ID | 취소된 예약 ID 반환 | `200 OK` |
| TC-RESERVATION-015 | reservation-service | 예약 취소 | `CUSTOMER` | 로그인없이 예약 취소 요청시 실패 | 예약 ID | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-RESERVATION-016 | reservation-service | 예약 취소 | `CUSTOMER` | 입력값 형식이 잘못되어 실패 | 예약 ID | 잘못된 입력값 메세지 반환 | `400 Bad Reques` |
| TC-RESERVATION-017 | reservation-service | 예약 취소 | `CUSTOMER` | 로그인한 유저의 예약 ID가 아닌 경우 실패 | 예약 ID | 권한 없음 메시지 반환 | `403 Forbidden` |
| TC-RESERVATION-018 | reservation-service | 예약 취소 | `CUSTOMER` | (체크인 24시간 전) 지나서 취소 요청 시 실패 | 예약 ID  | 취소 불가 메시지 반환 | `400 Bad Request`  |

---

### **Payment Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC-PAYMENT-001 | payment-service | 결제 요청 | `CUSTOMER` | 유효한 예약에 대해 결제 성공 | 예약 ID, 금액 | 결제됨 | `200 OK` |
| TC-PAYMENT-002 | payment-service | 결제 요청 | `NONE` | 로그인 없이 결제 요청 실패 | 예약 ID, 금액 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-PAYMENT-003 | payment-service | 결제 요청 | `CUSTOMER` | 금액 누락 시 실패 | 예약 ID만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` |
| TC-PAYMENT-004 | payment-service | 결제 완료 | `CUSTOMER` | 결제 완료 처리 | 예약 ID | 완료됨 | `200 OK` |
| TC-PAYMENT-005 | payment-service | 환불 요청 | `CUSTOMER` | 환불 처리 성공 | 예약 ID | 환불됨 | `200 OK` |

---

### **Review Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC-REVIEW-001 | review-service | 후기 작성 | `CUSTOMER` | 숙소 이용 후 후기 작성 성공 | 숙소 ID, 평점, 내용 | 작성됨 | `201 Created` |
| TC-REVIEW-002 | review-service | 후기 작성 | `NONE` | 비로그인 상태에서 후기 작성 실패 | 숙소 ID, 평점, 내용 | 인증 실패 메시지 반환 | `401 Unauthorized` |
| TC-REVIEW-003 | review-service | 후기 작성 | `CUSTOMER` | 평점 누락 시 후기 작성 실패 | 숙소 ID, 내용만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` |
| TC-REVIEW-004 | review-service | 후기 수정 | `CUSTOMER` | 본인이 작성한 후기 수정 성공 | 후기 ID, 내용 수정 | 수정됨 | `200 OK` |
| TC-REVIEW-005 | review-service | 후기 삭제 | `CUSTOMER` | 본인이 작성한 후기 삭제 성공 | 후기 ID | 삭제됨 | `204 No Content` |
| TC-REVIEW-006 | review-service | 후기 조회 | `NONE` | 숙소에 달린 후기 목록 조회 | 숙소 ID | 목록 조회됨 | `200 OK` |


<br/>

# ✅ 테스트 케이스 결과서
---

<details>
<summary><strong> 테스트 케이스 결과서 보기</strong></summary>

<br/>

### **User Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 | 결과 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| TC-USER-001 | user-service | 회원가입 | `NONE` | 유효한 정보로 회원가입 성공 | 이메일, 비밀번호 | 회원가입됨 | `201 Created` | SUCCESS |
| TC-USER-002 | user-service | 로그인 | `NONE` | 유효한 정보로 로그인 성공 | 이메일, 비밀번호 | 로그인됨 | `200 OK` | SUCCESS |
| TC-USER-003 | user-service | 로그인 | `NONE` | 잘못된 비밀번호로 로그인 실패 | 이메일, 틀린 비밀번호 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-USER-004 | user-service | 내 정보 조회 | `CUSTOMER` | 로그인한 사용자 자신의 정보 조회 | JWT 포함 | 정보 조회됨 | `200 OK` | SUCCESS |
| TC-USER-005 | user-service | 내 정보 조회 | `NONE` | 로그인 없이 정보 조회 요청 실패 | 없음 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-USER-006 | user-service | 전체 회원 조회 | `SELLER` | 관리자 권한으로 전체 사용자 조회 | JWT(admin) 포함 | 목록 조회됨 | `200 OK` | SUCCESS |
| TC-USER-007 | user-service | 전체 회원 조회 | `CUSTOMER` | 일반 사용자가 전체 사용자 조회 요청 시 실패 | JWT(customer) 포함 | 권한 부족 메시지 반환 | `403 Forbidden` | SUCCESS |

---

### **Room Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 | 결과 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| TC-ROOM-001 | room-service | 방 등록 | `SELLER` | 유효한 정보로 숙소 등록 | 제목, 가격 | 등록됨 | `201 Created` | SUCCESS |
| TC-ROOM-002 | room-service | 방 등록 | `NONE` | 로그인 없이 숙소 등록 요청 실패 | 제목, 가격 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-ROOM-003 | room-service | 방 등록 | `CUSTOMER` | 권한 없는 사용자 등록 요청 실패 | 제목, 가격 | 권한 부족 메시지 반환 | `403 Forbidden` | SUCCESS |
| TC-ROOM-004 | room-service | 방 수정 | `SELLER` | 유효한 정보로 숙소 수정 | 방 ID, 제목, 가격 | 수정됨 | `200 OK` | SUCCESS |
| TC-ROOM-005 | room-service | 방 삭제 | `SELLER` | SELLER 권한으로 숙소 삭제 | 방 ID | 삭제됨 | `204 No Content` | SUCCESS |
| TC-ROOM-006 | room-service | 전체 조회 | `NONE` | 전체 숙소 리스트 보기 | 없음 | 목록 조회됨 | `200 OK` | SUCCESS |
| TC-ROOM-007 | room-service | 상세 조회 | `NONE` | 특정 숙소 상세 정보 조회 | 방 ID | 상세 조회됨 | `200 OK` | SUCCESS |

---

### **Reservation Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 | 결과 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| TC-RESERVATION-001 | reservation-service | 예약하기 | `CUSTOMER` | 유효한 예약 정보로 예약 성공 | 숙소 ID, 예약 시작일, 예약 종료일, 금액 | 생성한 예약 ID 반환 | `201 Created` | SUCCESS |
| TC-RESERVATION-002 | reservation-service | 예약하기 | `CUSTOMER` | 로그인 없이 예약 요청 시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 금액 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-RESERVATION-003 | reservation-service | 예약하기 | `CUSTOMER` | 필수 입력값 누락 시 실패 | 숙소 ID만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-RESERVATION-004 | reservation-service | 예약하기 | `CUSTOMER` | 날짜 형식 오류 등 잘못된 입력값 시 실패 | 잘못된 날짜 형식 입력 | 잘못된 입력 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-RESERVATION-005 | reservation-service | 예약하기 | `CUSTOMER` | 과거 날짜로 예약 요청 시 실패 | 체크인 날짜가 오늘보다 이전 | 날짜 오류 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-RESERVATION-006 | reservation-service | 예약하기 | `CUSTOMER` | 시작일이 종료일 이후인 경우 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 잘못된 입력값 메세지 반환 | `400 Bad Request` | SUCCESS |
| TC-RESERVATION-007 | reservation-service | 예약하기 | `CUSTOMER` | 존재하지 않는 숙소 ID 입력시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 잘못된 입력값 메세지 반환 | `404 Not Found` | SUCCESS |
| TC-RESERVATION-008 | reservation-service | 예약하기 | `CUSTOMER` | 이미 예약된 기간으로 예약 요청 시 실패 | 숙소 ID, 예약 시작일, 예약 종료일, 결제 금액 | 중복 예약 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-RESERVATION-009 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 유효한 숙소 ID와 년도/월 입력시 예약 날짜 리스트 조회 성공 | 숙소 ID, 연도/월 | 숙소의 예약 날짜 리스트 반환 | `200 OK` | SUCCESS |
| TC-RESERVATION-010 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 존재하지 않는 숙소 ID 입력시 실패 | 숙소 ID, 연도/월 | 존재하지 않는 숙소 메세지 반환 | `404 Not Found` | SUCCESS |
| TC-RESERVATION-011 | reservation-service | 숙소의 예약 날짜 리스트 조회 | `NONE` | 입력값 형식이 잘못되어 실패 | 숙소 ID, 연도/월 | 잘못된 입력값 메세지 반환 | `*400 Bad Request*` | SUCCESS |
| TC-RESERVATION-012 | reservation-service | 예약 목록 조회 | `CUSTOMER` | 입력값 없이 요청 | - | 숙소 예약 목록 반환 | `200 OK` | SUCCESS |
| TC-RESERVATION-013 | reservation-service | 예약 목록 조회 | `CUSTOMER` | 로그인 없이 요청 |  | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-RESERVATION-014 | reservation-service | 예약 취소 | `CUSTOMER` | 유효한 예약 ID 입력시 성공 | 예약 ID | 취소된 예약 ID 반환 | `200 OK` | SUCCESS |
| TC-RESERVATION-015 | reservation-service | 예약 취소 | `CUSTOMER` | 로그인없이 예약 취소 요청시 실패 | 예약 ID | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-RESERVATION-016 | reservation-service | 예약 취소 | `CUSTOMER` | 입력값 형식이 잘못되어 실패 | 예약 ID | 잘못된 입력값 메세지 반환 | `400 Bad Reques` | SUCCESS |
| TC-RESERVATION-017 | reservation-service | 예약 취소 | `CUSTOMER` | 로그인한 유저의 예약 ID가 아닌 경우 실패 | 예약 ID | 권한 없음 메시지 반환 | `403 Forbidden` | SUCCESS |
| TC-RESERVATION-018 | reservation-service | 예약 취소 | `CUSTOMER` | (체크인 24시간 전) 지나서 취소 요청 시 실패 | 예약 ID  | 취소 불가 메시지 반환 | `400 Bad Request`  | SUCCESS |

---

### **Payment Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 | 결과 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| TC-PAYMENT-001 | payment-service | 결제 요청 | `CUSTOMER` | 유효한 예약에 대해 결제 성공 | 예약 ID, 금액 | 결제됨 | `200 OK` | SUCCESS |
| TC-PAYMENT-002 | payment-service | 결제 요청 | `NONE` | 로그인 없이 결제 요청 실패 | 예약 ID, 금액 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-PAYMENT-003 | payment-service | 결제 요청 | `CUSTOMER` | 금액 누락 시 실패 | 예약 ID만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-PAYMENT-004 | payment-service | 결제 완료 | `CUSTOMER` | 결제 완료 처리 | 예약 ID | 완료됨 | `200 OK` | SUCCESS |
| TC-PAYMENT-005 | payment-service | 환불 요청 | `CUSTOMER` | 환불 처리 성공 | 예약 ID | 환불됨 | `200 OK` | SUCCESS |

---

### **Review Service**

| TC ID | 서비스 | 기능 | 권한 | 시나리오 설명 | 입력값 예시 | 예상 응답 내용 | HTTP 코드 | 결과 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| TC-REVIEW-001 | review-service | 후기 작성 | `CUSTOMER` | 숙소 이용 후 후기 작성 성공 | 숙소 ID, 평점, 내용 | 작성됨 | `201 Created` | SUCCESS |
| TC-REVIEW-002 | review-service | 후기 작성 | `NONE` | 비로그인 상태에서 후기 작성 실패 | 숙소 ID, 평점, 내용 | 인증 실패 메시지 반환 | `401 Unauthorized` | SUCCESS |
| TC-REVIEW-003 | review-service | 후기 작성 | `CUSTOMER` | 평점 누락 시 후기 작성 실패 | 숙소 ID, 내용만 입력 | 입력값 누락 메시지 반환 | `400 Bad Request` | SUCCESS |
| TC-REVIEW-004 | review-service | 후기 수정 | `CUSTOMER` | 본인이 작성한 후기 수정 성공 | 후기 ID, 내용 수정 | 수정됨 | `200 OK` | SUCCESS |
| TC-REVIEW-005 | review-service | 후기 삭제 | `CUSTOMER` | 본인이 작성한 후기 삭제 성공 | 후기 ID | 삭제됨 | `204 No Content` | SUCCESS |
| TC-REVIEW-006 | review-service | 후기 조회 | `NONE` | 숙소에 달린 후기 목록 조회 | 숙소 ID | 목록 조회됨 | `200 OK` | SUCCESS |

</details>


<br>


# ✅ 후기
---

- 이지용
    - MSA구조에서 스프링 프로젝트를 진행하면서 배웠던 것을 활용해 볼 수 있어서 좋았고 앞으로 더 복잡한 구조에서 사용해 보고 싶다는 생각이 들었다. 또한 예외처리나 테스트 코드와 같은 좀 상세한 부분에서 세심하게 노력을 기울여야겠다는 생각이 들었기 때문에 다음 프로젝트를 하면서 이번에 느꼈던 부족한 점을 더 생각하면서 개발을 진행해야겠다는 생각을 했다.
- 박경빈
    - 이번 프로젝트를 통해 MSA 구조의 개발 방식이 얼마나 효율적인지 체감할 수 있었습니다. 미팅을 통해 합의된 내용을 바탕으로 각자 맡은 서비스를 개발하고, 이를 메인 시스템에 연동하는 과정이 매우 흥미로웠습니다. 특히 각 기능을 독립적으로 추가할 수 있다는 점이 인상 깊었고, 여기에 프론트엔드까지 더해진다면 더욱 완성도 높은 결과물이 나올 것 같아 기대됩니다.
- 이정아
    - 예약 서비스를 맡아 room-service와 payment-service와의 API 연동을 통해 자연스럽게 MSA 구조를 익힐 수 있었습니다. ERD 설계 없이 바로 구현에 들어가는 방식이 처음이라 당황했지만, 직접 부딪히며 왜 그렇게 진행하는지 이해할 수 있었습니다. 모든 팀원이 각자의 역할을 책임감 있게 수행해준 덕분에 프로젝트를 잘 마무리할 수 있었고, 이어지는 다음 프로젝트에서는 이번에 살짝 아쉬웠던 점들을 보완해 더 완성도 높은 결과물을 만들어보고 싶습니다.    
- 배기열
    - Postman을 통해 지금까지 작동 시 오류가 발생하는 경우가 많았는데 , 해당 프로젝트를 통해 오류가 나지 않고 잘 동작하는 것을 보며 신기했다. 생각보다 단순하기도 하고 복잡하기도 했으며, 기존 백엔드와 다른 느낌이 많이 들어 해당 용어가 무엇인지, 어떻게 인증, 인가를 하는지 찾는데 오래 걸렸다.
- 박범석
    - MSA 구조를 개발하면서 배운 것들을 직접 실습하며 한 단계 더 성장할 수 있었습니다. 부족한 부분은 팀원들과 함께 소통하고 협력하며 해결해 나갔고, 덕분에 프로젝트를 더욱 완성도 있게 마무리할 수 있었습니다. 이번 경험을 통해 기술적 역량뿐만 아니라 협업의 중요성도 다시 한 번 느낄 수 있어 뜻깊었습니다.
