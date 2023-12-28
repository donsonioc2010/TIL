# README

> [!NOTE]
> 인프런의 - [실전! Querydsl](https://www.inflearn.com/course/querydsl-%EC%8B%A4%EC%A0%84/dashboard) 강의를 듣고 정리한 내용입니다.

## JPA Query Factory

- Bean또는 생성자에서만 등록한다.

## where절 지원 Method

이외에도 있을 수 있는데 그냥 찍어봐라

| 메소드                     | 설명                                                |
|-------------------------|---------------------------------------------------|
| eq(Object)              | equal                                             |
| ne(Object)              | not equal                                         |
| eq(Object).not()        | not Equal                                         |
| isNull()                | is null                                           |
| isNotNull()             | is not null                                       |
| in(Object...)           | in                                                |
| notIn(Object...)        | not in                                            |
| between(Object, Object) | between                                           |
| goe(Object)             | greater than or equal (>=)                        |
| gt(Object)              | greater than (>)                                  |
| loe(Object)             | less than or equal (<=)                           |
| lt(Object)              | less than (<)                                     |
| like(String)            | like (example : "member%", "%member", "%member%") |
| contains(String)        | like %value%                                      |
| startsWith(String)      | like value%                                       |

## 결과 조회

- `fetch()` 리스트를 조회하며, 데이터가 없는 경우 빈 리스트를 반환한다.
- `fetchOne()` 단건을 조회한다.
    - 데이터가 없는 경우 `null`을 반환한다.
    - 데이터가 둘 이상인 경우 `com.querydsl.core.NonUniqueResultException` 예외가 발생한다.
- `fetchFirst()` 단건을 조회한다, `limit(1).fetchOne()`과 동일하다.
    - 데이터가 없는 경우 `null`을 반환한다.
- `fetchResults()` 페이징 정보를 포함한 `QueryResults`를 반환한다.
    - `Deprecated` 되었다.
    - [Deprecated의 이유](https://velog.io/@nestour95/QueryDsl-fetchResults%EA%B0%80-deprecated-%EB%90%9C-%EC%9D%B4%EC%9C%A0)
- `fetchCount()` count 쿼리로 변경해서 count 수를 조회한다.

## Join

### 기본 조인

> Join의 기본 문법은 `join(조인 대상, 별칭)`로 첫번째 파라미터에 조인 대상을 지정, 두번째 파라미터에 별칭으로 사용할 Q타입을 지정한다.

## 서브쿼리

- `com.querydsl.jpa.JPAExpressions`를 사용한다.

### SubQuery의 한계점

- `from`절의 서브쿼리는 지원하지 않는다.
- 하이버네이트의 경우 6.1부터 지원했으며, 그전버전은 지원하지 않음.
- Querydsl에서는 애초애 하이버네이트에서 지원하지 않아 미구현임

#### 해결방안

- 서브쿼리를 join으로 변경하는 것이 가능하다면 변경하는 것이 좋다.
- 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
- nativeSQL을 사용한다.

## 프로젝션
