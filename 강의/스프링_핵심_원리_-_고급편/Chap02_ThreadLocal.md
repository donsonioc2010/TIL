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

> 해당 Thread가 ThreadLocal을 모두 사용하고 나면 ThreadLocal.remove()를 호출해서 ThreadLocal에 저장된 값을 꼭 제거해주어야 한다.(OOM이 발생 가능)

> ThreadLocal의 데이터를 꼭 삭제해야 하는 이유가 1개의 요청이 들어오게 되는 경우 ThreadLocal을 사용하고 있을 때, 삭제를 하지 않으면 요청이 완료된 이후에도 ThreadLocal저장소에는 해당 데이터가 남아있게 된다.
> 이렇게 남아있는 데이터는 추후 다른 사용자가 동일한 쓰레드로 요청을 보내게 될 경우 동일한 ThreadLocal에서 다시 저장소를 가져오게 되는데 이렇게 가져온 데이터가 본인의 데이터가 아닌 타인의 데이터를 받게 되는 경우가 발생을 할 수가 있다.
> 위와 같은 케이스는 정보 유출이라는 대사고가 될 수 있으므로 예방을 하려면 꼭! Remove를 사용해야 한다.
