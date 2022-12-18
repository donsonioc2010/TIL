> 책을 읽어가며 필요한 부분들, 기억해야 할 부분들에 대한 기록.  
> 잘못 알고있던, 혹은 혼동되서 알고있던 기록들도 전부 기록.

<br>

## **Chap.02 : 스프링 부트에서 테스트 코드를 작성하자**

### **P.52**

**태스트 코드에 대한 소개**

> 1. TDD와 단위 테스트(Unit Test)는 다르다.
> 2. TDD는 테스트가 주도하는 개발로 `테스트 코드를 먼저 작성`하는 것에서부터 시작한다.

---

**TDD의 사이클링 순서**

1. Red - 항상 실패하는 테스트를 먼저 작성
2. Green - 테스트가 통과하는 프로덕션 코드를 작성
3. Refactor - 테스트가 통과하면 프로덕션 코드의 리팩토링

---

**단위 테스트**

1. 기능 단위의 테스트 코드를 작성하는 것
2. TDD와 달리 테스트코드를 먼저 작성하지도 않으며 리팩토링도 포함되지 않고 순수히 테스트 코드만 만드는 것을 의미

<br>

### **P.53**

**테스트 코드를 작성하는 이유**

1. 단위 테스트는 개발단계 초기에 문제를 발견하도록 도와준다.
2. 단위테스트는 개발자가 나중에 코드를 리팩토링 하거나 라이브러리에 업그레이드등에서 기존 기능이 올바르게 작동하는지 확인이 가능하다.
3. 기능에 대한 불확실성을 감소시킬 수 있다.
4. 시스템에 대한 실제 문서를 제공한다. 즉 테스트 자체가 문서로 사용이 가능하다.

<br>

### **P.54**

**저자가 느낀 테스트코드의 필요성**

1. 소스코드의 수정이 있는 경우 매번 수정을 할 때마다 사람이 직접 빌드를 하고 검수를 해야한다.
   1. 불필요한 많은 시간이 소요된다.
2. 개발자가 만든 기능을 안전하게 보호한다
   1. 개발자가 기능을 추가하거나 수정하게 될 경우 해당 기능이 아닌 다른 기능에서의 문제도 검증을 해줄 수 있다.
   2. 사람이 직접 수정, 추가점이 발생할 때마다 모든 기능을 테스트 할 수 없기 떄문
      1. 기존 기능이 잘 작동되는것을 보장 하는것

<br>

### **P.55**

**프로젝트 패키지명 구성**

> 도메인 주소가 admin.jojoldu.com이라면 패키지는 역순인 com.jojoldu.admin 으로 구성하면 된다.

<br>

### **P.57**

**@SpringBootApplication Annotation**

> 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정해준다.  
> 해당 어노테이션이 `존재하는 위치부터 설정을 읽어가기 때문`에 항상 프로젝트 최상단에 존재하여야 한다.

> `Main`의 `SpringApplication.run`메소드로 내장WAS(톰캣)을 실행한다.

<br>

### **P.58**

**왜 SpringBoot에선 내장WAS의 사용을 권장하는지?**

> 언제 어디서나 `같은 환경`에서 `스프링 부트`를 배포 할 수 있기 떄문이다.  
> 외장 WAS를 사용을 해도 되지만, 사용을 하게 되면 WAS의 종류와 버전, `설정을 모두 일치시켜야 하는 추가적인 작업이 필요`하기 때문, 즉 확장이 불편해지기 떄문

> 내장 WAS를 사용 할 경우 성능상 이슈의 경우에는 크게 고려하지 않아도 된다고 함.

<br>

### **P.72**

**`@RequiredArgsConstructor`란?**

1. 해당 어노테이션이 선언된 모든 final필드가 포함된 생성자를 생성한다.
2. final이 없는 필드는 생성자에 포함되지 않는다.

<br>

## **Chap.03 : 스프링부트에서 JPA로 DB를 다뤄보자**

### **P.79**

> SQL Mapper와 ORM은 구분하자...  
> `MyBatis, iBatis`도 ORM중 하나라고 생각했으나 두개는 `SQL Mapper`로 구분된다.

<br>

### **P.82~84**

**JPA의 이점**

1.  객체지향적으로 프로그래밍을 할 수 있도록 함
2.  SQL에 종속적인 개발을 하지 않을 수 있게 된다.

---

**Spring Data JPA**

> `JPA`는 `인터페이스`로 `자바 표준명세서`이다.  
> 인터페이스인 JPA를 사용하기 위해서는 `구현체가 필요`하며, 대표적으로 `Hibernate, Eclipse Link`등이 있다.  
> 하지만 Spring에서는 JPA를 활용시 `위 구현체를 직접 다루진 않고` 구현체들을 좀 더 쉽게 사용하고자 추상화한 `Spring Data JPA를 활용`한다.

> JPA 변천사 : JPA → Hibernate → Spring Data JPA

> `Hibernate`를 사용하는 것과 `Spring Data JPA`를 사용하는 것에는 `큰 차이는 없다`.  
> 그럼에도 Spring Data JPA를 `다시 제작한 이유는 크게 다음 두가지` 떄문
>
> 1. `구현체 교체`의 용이성
> 2. `저장소 교체`의 용이성

<br>

**구현체 교체의 용이성?**

> `Hibernate`가 언젠가 `수명`을 다해서 `새로운 JPA구현체가 대세로 떠오를 때` Spring Data JPA를 사용중이라면 `쉽게 교체가 가능`하며, Spring Data JPA는 내부에서 구현체 매핑을 지원하기 때문에 가능하다

> 이 예제는 최근 `Redis`에서 증명이 되는데 `Redis클라이언트의 대세`가 `Jedis`에서 `Lettuce`로 넘어 갈 때 `Spring Data Redis`를 사용하는 경우 `쉽게 교체`를 하였음.

<br>

**저장소 교체의 용이성?**

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

### **P.87**

**라이브러리**

1. `spring-boot-starter-data-jpa`
   1. Spring Boot용 Spring Data Jpa 추상화 Library
   2. Spring Boot의 버전에 맞춰 자동으로 JPA 관련 라이브러리 버전을 관리한다.
2. `h2`
   1. InMemory형 RDBMS
   2. 별도의 설치가 필요 없이 프로젝트 의존성만으로 관리 할 수 있다.
   3. 메모리에서 실행되기 때문에 어플리케이션을 재시작 할 때마다 초기화 되는 점을 활용 해서 테스트 용으로 자주 사용이 된다.

---

**domain 패키지**

> MyBatis나 iBatis같은 SQL Mapper에서 사용하던 dao 패키지 같이 쿼리의 결과등을 담던 일들이 모두 도메인에서 이루어 진다.

> 저자의 경우 `도메인에 대한 세부적인 공부를 더 희망`하는 경우 `최범균(저)-DDD Start`를 추천하였다.

<br>

### **P.91**

**자주 사용 되는 Entity Annotation**

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

> Entity의 PK의 경우에는 숫자를 사용하면 Long타입의 Auto_Increment를 추천한다.  
> Long을 활용할 경우 MySQL기준 BIGINT형으로 선언이된다.

<br>

### **P.92**

**Setter에 대한 고찰**

> 자바빈 규약을 생각하면서 `Getter, Setter를 무작정 생성하는 경우`가 있다. 그렇게 제작을 하게 되면 해당 클래스의 `인스턴스 값들이 언제 어디서 변해야 하는지` 코드상으로 명확히 `구분 할 수가 없다.` 그 결과로 차후 기능 변경이 복잡해지는 문제가 발생한다..

> 위의 경우에 따라 `Entity클래스`에서는 `절대 Setter메소드를 생성하지 않는다`. 대신 `필드의 값 변경이 필요`한 경우 명확히 그 `목적과 의도를 나타낼 수 있는 메소드를 추가` 한다.

