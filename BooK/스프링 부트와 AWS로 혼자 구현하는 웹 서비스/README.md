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
