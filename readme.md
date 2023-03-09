# 커피숍 주문 시스템

## 활용 기술

- Spring Boot 3.0.2
- Java 17
- JPA
- MySQL
- H2 (테스트 코드)

## ERD

![erd.png](erd.png)


### 작업내용
- DDL 작성 (데이터 타입, 인덱스, 유니크 고려)
- API 설계 (스웨거를 통한 API docs 제공)
- 서비스 로직 개발 (주문/충전/메뉴 조회/인기메뉴 조회)
- 예외 처리 및 API 응답 일관화
- 동시성 이슈 / 데이터 일관성 보장을 고려한 서비스 개발
  - 동시에 주문이 진행되어 포인트의 일관성이 깨지는 현상을 막기 위하여 Lock을 활용 
- 성능 향상을 위한 캐시 적용
  - 인기메뉴를 조회하는 쿼리의 비용을 줄이기 위한 캐시 활용
- 데이터 수집 플랫폼으로 실시간 주문 데이터 전송
  - 데이터 수집 플랫폼의 의존 없이 주문이 정상적으로 진행된 이후에 카프카 메시지를 발행
  - 트랜잭션이 커밋된 이후에 메시지 발행을 하기 위한 TransactionalEventListener 활용

### 테스트
- 레포지토리 테스트
- 단위 테스트
- 통합 테스트
    1. 주문
    2. 동시에 2건 이상의 주문



