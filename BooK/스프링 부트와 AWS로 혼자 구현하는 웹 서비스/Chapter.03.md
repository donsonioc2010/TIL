# **Chap.03 : 스프링부트에서 JPA로 DB를 다뤄보자**

## Index

- [**Chap.03 : 스프링부트에서 JPA로 DB를 다뤄보자**](#chap03--스프링부트에서-jpa로-db를-다뤄보자)
  - [Index](#index)
  - [ORM과 SQL Mapper의 차이](#orm과-sql-mapper의-차이)
  - [JPA 소개](#jpa-소개)
    - [JPA의 이점](#jpa의-이점)
    - [Spring Data JPA](#spring-data-jpa)
      - [구현체 교체의 용이성?](#구현체-교체의-용이성)
      - [저장소 교체의 용이성?](#저장소-교체의-용이성)
  - [프로젝트에 Spring Data Jpa 적용하기](#프로젝트에-spring-data-jpa-적용하기)
    - [적용이 필요한 라이브러리](#적용이-필요한-라이브러리)
    - [Domain 패키지에 대해서](#domain-패키지에-대해서)
    - [`Entity`를 사용시 자주 활용되는 Annotation](#entity를-사용시-자주-활용되는-annotation)
    - [Entity에서의 Setter와 Getter](#entity에서의-setter와-getter)
      - [Entity에서의 Setter에 대해서](#entity에서의-setter에-대해서)
      - [Entity에서의 Setter문제 관련 예제코드](#entity에서의-setter문제-관련-예제코드)
      - [`Setter`가 없이 DB에 값을 삽입하는 방법](#setter가-없이-db에-값을-삽입하는-방법)
    - [JPA Repository](#jpa-repository)
  - [Spring Data JPA 테스트 코드 작성하기](#spring-data-jpa-테스트-코드-작성하기)
    - [Junit의 `@After` Annotation란?](#junit의-after-annotation란)
    - [JPA Repository Object Method](#jpa-repository-object-method)
      - [`RepositoryObject.save`](#repositoryobjectsave)
      - [`RepositoryObject.findAll`](#repositoryobjectfindall)
    - [Layer의 역활](#layer의-역활)
      - [내가 잘못 알던 사항에 대해서...](#내가-잘못-알던-사항에-대해서)
      - [Layer의 종류와 역할](#layer의-종류와-역할)
      - [비즈니스 로직의 처리 위치의 방식](#비즈니스-로직의-처리-위치의-방식)
    - [Bean 주입의 방식](#bean-주입의-방식)
    - [Entity와 DTO](#entity와-dto)
    - [JPA를 사용시 테스트를 할 경우 Junit에서 필요한 Annotation](#jpa를-사용시-테스트를-할-경우-junit에서-필요한-annotation)
      - [SpringBootTest.WebEnvironment.RANDOM_PORT](#springboottestwebenvironmentrandom_port)
    - [JPA의 영속성 컨텍스트](#jpa의-영속성-컨텍스트)
    - [H2 웹콘솔 접근 설정 방법](#h2-웹콘솔-접근-설정-방법)
  - [JPA Auditing을 통한 생성, 수정시간 자동화](#jpa-auditing을-통한-생성-수정시간-자동화)
    - [LocalDate 사용](#localdate-사용)
    - [`Date`와 `Calendar` 클래스의 문제점](#date와-calendar-클래스의-문제점)
    - [LocalDate, LocalDateTime 관련 이슈](#localdate-localdatetime-관련-이슈)
    - [시간설정을 위해 사용된 예제와 설명](#시간설정을-위해-사용된-예제와-설명)
      - [예제 코드](#예제-코드)
      - [예제코드에서 사용된 Annotation에 대한 해설](#예제코드에서-사용된-annotation에-대한-해설)

## ORM과 SQL Mapper의 차이

> SQL Mapper와 ORM에 대해서 구분하자 구분하자...

> `MyBatis`, `iBatis`도 ORM중 하나라고 생각했으나 두개는 `SQL Mapper`로 구분된다.

## JPA 소개

### JPA의 이점

1.  `객체지향적`으로 프로그래밍을 할 수 있도록 함
2.  `SQL`에 `종속적인 개발`을 하지 않을 수 있게 된다.

### Spring Data JPA

> `JPA`는 `인터페이스`로 `자바 표준명세서`이다.  
> 인터페이스인 `JPA를 사용`하기 위해서는 `구현체가 필요`하며, 대표적으로 `Hibernate`, `Eclipse Link`등이 있다.  
> 하지만 Spring에서는 JPA를 활용시 위 `구현체를 직접 다루진 않고` 구현체들을 좀 더 쉽게 사용하고자 추상화한 `Spring Data JPA를 활용`한다.

> JPA 변천사 : `JPA` → `Hibernate` → `Spring Data JPA`

> `Hibernate`를 사용하는 것과 `Spring Data JPA`를 사용하는 것에는 `큰 차이는 없다`.  
> 그럼에도 Spring Data JPA를 `다시 제작한 이유는 크게 다음 두가지` 떄문
>
> 1. `구현체 교체`의 용이성
> 2. `저장소 교체`의 용이성

#### 구현체 교체의 용이성?

> `Hibernate`가 언젠가 `수명`을 다해서 `새로운 JPA구현체가 대세로 떠오를 때` Spring Data JPA를 사용중이라면 `쉽게 교체가 가능`하며, Spring Data JPA는 내부에서 구현체 매핑을 지원하기 때문에 가능하다

> 이 예제는 최근 `Redis`에서 증명이 되는데 `Redis클라이언트의 대세`가 `Jedis`에서 `Lettuce`로 넘어 갈 때 `Spring Data Redis`를 사용하는 경우 `쉽게 교체`를 하였음.

#### 저장소 교체의 용이성?

> `관계형 DB`외에 `다른 저장소로 쉽게 교체`하기 위함

> 초창기 서비스시 `관계형 DB`로 `모든 기능을 처리`했지만 트래픽이 많아지면서 관계DB로 유지가 불가능 해 `MongoDB 등등으로 저장소의 변경이 필요한 경우`에 `Spring Data JPA`에서 `Spring Data MongoDB`로 `의존성만 교체`하면 된다.

> `Spring Data`의 `하위 프로젝트들`은 기본적으로 `CRUD인터페이스가 같다`.  
>  그러다보니 `기본적인 기능`의 `변경`이 없어 `Hibernate를 직접 사용하는 것`보다는 `Spring Data프로젝트의 사용을 권장`한다.  
> Ex) Spring Data JPA, Spring Data Redis, Spring Data MongoDB등등

<br>

**실무에서의 JPA**  
적용을 못하는 이유

1. 높은 러닝 커브
2. 객체지향 프로그래밍과 데이터베이스 둘다 이해를 해야함.

적응을 함으로써 이점

1. CRUD쿼리를 직접 작성할 피료가 사라짐.
2. 객체 지향 프로그래밍을 쉽게 할 수 있슴.
   1. 부모-자식 관계 표현
   2. 1:N 관계 표현
   3. 상태와 행위를 한 곳 에서 관리

> 속도와 관련한 이슈는 있다. 하지만 많은 이슈 해결책들이 공유가 되고있는 상태이기 때문에 잘 활용하면 문제가 없다.

<br>

## 프로젝트에 Spring Data Jpa 적용하기

### 적용이 필요한 라이브러리

1. `spring-boot-starter-data-jpa`
   1. Spring Boot용 Spring Data Jpa 추상화 Library
   2. Spring Boot의 버전에 맞춰 자동으로 JPA 관련 라이브러리 버전을 관리한다.
2. `h2`
   1. `InMemory`형 `RDBMS`
   2. 별도의 `설치가 필요 없이 프로젝트 의존성만으로 관리` 할 수 있다.
   3. `메모리에서 실행`되기 때문에 `어플리케이션을 재시작 할 때마다 초기화 되는 점`을 활용 해서 `테스트 용`으로 자주 사용이 된다.

### Domain 패키지에 대해서

> `MyBatis`나 `iBatis`같은 SQL Mapper에서 사용하던 dao 패키지 같이 쿼리의 결과등을 담던 일들이 모두 도메인에서 이루어 진다.

> 저자의 경우 `도메인에 대한 세부적인 공부를 더 희망`하는 경우 `최범균(저)-DDD Start`를 추천하였다.

### `Entity`를 사용시 자주 활용되는 Annotation

> Entity의 PK의 경우에는 숫자를 사용하면 Long타입의 Auto_Increment를 추천한다.  
> Long을 활용할 경우 MySQL기준 BIGINT형으로 선언이된다.

1.  `@Entity`
    1.  `테이블`과 `링크`될 `클래스`
    2.  `기본값`으로 클래스의 `카멜케이스 이름`을 `언더스코어 네이밍(_)`으로 `테이블 명`을 매칭한다.
        1.  Ex) SalesManager.java → sales_manager table
2.  `@Id`
    1.  해당 테이블의 PK를 의미
3.  `@GeneratedValue`
    1.  PK의 생성 규칙을 의미
    2.  `스프링부트 2.0`에서는 `GenerationType.IDENTITY` 옵션을 `추가`해야만 `Auto Increment`가 되었었다.
4.  `@Column`
    1.  `테이블의 컬럼`을 의미하며 굳이 `선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 된다`.
    2.  사용하는 이유는 `기본값` 외에 `추가로 변경이 필요한 옵션`이 있으면 사용한다.
    3.  문자열의 경우 `VARCHAR(255)가 기본값`이나, `사이즈 증가`, `VARCHAR타입을 TEXT로 변경`이 필요한 경우 등등에 사용한다.

### Entity에서의 Setter와 Getter

#### Entity에서의 Setter에 대해서

> 자바빈 규약을 생각하면서 `Getter, Setter를 무작정 생성하는 경우`가 있다. 그렇게 제작을 하게 되면 해당 클래스의 `인스턴스 값들이 언제 어디서 변해야 하는지` 코드상으로 명확히 `구분 할 수가 없다.` 그 결과로 차후 기능 변경이 복잡해지는 문제가 발생한다..

> 위의 경우에 따라 `Entity클래스`에서는 `절대 Setter메소드를 생성하지 않는다`. 대신 `필드의 값 변경이 필요`한 경우 명확히 그 `목적과 의도를 나타낼 수 있는 메소드를 추가` 한다.

#### Entity에서의 Setter문제 관련 예제코드

```
// Wrong → 메소드 명의 사용 의미가 불명
public class Order {
   public void setStatus(boolean status){
      this.status = status
   }
}

// Right → 메소드 명에서 사용처의 명확한 이유 파악이 가능
public class Order {
   public void cancelOrder(boolean status){
      this.status = status
   }
}
```

#### `Setter`가 없이 DB에 값을 삽입하는 방법

> `Setter`가 없이 DB에 `Insert`는 `생성자를 통해 최종 값을 채운 이후` DB에 `삽입`한다.  
> 또한 `값의 변경이 필요한 경우`에는 해당 이벤트에 맞는 `public 메소드를 호출하여 변경`하는것을 전제로 한다.

> 생성자를 대신해서 `@Builder` 어노테이션을 통해서의 사용도 가능하다.

<br>

### JPA Repository

> `iBatis, MyBatis`같은 SQL Mapper를 사용 할 떄에는 `Dao라는 명칭`으로 불리던 `DB Layer`가 `JPA 에서는 Repository`로 칭하며 `Interface로 생성`한다.

> Interface의 생성 후 `JpaRepository<Entity Class, PK 타입>`으로 상속하면 `기본적인 메소드가 자동으로 생성`되며, `@Repository`를 따로 `선언할 필요가 없다`.
>
> > 주의할 사항으로는 `Entity클래스`와 `Entity Repository`는 `함께 위치`해야 한다.  
> > Entity 클래스는 기본 `Repository`없이는 제대로 `역할을 할 수가 없다`.
> >
> > 추후 프로젝트의 규모가 커져 도메인별로 프로젝트를 분리해야 한다면, Entity클래스와 기본 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.

<br>

## Spring Data JPA 테스트 코드 작성하기

### Junit의 `@After` Annotation란?

- Junit에서 `단위테스트`가 `끝날 때마다 수행`되는 `메소드`
- 보통은 `배포 전 전체 테스트를 수행 할 때 테스트 간 데이터 침범을 막기 위해 사용`한다.
- 여러 `테스트가 동시에 수행`되면 테스트용 DB인 `H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 실패` 할 수가 있다.
- 반대의 사용으로는 `@Before`가 존재하며 `단위테스트가 시작되기 전에 수행`이 된다.

### JPA Repository Object Method

#### `RepositoryObject.save`

1.  테이블에 `insert` / `update` 쿼리를 실행한다.
2.  id값이 있다면 `update`, 없다면 `insert`쿼리를 실행

#### `RepositoryObject.findAll`

1.  테이블에 존재하는 모든 데이터를 조회하는 메소드.

<br>

### Layer의 역활

#### 내가 잘못 알던 사항에 대해서...

> 내가 너무 크게 잘못 생각하던 부분으로 `비즈니스 로직`의 `처리`하는 것은 `Service`로 알고 있었으나 `Service`에서는 `트랜잭션`, `도메인 간의 순서 보장`역할만 한다..

#### Layer의 종류와 역할

1.  Web Layer
    1.  주로 사용되는 `@Controller`와 `JSP` / `Freemarker` 등의 View 템플릿 영역
    2.  외에도 `@Filter`, `인터셉터`, `@ControllerAdvice`등 외부 요청과 응답에 대한 전반적인 영역을 의미
2.  Service Layer
    1.  `@Service`에 사용되는 영역
    2.  보통 `@Controller`와 `Dao`의 중간역할에 사용된다.
    3.  `@Transactional`이 사용되어야 하는 영역이다.
3.  Repository Layer
    1.  Database와 같이 `데이터 저장소에 접근`하는 영역
    2.  기존에 `Dao영역`으로 이해하면 좋을 듯 하다.
4.  Dtos
    1.  `계층 간`의 `데아터 교환`을 위한 `객체`를 이야기하며 Dtos는 이들의 영역을 얘기한다.
    2.  Ex로 `Web Layer`에서 사용될 객체나 `Repository Layer`에서 결과로 넘겨준 객체등이 Dtos를 의미
5.  Domain Model
    1.  도메인이라 불리는 `개발 대상`을 모든 사람이 `동일한 관점`에서 `이해` 할 수 있고 `공유`할 수 있도록 `단순화` 한 것을 도메인 모델이라 칭함.
    2.  택시앱이 예제일 경우 `배차`, `탑승`, `요금` 등이 모두 도메인이 될 수가 있다
    3.  `@Entity`가 사용된 영역 역시 `도메인 모델`로 이해하면 좋다.
    4.  무조건 `데이터베이스의 테이블`(@Entity)과 관계가 있어야만 하는 것은 아니다.
    5.  `VO`처럼 `값 객체`들도 `도메인 모델` 영역에 해당 한다.

#### 비즈니스 로직의 처리 위치의 방식

> 해당 `Web`, `Service`, `Repository`, `Dto`, `Domain` 5개 Layer에서 `비즈니스 로직을 처리`하는 Layer는 `Domain`이며,  
>  `Service Layer`에서 처리하던 방식을 `트랜잭션 스크립트`라고 한다.

<br>

### Bean 주입의 방식

> Bean주입을 할때 방법은 3개로 `@Autowired`, `Setter`, `생성자` 3개가 있다.  
> 그 중 권장하는 방법은 `생성자를 통한 주입`이며, `@RequiredArgsConstructor` 어노테이션의 경우에는 `final로 선언된 모든 필드의 인자값`으로 하는 생성자를 롬복이 생성한다.

### Entity와 DTO

> 작성을 하면서 이해가 되지 않았던 부분이다. `Entity`와 `Dto`가 거의 유사한 코드로 진행되었는데 왜? 굳이? Dto로 따로 제작을 하였는지가 의문이었다.  
> 해당 내용에 대해서 처음 책을 학습할때는 이해를 못했다. 하지만 실제 읽고 실습을 하면서 `Entity`는 모델링에만 전념하고 해당 모델링을 바탕으로 `DTO`를 따로 작성하는 이유를 깊게 체감하였다.

> Entity클래스를 절대로 `Request` / `Response`클래스로 사용해서는 안된다.  
> Entity클래스는 `Database와 맞닿은 핵심 클래스`이다. 따라서 Entity클래스를 기준으로 테이블이 생성되고 스키마가 변경된다.  
> 화면 변경은 아주 사소한 기능 변경인데 이를 위해 테이블과 연결된 Entity클래스를 변경하는 것은 너무 큰 변경이기 떄문에 하면 안된다.

### JPA를 사용시 테스트를 할 경우 Junit에서 필요한 Annotation

> JPA의 기능 테스트(Repository) 메소드를 사용해야 하는 경우에 `@WebMvcTest` 어노테이션의 경우에는 `기능이 작동하지 않는다.`  
> `JPA기능까지 한번에 테스트가 필요한 경우`에는 `@SpringBootTest` 어노테이션 또는 `RestTemplate`를 활용해야 한다.

#### SpringBootTest.WebEnvironment.RANDOM_PORT

> 랜덤포트를 실행해서 객체를 가져오며. `@LocalServerPort` 어노테이션이랑 같이 활용

### JPA의 영속성 컨텍스트

> 예제 소스를 제작하면서 repository의 update메소드를 사용해 인자값을 전달만해줬슴.  
> 문제가 없는 이유는 `JPA`의 `영속성 컨텍스트` 떄문이며, 영속성 컨텍스트는 `엔티티를 영구 저장`하는 환경이다.
>
> JPA의 핵심 내용은 `엔티티`가 `영속성 컨텍스트에 포함이 되어 있냐 아니냐`로 갈린다.
>
> JPA의 `엔티티 매니저`가 `활성화된 상태`로 `트랜잭션 안`에서 `데이터베이스 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지`된다.

> ※ 참고로 Spring Data Jpa를 사용한다면 `엔티티 매니저`는 기본 옵션으로 활성화가 되어있다.  
> `트랜잭션 안`에서 `데이터의 값을 변경`하면, `트랜잭션이 끝나는 시점`에 `해당 테이블에 변경분을 반영 한다.` → 즉 `받아온 Entity 객체의 값`만 `변경`하면 별도의 `Update쿼리를 날릴 필요가 없다.`

> ※ 위의 개념을 `더티 체킹(dirty checking)` 이라고 한다.

### H2 웹콘솔 접근 설정 방법

> `application.yml` 또는 `properties`에서 `spring.h2.console.enabled`항목 true로 변경  
> 직접 접속 할 수 있도록 하는 설정 `http://localhost:8080/h2-console/` 로 접근이 가능하며, URL 기본값으로 `jdbc:h2:mem:testdb`를 통해서 접근이 가능하다.

<br>

## JPA Auditing을 통한 생성, 수정시간 자동화

> 보통 `Entity`에서 `데이터의 수정`, `생성시간`을 포함하는 경우가 많으며 해당 정보는 추후 `유지보수`를 함에 있어서 `중요한 정보`이다 보니 DB에 `삽입`, `갱신`전에 수정하는 코드가 들어간다.  
> 하지만 매번 `단순하고 반복적인 코드`이기 때문에 지저분해지기 마련이지만 이 `문제를 해결 할 때 JPA Auditing을 사용`한다.

### LocalDate 사용

> `Java8`부터는 `LocalDate`와 `LocalDateTime`이 등장하였다. 그 전에는 기본 날짜 타입인 Date를 사용했으나 해당 객체는 문제점이 있었고 해당 문제들을 보완한 것이며, Java8 이후 버전일 경우에는 무조건 사용한다 생각하면 좋다.

### `Date`와 `Calendar` 클래스의 문제점

먼저 문제들과 관련해서는 `https://d2.naver.com/helloworld/645609` 링크를 같이 보면 좋을 듯 하다.

1. `불변 객체`가 아니다.
   - `멀티 스레드` 환경에 문제가 발생할 수 있다.
2. Calendar는 `월(Month) 설계`가 잘못되었다.
   - 10월을 나타내는 `Calendar.OCTOBER는 숫자 '9'`

### LocalDate, LocalDateTime 관련 이슈

> DB에서 매핑이 되지 않는 문제가 실제 존재하였으나 Hibernate 5.2.10버전 이후 문제가 해결됨.

### 시간설정을 위해 사용된 예제와 설명

#### 예제 코드

```
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;

}
```

#### 예제코드에서 사용된 Annotation에 대한 해설

1.  `@MappedSuperclass`
    - `JPA Entity 클래스`들이 `BaseTimeEntity를 상속할 경우` 필드들도 컬럼으로 인식합니다.
    - `상속`을 해줘야 `Entity에 컬럼이 생성`됩니다.
2.  `@EntityListeners(AudtingEntityListener.class)`
    - `BaseTimeEntity 클래스에 Auditing 기능을 포함`시킵니다.
3.  `@CreatedDate`
    - Entity가 생성되어 `저장`될 때 `시간이 자동 저장`됩니다.
4.  `@LastModifiedDate`
    - 조회한 Entity의 값이 `변경`되는 경우 `시간이 자동 저장`됩니다.
5.  `@EnableJapaAuditing`
    - Spring Application을 Run하는 Main클래스 위에 기록을 해야합니다.
    - 허용을 해놓지 않는 경우 DB에 추가된 LocalDateTime컬럼은 데이터 추가, 수정시마다 기록이 되지 않고 Null로 기록됩니다.
