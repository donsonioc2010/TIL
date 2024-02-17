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
