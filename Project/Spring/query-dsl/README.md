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