```
// Example Code
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

<br>

### **P.93**

**`Setter`가 없이 `Insert`는 어떻게?**

> `Setter`가 없이 DB에 `Insert`는 `생성자를 통해 최종 값을 채운 이후` DB에 `삽입`한다.  
> 또한 `값의 변경이 필요한 경우`에는 해당 이벤트에 맞는 `public 메소드를 호출하여 변경`하는것을 전제로 한다.

> 생성자를 대신해서 `@Builder` 어노테이션을 통해서의 사용도 가능하다.

<br>

### **P.95**

**Repository**

> `iBatis, MyBatis`같은 SQL Mapper를 사용 할 떄에는 `Dao라는 명칭`으로 불리던 `DB Layer`가 `JPA 에서는 Repository`로 칭하며 `Interface로 생성`한다.

> Interface의 생성 후 `JpaRepository<Entity Class, PK 타입>`으로 상속하면 `기본적인 메소드가 자동으로 생성`되며, `@Repository`를 따로 `선언할 필요가 없다`.
>
> > 주의할 사항으로는 `Entity클래스`와 `Entity Repository`는 `함께 위치`해야 한다.  
> > Entity 클래스는 기본 `Repository`없이는 제대로 `역할을 할 수가 없다`.
> >
> > 추후 프로젝트의 규모가 커져 도메인별로 프로젝트를 분리해야 한다면, Entity클래스와 기본 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.

<br>

### **P.97**

**Junit `@After` Annotation**

- Junit에서 단위테스트가 끝날 때마다 수행되는 메소드
- 보통은 배포 전 전체 테스트를 수행 할 때 테스트 간 데이터 침범을 막기 위해 사용한다.
- 여러 테스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 실패 할 수가 있다.
- 반대의 사용으로는 `@Before`가 존재하며 단위테스트가 시작되기 전에 수행이 된다.

**JPA Repository Object Method**

1. `RepositoryObject.save`
   1. 테이블에 `insert` / `update` 쿼리를 실행한다.
   2. id값이 있다면 `update`, 없다면 `insert`쿼리를 실행
2. `RepositoryObject.findAll`
   1. 테이블에 존재하는 모든 데이터를 조회하는 메소드.

<br>

### **P.101 ~ 102**

**Layer의 역활**  
내가 너무 크게 잘못 생각하던 부분으로 `비즈니스 로직`의 `처리`하는 것은 `Service`로 알고 있었으나 `Service`에서는 `트랜잭션`, `도메인 간의 순서 보장`역할만 한다..

> 먼저 각 계층이 해당하는 순서는 아래의 리스트와 같다.
>
> 1. Web Layer
>    > 1. 주로 사용되는 `@Controller`와 `JSP` / `Freemarker` 등의 View 템플릿 영역
>    > 2. 외에도 `@Filter`, `인터셉터`, `@ControllerAdvice`등 외부 요청과 응답에 대한 전반적인 영역을 의미
> 2. Service Layer
>    > 1. `@Service`에 사용되는 영역
>    > 2. 보통 `@Controller`와 `Dao`의 중간역할에 사용된다.
>    > 3. `@Transactional`이 사용되어야 하는 영역이다.
> 3. Repository Layer
>    > 1. Database와 같이 `데이터 저장소에 접근`하는 영역
>    > 2. 기존에 `Dao영역`으로 이해하면 좋을 듯 하다.
> 4. Dtos
>    > 1. `계층 간`의 `데아터 교환`을 위한 `객체`를 이야기하며 Dtos는 이들의 영역을 얘기한다.
>    > 2. Ex로 `Web Layer`에서 사용될 객체나 `Repository Layer`에서 결과로 넘겨준 객체등이 Dtos를 의미
> 5. Domain Model
>    > 1. 도메인이라 불리는 `개발 대상`을 모든 사람이 `동일한 관점`에서 `이해` 할 수 있고 `공유`할 수 있도록 `단순화` 한 것을 도메인 모델이라 칭함.
>    > 2. 택시앱이 예제일 경우 `배차`, `탑승`, `요금` 등이 모두 도메인이 될 수가 있다
>    > 3. `@Entity`가 사용된 영역 역시 `도메인 모델`로 이해하면 좋다.
>    > 4. 무조건 `데이터베이스의 테이블`(@Entity)과 관계가 있어야만 하는 것은 아니다.
>    > 5. `VO`처럼 `값 객체`들도 `도메인 모델` 영역에 해당 한다.
>
> 해당 `Web`, `Service`, `Repository`, `Dto`, `Domain` 5개 Layer에서 `비즈니스 로직을 처리`하는 Layer는 `Domain`이며,  
>  `Service Layer`에서 처리하던 방식을 `트랜잭션 스크립트`라고 한다.

<br>

### **P.106**

**Bean 주입과 관련하여**
Bean주입을 할때 방법은 3개로 `@Autowired`, `Setter`, `생성자` 3개가 있다.  
그 중 권장하는 방법은 `생성자를 통한 주입`이며, `@RequiredArgsConstructor` 어노테이션의 경우에는 `final로 선언된 모든 필드의 인자값`으로 하는 생성자를 롬복이 생성한다.

<br>

### **P.107**

**Entity와 Dto**
작성을 하면서 이해가 되지 않았던 부분이다. Entity와 Dto가 거의 유사한 코드로 진행되었는데 왜? 굳이? Dto로 따로 제작을 하였는지가 의문이었다.

> Entity클래스를 절대로 Request / Response클래스로 사용해서는 안된다.  
> Entity클래스는 Database와 맞닿은 핵심 클래스이다. 따라서 Entity클래스를 기준으로 테이블이 생성되고 스키마가 변경된다.  
> 화면 변경은 아주 사소한 기능 변경인데 이를 위해 테이블과 연결된 Entity클래스를 변경하는 것은 너무 큰 변경이기 떄문에 하면 안된다.

<br>

### **P.110**

**JPA를 사용시 테스트 Annotation**

> JPA의 기능 테스트(Repository) 메소드를 사용해야 하는 경우에 `@WebMvcTest` 어노테이션의 경우에는 `기능이 작동하지 않는다.`  
> `JPA기능까지 한번에 테스트가 필요한 경우`에는 `@SpringBootTest` 어노테이션 또는 `RestTemplate`를 활용해야 한다.

---

**SpringBootTest.WebEnvironment.RANDOM_PORT**

> 랜덤포트를 실행해서 객체를 가져온다. `@LocalServerPort` 어노테이션이랑 같이 활용

<br>

### **P.113~114**

**JPA의 영속성 컨텍스트**
예제 소스를 제작하면서 repository의 update메소드를 사용해 인자값을 전달만해줬슴.  
문제가 없는 이유는 `JPA`의 `영속성 컨텍스트` 떄문이며, 영속성 컨텍스트는 `엔티티를 영구 저장`하는 환경이다.

JPA의 핵심 내용은 `엔티티`가 `영속성 컨텍스트에 포함이 되어 있냐 아니냐`로 갈린다.

JPA의 `엔티티 매니저`가 `활성화된 상태`로 `트랜잭션 안`에서 `데이터베이스 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지`된다.

※ 참고로 Spring Data Jpa를 사용한다면 `엔티티 매니저`는 기본 옵션으로 활성화가 되어있다.

`트랜잭션 안`에서 `데이터의 값을 변경`하면, `트랜잭션이 끝나는 시점`에 `해당 테이블에 변경분을 반영 한다.` → 즉 `받아온 Entity 객체의 값`만 `변경`하면 별도의 `Update쿼리를 날릴 필요가 없다.`

※ 위의 개념을 `더티 체킹(dirty checking)` 이라고 한다.

<br>

### **P.116**

**H2 웹콘솔 접근 설정 방법**

> `application.yml` 또는 `properties`에서 `spring.h2.console.enabled`항목 true로 변경  
> 직접 접속 할 수 있도록 하는 설정 `http://localhost:8080/h2-console/` 로 접근이 가능하며, URL 기본값으로 `jdbc:h2:mem:testdb`를 통해서 접근이 가능하다.

<br>

### **P.119**

#### **JPA Auditing을 통한 생성, 수정시간 자동화**

> 보통 `Entity`에서 `데이터의 수정`, `생성시간`을 포함하는 경우가 많으며 해당 정보는 추후 `유지보수`를 함에 있어서 `중요한 정보`이다 보니 DB에 `삽입`, `갱신`전에 수정하는 코드가 들어간다.  
> 하지만 매번 `단순하고 반복적인 코드`이기 때문에 지저분해지기 마련이지만 이 `문제를 해결 할 때 JPA Auditing을 사용`한다.
>
> > **LocalDate 사용**  
> > Java8부터는 LocalDate와 LocalDateTime이 등장하였다. 그 전에는 기본 날짜 타입인 Date를 사용했으나 해당 객체는 문제점이 있었고 해당 문제들을 보완한 것이며, Java8 이후 버전일 경우에는 무조건 사용한다 생각하면 좋다.
> >
> > > **`Date`와 `Calendar` 클래스의 문제점**  
> > > 먼저 문제들과 관련해서는 `https://d2.naver.com/helloworld/645609` 링크를 같이 보면 좋을 듯 하다.
> > >
> > > 1. 불변 객체가 아니다.  
> > >    →멀티 스레드 환경에 문제가 발생할 수 있다.
> > > 2. Calendar는 월(Month) 설계가 잘못되었다.  
> > >    →10월을 나타내는 Calendar.OCTOBER는 숫자 '9'
>
> > **LocalDate, LocalDateTime 관련 이슈**  
> > DB에서 매핑이 되지 않는 문제가 실제 존재하였으나 Hibernate 5.2.10버전 이후 문제가 해결됨.

<br>

### **P.121~122**

**사용된 Annotation들에 대해서**

> 1. `@MappedSuperclass`
>
>    > - JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 컬럼으로 인식합니다.
>    > - 상속을 해줘야 Entity에 컬럼이 생성됩니다.
>
> 2. `@EntityListeners(AudtingEntityListener.class)`
>    > - BaseTimeEntity 클래스에 Auditing 기능을 포함시킵니다.
> 3. `@CreatedDate`
>    > - Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
> 4. `@LastModifiedDate`
>    > - 조회한 Entity의 값이 변경되는 경우 시간이 자동 저장됩니다.
> 5. `@EnableJapaAuditing`
>    > - Spring Application을 Run하는 Main클래스 위에 기록을 해야합니다.
>    > - 허용을 해놓지 않는 경우 DB에 추가된 LocalDateTime컬럼은 데이터 추가, 수정시마다 기록이 되지 않고 Null로 기록됩니다.

<br>

## **Chapter. 04 : 머스테치를 통한 화면 구성하기**

### **P.125~127**

**템플릿 엔진의 종류**

> `템플릿 엔진`이란? `지정된 템플릿 양식과 데이터`가 합쳐져 `HTML문서`를 `출력`하는 소프트웨어를 의미
>
> > **JSP, Freemarker등등**
> >
> > - 서버 템플릿 엔진
> > - 서버(WAS)에서 Java코드로 명령어를 (문자열을) 실행한 이후 HTML로 변환하여 브라우저로 전달한다.
>
> > **React, Vue 등등**
> >
> > - 클라이언트 템플릿 엔진
> > - SPA로도 칭한다.(Single Page Application)
> > - 서버 템플릿 엔진과는 다르게 브라우저 위에서 작동된다. 즉 브라우저에서 화면을 생성하기 떄문에 서버에서 이미 코드가 벗어난 경우다.
> > - Json또는 Xml형식의 데이터만 전달하고 클라이언트에서 해당 데이터를 조립한다.

<br>

### **P.128~9**

**머스테치란?**

