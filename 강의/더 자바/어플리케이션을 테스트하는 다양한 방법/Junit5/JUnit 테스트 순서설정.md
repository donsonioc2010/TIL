# Junit의 테스트 순서

> JUnit의 로직에 따라 테스트 순서의 경우 대부분 순서대로 나오나, 언제든 변경이 될 수 있다.

## 순서의 설정 없이 TestCase가 실행이 가능한 이유?

> 정상적으로 제작된 테스트 케이스끼리는 독릭적으로 실행이 가능해한다.
> 즉 의존성이 없어야 한다는 의미이며 그렇기 때문에 순서의 설정을 따로 표출하지는 않는 것

## 순서가 필요한 경우?

> 테스트만이 아니라 어느 일을 하던, 모든게 정답은 없는법...

- 기능테스트, 통합테스트, 시나리오 테스트 등의 순차적인 테스트가 필요한경우
  - Example
    - 회원가입 → 로그인 → 정보수정 등등
    - 게시물 조회 → 작성 → 상세조회 → 수정/삭제 등등

## 순서 설정

1. Test Class위에 `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`어노테이션 추가
2. Method에 `@Order`어노테이션 추가
   1. `springframework.core.annotation`패키지의 `@Order(Integer Value)`와 혼동하는 것 주의
   2. `junit`의 `@Order(Integer value)`를 사용한다
      1. 낮은 Value일수록 `높은 우선순위`를 가진다

### `@TestMethodOrder`의 Value

> `@TestMethodOrder`의 경우 annotation의 `value`로 `MethodOrderer`의 구현체를 요구한다.

#### `MethodOrderer`의 구현체의 종류

> 기본 구현체는 다음과 같다.

- `Alphanumeric`
- `OrderAnnotation`
- `Random`
