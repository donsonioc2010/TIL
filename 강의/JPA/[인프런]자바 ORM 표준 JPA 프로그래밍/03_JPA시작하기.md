# JPA 시작하기

## Index

- [JPA 시작하기](#jpa-시작하기)
  - [Index](#index)
  - [Hello JPA 프로젝트 생성](#hello-jpa-프로젝트-생성)
    - [프로젝트 생성](#프로젝트-생성)
      - [Maven 설정](#maven-설정)
      - [persistence.xml 설정](#persistencexml-설정)
      - [Database의 방언](#database의-방언)
  - [Hello JPA 어플리케이션 개발](#hello-jpa-어플리케이션-개발)
    - [JPA 구동 방식](#jpa-구동-방식)
    - [Main 예제 코드](#main-예제-코드)
      - [실제 작동이 되지 않는 이유](#실제-작동이-되지-않는-이유)
      - [트랜잭션 추가 코드](#트랜잭션-추가-코드)
      - [JPA의 실행 결과 쿼리](#jpa의-실행-결과-쿼리)
    - [EntityManager Factory의 주의점](#entitymanager-factory의-주의점)
    - [Entity의 선언방법 예제](#entity의-선언방법-예제)
    - [JPQL](#jpql)

## Hello JPA 프로젝트 생성

### 프로젝트 생성

> 프로젝트의 경우 순수 JAVA를 활용해서 JPA로 실행해본다.

#### Maven 설정

> 두개의 Dependency만 추가하고 Spring 설정은 사용하지 않는다.

```
    <!-- H2 -->
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <version>2.1.214</version>
    </dependency>

    <!-- Hibernate EntityManager -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-entitymanager</artifactId>
      <version>5.6.14.Final</version>
    </dependency>
```

#### persistence.xml 설정

1. JPA 설정파일
2. /META-INF/persistence.xml 위치
3. persistence-unit name으로 이름 지정
4. javavx.persistence로 시작 : JPA 표준 속성
5. hibernate로 시작 : 하이버네이트 전용 속성

#### Database의 방언

- JPA는 특정 DB에 종속적이지 않도록 설계되어있다.
  - 즉 MySQL을 쓰다가 Oracle을 써도 문제가 없어야 한다.
- 각각의 DB가 제공하는 SQL문법과 함수가 조금씩 다른다
  - DB데이터 타입
  - 페이징 문법
  - 문자열 자르는 함수 등등

> DB들의 방언을 해결하기 위해 JPA는 Dialect를 사용해 방언을 해결한다.

> 사용이 가능한 문법의 종류는 **hibernate.dialect**패키지에 존재하는 클래스들이 해당 종류들이며 **hibernate-core**에 해당 패키지가 존재한다.

## Hello JPA 어플리케이션 개발

### JPA 구동 방식

1.  **Persistence Class**에서 *META-INF/persistence.xml*의 설정 정보를 읽는다.
2.  설정을 읽은 이후 **Persistence Class**는 *EntityManagerFactory Class*를 생성한다.
3.  EntityManagerFactory Class에서는 EntityManager를 찍어서 제작한다

### Main 예제 코드

> 해당 코드는 실제 저장이 되지 않는다

```
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

  public static void main(String[] args) {
    // emf를 제작한 순간 DB연결이 됨, 파라미터 값은 persistence.xml의 UnitName값을 제공해야함.
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    // EntityManagerFactory의 연동이 성공하면 EntityManager Factory의 EntityManager 객체를 생성해야 한다.
    EntityManager em = emf.createEntityManager();

    // S : 실제 코드 구현부
    Member member = new Member();
    member.setId(1L);
    member.setName("HelloA");
    em.persist(member);
    // E : 실제 코드 구현부

    em.close();
    emf.close();
  }

}
```

#### 실제 작동이 되지 않는 이유

> JPA의 경우에는 Transaction의 단위가 매우 중요하다. 모든 데이터와 관련된 작업은 Transaction이 들어가야하는데 없기 위의 코드는 Transaction이 존재하지 않아 오류가 발생함

#### 트랜잭션 추가 코드

> 다음과 같이 트랜잭션을 EntityManager에서 획득후 시작과 종료가 필요

```
    // GetTransaction And Start
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    // S : 실제 코드 구현부
    Member member = new Member();
    member.setId(1L);
    member.setName("HelloA");
    em.persist(member);
    // E : 실제 코드 구현부

    tx.commit();
```

#### JPA의 실행 결과 쿼리

```
Hibernate:
    /* insert hellojpa.Member
        */ insert
        into
            Member
            (name, id)
        values
            (?, ?)
```

> 기본 설정의 경우 False로 되어있어서 쿼리가 원래는 나오지 않는다. 쿼리가 나올 수 있는 이유는 설정의 변경이며, 해당 MD에 기록된 예제는 JAVA 기본 프로젝트여서 persistence.xml의 설정을 따름

1. `<property name="hibernate.show_sql" value="true"/>`
   1. 쿼리가 출력되는 역할을 함
2. `<property name="hibernate.format_sql" value="true"/>`
   1. 쿼리를 보기 편하게 포멧팅을 함
3. `<property name="hibernate.use_sql_comments" value="true"/>`
   1. 쿼리가 무슨이유로 실행되었는지를 출력함 insert hellojpa.Member 해당 코멘트가 이 설정임.

### EntityManager Factory의 주의점

> **EntityManager Factory**의 경우 **Application 로딩 시점에 딱 한개**만 만들어야 한다.
> 실제 DB에 저장하는 Transaction 단위를 사용할 때 (DB Connection을 얻고 쿼리를 날리는 작업) 마다는 EntityManager를 꼭 만들어 줘야 한다.

- **EntityManager Factory**는 하나로 *Application이 전체에서 공유*한다.
- **EntityManager**는 _쓰레드간에 공유를 하면 안되고 사용후 버려야 한다_

### Entity의 선언방법 예제

```
package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

  @Id
  private Long id;
  private String name;

  /*Getter, Setter*/
}
```

1. 테이블 명이 다른경우 Entity어노테이션 위나 아래에 `@Table(name="테이블명")`으로 선언한다.
2. 컬럼명이 다른 경우 필드위에 `@Column(name="실제 DB 필드명")`어노테이션을 사용한다.

### JPQL

> 특정 검색조건 [범위검색, 조인 등]을 하고 싶은 경우에는 JPQL을 활용해야 한다.

> JPA에서는 엔티티를 활용해서 객체중심의 개발을 진행하다보니, JPQL쿼리제작을 통한 검색을 할 때도 테이블이 아닌 엔티티를 대상으로 한 쿼리문 제작이 가능하다.

> 모든 DB데이터를 객체로 변환해서 검색하는 것은 불가능 하다.
