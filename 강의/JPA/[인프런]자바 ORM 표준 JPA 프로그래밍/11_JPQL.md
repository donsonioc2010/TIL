# JPQL

## JPA의 문제점

- JPA를 사용할 경우 엔티티를 중심으로 개발되지만, 문제가 검색 쿼리를 작성할 떄 이다.
- 검색 할 떄 **테이블이 아닌 엔티티 객체를 대상**으로 검색해야한다.
- 모든 DB데이터를 객체로 변환해서 검색하는 것은 불가능하다.
- 어플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색조건이 포함된 SQL이 필요하다.
  - 최소한의 데이터만을 가져와야 DB성능도 챙기고, 네트워크 비용도 아낄 수 있기 때문

## JPQL이란?

- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공한다.
- SQL과 문법이 유사하며, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN을 지원한다.
- JPQL은 결국 SQL로 변환해서 실행된다.

## JPQL과 SQL의 차이점

- JPQL : 엔티티 객체를 대상으로 쿼리를 작성한다.
- SQL : DB의 테이블을 대상으로 쿼리를 작성한다

## JPQL의 특이점

- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향쿼리
- SQL을 추상화 하였기 떄문에 특정 DB 방언에 의존하지 않는다.

## JPQL 문법

- 규칙으로는 일반 SQL문법과 동일하다
- 문법의 작성을 진행 할 때, **엔티티와 속성의 대소문자 구분**을 한다.
- JPQL의 키워드(DDL등)의 SELECT, FROM 등은 **대소문자의 구분을 하지 않는다**
- 문법 작성시 테이블 명을 조회하는게 아닌, **엔티티**명을 사용한다.
  - 정확히는 `@Entity(name={value})`의 value명칠을 사용해야 하나, 선언하지 않는 경우 Public Class명칭이 설정된다.
- **별칭은 필수**이며 **as(alias)는 상략이 가능**하다

## TypeQuery, Query

### TypeQuery

> 반환 타입이 명확할때 사용한다.

```java
TypeQuery<Member> query = em.createQuery("SELETCT m FROM Member m", Member.class);
```

### Query

> 반환타입이 명확하지 않을 때 사용한다.

```java
Query query = em.createQuery("SELETCT m.username, m.age FROM Member m");
```

### Query의 타입을 못잡는 Example

```java
//username, age의 타입이 다르기 때문에 타입의 지정이 불가능함.
Query query = em.createQuery("SELETCT m.username, m.age FROM Member m");

//username만이라는 정확한 Type이 존재
TypeQuery<String> query = em.createQuery("SELETCT m.username FROM Member m", String.class);
```

### 결과 조회

#### 복수개 반환

- **결과가 하나 이상**일 때 사용 가능하며, **리스트로 반환**한다.
  - **NPE**에 안전함.
- 결과가 **존재하지 않는 경우**에는 **빈 리스트를 반환**한다.

```java
List<Member> result = query.getResultList();
List<String> result = query.getResultList();
```

#### 단일 반환

- 결과가 **정확히 하나** 일 떄 , 단일 객체를 반환한다.
  - 결과가 없는 경우 **NoResultException**이 발생한다.
    - Spring Data JPA에서는 **Optional** 또는 **Null**로 반환한다.
      - 아....API내부에서 Try-Catch이후 Exception발생시 Null또는 Optional반환을 진행한다.
  - 결과가 복수개인 경우에는 **NonUniqueResultException**이 발생한다.

```java
Member result = query.getSingleResult();
String result = query.getSingleResult();
```

### Parameter Binding

> 위치 기반이 가능은 하나 PASS

```java
TypeQuery<Member> query = em.createQuery(
  "SELETCT m FROM Member m WHERE m.username=:username", Member.class
);
query.setParameter("username", "member1");
List<Member> result = query.getResultList();


List<Member> result = em.createQuery(
    "SELETCT m FROM Member m WHERE m.username=:username", Member.class
  )
  .setParameter("username", "member1")
  .getResultList();
```

## 프로젝션

### 프로젝션이란?

> SELECT절에 조회할 대상을 지정하는 것이다.

### 프로젝션의 대상?

> 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등의 기본 데이터 타입)

#### 엔티티 프로젝션 주의사항

> 엔티티 프로젝션으로 조회한 데이터는 영속성 컨텍스트에서 전체적으로 관리가 된다...
> 그래서 수정하게 되도 반영 된다

#### 스칼라 타입의 주의사항

> Object배열로 반환된다.

