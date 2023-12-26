# README
> [!NOTE]   
> 인프런의 - [실전! 스프링 데이터 JPA](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84/dashboard) 강의를 수강하면서 들은 내용을 정리한 프로젝트

## Gradle 전체 의존관계 확인 하고 싶은 경우
- `./gradlew dependencies --configuration compileClasspath`

## 강의의 인터페이스 생성방식
- EntityManager를 주입받아 순수한 JPA기반 Repository를 생성
- Spring Data JPA에 대한 소개
- Spring Data JPA 공통 인터페이스 활용 및 생성

## 반환타입
- List인 경우에는 Empty List를 반환한다.
- Optional인 경우에는 Optional.empty를 반환한다.
- 단건 조회인 경우에는 JPA는 Exception이 발생하나, Spring Data JPA는 결과가 없으면 null을 반환한다.
  - 그래서 단건은 Optional을 써라

### 단건 반환 주의사항
- 단건인 경우에는 반환이 가능하나, 여러건인 경우에는 반환할 수 없다.
  - 단건에서 여러건이 발생시 NotUnique Exception이 발생한다.

## 페이징
- `org.springframework.data.domain.Pageable`를 사용한다. 페이징 기능에 활용하며, 내부에 `Sort`가 포함되어있다.
- `org.springframework.data.domain.Sort`를 사용한다. 정렬 기능에 활용한다.

### 특별한 반환타입
- `org.springframework.data.domain.Page` 추가 count쿼리 결과를 포함하는 페이징
- `org.springframework.data.domain.Slice` 추가 count쿼리 없이 다음 페이지만 확인 가능한 페이징
- `List`(자바 컬렉션) 추가 count쿼리 없이 결과만 반환

### Pageable객체 생성시 주의사항
- Page가 0부터 시작한다.

## 기록
- NamedQuery는 거의 사용하지 않는다.
