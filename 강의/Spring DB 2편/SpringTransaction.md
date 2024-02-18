# Spring Transaction

## 트랜잭션을 코드로 사용하는 방법들

### JDBC

> JDBC의 트랜잭션 시작방식

```java
Connection con = dataSource.getConnection()
con.setAutoCommit(false);
```

### JPA

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득
try{
    tx.begin();
    // Business Logic
    tx.commit();
}catch(Exception e) {

}
```

---

## 트랜잭션의 추상화

> 위와 같이 사용법이 불편하다 보니 스프링에서는 트랜잭션의 추상화를 진행하였고 해당 인터페이스는 `PlatformTransactionManager`이다.
> 개발자가 사용하는 JDBC트랜잭션 매니저, JPA트랜잭션 매니저, 하이버네이트 트랜잭션매니저등등 각각의 트랜잭션 매니저는 해당 인터페이스를 상속받아 사용을 하게 되며,
> 스프링 부트는 어떤 데이터 접근 기술을 사용하는지를 자동으로 인식해서 적절한 트랜잭션 매니저 를 선택해서 스프링 빈으로 등록해주기 때문에 트랜잭션 매니저를 선택하고 등록하는 과정도 생략하게 된다.
>
> > 만약 다른 직접 구현하고싶으면 구현도 가능하다.

### PlatformTransactionManager Code

```java
package org.springframework.transaction;

import org.springframework.lang.Nullable;

public interface PlatformTransactionManager extends TransactionManager {
    TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

	void rollback(TransactionStatus status) throws TransactionException;
}
```

---

## Spring Transaction의 사용 방식

> `선언적 트랜잭션 방식`과 `프로그래밍 방식 트랜잭션 관리`가 있다.

### 선언적 트랜잭션 방식

- `@Transactional` 어노테이션 하나만으로 편리하게 적용하는 것
- 과거에는 XML에 설정하기도 하였슴.
- 명칭의 의미 그대로 로직에 트랜잭션을 적용하겠다고 어딘가에 선언하면 적용되는 방식

### 프로그래밍 방식 트랜잭션 관리

- 트랜잭션 매니저 또는 템플릿을 사용해서 트랜잭션 관련 코드를 직접 작성하는 방식, 위의 예제코드의 방식을 의미한다.
- 어플리케이션 코드가 트랜잭션 관련 코드와 강하게 결합하게 된다.

---

## @Transactional 작동원리

`@Transactional`이 클래스나 메소드에 하나라도 존재하는 경우 Transaction AOP는 해당 클래스를 프록시로 생성하여 기존의 클래스를 Spring Container에 등록하는게 아닌 프록시 기술로 제작된 클래스를 Spring Container에 등록한다.

---

## @Transactional의 적용위치와 우선순위

> `@Transactional`어노에티션은 클래스에도, 메소드에도 붙일수 있다. 그러다 보니 어느곳이 우선순위가 높은지 혼란이 올 수 있어 다음과 같이 기록한다.

> [!NOTE]
> 스프링에서 우선순위는 항상 `더 구체적이고 자세한 것이 높은 우선순위를 갖는다`
> 이 말은 Class, Method두개에 모두 어노테이션이 붙어 있는 경우 더 구체적이고 자세한것은 Method라는 것이다.
> 그러면 더 추가해서 Interface, 구현체Class, Method 세개가 존재하는경우에는 `Method > 구현체 클래스 > Interface순으로 더 구체적이라는 것을 의미`한다.

### 적용되는 순서의 우선순위

> `@Transactional`이 적용되는 우선순위는 다음의 순위로 이뤄진다.
> 하지만 Spring 공식 메뉴얼에서 적혀있는 사항으로는 Interface에서 `@Transactional`을 사용하는 것은 공식 메뉴얼에서 적용하지 않으며, 이유는 추후 AOP의 방식이 어떻게 될지 모르기 때문. 사용은 가능하다.

1. 클래스내부의 메소드
2. 클래스의 타입(상단)
3. 인터페이스의 메소드
4. 인터페이스의 타입(상단)

---

## 트랜잭션 AOP사용시 주의사항

> `@Transactional`을 사용을 하면 스프링의 트랜잭션 AOP가 적용이 되며, 트랜잭션 AOP는 기본적으로 프록시 방식의 AOP를 사용한다.
> 즉 실행되는 순서는 `트랜잭션을 적용한 프록시 객체가 먼저 요청을 처리(트랜잭션처리)`를 한 이후 `실제 로직이 구현된 메소드를 호출`한다.
>
> 위의 말의 뜻은 트랜잭션을 적용하려면 항상 프록시를 통해서 대상객체(로직이 구현된 메소드)를 호출해야 하며, 만약 프록시를 거치지 않고 대상객체를 직접 호출하게 되면 트랜잭션 AOP가 적용되지 않고 그 말은 트랜잭션이 적용되지 않는다는 것을 의미한다.

### 프록시의 내부 호출 문제

> 위의 주의사항처럼 대상객체를 직접 호출하게 되면 트랜잭셔 AOP가 적용되지 않는다 하였다.
> 직접 호출하는 경우중 자주 발생하는 실수는 대상객체(클래스 내부)에서 다른 같은 클래스의 다른 메소드를 호출을 하게 될 경우 프록시를 거치지 않고 메소드를 실행하게 된다.
> 그러면 대상 객체에서 호출한 외부의 메소드는 트랜잭션의 처리가 없이 기능을 실행하는 문제가 발생하게 된다.
>
> > 즉 Target에서 this.메소드를 호출할 경우를 의미한다.

#### Code Example And Result

> 다음과 같이 내부호출을 일으켰을때 `@Transactional`이 정상적으로 이뤄지지 않는 로그를 볼 수 있다.

```log
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : Call External
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : [external]tx active >>> false
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : [external]tx readOnly >>> false
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : Call Internal
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : [internal]tx active >>> false
INFO 86706 c.j.s.a.InternalCallV1Test$CallService   : [internal]tx readOnly >>> false
```

```java
@Slf4j
@SpringBootTest
public class InternalCallV1Test {
    @Autowired
    private CallService callService;