> 다양한 언어를 지원하는 템플릿 엔진으로, Java에서 사용할 때는 서버 템플릿엔진으로, JS에서 사용할 떄는 클라이언트 템플릿 엔진으로 사용이 가능하다.

> 작가와 동일한 생각인 내용이 있어 기록한다. `템플릿 엔진`은 `화면 역할에만 충실`해야 한다. 너무 많은 기능을 제공하고 `API와 템플릿 엔진`, `자바스크립트`가 `서로 로직을 나눠 갖게 되면 유지보수를 하기가 굉장히 어려워진다.`
>
> > 이 전 회사에서 겪었던 점이다.. 회사에서 JSP와 Servlet을 활용하여 Application을 구축했는데 JSP에 너무나도 심각하게 많은 로직들이 있었다.  
> > 유지보수를 하려고 보면 Debug도 정상적으로 이뤄지지도 않아 추적이 힘들고 유지보수를 하면서 JSP영역에 있다 보니 해당 기능들을 생각을 하지 못하고 개발하거나 변경하는 경우가 너무나도 빈번했었다.  
> > 그러다보니 나의 경우에도 Back과 Front는 무조건 확실하게 분리하고 Front는 화면만 해야 한다는 신념을 가지게 되었다.

<br>

### **P.134**

**Page에 대한 Test**
HTML마크업 페이지에 대해서 테스트를 할 때 정상적으로 로딩이 되었는 지를 확인하기 위한 케이스를 만들 경우, 해당 페이지를 호출해서 일치하는 문자열이 존재하는지 검증하는 방식으로 테스트하면 수월하다.

<br>

### **P.137**

**CSS와 JS의 위치**

> `CSS`의 경우에는 `header`에 , `jquery`, `bootstrap.js`의 경우에는 `footer`에 배치를 하였으며 이유는 `페이지의 로딩속도를 높이기 위해서` 이다.
>
> 왜 이러냐면 보통 head가 다 불러지지 않으면 사용자 쪽에선 `백지 화면`만 노출이 된다.  
> 특히 `JS의 용량`이 `크면 클수록` body 부분의 `실행이 늦어지기 때문`에 js는 body의 하단에 두어 화면이 다 그려진 이후에 호출하는 것이 좋다.
>
> 반면에 `CSS`의 경우에는 `위`에 둬야 하는 이유는 CSS가 적용되지 않을 경우 `깨진 화면`을 사용자가 보게 되기 때문에 먼저 호출 한다.

※ bootstrap.js의 경우에는 4까지는 jQuery가 꼭 있어야만 한다. 하지만 5가되면서 jQuery를 걷어냈다.

<br>

### **P.138**

**{{>filePath}}**

> `{{>layout/footer, header}}`를 통하여 footer와 header를 참조하였다.  
> `{{>filePath}}`는 다른 파일을 가져와야 할 경우에 사용이 된다.

<br>

### **P.146**

**Example Code**

```
   {{#posts}}
      <tr>
        <td>{{id}}</td>
        <td>{{title}}</td>
        <td>{{author}}</td>
        <td>{{modifiedDate}}</td>
      </tr>
    {{/posts}}
```

> `{{#posts}}`
>
> > - posts라는 List를 순회하는 역할로 Java의 for문과 동일
>
> `{{id}}등의 {{변수명}}`
>
> > - `Controller`에서 가져온 `Entity(Dto)`결과의 객체의 필드값

<br>

### **P.147**

**JPA에서의 쿼리 사용**

```
@Query("SELECT p From Posts p ORDER BY p.id DESC")
  List<Posts> findAllDesc();
```

- `Spring Data JPA`에서도 `쿼리` 사용 가능하다는 예제..
- Repository Interface에서 사용한다.

---

**저자의 조언**

> 큰 규모의 프로젝트 에서는 데이터의 조회에 외래키의 조인 등 여러가지 `복잡한 조건`으로 인하여 `Entity클래스`만으로는 `처리하기 어려운 조건의 조회`를 사용하는 경우가 있다.
>
> 이런 경우 `조회용 프레임워크`를 추가로 사용을 하며 대표적인 종류는 3가지로 `Querydsl`, `jooq`, `MyBatis`등이 있다.
>
> 사용 방식은 조회는 위의 `조회용 프레임워크`를 통하여 하고 `등록`, `수정`, `삭제`의 경우에는 `SpringDataJpa`를 통해서 진행하는 방식으로 사용한다.
>
> 그 중에서 `Querydsl`을 추천하며 다음과 같다고 한다.
>
> > 1. 타입의 안정성을 보장한다.
> >    > 단순히 `문자열`로 `쿼리를 생성하는 것이 아니라`, `메소드를 기반으로 쿼리를 생성`하여 `오타나 존재하지 않는 컬럼명`을 명시할 경우 IDE에서 `자동으로 검출`된다.  
> >    > 해당 장점은 `Jooq에서도 지원`하는 장점이지만 `MyBatis에서는 지원하지 않는다.`
> > 2. 국내의 많은 회사에서 사용중이다.
> >    > 다양한 큰 서비스 기업에서 Querydsl을 적극 사용중이다
> > 3. Reference가 많다.
> >    > 사용하는 곳이 많고 기술공유를 많이 하는 기업들이다 보니 그만큼 국내 자료가 많고 커뮤니티 형성이 잘 되어있다.  
> >    > 이는 장애나 이슈가 발생시 그에 대한 답변을 쉽게 얻을 수 있다는 장점이 있다.

<br>

### **P.148**

**Example Code**

```
  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAll().stream()
        .map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }
```

> `@Transactional(readOnly = true)`
>
> > `트랜잭션의 범위는 유지`하면서 `조회기능`만 남겨두는 Annotation으로 `조회속도가 개선,상향` 되기 때문에 `등록`, `수정`, `삭제` 기능이 전혀 `없는 메소드`에 사용하면 좋다.
>
> `.map(PostsListResponseDto::new)`
>
> > `.map(posts -> new PostsListResponseDto(posts))`와 동일 하다
>
> PostsListResponseDto로 변환한 이후 PostsListResponseDto List로 만들어서 반환하는 메소드.

<br>

### **P.150**

**Model**

> - `org.springframework.ui.Model`에 속해 있다
> - 서버 템플릿 엔진에서 사용 할 수 있는 객체를 저장 한다.
> - 여기서 addAttribute메소드를 통하여 posts를 저장한 이후 index.mustache에 전달이 가능하다

<br>

### **P.159**

```
  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

    postsRepository.delete(posts);
  }
```

> jpaRepository에서는 이미 delete 메소드를 지원하고 있어 해당 부분을 사용하면 편하다.
>
> `Entity`에서 `파라미터로의 삭제`도 가능하고, `deleteById메소드`를 활용해 `id로의 삭제`도 가능하다.
>
> 삭제 전에 실제 존재하는 데이터인지는 확인 정도는 하자...!

<br>

## **Chapter. 05 : 스프링 시큐리티와 OAuth2.0으로 로그인 기능 구현하기**

### **P.163**

**Spring Security란?**

> Spring Security는 막강한 인증(Authentication)과 인가(authorization) 혹은 권한부여 기능을 가진 프레임워크다.
>
> 스프링 기반의 Application에서는 보안을 위한 표준일 정도.
>
> Interceptor, Filter기반의 보안을 구현하는 것보다는 Spring Security를 통해서 구현하는걸 적극적으로 권장하고 있다.

---

### **다양한 서비스에서 로그인 기능을 왜 소셜로그인을 쓸까?**

> 많은 서비스가 최근 추세가 로그인 기능을 ID / PW를 대신하여 구글, 페이스북, 네이버 로그인과 같은 서비스를 사용하는 것일까?
>
> 그 이유는 아마 **배보다 배꼽이 더 커지기 떄문**일 것이며, 직접 구현해야 하는게 많기 떄문일 것이며 아래의 리스트를 모두 개발해야 하기 떄문이다. (Oauth를 사용해도 개발해야 하는것을 제외한 리스트 라고 한다.)
>
> > - 로그인 시 보안
> > - 회원가입 시 이메일 혹은 전화번호 인증
> > - 비밀번호 찾기
> > - 비밀번호 변경
> > - 회원정보 변경
>
> OAuth를 통한 로그인 구현시에는 위의 리스트들에 대해서는 해당 서비스에 맡겨버리면 되기 떄문에 다른 도메인의 개발에 집중할 수 가 있기 떄문이다.

<br>

### **P.164~165**

**스프링부트 1.5 VS 스프링부트 2.0**

> 스프링 부트 2버전에서 1.5 Oauth2의 연동을 사용할 수가 있고 설정방법에 크게 차이가 없는 자료를 많이 볼 수 있다. 해당 이유는 `spring-security-oauth2-autoconfigure`로 인해서 그대로 사용할 수 있다.
>
> 하지만 스프링 팀에서 기존 1.5에서 사용하던 spring-security-oauth 프로젝트의 경우 유지하면서 버그수정만 하고 신규기능은 추가를 하지 않고 oauth2라이브러리에만 지원하겠다 선언하였다.
>
> 이유는 확장 포인트가 적절하게 오픈되어 있지 않아 직접상속 또는 오버라이딩을 해야 하고 신규 라이브러리인 경우에는 확장포인트를 고려해서 설게된 상태 이기때문이라고 한다.
>
> 해당 저서는 Spring Security Oauth2 Client라이브러리를 사용해서 진행되었다.
>
> 추후 검색시 두가지를 화인하면 좋을 듯 하다.
>
> 1. spring-security-oauth2-autoconfigure 라이브러리를 사용하였는지
> 2. application.peoperties 또는 application.yml의 정보
>    > - 스프링부트 1.5에서는 url주소를 모두 명시해야 하지만 2.0 이후에서는 client인증 정보만 기입을 하면된다.
>    > - 2.0으로 넘어오면서 1.5버전에서 입력했던 url값들 등이 enum으로 대체되었으며, CommonOAuth2Provider라는 명싱이고, 구글, 깃허브, 페이스북, 옥타등 기본 설정값이 모두 제공된다.

