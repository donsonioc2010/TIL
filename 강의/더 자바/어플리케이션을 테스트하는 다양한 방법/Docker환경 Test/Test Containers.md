# Test Containers

## 내가 알던 개발방법들의 문제

### H2를 Local, Test용 Database로 선택하는 경우

- `Isolation Level`이라든가하는 Transactional Rule이 결국 DB에서 가져오게 되는건데, 아무리 비슷해도 비슷할 뿐이지, 같지는 않다.
- `DB Vendor`가 다름으로 인해 발생하는 문제를 놓치게 될수 있다.

### Docker만 활용하게 되는 경우

- Docker Container를 까먹고 실행하지 않으면 Test또는 Local 실행이 불가능하다

## 불편함을 해결하는 방법의 Test Containers

### 사용이유

> 개발방법들의 문제들을 한번에 처리가 가능하다.
> Docker Container를 직접 개발자가 관리할 필요가 없지만 실제 해당 DB의 컨테이너를 실행해 테스트를 돌리고 삭제를 하고, Test용 Application.yml을 추가로 만드는 등의 작업을 안해도 되기 때문.

### 주의사항

> 사용하는 환경의 모듈들을 설치해야하는 복잡함이 존재함.

## 사용 방법

1. [Test Containers Dependencies 추가](#test-containes-dependencies-추가)
2. [사용해야 할 서비스 모듈 추가](#사용해야-할-서비스-모듈-추가)

### Test Containes Dependencies 추가

> 지속적인 버전 증가가 발생할 수 있기 떄문에, 링크로 대체한다

- https://java.testcontainers.org/test_framework_integration/junit_5/
- https://java.testcontainers.org/

#### 의존성을 추가한 이후 가능한 작업

- `@Testcontainers`
  - JUnit5확장팩으로 테스트 클래스에`@Container`를 사용한 필드를 찾아서 컨테이너 라이프사이클 관련 메소드를 실행하는 역할을 한다.
- `@Container`
  - 인스턴스 필드에 사용하면 모든 테스트 마다 컨테이너를 재시작 한다
  - 스태틱 필드에 사용하면 클래스 내부 모든 테스트에서 동일한 컨테이너를 재사용 한다.

### 사용해야 할 서비스 모듈 추가

> 공식 페이지 - modules에서 해당하는 module을 직접 추가해야한다.

- https://www.testcontainers.org/

#### 내가 자주 사용할 모듈 항목

- [Docker Compose Module](https://java.testcontainers.org/modules/docker_compose/)
- [NGINX Module](https://java.testcontainers.org/modules/nginx/)
- [Kafka Module](https://java.testcontainers.org/modules/kafka/)
- [RabbitMQ Module](https://java.testcontainers.org/modules/rabbitmq/)
- [MariaDB Module](https://java.testcontainers.org/modules/databases/mariadb/)
- [MySQL Module](https://java.testcontainers.org/modules/databases/mysql/)
- [MongoDB Module](https://java.testcontainers.org/modules/databases/mongodb/)

## `@Container`설정 주의

### 주의점

> [어노테이션 없는 설정 예제](#어노테이션이-없는-설정-예제)처럼 작성해도 테스트 자체의 실행은 가능하다. 하지만 문제점은 다음과 같다. 해당 문제점을 인지하고 설정하자

- Port가 Random하게 변한다.
- application.yml (Test Resource yml)을 따라간다.

### 올바른 설정 방법(DB)

> 각 Database의 종류에 맞게 Sample Path가 Docs에 존재한다. 해당 부분 확인..!
>
> > `HostName`과 `Port`의 경우에는 무시를 해도 된다고 한다. 해당 값을 그대로 둬도, 임의의 값으로 설정을 할 수도 있다고 한다.
> > `jdbc:mysql:5.7.34://localhost:3306/databasenam` `jdbc:mysql:5.7.34:///databasename` 두개는 모두 TestContainers의 관점에서 동일한 URI이기 때문에 불필요 하다고 한다.

#### Docs

- https://java.testcontainers.org/modules/databases/jdbc/

### 어노테이션이 없는 설정 예제

```java
@SpringBootTest
@ActiveProfiles("test")
class Test {

  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
  }
  @AfterAll
  static void afterAll() {
    postgreSQLContainer.stop();
  }
}
```

### 올바른 설정 예제

#### TestResource application.yml

```yml
spring.datasource.url=jdbc:tc:postgresql:///sampletest
spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
```

#### Java Code

> application.yml을 설정한 이후에는 다음과 같이 주입이 가능하다.
> 어노테이션을 사용하지 않는 경우에 말이다.

```java
@SpringBootTest
@ActiveProfiles("test")
class Test {
  static PostgreSQLContainer postgreSQLContainer =
          new PostgreSQLContainer().withDatabaseName("sampletest");

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
  }
  @AfterAll
  static void afterAll() {
    postgreSQLContainer.stop();
  }
}
```

#### Use Annotation Sample Code

> 위의 코드가 아래처럼 바뀌게 되며, beforeEach의 경우 없어도 상관은 없으나, static선언시 같은 Instance를 공유하기 때문에 Data가 남아있게 된다.  
> 매 테스트 마다 데이터를 없애주기 위해서 beforeEach를 선언해 사용하면 유용하다

```java
@SpringBootTest
@ActiveProfiles("test")
@TestContainers
class Test {
  @Container
  static PostgreSQLContainer postgreSQLContainer =
          new PostgreSQLContainer().withDatabaseName("sampletest");

  @BeforeEach
  void beforeEach() {
      studyRepository.deleteAll();
  }
}
```
