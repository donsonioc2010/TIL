# README

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