<br>

### **P.172**

**구글 OAuth프로젝트를 제작하며 있던 승인된 리디렉션 URI?**

- 서비스에서 파라미터로 `인증 정보를 주었을 때 인증이 성공하면 구글에서 리다이렉트 할 URL`이다.
- 스프링 부트2 버전의 시큐리티에서는 기본적으로 `{도메인}/login/oauth/code/{소셜서비스코드}`로 `리다이렉트 URL을 지원`하고 있다.
- 현재는 개발 단계이므로 http://localhost:8080/login/oauth2/code/google로만 등록한다.
- AWS서버에 배포를 하게 되면 localhost이외의 주소를 추가해야 하므로 발급받은 이후 하면 된다.

<br>

### **P.174~175**

**[Google]OAuth.properties**

```
spring.security.oauth2.client.registration.google.client-id=클라이언트ID
spring.security.oauth2.client.registration.google.client-secret=클라이언트PW
spring.security.oauth2.client.registration.google.scope=profile,email
```

> Google Cloud를 통해서 생성한 프로젝트의 발급받은 ID와 PW를 등록하면 된다.
>
> 많은 예제들이 `scope` 설정을 따로 하지 않는다. 이유는 기본값이 openid, profile, email이기 때문이며, scope를 따로 지정한 이유는 openid라는 scope가 존재하면 Open Id Provider로 인식하기 때문이다.
>
> OpenId Provider인 서비스로 만들어 버리게 되면 그렇지 않은 서비스와 나눠서 각각 OAuth2Service를 만들어야 하기 떄문이다.
>
> 예제에서 일부로 Open id의 scope를 하지 않은 이유는 하나의 OAuth2Service를 사용하기 위함이다.

---

**application-oauth.properties로 제작한 이유?**

> 스프링 부트에서는 properties의 이름을 만들 때 `application-xxx.properties`로 만들 경우 xxx라는 이름의 profile이 생성되어 관리가 가능하다.
>
> 즉 profile=xxx라는 식으로 호출하면 해당 properties의 설정들을 가져 올 수 있다
>
> 제작을 한 이후 application.properties에 `spring.profiles.include=oauth` 과 같은 항목을 추가하여 설정을 가져오면 된다.

<br>

### **P.178**

**`@Enumerated(EnumType.STRING)`**

- JPA로 데이터베이스에 저장 할 때 Enum값을 어떤 형태로 저장 할지를 결정한다.
- 기본값으로는 int로 된 숫자가 저장된다.
- 숫자로 저장되면 데이터베이스로 확인 할 떄 그 값이 무슨 코드를 가지고 있는지 의미를 알 수가 없다.
- 이해를 하기 위해 EnumType.STRING을 통하여 이해할 수 있도록 저장방식을 변경해서 선언한다.

<br>

### **P.181**

**`Spring Security Config`**

> 예제의 작성된 코드는 다음과 같으며 위에서부터 기록을 해보자면.

```
import com.jongwon.dev.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
         .csrf().disable()
         .headers().frameOptions().disable()
         .and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated()
         .and()
            .logout()
               .logoutSuccessUrl("/")
         .and()
            .oauth2Login()
               .userInfoEndpoint()
                  .userService(customOAuth2UserService);
  }
}
```

1. `@EnableWebSecurity`
   - Spring Security의 설정을 활성화 하는 Annotation
2. `.csrf().disable().headers().frameOptions().disable()`
   - h2-console 화면을 사용하기 위해서 해당 옵션의 disable설정 (왜...?)
3. `.authorizeRequest()`
   - URL별 권한 관리를 설정하는 옵션의 시작점
   - `authorizeRequests`가 선언되어야만 antMatchers옵션을 사용 할 수 있다.
4. `.antMatchers()`
   - 권한 관리 대상을 지정하는 옵션
   - URL, HTTP메소드 별로 관리가 가능하다.
   - `/` 등의 지정된 URL들은 `permitAll()` 옵션을 통해 전체 열람권한하도록 예제가 작성되었다.
   - `/api/v1/**`주소들의 경우에는 USER권한을 가진 사람만 가능하도록 제작되었다.
5. `.anyRequest()`
   - 설정된 값들 이외의 나머지 URL들을 나타낸다.
   - `authenticated()`메소드를 활용해서 나머지 모든 URL은 인증된 사용자들에게만 허용하도록 제작
   - 인증된 사용자는 로그인을 한 사용자들을 의미
6. `.logout().logoutSuccessUrl()`
   - 로그아웃 기능에 대한 설정의 진입점
   - 로그아웃 성공시에는 `/` 주소로 이동
7. `.oauth2Login()`
   - OAuth2 로그인 기능에 대한 설정의 진입점.
8. `.userInfoEndpoint()`
   - OAuth2 로그인 성공 이후 사용자 정보를 가져 올 떄의 설정들을 담당한다.
9. `.userService()`
   - 소셜로그인 성공 시 후속 조치를 진행 할 UserService인터페이스의 구현체를 등록한다.
   - 리소스 서버(소셜 서비스를 의미)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능의 명시가 가능하다.

<br>

### **P.184**

**`CustomOAuth2UserService`**

> `OAuth2UserService<OAuth2UserRequest, OAuth2User>`를 상속받아 제작된 Custom이며 소스의 기능에 대한 설명은 다음과 같다..

1. `userRequest.getClientRegistration().getRegistrationId()`
   - 현재 로그인이 진행중인 서비스를 구분하는 코드로 현재 단일 서비스를 하는 경우에는 불필요한 값이지만 이후 다른 소셜 서비스를 연동할 때 어떤 로그인인 인지 구분하기 위해 사용한다.
2. `userRequest.getClientRegistration().getProviderDetails()
.getUserInfoEndpoint().getUserNameAttributeName()`
   - OAuth2로그인 진행 시 키가 되는 필드값을 이야기 하며, PK와 같은 의미이다.
   - 구글의 경우 기본적으로 코드를 지원하지만, 네이버나 카카오 등은 기본 지원하지 않으며, 구글의 기본 코든는 `sub`ek
   - 추후 네이버, 구글 로그인을 동시 지원 할 때 사용할 예정이다.
3. `OAuthAttributes`
   - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스로 추후 다른 소셜 로그인도 같이 사용을 진행할 예정이다.
4. `SessionUser`
   - 세션의 사용자 정보를 저장하기 위한 Dto클래스로, User클래스와 따로 분류하였다.

<br>

### **P.186~187**

**`OAuthAttributes` Dto의 예제를 제작 해설**

1. `of()`
   - OAuth2User에서 반환하는 사용자 정보는 Map이기 떄문에 값 하나하나를 변환해야만 하기에 제작.
2. `toEntity()`
   - User 엔티티를 생성한다.
   - OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때.
   - 가입할 때의 기본 권한을 GUEST로 주기 위해서 role빌더값에는 Role.GUEST를 사용한다.

<br>

### **P.188**

**`SessionUser`를 따로 제작해서 Session에 저장하는 이유는?**

> `User`클래스를 바로 Session에 저장을 하게 되면 `Failed to convert from type(Object) to type(byte[]) for value ~~` 오류가 발생하게 된다.
>
> 해당 오류는 `User` 클래스에 직렬화를 구현하지 않아서 발생이 되는 오류이다.
>
> 하지만 `User` 클래스에 직접 직렬화를 구현하지 않는 이유는 해당 클래스는 **`Entity`** 이기 때문이다. Entity클래스는 `언제 다른 엔티티 클래스와 관계가 형성될지 모르기 떄문`이다.
>
> `@OneToMany`,`@ManyToMany`등으로 자식 엔티티를 갖게 되면 직렬화 대상이 자식으로까지 넓어지기 떄문에 `성능이슈를 포함하여 여러 이슈가 발생`할 수 있기 때문이다.
>
> 위와 같은 사유로 `SessionUser`를 따로 제작하여 Session에 저장하듯 `직렬화 기능을 가진 세션 Dto`를 하나 추가로 만드는게 추후 나의 운영 및 유지보수에 많은 도움이 될 것이다.

<br>

### **P.189**

**`mustache`의 해설?**

1. `{{#userName}}`
   - `mustache`의 경우 if문을 제공하지 않으며, `true` / `false`여부만 판단한다. 그렇기 떄문에 `항상 최종 값`을 넘겨주어야 한다.
2. `a href="/logout"`
   - `Spring Security`에서 기본적으로 제공하는 `로그아웃 URL`이다.
   - 개발자가 별도로 저 URL에 해당하는 컨트롤러를 제작하지 않아도 된다.
   - SecurityConfig 클래스에서 URL을 변경 할 수 있지만, 기본 URL을 사용하도 충분하다.
3. `{{^userName}}`
   - `mustache`에서 해당 값이 존재하지 않는 경우 `^객체명`을 사용한다.
4. `a href="/oauth2/authorization/google"`
   - `Spring Security`에서 기본적으로 제공하는 `로그인 URL`이다.

<br>

### **P.196**

**Custom Annotation**

1. **`@Target(ElementType.PARAMETER)`**
   - 해당 어노테이션이 생성 될 수 있는 위치를 설정하는 Annotation
   - 해당 예제는 PARAMETER로 지정하였기 때문에 메소드의 파라미터로 선언된 객체에만 사용 할 수 있다.
   - 외에도 다양한 위치에 조절 할 수 있도록 다양한 Type값이 존재한다.
2. **`@interface`**
   - 해당 파일을 Annotation Class로 지정하는 선언

