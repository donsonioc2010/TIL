# Mokito

## Mokito란?

> `Mock`을 지원하는 프레임워크를 의미하며, `Mock`객체를 쉽게 제작, 관리, 검증 할 수 있는 방법을 제공한다.

### Mock이란?

> **진짜 객체와 비슷하게 동작**하지만 프로그래머가 직접 그 객체의 행동을 개발자가 직접 관리하는 가짜(?) 객체

### Mock이 필요한 이유?

- 속도
  - 실제 `@SpringBootTest`는 상대적으로 느린점이 문제
- 외부 API 테스트의 동작 예측 이후 기능 테스트등
  - 개발서버가 존재하는 경우 실제 Response를 받아서 처리를 진행하면 되지만, 존재하지 않는 경우 Mock을 활용시 더욱 유용

### How To Start

> `spring-boot-starter-test`에 기본 include되어있슴

#### Spring Boot를 활용하지 않는 경우 필요 Dependencies

- `mockito-core`
  - mockito를 사용할 수 있는 Library
- `mockito-junit-jupiter`
  - junut test에서 mockito를 연동해서 사용할 수 있게 하는 Library
