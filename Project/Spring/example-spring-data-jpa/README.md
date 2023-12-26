# README
> [!NOTE]   
> 인프런의 - [실전! 스프링 데이터 JPA](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84/dashboard) 강의를 수강하면서 들은 내용을 정리한 프로젝트

## Gradle 전체 의존관계 확인 하고 싶은 경우
- `./gradlew dependencies --configuration compileClasspath`

## 강의의 인터페이스 생성방식
- EntityManager를 주입받아 순수한 JPA기반 Repository를 생성
- Spring Data JPA에 대한 소개
- Spring Data JPA 공통 인터페이스 활용 및 생성

## 기록
- NamedQuery는 거의 사용하지 않는다.