<br>

### **P.197**

**`HandleMethodArgumentResolver`란?**

> 조건에 맞는 메소드가 있다면 `HandlerMethodArgumentrResolver`의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있다.

1. **Override한 `supportsParameter(MethodParameter)`**

   - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단하는 Method
   - 예제 소스에서는 파라미터에 `@LoginUser` 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class 인 경우 true를 반환한다.
     - ```
         아래의 소스코드 참고
         boolean isLoginUserAnnotation =
            parameter.getParameterAnnotation(LoginUser.class) != null;
         boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
       ```

2. **Override한 `resolveArgument`**
   - 파라미터에 전달할 객체를 생성한다.
   - 여기서는 세션에서 객체를 가져온다.

<br>

### **P.200**

**Resolver를 등록함으로 생긴 이점**

```
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpSession httpSession;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

    return isLoginUserAnnotation && isUserClass;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory
  ) throws Exception {
    return httpSession.getAttribute("user");
  }

}

```

해당 Resolver를 등록함으로써 Parameter에 직접 제작한 `@LoginUser`어노테이션과 `Type`을 `SessionUser`로 진행을 할 경우 굳이 Session의 getAttribute를 활용해서 가져오는 절차 없이 불러올 수 있다.

<br>

### **P.201**

**WAS의 세션 저장소로 DB를 활용하기**

> WAS의 경우 어플리케이션을 재실행 하는 경우 Session이 유지가 되지 않는다. 세션의 경우 `내장 톰캣의 메모리 영역`에 저장이 되기 떄문이다.  
> 즉 `내장 톰캣처럼 어플리케이션이 실행시 실행이 되는 구조에서는 항상 초기화`가 된다.
>
> 위의 말의 문제점은 `배포를 진행 할 때마다 톰캣이 재시작`하고 세션데이터가 증발하는 문제를 말 하는 것이다.
>
> 또 다른 문제점은 실제 `복수개의 서버`에서 `서비스를 진행`하고 있을 경우 `톰캣마다 세션동기화`를 해야한다. 그렇지 않으면 `한쪽만 세션데이터를 소지하고 있게 되는 것`이다.
>
> 그래서 실제 현업에서는 보통 3가지중에 한개를 선택한다.
>
> 1. 톰캣 세션을 사용한다
>    > - 일반적으로 사용하는 케이스로, 설정을 하지 않을 때 기본적으로 선택되는 방식
>    > - WAS에 저장이 되기에 아까 기록한 대로 `2대 이상의 WAS`가 구동되는 경우 세션공유를 위한 `추가 설정이 필요`하다
> 2. `MySQL`처럼 `데이터베이스`를 세션 저장소로 사용한다.
>    > - 여러 WAS간에 공용세션을 사용 할 수 있는 가장 쉬운 방법
>    > - 많은 설정은 필요 없지만 `DB IO가 매우 자주 발생`하기 떄문에 `성능상 이슈가 발생` 할 수 있다.
>    > - 보통 `로그인 요청이 많이 없는` 백오피스 또는 사내 시스템 등에서 자주 사용된다.
> 3. `Redis` ,`Memcached`와 같은 `메모리DB`를 세션 저장소로 사용한다
>    > - 가장 많이 활용되는 방식이다
>    > - 실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방식이 아니라, 외부 메모리 서버를 따로 구축해서 활용한다. (추가적인 서버 비용 발생)

<br>

### **P.202**

**DB로 세션 저장 변경방법**
JPA를 활용할 경우 변경은 쉽다.

1. `spring-session-jdbc`의존성 추가
2. `application.properties or application.yml`에 `spring.session.store-type`으로 `value`를 `jdbc`로 설정

<br>

### **P.211**

**기존 테스트에 Security 적용**
Security옵션이 활성화 되면 인증된 사용자만 API를 호출 할 수 있다. 이는 테스트코드 역시 마찬가지다.
만약 테스트코드도 정상적으로 진행을 하고 싶다면 테스트 코드마다 인증한 사용자가 호출한 것처럼 작동이 되도록 수정을 해야 한다.

application-oauth.properties에 설정값을 추가하였음에도 설정이 없다고 나오는 이유는 `src/main`환경과 `src/test`의 환경이 차이가 나기 때문이며, 둘은 환경구성을 따로따로 가지기 떄문이다.

여기서 의문인건 test에 application.properties가 없음에도 실행되는게 의문일 수도 있다. test의 application.properties가 없는경우에는 main의 설정을 그대로 가져오기 떄문에 문제가 없다.

하지만 가져오는 옵션의 범위는 application.properties까지 이기 때문에, 예제의 application-oauth.properties는 test에 없다고 가져오는 파일이 아니라고 나오는 것.

해당 문제를 해결하기 위해서는 application.properties를 test환경에 구축해야한다

<br>

### **P.215**

**`src/test`에 `application.properties` 구축이후**

> 그래도 테스트를 진행할 경우 302로 실패된 케이스가 존재한다. 이유는 스프링 시큐리티 설정 때문에 `인증되지 않은 사용자의 요청은 이동` 시키기 때문이다.  
> 이런 요청의 경우에는 `임의로 인증된 사용자를 추가`해 `API만 테스트 해 볼 수 있도록` 해야한다
>
> 해당 테스트에 대해서는 Spring Security에서 공식적인 방법을 지원하며, `Spring security Test`를 통해 테스팅이 가능하다
>
> `302`에러가 나는 경우이며, `@WithMockUser(roles="USER")`를 테스트케이스에 선언하여 Mock테스트 시에 USER권한을 부여한다. (MocvMVC 에서만 사용가능하다.)
>
> 이후 SpringBootTest에서 MockMvc테스트로 전환하자.

<br>

### **P.220**

**`CustomOAuth2UserService`를 스캔못한 오류는 왜날까?**

> Custom으로 제작한 OAuthService오류는 제목그대로 스캔하지 못하기떄문에 발생하고있다.  
> 해당 Controller의 경우에는 `WebMvcTest`를 사용해서 테스트를 하고있으며, WebMvcTest는 `@CustomOAuth2UserService`를 스캔하지 못하기 떄문에 문제가 발생하고 있는 것이다.
>
> `@WebMvcTest`는 `WebSecurityConfigureAdapter`, `WebMvcConfigurer`를 비롯하여 `@ControllerAdvice`, `@Controller`를 읽는다.  
> 즉 `@Service`, `@Repository`, `@Component`는 스캔의 대상이 아니다.
>
> 문제가 나오는 이유는 이 부분때문인며, Security Config는 읽었지만 CustomOAuth2UserService는 읽지를 못해서 에러가 발생한 것이라 해당 문제를 위해서는 SecurityConfig를 HelloController에서는 제거하는 것이다.
>
> 아래와 같은 설정을 해서 SecurityConfig를 제거하자.

```
@WebMvcTest(controllers = HelloController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    })
```

> MockMvc테스트기 떄문에 테스트마다 `@WithMockUser(roles = "USER")`를 통해 User권한도 부여하자

> 이후에는 `JPA metamodel must not be empty!`에러가 난다.
> `@EnableJpaAuditing`으로 인해 발생하는 문제로 하나 이상의 Entity가 필요하다는 건데, `@WebMvcTest`라 Entity가 따로 없다.
> 하지만 예제에서 `@EnableJpaAuditing`과 `@SpringBootApplication`이 Main에 같이 존재하기 떄문에 `@WebMvcTest`에서도 같이 스캔하게 된 것이다.  
> 아래와 같이 따로 분류하도록 하자.

```
//@EnableJpaAuditing 주석처리
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

@Configuration
@EnableJpaAuditing
public class JpaConfig {}

```

<br>

## **Chapter. 06 : AWS서버 환경을 만들어 보자 - AWS EC2**

### **P.225**

**24시간 작동하는 서버의 선택**

> 1.  집에 PC를 24시간 구동
> 2.  호스팅 서비스를 이용 [Cafe24가 대표적]
> 3.  클라우트 서비스를 이용 [AWS, NCP, AZURE, GCP등등]

---

**비용적으로 유리한 방법**

> 집에서 서버를 구동 또는 호스팅 서비스가 유리

---

**몰리는 트래픽의 처리가 필요한경우**

> 클라우드 서비스가 유리

<br>

## **Chapter. 07 : AWS에서 DB환경 만들기 - RDS**

### **P.290**

> 테이블 생성의 경우 인코딩 설정 변경전에 생성하면 안된다.  
> 만들어질 당시의 설정값을 그대로 유지하고 있어, 자동 변경이 되지 않고 강제로 변경해야 한다.
> 테이블은 모든 설정이 끝나느 이후 생성하도록 한다.

### **P.291**

**EC2에서 RDS를 통한 접근**

1. EC2 접속
2. MySQL 설치
3. mysql -u `username` -p -h `AWS Endpoint`
4. PW입력

<br>

## **Chapter. 08 : EC2서버에 프로젝트 배포하기**

**EC2에 프로젝트 Clone하기**

1. 깃 설치
   1. `sudo yum install git`
2. git 설치 상태 확인
   1. 책에서는 `git --version`을 통해 확인하였다.
3. clone해 넣어둘 디렉토리 생성
   1. `mkdir ~/app && ~/app/step1`
   2. `cd ~/app/step1`
4. 프로젝트 clone
   1. git clone git페이지주소
   2. clone한 프로젝트 실행해보기
      1. 제작한 TestCase를 실행해해봄
         1. `cd 프로젝트명`
         2. `./gradlew test`
            1. 실패의 경우 수정이후 `git pull`
            2. gradlew의 Permission denied인 경우 `chmod +x ./gradlew`

---

**배포란?**

> 작성한 코드를 실제 서버에 반영하는 것을 의미한다.

---