    @Test
    void external() {
        callService.external();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {

        // 외부에서 호출하는 메소드
        public void external() {
            log.info("Call External");
            printTxInfo("external");
            internal();
        }

        @Transactional
        public void internal() {
            log.info("Call Internal");
            printTxInfo("internal");
        }

        private void printTxInfo(String callMethod) {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("[{}]tx active >>> {}", callMethod, txActive);

            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("[{}]tx readOnly >>> {}", callMethod, readOnly);
        }
    }
}

```

#### 프록시 방식의 AOP한계

> `@Transactional`을 사용하는 트랜잭션 AOP는 프록시를 사용 하고, 프록시를 사용하면 메서드 내부 호출(this)에 프록시를 적용할 수 없다.

#### 해결방법

- 가장 단순한건 내부호출을 피하기 위해서 메소드를 별도의 클래스로 분리하는 것

#### `@Transactional`의 적용 가능 접근제어자

- `public`만 적용이 되며, `protected`, `private`, `default(package-visible)`에는 적용되지 않는다.
- 어노테이션의 선언 자체는 가능하다.

### 트랜재션 AOP의 초기화 시점

> 스프링 어플리케이션이 초기화 하는 시점에는 `@Transactional`이 적용이 안될 가능성이 있다.

#### 요약 및 이유, 해결방안

> [!NOTE]  
> `@PostConstruct` : 스프링이 Bean을 생성하고 초기화하는 시점으로, 적용이 안될 수 있다.
>
> > 이유는 `@PostConstruct`같이 초기화 코드가 먼저 호출이 된 이후에 `트랜잭션 AOP`가 적용이 되기 때문이며,  
> > 그로 인해서 초기화 메소드를 호출하는 시점에는 트랜잭션을 획득 할 수 없는 것이다.

> [!NOTE]  
> 해결 방법으로는`@EventListener`를 활용하고, `ApplicationReadyEvent`시점으로 적용하면 된다.

#### 분석 로그

> 아래의 로그 내역을 보면 알 수 있겠지만(?) `@PostConstruct`가 먼저 실행이 되면서, Trnasaction이 활성화가 안되어 있다.
> 이후에 SpringApplication의 실행이 완료가 되고 그 다음이 `@EventListener`가 호출이 됨과 동시에 Transaction이 활성화가 되어 있는 것을 볼 수가 있다.
>
> 위와 같은 이유로 만약 `@Transactional`을 초기화 시점에 사용해야 한다면 `@EventListner`를 사용해야 한다.

```log
c.jong1.springtx.apply.InitTxTest$Hello  : Hello init @PostConstruct Transaction Active: false
com.jong1.springtx.apply.InitTxTest      : Started InitTxTest in 0.898 seconds (process running for 1.375)
o.s.t.i.TransactionInterceptor           : Getting transaction for [com.jong1.springtx.apply.InitTxTest$Hello.initV2]
c.jong1.springtx.apply.InitTxTest$Hello  : Hello init @EventListener(ApplicationReadyEvent.class) Transaction Active: true
o.s.t.i.TransactionInterceptor           : Completing transaction for [com.jong1.springtx.apply.InitTxTest$Hello.initV2]
```

#### Test Sample Code

##### 오류가 발생하는 Sample

```java
@SpringBootTest
public class InitTxTest {
    @Autowired
    private Hello hello;

