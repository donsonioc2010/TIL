# README

## Transaction

한국어로는 거래라는 의미, DB에서의 트랜잭션은 하나의 거래를 안전하게 처리하도록 보장하는 것을 의미.

여러개의 쿼리를 동시에 실행해야 할때, 모든작업이 성공해야 반영하게 하는 Commit과 작업중 하나라도 실패시 거래 이전으로 돌리는 Rollback이 핵심

### 트랜잭션의 ACID

- Atomicity (원자성)
    - 트랜잭션의 모든 연산이 정상적으로 수행되어야만 성공
    - 하나라도 실패하면 전체가 실패
- Consistency (일관성)
    - 트랜잭션이 성공적으로 완료되면 일관성있는 상태로 유지
- Isolation (고립성, 격리성)
    - 트랜잭션은 다른 트랜잭션과 격리되어 실행되어야 함
    - 다른 트랜잭션의 연산이 트랜잭션에 영향을 미치지 않아야 함
- Durability (지속성)
    - 트랜잭션이 성공적으로 완료되면 영구적으로 반영되어야 함
    - 시스템에 문제가 발생해도 트랜잭션의 결과는 보존되어야 함

> 트랜잭션은 이러한 ACID를 보장해야 한다. 하지만 큰 문제가 있는데, 그것은 바로 격리성이며, 태른잭션간에 격리성을 완벽히 보장하려면 트랜잭션을 거의 순서대로 실행해야 하기
> 때문에 성능이 떨어진다.
> 이런 문제로 인하여 ANSI표준은 트랜잭션 격리 수준을 4단계로 나누어 정리하였다.

### 트랜잭션 격리 수준 - Isolation Level

> 레벨이 높아질 수록 격리성이 높아지지만 성능이 떨어진다.
> 자주 사용되는 레벨은 Read Committed와 Repeatable Read이다.

- Read Uncommitted (레벨 0)
    - 다른 트랜잭션이 커밋하지 않은 데이터를 읽을 수 있음
    - Dirty Read, Non-Repeatable Read, Phantom Read 발생 가능
- Read Committed (레벨 1)
    - 다른 트랜잭션이 커밋한 데이터만 읽을 수 있음
    - Dirty Read는 발생하지 않음
    - Non-Repeatable Read, Phantom Read 발생 가능
- Repeatable Read (레벨 2)
    - 트랜잭션 내에서 같은 쿼리를 실행하면 같은 결과가 나와야 함
    - Dirty Read, Non-Repeatable Read는 발생하지 않음
    - Phantom Read 발생 가능
- Serializable (레벨 3)
    - 트랜잭션 간에 격리성을 완벽하게 보장
    - Dirty Read, Non-Repeatable Read, Phantom Read 모두 발생하지 않음

### 트랜잭션의 시작위치와 비즈니스 로직

- 트랜잭션의 시작은 비즈니스 로직이 존재하는 서비스 계층에서 시작해야 한다. 비즈니스 로직이 잘못되면 해당 비즈니스 로직으로 인해 문제가 되는 부분을 함께 롤백해야 하기 떄문
- 트랜잭션을 시작하기 위해서는 커넥션이 필요하며, 결국 서비스계층에서 커넥션을 생성하고 트랜잭션 커밋이후 커넥션을 종료해야함.
- 어플리케이션에서 **DB 트랜잭션을 사용하려면 트랜잭션을 사용하는 동안 같은 커넥션을 유지**해야한다. 그래야 **같은 DB세션을 사용** 할 수 있기 떄문.

## Exception
### Exception의 종류
- `Throwable`: 최상위 예외이며, 자식으로 `Error`와 `Exception`이 있다.
- `Error`: 메모리 부족등 심각한 시스템 오류와 같은 어플리케이션에서 복구 불가능한 예외들이며, 이 예외는 잡으려고 하면 안됨.
  - 어플리케이션레벨에서 `Throwable`같은 상위 에러를 잡으면 안되는 이유는 `Error`까지 같이 잡힐 수 있기 떄문임.
  - 어플리케이션레벨에서 `Catch`하는 오류는 위와 같은 이유로 `Exception`부터 필요한 예외로 생각하고 잡아야 한다.
  - `Error`역시 `UnCheckedException`이다.
- `Exception`: 체크예외이며 어플리케이션 로직에서 활용이 가능한 실질적인 최상위 예외이다.
  - `Exception`과 그  하위 예외는 모두 컴파일러가 체크하는 체크 예외이다.
    - 예로 `SQLException`이라든가 `IOException`등이 있다.
  - `RuntimeException`은 예외이다.
    - 예외라는 의미는 컴파일러가 체크하지 않는 `UnChecked`예외라는 의미.
- `RuntimeException`: `Uncehcked Exception`, 런타임 예외
  - 컴파일러가 체크하지 않는 언체크 예외
  - `RuntimeException`과 그 하위 예외는 모두 언체크 예외이다.
    - 예로 `NullPointerException`이라든가 `ArrayIndexOutOfBoundsException`등이 있다.

### Checked Exception?
> `RuntimeException`과 그 하위를 제외한 `Exception`과 그 하위예외는 모두 컴파일러가 체크하는 체크 예외이다.  
> `Chcekced Exception`은 컴파일러가 체크하기 때문에 반드시 잡아서 처리하거나 또는 밖으로 던지도록 선언해야 하며, 그렇지 않은 경우 컴파일 오류가 발생한다.

#### Checked Exception의 장단점
- 장점
  - 개발자가 무조건 예외처리를 해야하기 때문에 예외에 대한 처리가 강제되어 훌륭한 안전장치가 된다.
- 단점
  - 예외처리를 반드시 잡거나 던지도록 해야하기 때문에 너무 번거로워 진다.
  - 크게 신경쓰고 싶지 않은 예외까지 모두 챙겨야 하며, 추가로 의존관계에 따른 단점도 발생한다

### UnChecked Exception?
> `RuntimeException`과 자식 Exception들이 `UnCheckedException`으로 분류되며, 컴파일러가 예외를 체크하지 않는 예외이다.
> `UnCheckedException`은 컴파일러가 체크하지 않기 때문에 예외처리를 강제하지 않는다.

#### UnChecked Exception의 장단점
- 장점
  - 예외처리를 강제하지 않기 때문에 개발자가 예외처리에 대한 부담을 덜 수 있다.
  - 신경쓰고 싶지 않은 예외의 의존관계를 참조하지 않아도 된다.
- 단점
  - 예외처리를 강제하지 않기 때문에 개발자가 예외 처리를 누락 할 수 있다.

### 체크 예외 활용
- 기본적으로 `UnCheckedException`예외를 사용한다.
- `CheckedException`는 비즈니스 로직상 **의도적으로 던지는 예외**만 사용한다.
  - 해당 예외인 경우로는, 예외를 반드시 처리해야 하는 문제일 떄만 `CheckedException`을 사용한다.
    - 계좌이체 실패 예외
    - 결제시 포인트 부족 예외
    - 로그인 ID, PW 불일치 예외
  - 위의 예시처럼, 비즈니스 로직, 사용자 경험상 매우 중요하여 놓치면 안되는 중요한 문제들을 예외로 Throws를 하여 **컴파일러를 통해 인지할 수 있게**한다.