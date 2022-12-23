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
