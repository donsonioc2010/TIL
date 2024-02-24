# Chap02 - Thread Local

> Thread Local은 해당 스레드만 접근할 수 있는 특별한 저장소인지를 의미한다.
> 예시가 은행창구로, 우리는 금고같은장소에 물건을 보관하더라도, 해당 창구의 직원들은 사용자를 구분해서 물건을 돌려주는것과 동일한 개념이다.

## ThreadLocal 선언 및 사용법

```java
// 선언
ThreadLocal<?> variable = new ThreadLocal<>();

// 저장
variable.set(ObjectVariable);

// 조회
variable.get();

// 삭제
variable.remove();
```

## ThreadLocal사용시 주의사항

> 해당 Thread가 ThreadLocal을 모두 사용하고 나면 ThreadLocal.remove()를 호출해서 ThreadLocal에 저장된 값을 꼭 제거해주어야 한다.