**책의 예제의 배포 과정은?**

1. Git Clone 과 Git Pull을 통해 새 버젼의 프로젝트를 받는다.
2. Gradle 또는 Maven을 통해 해당 프로젝트를 테스트 및 빌드한다.
3. EC2서버에서 해당 프로젝트 실행 및 재실행한다.

---

**배포 스크립트 만들기**

> 위 예제의 배포과정을 배포할 때마다 개발자가 하나 하나 명령어를 실행하는 것은 불편함이 많다, 그렇기에 이를 쉘 스크립트로 작성해 스크립트를 실행시켜 앞의 과정이 한번에 진행되도록 설정하는 것

```
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=SpringBoot-With-Aws

cd $REPOSITORY/$PROJECT_NAME/
git pull
./gradlew build
cd $REPOSITORY
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
if [ ~z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `#!/bin/bash`
   - `bash`로 쉘을 실행시키는 의미다.
2. `REPOSITORY=/home/ec2-user/app/step1`와 `PROJECT_NAME=SpringBoot-With-Aws`
   - `$REPOSITORY`, `$PROJECT_NAME` 으로 호출을 하기 위한 변수 생성 및 경로값을 대입
3. `cd $REPOSITORY/$PROJECT_NAME/`후 `git pull`
   - 프로젝트 디렉토리에 이동한 이후 버전 최신화
4. `./gradlew build`
   - 프로젝트 빌드
5. `cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/`
   - build를 진행한 이후 나온 프로젝트 jar파일을 $REPOSITORY에 복사
6. `CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)`
   - 기존에 수행중이던 스프링 부트를 종료한다
   - `pgrep`은 process id 만 추출하는 명령어이다.
   - `-f` 옵션은 프로세스 이름으로 찾는 기능이다.
7. `if ~ else ~ fi`
   - 현재 구동중인 프로세스가 존재하는지 여부 판단을 위한 조건문
   - `process id` 값을 보고 프로세스가 존재하면 해당 프로세스를 종료한다
8. `JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)`
   - 새로 실행할 jar파일명을 찾아 JAR_NAMEdp sjgdma.
   - 여러 jar파일이 생기기 때문에 tail -n을 하여 가장 최신의 jar파일을 변수에 저장한다.
9. `nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &`
   - 발견한 jar파일명으로 해당 jar을 nohup으로 실행한다.
   - 그냥 java -jar을 통해 실행하는 경우 사용자가 터미널 접속 종료시에 Application도 같이 종료가 되기 떄문에 nohup을 활용
10. 이후 생성한 스크립트 파일 권한 변경
    - `chmod +x ./스크립트 파일`

---

**배포 스크립트 수정 - OAuth 적용**

> 위의 스크립트를 사용시에 `Spring Security`의 값을 가져올 수 있는 `application-oauth.properties`가 존재하지 않기 때문에 실패를 한다.  
> 하지만 `application-oauth.properties`의 경우 Git Ignore가 되어 있기 때문에 직접적으로 운영, 개발서버에 추가하고 설정해줘야한다.

1. `희망위치/application-oauth.properties`생성
2. 개발된 내용의 oauth복사
3. 배포스크립트 내용 수정

   ```
   //수정전
   nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

   //수정후
   nohup java -jar \
   -Dspring.config.location=classpath:/application.properties, 생성한파일위치/application-oauth.properties \
   $REPOSITORY/$JAR_NAME 2>&1 &
   ```

**`-Dspring.config.location`**

- 스프링 설정 파일 위치를 지정한다.
- 지정된 properties마다 따로 지정이 가능하다
- classpath가 붙으면 jar안에 있는 resources디렉토리를 기준으로 경로가 생성된다.
- application-oauth.properties의 경우에는 외부에 파일이 있기 때문에 절대 경로를 사용한다.

---

**RDS서버에 테이블 생성**

> 현재 책의 예제의 테이블은 2종류가 있로 `JPA를 활용하는 엔티티 테이블` 그리고 DB를 세션으로 사용하고 있는 `세션테이블`이 존재한다. 이 2종류의 테이블을 만들 수 있는 쿼리는 다음과 같다.
>
> 1. `엔티티 테이블`은 `Application 실행시키며 생성되는 쿼리`를 사용한다.
> 2. `세션 테이블`은 프로젝트에 있는 `schema-mysql.sql`파일에서 추출한다

---

**EC2서버에서 RDS로 접속을 위한 `application-real-db.properties`설정**

```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://AWS Endpoint:Port/Database Name
spring.datasource.username=DB ID
spring.datasource.password=DB PW
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

1. `spring.jpa.hibernate.ddl-auto=none`
   - JPA로 테이블이 자동 생성되는 옵션을 None(생성하지 않도록) 한다.
   - RDS에는 실제 운영에서 사용될 테이블이기 떄문에 절대로 스프링 부트에서 새로 만들도록 하지 않아야 한다.
   - 이 옵션을 설정하지 않으면 테이블이 모두 새로 생성될 수 있기에 주의해야 한다.

---

**DB설정 추가에 따른 배포스크립트의 수정영역**

```
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties, /home/ec2-user/app/application-real-db.properties, classpath:/application-real.properties \
        -Dspring.profiles.active=real \
        $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `application-real`와 `application-real-db`설정 파일의 추가
2. `-Dspring.profiles.active=real`
   - `application-real.properties`항목을 활성화 시킨다.
   - `application-real.properties`의 spring.profiles.include=oauth,real-db옵션 때문에 real-db역시 같이 활성화 대상에 포함된다.

---

<br>

## **Chapter. 09 : 코드가 푸시되면 자동으로 배포하기 - Travis CI를 통한 배포 자동화**

> 24시간 365일 운영되는 서비스에서는 `CI / CD` 환경 구축은 선택이 아닌 필수이며, 책에서의 예제는 `Travis CI`를 활용해 구축할 예정이다.  
> 또한 `8장`에서 직접 경험을 해봤기 때문에 알 수 있듯이 `배포 스크립트`를 통한 배포는 개발자가 직접 실행을 해야하는 불편함이 있기 때문에 더더욱 필요한 사항이다.

<br>

**`CI` / `CD`**

> `CI`란 `Continuous Integration`이라 칭하며 `지속적 통합`이라 칭한다.  
> 코드 버전 관리(VCS)를 하는 시스템(`Git`또는 `SVN`)에 `PUSH`가 되면 자동으로 테스트와 빌드가 수행되는 것을 의미한다.

> `CD`란 `Continous Deployment`라 칭하며 `지속적인 배포`라고 칭한다.  
> `CI`의 과정(`테스트`와 `빌드`)가 전부 이뤄지면 `빌드 결과를 바탕으로 자동으로 운영서버에 무중단 배포까지 진행되는 과정`을 의미한다.

<br>

**`CI`와 `CD`의 역사? 개념이 나오게 된 배경**

> 현대의 웹 서비스의 경우 하나의 프로젝트를 다수의 개발자가 함께 개발을 진행하고 있다.  
> 그러다 보니 각자가 개발한 코드를 합쳐야 할 때 마다 큰 일 이었고, 병합일(코드를 Merge만 하는날)을 정해서 할 정도였다.
>
> 이런 작업은 생산성에 저하를 하는 요소이기 떄문에 CI환경이 구축된 것이다.
>
> 또한 CD의 경우에도 한두대의 서버에 개발자가 수동으로 배포는 할 수 있지만, 수십에서 수백대의 서버에 개발자가 직접 배포를 해야 하는 상황에서나 긴급하게 당장 배포하는 일을 수동으로만은 할 수 없게 되었고, 그렇기에 CD역시 구축을 하게 되었다.
>
> CI / CD 환경을 구축하게 되면 개발자가 갖게 되는 장점은 `개발자가 개발에만 집중 할 수 있다`는 장점이 생기게 된다.

<br>

**마틴 파울러의 CI에 대한 4가지 규칙**

> `CI도구를 도입했다고 해서 CI를 하고 있는 것은 아니다`를 명심하며, 마틴 파울러의 4가지 규칙을 기억하자.

1. `모든 소스코드가 살아(현재 실행되고) 있고` 누구든 `현재의 소스에 접근 할 수 있는 단일 지점을 유지`해야 한다.
2. `빌드 프로세스를 자동화` 해서 누구든 `소스로부터 시스템을 빌드하는 단일 명령어`를 `사용`할 수 있도록 한다.
3. `테스팅을 자동화` 해서 단일 명령어로 `언제든지 시스템에 대한 건전한 테스트 수트를 실행` 할 수 있게 한다.
4. 누구든 현재 실행 파일을 얻으면 지금까지 `가장 완전한 실행 파일을 얻었다는 확신`을 하게 한다.

> 여기서 3번 항목인 `테스팅 자동화`는 특히나 중요하다. 지속적인 통합을 하기 위해서는 이 프로젝트가 현재 `완전한 상태를 보장`해야 하기 위한 `테스트 코드`가 구현되어 있어야 하기 떄문이다.

<br>

**Travis CI?**

> Github에서 제공하는 무료 CI 서비스이며, Private Repository에는 다양한 유료플랜을, Public Repository에는 무료 플랜을 제공하고 있다.

<br>

**Travis CI 웹서비스 설정 방법**

1. https://travis-ci.org 접속 및 계정 생성
2. profile → settings
3. Repositories에서 Project 선택
4. 필자의 경우 `Build Pushed branches`와 `Build Pushed Pull Requests`에 대해서만 설정하였다.

**CI설정을 진행할 `.travis.yml` 파일 제작**

> 해당 파일은 `build.gradle`과 같은 파일에 있어야 한다.