    @Test
    void go() {
        // 초기화 코드(@PostConstruct)는 스프링이 초기화 시점(Bean등록)에 호출한다
    }

    @TestConfiguration
    static class InitTxTestConfig {
        @Bean
        public Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        @PostConstruct
        @Transactional
        public void initV1() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct Transaction Active: {}", isActive);
        }
    }

}
```

##### 오류를 해결한 Sample

```java
@SpringBootTest
public class InitTxTest {
    @Autowired
    private Hello hello;

    @Test
    void go() {
        // 초기화 코드(@PostConstruct)는 스프링이 초기화 시점(Bean등록)에 호출한다
    }

    @TestConfiguration
    static class InitTxTestConfig {
        @Bean
        public Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @EventListener(ApplicationReadyEvent.class) Transaction Active: {}", isActive);

        }
    }

}

```

---

## @Trnasactional의 옵션

### value, transactionManager

> [!NOTE]
> 코드로 직접 **트랜잭션을 사용할 때 분명히 트랜잭션 매니저를 주입받아 사용**을 했었다.
> `선언적 트랜잭션`을 활용할 때도 마찬가지로 **트랜잭션 프록시가 사용할 트랜잭션 매니저를 지정**해 주어야 한다.
> `value`의 값은 `transactionManager`를 사용하게 되는데, `value`또는 `transactionManager`에 **트랜잭션 매니저의 Spring Bean이름**을 적어주면 **해당 TxManager를 사용**하게 되며, **생략하게 되면 기본으로 등록된 TxManager를 사용**하게 된다.
>
> > 만약 사용하는 트랜잭션 매니저의 이름이 복수개라면 매니저 이름을 구분해서 활용하면 된다.

### rollbackFor

> [!NOTE]  
> 예외 발생시 Spring Transaction의 기본정책은 아래와 같다.
>
> > - UncheckedException인 `RuntimeException`, `Error`와 같은 하위 예외가 발생하는 경우에는 `Rollback`
> > - CheckedException인 `Exception`예외들은 커밋한다.
>
> 개발을 하다보면 CheckedException인 경우에도 롤백을 진행하고 싶은 경우가 발생할 수도 있는데, 해당 옵션을 사용하면 Spring Transaction의 기본정책 이외의 예외때도 롤백을 지정할 수가 있다.
>
> > Sample코드처럼 사용가능하며 Exception.class를 사용하는 경우에는 Exception하위 전체를 롤백한다.
> > 특별히 지정하고싶은 Exception이 존재하는 경우에는 해당 클래스만 꼭 집으면 된다.
> >
> > ```java
> > @Transactional(rollbackFor = Exception.class)
> > ```

#### rollbackForClass

> 해당 옵션은 `rollbackFor`의 확장판인데, `rollbackFor`은 예외Class를 직접 지정했다면, `rollbackForClass`의 경우에는 예외의 이름을 문자로 넣으면 된다.

### noRollbackFor

> `rollbackFor`와는 반대의 옵션으로 특정 예외가 발생 했을때 Rollback을 하면안되는지를 지정할 수 있다.

#### noRollbackForClassName

> 해당 옵션도 `rollbackForClass`와 는 반대의 옵션으로, 기본정책에서 특정 예외의 경우 rollback처리를 하면 안되는지를 지정하는 것을 예외 이름으로 지정하는 옵션이다.

### Propagation

> 트랜잭션 전파를 설정하는 옵션.

### isolation

> 트랜잭션의 격리 수준을 지정할 수 있는 옵션, 기본값은 DB에서 설정한 트랜잭션 격리수준을 사용하는 `DEFAULT`옵션을 사용하며, 옵션을 설정할 경우 변경이 가능하다.

|       Value        | Description                           |
| :----------------: | :------------------------------------ |
|     `DEFAULT`      | DB에 설정된 격리수준을 적용한다.      |
| `READ_UNCOMMITTED` | 커밋되지 않은 데이터까지 모두 읽는다. |
|  `READ_COMMITTED`  | 커밋된 데이터만 읽는다.               |
| `REPEATABLE_READ`  | 반복 가능한 읽기                      |
|   `SERIALIZABLE`   | 직렬화 가능                           |

### timeOut

> [!NOTE]
> 트랜잭션의 수행 시간에 대한 TimeOut을 초 단위로 지정하며, 기본값은 트랜잭션 시스템의 타임아웃을 사용한다.
> OS에 따라 동작할 수도, 그렇지 않은 경우도 존재한다.

### label

> 트랜잭션 어노테이션에 있는 값을 직접 읽어서 어떤 동작을 하고싶을 떄 사용하나, 사용하는 케이스가 거의 없다.
> 어플리케이션 코드의 복잡도가 증가하기 떄문...

### readOnly

> [!NOTE]
> 트랜잭션은 기본적으로 Default가 false이며, 해당 경우에는 `읽기`, `쓰기`가 모두 가능한 트랜잭션이 된다.
> `readOnly`의 값을 true로 부여하게 될 경우 `읽기`만 가능한 트랜잭션이 생성되며, 해당 트랜잭션은 `추가`, `수정`, `삭제`가 작동하지 않으나,(드라이버 또는 DB에 따라 정상적으로 동작하지 않을 수도 있다)
> `readOnly`를 사용하면 대부분의 경우 `읽기`에서 성능 최적화가 발생할 수 있다.

---

## Spring Transaction 전파 - Propagation

### 트랜잭션 전파란?

> [!NOTE]  
> 트랜잭션을 각각 사용하는게 아니라, 이미 열려있는 트랜잭션에서 추가로 트랜잭션을 획득해서 사용하면 어떻게 될경우를 생각해본적이있는가?  
> 기존 트랜잭션과 별도로 트랜잭션을 진행해야 할지? 또는 기존 트랜잭션을 물려받아 수행해야할지 생각해 본적이 있는가?
>
> 위와 같은 경우에 어떻게 동작할지를 결정하는 것을 트랜잭션 전파라고 한다.

### 트랜잭션 전파 개념

#### REQUIRED

> 스프링의 경우 외부 트랜잭션(처음시작한 트랜잭션)에서 내부 로직이 트랜잭션을 추가로 사용해야 하는 경우 새로운 트랜잭션을 만들지 않는다.
> 외부의 트랜잭션과 내부의 트랜잭션을 하나로 묶어서 해주며, 내부트랜잭션이 외부 트랜잭션에 참여하게 된다.

REQUIRED를 사용시의 원칙은 다음과 같다.

- 모든 논리 트랜잭션이 커밋되어야 물리 트랜잭션(모든 과정)이 커밋된다.
- 어느 한곳의 논리 트랜잭션이라도 롤백이 되면 물리 트랜잭은 롤백된다.

#### REQUIRED_NEW

> 외부 트랜잭션과 내부 트랜잭션을 분리해서 사용해야 할 경우에 사용하는 전파 속성이다.

### 트랜잭션 전파의 옵션관련 주의

### 전파의 종류 및 속성별 트랜잭션 처리

> [!NOTE]  
> `isolation` , `timeout` , `readOnly` 는 트랜잭션이 처음 시작될 때만 적용된다. 트랜잭션에 참여하는 경우에는 적용되지 않는다.
> 예를 들어서 `REQUIRED` 를 통한 트랜잭션 시작, `REQUIRES_NEW` 를 통한 트랜잭션 시작 시점에만 적용된다.

#### REQUIRED

> 기존 트랜잭션이 없으면 생성하고, 있으면 참여한다.

- 기존 트랜잭션 없음: 새로운 트랜잭션을 생성한다.
- 기존 트랜잭션 있음: 기존 트랜잭션에 참여한다.

#### REQUIRED_NEW

> 항상 새로운 트랜잭션을 생성한다.

- 기존 트랜잭션 없음: 새로운 트랜잭션을 생성한다.
- 기존 트랜잭션 있음: 새로운 트랜잭션을 생성한다.

#### SUPPORT

> 트랜잭션을 지원한다는 뜻이다. 기존 트랜잭션이 없으면, 없는대로 진행하고, 있으면 참여한다.

- 기존 트랜잭션 없음: 트랜잭션 없이 진행한다.
- 기존 트랜잭션 있음: 기존 트랜잭션에 참여한다.

#### NOT_SUPPORT

> 트랜잭션을 지원하지 않는다는 의미이다.

- 기존 트랜잭션 없음: 트랜잭션 없이 진행한다.
- 기존 트랜잭션 있음: 트랜잭션 없이 진행한다. (기존 트랜잭션은 보류한다)

#### MANDATORY

> 의무사항이다. 트랜잭션이 반드시 있어야 한다. 기존 트랜잭션이 없으면 예외가 발생한다.

- 기존 트랜잭션 없음: `IllegalTransactionStateException` 예외 발생
- 기존 트랜잭션 있음: 기존 트랜잭션에 참여한다.

#### NEVER

> 트랜잭션을 사용하지 않는다는 의미이다. 기존 트랜잭션이 있으면 예외가 발생한다.  
> 기존 트랜잭션도 허용하지 않는 강 한 부정의 의미로 이해하면 된다.

- 기존 트랜잭션 없음: 트랜잭션 없이 진행한다.
- 기존 트랜잭션 있음: `IllegalTransactionStateException` 예외 발생

#### NESTED

- 기존 트랜잭션 없음: 새로운 트랜잭션을 생성한다.
- 기존 트랜잭션 있음: 중첩 트랜잭션을 만든다.
  - 중첩 트랜잭션은 외부 트랜잭션의 영향을 받지만, 중첩 트랜잭션은 외부에 영향을 주지 않는다.
  - 중첩 트랜잭션이 롤백 되어도 외부 트랜잭션은 커밋할 수 있다.
  - 외부 트랜잭션이 롤백 되면 중첩 트랜잭션도 함께 롤백된다.

> [!WARNING]
>
> - JDBC savepoint 기능을 사용한다. DB 드라이버에서 해당 기능을 지원하는지 확인이 필요하다.
> - 중첩 트랜잭션은 JPA에서는 사용할 수 없다.

---