```java
//Solution 1
List result = em.createQuery(
  "SELECT m.username, m.age FROM Member m"
).getResultList();
for(Object o : result) {
  Object[] oAry = (Object[])o;
  for(Object result : oAry) {
    System.out.println("result : "+result);
  }
}

//Solution 2
List<Object[]> result = em.createQuery(
  "SELECT m.username, m.age FROM Member m"
).getResultList();
for(Object o : result) {
  Object[] oAry = (Object[])o;
  for(Object result : oAry) {
    System.out.println("result : "+result);
  }
}

// Solution 3 Dto를 통한 조회
public class MemberDTO {
  private String username;
  private int age;
  public MemberDTO(String username, int age){
    this.username = username;
    this.age = age
  }
  ...Getter Setter
}

//패키지명을 다 적어야함
List<MemberDTO> result = em.createQuery(
  "SELECT new a_package.dto.MemberDTO(m.username, m.age) FROM Member m", MemberDTO.class
).getResultList();
```

### Example

- 엔티티 프로젝션
  - SELECT **m** FROM Member m
  - SELECT **m.team** FROM Member m
    - Team이라는 Entity존재
- 임베디드 타입 프로젝션
  - SELECT **m.address** FROM Member m
    - address 값타입
- 스칼라 타입 프로젝션
  - SELECT **m.username, m.age** FROM Member m

### 번 외

- DISTINCT 를 JPQL에서 활용 가능하며, 중복제거 된다.

## 페이징

> JPA에서는 페이징을 다음 두개의 API로 추상화 함

- `setFirstResult(int startPosition)` : 조회 시작 위치 (Default : 0)
- `setMaxResults(int maxResult)` : 조회 해 올 데이터의 갯수

### example

```java
List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
  .setFirstResult(0) //0번쨰부터
  .setMaxResults(10) //10개의 데이터
  .getResultList();
```

## 조인

> 기본적으로 안의 연관관계의 값을 바로 객체로 뽑아서 조인관계의 형성이 가능하다.

### Join종류 및 Example

#### Inner Join

> Inner가 생략 가능하다

`SELECT m FROM Member m [INNER] JOIN m.team t`

#### Outer Join

> OUTER의 생략 가능

`SELECT m FROM Member m LEFT [OUTER] JOIN m.team t`
`SELECT m FROM Member m RIGHT [OUTER] JOIN m.team t`

#### 세타조인

> 막조인

`SELECT count(m) FROM Member m, Team t WHERE m.username = t.name`

### ON 절을 활용한 Join

- 조인 대상의 필터링
- 연관관계가 없는 엔티티의 외부 조인

#### 조인 대상의 필터링 Example

```
// JPQL
SELECT m, t FROM Member m LEFT JOIN m.team ON t.name='A'

// SQL
SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID = t.ID and t.name = 'A'
```

#### 연관관계가 없는 엔티티의 외부 조인 Example

> 서로 관계가 없음에도 조인을 하고싶은 경우 활용하면 좋음

```
// JPQL
SELECT m, t FROM Member m LEFT JOIN m.team ON m.username = t.name

// SQL
SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.username = t.name
```

## Sub Query

### Sub Query지원 함수

- \[NOT\] EXISTS (sub query) : 서브쿼리에 결과가 존재하는 경우 참참참
- {ALL, ANY, SOME} (sub query)
  - ALL : 모두 만족하는 경우 참참참
  - ANY, SOME : 같은 의미로, 조건을 하나라도 만족하는 경우 참
- \[NOT\] IN (sub query) : 서브쿼리의 결과 중 하나라도 같은것이 존재하는 경우 참이다.

### JPA 서브쿼리의 한계

- WHERE, HAVING절에서만 SubQuery의 사용이 가능하다.
- 하이버네이트에서는 SELECT절에서도 활용은 가능하다.
- FROM절의 서브쿼리는 JPQL에서는 불가능하다.
  - 조인으로 문제를 해결할 수 있으면 가능하지만 조인으로 해결을 못하는 경우 해결불가능
    - 쿼리를 분해해서 두번에 걸쳐서 작업을 진행
    - Application 레벨에서 데이터를 조립
    - Native SQL을 사용한다

## JPQL 타입 표현

- 문자
  - 'HELLO', 'abc'
- 숫자
  - 10L(Long), 10D(Double), 10F(Float), 10
- Boolean
  - TRUE, FALSE
- ENUM (패키지명을 다 기입해야함)
  - jpabook.MemberType.Admin
  - 파라미터 매핑을 하게 될 경우에는 패키지명 불필요