```
//1번
language: java

//2번
jdk:
  - openjdk8

//3번
branches:
  only:
    - main

//4번
cache:
  directories:
    - '$HOME/.m2/repository'
    - '$HOME/.gradle'

//5번
script: "./gradlew clean build"

//6번
notifications:
  email:
    recipients:
      - whddnjs822@gmail.com
```

1. 자바 언어 설정
2. JDK 버전 설정
3. Travis CI를 어떤 브랜치가 푸쉬 될 때 수행할지 지정하며, 예제는 main브랜치가 푸쉬될때만 수행한다.
4. gradle을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여 같은 의존성은 다음 배포때 부터 다시 받지 않도록 한 설정이다.
5. master브랜치에 푸시되었을 때 수행하는 명령어이며 프로젝트 내부에 둔 gradlew를 통해 clean & build를 수행한다.
6. Travis CI실행 완료시 자동으로 설정한 메일로 알람이 오도록 하는 설정

<br>

**AWS S3**

> S3란 AWS에서 제공하는 일종의 파일서버중 하나이다.
> 이미지 파일을 비롯하여 정적 파일을 관리하거나 예제에서 진행하는 것처럼 배포 파일들을 관리하는 등의 기능을 지원한다.
> 보통 이미지 업로드를 구현한다면 S3를 활용해 구현하는 경우가 많다.

<br>

