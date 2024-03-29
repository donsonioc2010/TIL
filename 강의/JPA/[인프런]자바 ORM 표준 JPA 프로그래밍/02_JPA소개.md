# JPA 소개

## Index

- [JPA 소개](#jpa-소개)
  - [Index](#index)
  - [SQL중심적인 개발의 문제점](#sql중심적인-개발의-문제점)
    - [차이점과 문제점](#차이점과-문제점)
    - [객체와 RDBMS의 차이점](#객체와-rdbms의-차이점)
      - [1. 상속](#1-상속)
      - [2. 연관관계](#2-연관관계)
    - [문제점](#문제점)
  - [JPA의 소개](#jpa의-소개)
    - [JPA 란?](#jpa-란)
    - [ORM 이란?](#orm-이란)
    - [JPA의 동작 위지](#jpa의-동작-위지)
    - [JPA의 동작 방식](#jpa의-동작-방식)
      - [저장](#저장)
      - [조회](#조회)
    - [JPA를 사용하는 이유](#jpa를-사용하는-이유)
      - [생산성의 증가](#생산성의-증가)
      - [유지보수의 편이 증가의 의미](#유지보수의-편이-증가의-의미)
      - [패러다임 불일치 해결의 의미](#패러다임-불일치-해결의-의미)
    - [지연 로딩과 즉시 로딩](#지연-로딩과-즉시-로딩)

---

## SQL중심적인 개발의 문제점

### 차이점과 문제점

> Application을 개발할 때는 보통 **객체지향언어로 개발**을 하게 되지만 DB의 경우에는 보통 **관계형 DB의 사용**이 많다.
>
> 즉 **제작한 객체들을 관계형 DB에 넣고 관리**를 하는 경우가 많은데 , 이 객체를 관계형 DB에 _삽입_ , _수정_, _조회_ , *삭제*등을 하기 위해서는 **SQL언어를 사용**해야 한다.
>
> 이게 SQL 중심적인 개발의 문제점으로 즉 **무한반복** 되고 **지루한 코드**를 작성해야한다.
>
> **Java객체를 SQL** 로, **SQL을 Java객체** 로 계속 _변경_ 을 해야하는 것이다.

> 급하게 **수정이 필요하게 되는 경우**에도 모든 **SQL문을 수정**해야 한다는 문제점 또한 있다.

### 객체와 RDBMS의 차이점

1. 상속
2. 연관관계
3. 데이터 타입
4. 데이터 식별 방법

#### 1. 상속

> RDBMS의 경우에는 상속의 관계가 없다. 하지만 비슷하게 풀어낼 수 있는데 그것은 *부모테이블과 자식테이블을 제작해서 조인*하는 방식으로, **슈퍼타입 서브타입 관계**라고 한다.

![Alt text](image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-12-23%20%EC%98%A4%ED%9B%84%201.20.38.png)

> 위와 같은 이미지가 있을 때 , **SQL중심의 개발**을 진행하게 될 경우에는 ITEM을 중심으로, ALBUM, MOVIE, BOOK을 전부 조인해서 데이터를 가져오거나, 저장을 ITEM->ALBUM, MOVIE, BOOK을 해야하는 문제가 있다. 즉 쿼리작업이 많아 복잡해 지고 이걸 개발자가 하게 되는것
>
> 반면에 객체중심적인(JPA)개발을 하게 되면 한줄로 조회, 삭제등 모든 작업이 가능하다.

#### 2. 연관관계

> 객체는 **참조**를 사용하며, 테이블은 **외래 키**를 사용한다.

```
# 객체
Exam ) member.getTeam();

# DB
JOIN ON M.ID = T.ID
```

> 모델링의 경우에도 객체를 테이블에 맞추어 모델링 하는 경우와 객체에 맞춘 모델링이 차이가 나게 되며 아래와 같다.

```java
# 테이블에 맞춘 객체 모델링 [외래키를 바탕]
class Member {
    String id;
    Long teamId; //FK
    String username;
}

class Team {
    Long id;
    String name;
}


# 객체를 기반으로 한 모델링 [참조를 바탕]
class Member {
    String id;
    Team team;
    String username;

    Team getTeam() {
        return team;
    }
}

class Team {
    Long id;
    String name;
}
```

> 객체는 객체 내부를 자유롭게 탐색을 해야 하는데 **SQL 중심의 개발**일 경우 조회범위를 개발자가 지정하기 때문에 특정 객체가 **null**인 경우가 발생 할 수 있다.  
> 하지만 **객체 중심의 개발**인 경우 객체 한번 조회에 연관 정보를 모두 가져오기 떄문에 비어있는 경우가 없다.
>
> 해당 문제에 대해서 `엔티티 신뢰 문제`라고 한다.

> **객체 중심의 개발** 인 경우 모든 객체를 다 가져오면 성능의 문제가 있을 수 있기 떄문에 메소드를 여러개 만들어서, 특정 객체만 가져오는 방식으로 보통 개발을 많이 진행하긴 한다.

> 비교를 진행하는 경우 **SQL 중심의 개발**일 경우에는 아래의 결과가 **다르게** 나온다

```java
Member member1 = memberDAO.getMember(memberId);
Member member2 = memberDAO.getMember(memberId);

member1 == member2

class MemberDAO {
    public Member gerMember(String memberId) {
        String sql="~";

        return new Member();
    }
}
```

> 하지만 **객체 중심**의 개발인 경우는 같은 MemberId를 통한 조회를 하게 될 경우 **같은** 결과가 출력된다.

### 문제점

> 즉 예시를 봤듯이 객체답게 모델링을 진행할 수록 **SQL 중심적인 개발**의 문제점은 매핑작업만 늘어나게 된다.
> 이러한 문제를 해결하기 위해 **JPA를 활용하게 된 것**

---

## JPA의 소개

### JPA 란?

> **Java Persistence API**의 약자로 **Java진영의 ORM 표준 기술**이다.

### ORM 이란?

> **Object-relational mapping (객체 관계 매핑)**의 약자로, Object는 객체이며 객체랑 Relational은 관계형 DB을 의미하며 객체랑 관계형DB를 Mapping을 해준다는 의미이다.

> **객체는 객체대로 설계**하고 **RDB는 RDB대로 설계**를 하고, ORM프레임워크가 두개를 연관해주는 기술을 의미한다.

> 대부분의 대중언어는 ORM이 존재한다..

### JPA의 동작 위지

> JPA는 **Java Application**과 **JDBC API** 사이에서 동작을 한다.
>
> 즉 기존에는 개발자가 직접 **JDBC API**를 사용했다면, **JPA**가 직접 JDBC API를 사용하는것으로 변경된것.

### JPA의 동작 방식

#### 저장

> Member를 예시로 해서 저장을 진행 할 경우를 가정한다.

1. 데이터를 MemberDAO에 제공한다.
2. MemberDAO에서는 Persist (Entity Object) 를 JPA에게 던지면저 저장요청을 한다
3. JPA는 Entity를 분석하여 Insert SQL을 생성하고 JDBC API에 쿼리문을 던져서 저장과 패러다임 불일치문제를 해결한다.

#### 조회

> 조회의 경우에도 저장과 크게 다르지는 않다.

1. MemberDAO에 찾고자 하는 Column의 값을 찾아달라 요청한다.
2. MemberDAO는 JPA에게 해당 Column과 함꼐 요청을 한다.
3. JPA가 SELECT SQL을 생성한 이후 JDBC API를 활용해 결과를 얻은 후 ResultSet 매핑을 하고 패러다임 불일치문제를 해결한다.
4. 받은 결과를 통해 MemberDAO에 분석한 Entity(Member)을 Return한다.

### JPA를 사용하는 이유

- 객체 중심적으로 개발이 가능
- 생산성 증가와 유지보수의 편이
- 패러다임 불일치문제의 해결
- 성능의 향상

#### 생산성의 증가

> 예시로 member라는 객체가 존재할 떄를 가정하면 다음과 같은 코드로 개발된다.

```java
# 저장
jpa.persist(member);

# 조회
Member member = jpa.find(memberId);

# 수정
member.setName("변경 Name");

# 삭제
jpa.remove(member);
```

> **수정**의 경우에는 조회한 **Transactional** 안에서 수정을 진행한 이후 **Transactional**이 종료될 경우에 **변경점이 존재**하면 JPA가 **수정 쿼리를** 진행 한다.

#### 유지보수의 편이 증가의 의미

> 간단한 예제로 객체의 필드 추가작업이 필요한 경우 기존 SQL중심 개발의 경우 필드만 추가하는것이 아니라 SQL전체를 수정해야 하지만, JPA는 Entity에서 필드만 추가하면 되며 SQL의 수정은 없기 떄문

#### 패러다임 불일치 해결의 의미

1. JPA와 상속
   1. 기존에는 여러개의 연관 관계가 있는 경우 각자 저장, 조회를 해야했지만, 모든걸 JPA가 해결하는 걸 의 미
2. JPA와 연관관계
3. JPA와 객체 그래프 탐색
4. JPA와 비교하기

### 지연 로딩과 즉시 로딩

> 두가지 모두 JPA는 지원한다.

- 지연 로딩 : 객체가 실제 사용 될 때 로딩
- 즉시 로딩 : JOIN SQL로 한번에 연관된 객체까지 미리 조회