- 엔티티 타입(상속관계에서 활용한다.)
  - TYPE(m) = Member
  - 상속관계로 타입이 분리되었을 때 사용가능한 항목 (DTYPE)

## 조건문 - CASE

### 기본

```
SELECT
  CASE
    WHEN m.age <= 10 then '학생금액'
    WHEN m.age >= 60 then '경로금액'
    ELSE '일반요금'
  END
FROM Member m
```

### 단순 하드코딩

```
SELECT
  CASE t.name
    WHEN '팀A' then '인센티브 110%'
    WHEN '팀B' then '인센티브 120%'
    ELSE '인센티브 105%'
  END
FROM Team t
```

### COALESCE, NULLIF

- COALESCE : 한개씩 조회를 하며, NULL이 아닌 경우 반환한다.
- NULLIF : 두개의 값이 같은 경우 NULL을 반환하며, 다르면 첫번째 값을 반환한다.

```
// 사용자 이름이 없는 경우 '없워'를 반환한다.
SELECT COALESCE(m.username, '없워') FROM Member m

// 사용자 이름이 '관리자'인 겨우  NULL을 반환하고 나머지는 본인의 이름을 반환한다.
SELECT NULLIF(m.username, '관리자') FROM Member m
```

## 기본함수

> JPQL이 제공하는 표준함수로, DB방언에 상관없이 사용이 가능하다

- CONCAT : 문자열을 합칠 떄
  - `SELECT CONCAT('a','b') FROM Table~`
  - `SELECT 'a' || 'b' FROM Table~` (하이버네이트를 활용시 가능)
- SUBSTRING : 문자열 자를때
  - `SUBSTRING(m.username, 2, 3)`
- TRIM : 공백제거
- LOWER,UPPER
- LENGTH : 문자열 길이
- LOCATE : 문자열 위치를 찾을 때
- ABS, SQRT, MOD
- SIZE, INDEX (JPA용도)
  - SIZE : 엔티티에 연관된 컬렉션의 데이터 갯수를 반환한다.
  - INDEX : 값타입에서 `@orderColumn`을 쓸떄 같이 활용은 가능하나 사용을 추천하진 않는다

## 사용자 정의 함수

> 위의 기본함수에서 실행이 안되는 경우 또는, DB에 추가한 함수에 대해서 활용해야 하는 경우 사용한다.
> JPA는 DB에 있는 함수를 파악이 불가능 하기 떄문이다.

- 사전 작업으로 DB방언에 정의한 함수를 추가해야한다.

1. Dialect를 제작해야 하며, 내가 사용하는 DBConnection의 Dialect를 상속받는다.
2. pulbic 생성자를 생성한다.
3. registerFunction메소드를 호출해서 등록한다.
   1. 부모 클래스 열어서 registerFunction은 확인해볼것
4. 이후 기존의 dialect를 대신해 직접 제작한 dialect를 설정파일에 등록한다.

`select function('group_concat', i.name) from Item i`

## JPQL 경로 표현식

> .을 활용해 객체(엔티티)의 그래프를 탐색하는 것

### Example

```
SELECT m.username → 상태 필드
FROM Member m
  JOIN m.team t  → 단일 값 연관 필드
  JOIN m.orders o  → 컬렉션 값 연관 필드
WHERE t.name = '팀A'
```

### 상태필드

> 단순히 값을 저장하기 위한 필드

### 연관필드

> 연관관계를 위한 필드

#### 단일 값 연관 필드

> `@ManyToOne`, `@OneToOne`, 대상이 엔티티인 경우

#### 컬렉션 값 연관필드

> `@OneToMany`, `@ManyToMany`등 다수로 대상이 컬렉션인 경우

### 경로 표현식의 특징

- 상태필드
  - 경로 탐색의 끝으로 추가적인 탐색이 불가능하다
- 단일 값 연관 경로
  - 쿼리에서 내부로 들어가게 될 경우 묵시적인 내부 조인이 발생한다
  - 추가적으로 내부 경로 탐색도 가능하다.
    - 하지만 추가적인 조인이 발생한다.
- 컬렉션 값 연관 경로
  - 묵시적 내부조인이 단일 값 연관경로처럼 발생한다.
  - 추가적인 내부 경로 탐색은 불가능하다
    - FROM절에서 명시적 조인을 통해 별칭을 얻은 경우에는 별칭을 통해서 탐색이 가능하다.

#### 묵시적 내부조인 관련

> 쿼리 튜닝이 어려워진다.