**Travis CI와 AWS S3를 활용한 연동 구조**  
![이미지](https://raw.githubusercontent.com/smpark1020/tistory/master/CI%20%26%20CD/%5BTravis%20CI%5D%20%EC%BD%94%EB%93%9C%EA%B0%80%20%ED%91%B8%EC%8B%9C%EB%90%98%EB%A9%B4%20%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C%20%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0%203%20-%20Travis%20CI%EC%99%80%20AWS%20S3%20%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0/1.jpg)

> Travis CI를 활용하게 될 경우 다음과 같은 구조가 이뤄지게 된다  
> 실제 배포에는 `AWS의 CodeDeploy` 서비스를 활용해야 한다. 하지만 S3연동이 먼저 필요한 이유는 `Jar파일의 전달`이 필요하기 때문이다.  
> `CodeDeploy`의 경우에는 `저장`기능이 없다. 그렇기 떄문에 Travis CI가 빌드한 결과물을 받아서 `CodeDeploy`가 가져갈 수 있도록 보관 할 수 있는 공간이 필요하며, 이때 `S3`를 활용한다.

> 추가적으로 `CodeDeploy`에서 빌드도 하고 배포도 할 수 있지만, `GitHub의 코드를 가져오는 기능`은 지원하지 않는다. 이렇게 되면 두개가 합쳐지기 때문에 빌드가 필요 없이 과거 버전에 대한 배포를 할떄 이전 빌드정보가 없기 때문에 대응하는게 힘들다.

> `빌드`와 `배포`가 분리되어 있으면 `과거에 빌드`되어 만들어진 파일(Jar, War)를 `재사용`하면 되지만 `CodeDeploy`가 모두 진행하게 되면

<br>

**AWS의 IAM을 사용하는 이유와 IAM에 대해서**

> 일반적으로 `AWS 서비스`에 `외부 서비스가 접근 할 수 없다.` 그렇기 때문에 `접근 가능한 권한을 가진 Key`를 `생성`해서 `사용`해야 한다.  
> AWS에서는 이러한 `인증과 관련된 기능을 제공하는 서비스`를 `IAM(Identity and Access Management)`가 존재한다.  
> IAM은 `AWS에서 제공하는 서비스`의 `접근 방식`과 `권한을 관리`한다.

> 예제에서 사용하는 `Travis CI`가 `AWS`의 `S3`와 `CodeDeploy`에 있도록 하기 위해서도 발급이 필요하다.

**AWS Key발급 방법(Travis CI Deploy적용방식)**

1. `AWS웹 콘솔`에서 `IAM`을 검색해 항목으로 이동한다.
2. 좌측 사이드바에서 `사용자` → `사용자 추가` 버튼을 차례로 클릭한다.
3. `사용자 이름 기입`과 `액세스키-프로그래밍 방식` 선택후 다음
4. `기존 정책 직접 연결` 선택 후 `AmazonS3FullAccess`, `AWSCodeDeployFullAccess`항목을 추가후 다음
   1. 실제 서비스 회사의 경우에는 `S3`와 `CodeDeploy`를 `분리해서 관리`하기도 하나, 예제의 경우에는 간단히 두개를 동시에 관리하도록 한다.
5. `태그`의 경우 키에 `Name`을 지정하고 값도 지정하나 예제의 경우에는 IAM 사용자이름과 일치시켰다.
6. 이후 정보확인을 하고 사용자 생성을 하면 `엑세스 키`와 `비밀 엑세스`키가 생성된다.

<br>

**Travis CI에서 Key설정**

1. `Travis CI`의 `Repository`별 Settings이동
2. `Environment Variables`항목에서 `NAME`을 `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`로 발급받은 KEY값을 넣고 추가한다.
3. 해당 항목은 `.travis.yml`항목에서 `$AWS_ACCESS_KEY`, `$AWS_SECRET_KEY`라는 항목으로 활용이 가능하다

<br>

**S3 버킷 생성**

> S3는 일종의 파일서버로 순수하게 파일들을 저장하고 접근 권한을 관리, 검색등을 지원하는 역할을 한다.  
> S3는 보통 게시글을 쓸 때 나오는 `첨부파일 등록을 구현`할때 많이 이용된다.  
> Travis CI에서 생성된 Build파일을 저장하도록 예제는 구성을 하였다.

1. 버킷만들기 버튼을 통한 페이지 이동
2. 버킷 이름 생성
3. 버킷의 퍼블릭 액세스 차단 설정의 경우에는 `모든 퍼블릭 액세스 차단`으로 설정한다.
   1. 액세스의 차단이 중요한 이유는 실제 빌드후 배포해야 할 파일이 퍼블릭일 경우 누구나 내려받을 수 있기 떄문이다.
   2. 예제의 경우 IAM사용자를 통해 발급받는 키를 사용하기 때문에 접근 가능하다.

<br>

**.travis.yml추가**

> 다음과 같은 항목이 추가되었다.

```
before_deploy:
  - zip -r jong1-springboot-webservice *
  - mkdir -p deploy
  - mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip

deploy:
  - provider: s3
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    region: ap-northeast-2
    skip_cleanup: true
    acl: private
    local_dir: deploy
    wait_until-deployd: true
```

1. `before_deploy`
   1. deploy명령어가 실행되기전에 수행한다.
   2. CodeDeploy는 Jar파일을 인식하지못하므로 Jar + 기타 설정 파일들을 모아서 압축(zip)한다.
2. `zip -r jong1-springboot-webservice *`
   1. 현재위치의 모든 파일을 zip파일로 압축한다.
3. mkdir -p deploy
   1. deploy라는 디렉토리를 Travis CI가 실행중인 위치에 생성한다.
4. mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip
   1. zip파일을 deploy디렉토리로 이동
5. deploy
   1. S3로 파일 업로드 혹은 CodeDeploy로 배포 등 외부 서비스와 연동될 행위들을 선언한다.
6. local_dir: deploy
   1. 앞에서 생성한 deploy 디렉토리를 지정한다.
   2. 해당위치의 파일들만 S3로 전송한다.

<br>

**Travis CI와 AWS S3, CodeDeploy 연동하기**

> CodeDeploy를 연동 받을 수 있게 하기 위해 역할의 추가가 필요하다.

> IAM에서 사용자와 역할은 차이가 존재한다. 역할의 경우에는 AWS서비스에만 할당할 수 있는 권한을 의미하며`EC2`, `CodeDeploy`, `SQS`등등
> 사용자의 경우에는 `AWS 서비스 외`에 사용할 수 있는 권한으로 `로컬PC`, `IDC서버등이있다` 현재의 경우 EC2에서 활용할 것이기 떄문에 역할로 처리해야 한다.

1. IAM이동
2. 역할 → 역할만들기
3. AWS서비스, EC2 선택후 다음
4. 권한은 AmazonEC2RoleforAWSCodeDeploy 선택 후 다음
5. 역할이름 기입및 태그Key, Value입력
6. EC2에서 적용을 희망하는 인스턴스 우클릭 → 보안 → IAM역할 수정 → 제작한 IAM역할로 업데이트
7. EC2 재부팅
   1. 재부팅을 하지 않을 경우 역할이 정상적으로 적용되지 않기 떄문

<br>

**CodeDeploy 에이전트 설치하기**

1. EC2접속
2. aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
   1. 다운로드에 성공하면 `download: s3://aws-codedeploy-ap-northeast-2/latest/install to ./install` 다음과 같은 echo를 리턴받는다.
3. `chmod +x ./install`을 하여 다운로드 받은 설치파일의 권한증가
4. `sudo ./install auto`스크립트로 다운로드 진행
   1. `/usr/bin/env: ruby: No such file or directory` 오류가 발생하며, ruby가 설치되어있지 않아 발생하는 오류이다.
   2. 나의 경우 `sudo yum install ruby`를 통하여 `ruby`의 설치를 진행하였다.
5. `sudo service codedeploy-agent status`를 통하여 서비스 실행 여부 확인
   1. `The AWS CodeDeploy agent is running as PID XXXX` 서비스 실행 문구이다.

<br>

**CodeDeploy를 위한 권한 생성**

> CodeDeploy에서 EC2에 접근하려면 마찬가지로 권한이 필요하다. AWS의 서비스를 통한 접근이기에 IAM에서 역할의 생성으로 필요하다

1. IAM 역할만들기
2. 사용사례에서 CodeDeploy 설정 → `AWSCodeDeployRole` 설정 확인
3. 역할이름, 태그설정

<br>

**AWS의 배포 생태계**

> 근데 일단 해당 저서가 몇년전에 만들어 진 것 이다보니 현재 TIL을 작성하는 2022년을 기준으로 맞는지는 모르겠네...

> 유명한 3종류가 존재하며 다음과 같다.

1. `Code Commit`
   - 깃허브 처럼 코드저장소의 역할을 한다.
   - 프라이빗 기능을 지원한다는 강점이 있지만 현재 Github에서 Private지원을 하고 있어 거의 사용하지 않는다.
2. `Code Build`
   - Travis CI와 마찬가지로 빌드용 서비스이다.
   - 멀티모듈을 배포해야 하는 경우에 사용 해 볼법 하지만 규모가 있는 서비스에서는 대부분 `젠킨스` / `심시티` 등을 이용하는등 외부 서비스를 사용하기 때문에 사용할 경우가 거의 없다.
3. `CodeDeploy`
   - AWS의 배포 서비스이다
   - `Code Commit`과 `Code Build`의 경우에는 다른 서비스들로 대체제가 존재하지만 `CodeDeploy`의 경우에는 대체제가 없다.
   - `오토 스케일링 그룹 배포`, `블루 그린 배포`, `롤링 배포`, `EC2 단독 배포`등 많은 기능을 지원한다.

<br>

**CodeDeploy 생성방법**

1. CodeDeploy 이동 및 Application생성
2. 어플리케이션 설정 및 EC2의 경우 컴퓨터 플랫폼에서 `EC2/온프레미스`를 사용한다.
3. 이후 배포그룹을 생성
   1. 배포그룹명기입 및 이전에 생성한 서비스 역할 선택
   2. 배포 유형의 경우 `내가 배포할 서비스가 2대 이상이라면 블루 / 그린`을 선택한다. 현재는 EC2한대만 이기 떄문에 `현재위치` 항목으로 진행한다.
   3. 환경구성은 EC2를 사용하기 떄문에 Amazon EC2 인스턴스를 사용한다.
   4. 배포 설정의 경우 CodeDeployDefault.AllAtOnce 항목 선택
      1. 항목이 `CodeDeployDefault.OneAtATime`, `CodeDeployDefault.HalfAtATime`, `CodeDeployDefault.AllAtOnce`이 존재한다.
      2. 각각 배포때 10대의 배포라면 한번에 한대씩 배포할지, 전체배포할지 등 조율하는 항목이다
      3. 커스텀으로 조절도 가능하다.
   5. 로드밸런싱은 현재 필요없기에 해제한다.

<br>

**Travis CI, S3, CodeDeploy 연동 순서**

> 해당 내역은 빌드한 파일을 연동까지만의 과정이며 `배포(실행)`하는 과정은 빠져있다..

1. S3에서 Zip파일을 넘겨받을 Directory 추가
   1. Travis CI의 Build가 끝나고 S3에 Zip파일이 전송된다.
   2. Zip파일을 생성한 Directory로 복사후 압출을 푼다.
   3. 설정은 .travis.yml에서 진행하며, AWS CodeDeploy의 경우에는 `appspec.yml`에서 진행한다
2. `appspec.yml`작성
3. `.travis.yml`에서 `CodeDeploy` 설정 추가
4. 설정 Push 이후 `CodeDeploy 웹콘솔` 에서 배포내역 확인

**`appspec.yml` 작성**

```
version: 0.0
os: linux
files:
- source: /
   destination: /home/ec2-user/app/step2/zip
   overwrite: yes
```

1. `version: 0.0`
   1. CodeDeploy버전을 의미한다.
   2. 프로젝트 버전이 아니므로 0.0 외에 다른 버전을 사용하는 경우 오류가 발생한다.
2. `source : /`
   1. CodeDeploy에서 전달을 해 준 파일 중 `destination`으로 이동시킬 대상을 지정한다.
   2. `루트경로(/)` 를 지정하면 전체 파일을 의미한다.
3. `destination: 경로`
   1. source에서 지정한 파일을 받을 위치를 의미한다.
   2. 이후 Jar을 실행하는 등은 destination에서 옮긴 파일들로 진행한다.
4. `overwrite`
   1. 기존에 파일들이 있으면 덮어쓸지를 결정한다.
   2. 현재 `yes`로 지정을 하였기 때문에 파일들을 덮어쓰게 된다.

<br>

**`.travis.yml`에서 `CodeDeploy` 설정 추가**

```
  - provider: codedeploy
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    key: jong1-springboot-webservice.zip
    bundle_type: zip
    application: jong1-springboot-webservice
    deployment_group: jong1-springboot-webservice-group
    region: ap-northeast-2
    wait-until-deployed: true
    on:
      all_branches: true
```

1. `bucket: jong1-springboot-build`
   - AWS의 S3 버킷에 설정한 명칭
2. `key: jong1-springboot-webservice.zip`
   - 빌드 파일을 압축해서 전달한다.
3. `bundle_type: zip`
   - 압축 확장자
4. `application: jong1-springboot-webservice`
   - CodeDeploy 웹 콘솔에서 등록한 Application 명
5. `deployment_group: jong1-springboot-webservice-group`
   - 웹 콘솔에서 등록한 CodeDeploy 배포 그룹
6. `on.all_branches: true`
   - `S3 Bucket` 설정때와 마찬가지로 작업을 진행햇을때 작업이 정상적으로 마치질 않았다.
   - 빌드는 성공했으나 파일이 없었고, AWS의 CodeDeploy 배포내역에도 존재하지 않는현상.
   - 브랜치설정을 추가한후 다시 빌드 했을 경우 정상적인 파일 배포가 가능해졌다.

**배포 자동화(실행)**

> 쉘 스크립트를 인텔리제이에서 제작할 때 `BashSupport`를 활용하면 보조를 받을 수 있다.

1. 쉘 배포 스크립트 프로젝트에서 생성
2. `.travis.yml` 수정
3. `appspec.yml` 수정

**쉘 배포 스크립트 변경사항**

```
# 기존
$REPOSITORY/$JAR_NAME 2>&1 &

# 변경
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

1. nohup 실행을 하게 될 경우 `CodeDeploy`는 `무한 대기`를 하게 된다.
2. 해당 이슈를 해결 하기 위해 nohup.out파일을 표주준 입출력용으로 별도 사용해야 한다.
   1. 이렇게 사용하지 않을 경우에는 nohup.out파일이 생기지 않으며 `CodeDeploy`로그에 표준 입력이 출력된다.
   2. nohup이 끝나기 전 까지는 `CodeDeploy`도 끝나지 않으니 꼭 이렇게 해야 한다.

**`.travis.yml`의 `before-deploy` 수정**

```
before_deploy:
  - mkdir -p before-deploy
  - cp scripts/*.sh before-deploy/
  - cp appspec.yml before-deploy/
  - cp build/libs/*.jar before-deploy/
  - cd before-deploy && zip -r before-deploy *
  - cd ../ && mkdir -p deploy
  - mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip
```

> 실제 필요한 파일은 `스크립트 실행(sh)파일`, `appspec.yml`, `jar` 총 세가지만 필요하나 현재 모든 파일을 zip으로 올리던 상황. 필요한 파일만 담도록 수정한것.

1. `mkdir -p before-deploy`
   1. 기존 `deploy`파일을 사용하는게 아니라 사전에 파일을 정리할 Directory 새로 생성
2. `cp scripts/*.sh before-deploy/`
   1. `쉘 스크립트 실행파일(sh)`을 `before-deploy`로 복사
3. `cp appspec.yml before-deploy/`
   1. `appspec.yml`을 `before-deploy`로 복사
4. `cp build/libs/*.jar before-deploy/`
   1. `jar`파일들을 `before-deploy`로 복사
5. `cd before-deploy && zip -r before-deploy *`
   1. `before-deploy`로 이동 및 `before-deploy`라는 명칭으로 디렉토리내 모든 파일 zip
6. `cd ../ && mkdir -p deploy`
   1. 상위 디렉토리 이동 및 `deploy`디렉토리 생성
7. `mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip`
   1. `before-deploy`에 생성한 zip파일을 `deploy`디렉토리에 서비스 명칭으로 zip파일 명칭 변경해서 생성

<br>

**`appspec.yml` 수정**

```
permissions:
  - object: /
    pattern: "**"
    owner: ec2-user
    group: ec2-user

hooks:
  ApplicationStart:
    - location: deploy.sh
      timeout: 60
      runas: ec2-user
```

1. `permissions:`
   1. `CodeDeploy`에서 `EC2`서버로 넘겨준 파일들을 모두 `ec2-user`권한을 갖도록 설정.
2. `hooks`
   1. `CodeDeploy`배포 단계에서 실행할 명령어를 지정한다.
   2. `ApplicationStart`라는 단계에서 deploy.sh를 ec2-user 권한으로 실행한다.
   3. `timeout: 60`설정으로 인해서 스크립트 실행이 60초 이상 수행되면 실패가 된다.
      1. 무한정 기다릴순 없으므로 시간제한은 필수다.

<br>

**`CodeDeploy`를 통한 배포 로그 확인 방법**

1. `/opt/codedeploy-agent/deployment-root`에서 `9025f408-3a26-4083-862d-a0f124a5b95d` 이런 형식의 Directory의 역할
   1. 사용자의 `CodeDeploy ID` 이며, 고유한 ID로 각자 다른 ID가 발급된다.
   2. 해당 디렉토리에는 `배포를 진행한 단위별로 배포 파일들이 존재`하며, 본인의 `배포 파일을 정상적으로 수신하였는지 확인`이 가능하다
2. `/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log`
   1. `CodeDeploy`의 로그 파일이 들어가게 된다.
   2. `CodeDeploy`로 이루어지는 배포 내용 중 입 / 출력 내용이 담기게 되며 작성한 `echo`내용도 모두 표기가 된다.
