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

## Spring Transaction의 사용 방식

> `선언적 트랜잭션 방식`과 `프로그래밍 방식 트랜잭션 관리`가 있다.

### 선언적 트랜잭션 방식

- `@Transactional` 어노테이션 하나만으로 편리하게 적용하는 것
- 과거에는 XML에 설정하기도 하였슴.
- 명칭의 의미 그대로 로직에 트랜잭션을 적용하겠다고 어딘가에 선언하면 적용되는 방식

### 프로그래밍 방식 트랜잭션 관리

- 트랜잭션 매니저 또는 템플릿을 사용해서 트랜잭션 관련 코드를 직접 작성하는 방식, 위의 예제코드의 방식을 의미한다.
- 어플리케이션 코드가 트랜잭션 관련 코드와 강하게 결합하게 된다.

## @Transactional 작동원리

`@Transactional`이 클래스나 메소드에 하나라도 존재하는 경우 Transaction AOP는 해당 클래스를 프록시로 생성하여 기존의 클래스를 Spring Container에 등록하는게 아닌 프록시 기술로 제작된 클래스를 Spring Container에 등록한다.

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

#### 요약

`@PostConstruct` : 스프링이 Bean을 생성하고 초기화하는 시점으로, 적용이 안될 수 있다.
