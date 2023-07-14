# Test Containers 환경설정 기능들

## 필요한 이유?

> 항상 우리가 원하는 설정들만 사용할 수는 없다.
> 공식 페이지에서 생성되어 있지 않는 모듈을 사용해야 하는 경우 특히 더 유용하다

## 컨테이너 제작방법

> `GenericContainer` Class를 활용해서 제작이 가능하다.
>
> > `new GenericContainer(String dockerImageName)`으로 Container 설정을 한다.

```java
@SpringBootTest
@ActiveProfiles("test")
@TestContainers
class Test {
  @Container
  static GenericContainer postgreSQLContainer = new GenericContainer("postgres");
}
```

### 직접 컨테이너 제작에 따른 단점

1. Docker Image가 Public으로 공개되지 않아 있는 경우 난감해질 수 있다.
2. 특화된 Method들을 사용하지 못한다.
   1. 예를 들어, DB의 경우 DB의 `.withDatabaseName()`이라든가 `.withPassword()`등의 메소드를 말한다.
3. 환경 설정을 `.withEnv(String key, String value)`로 설정해야 한다.

## 환경 설정 방법

> `.withEnv(String key, String value)`메소드를 통해서 진행한다.

```java
@Container
static GenericContainer postgreSQLContainer = new GenericContainer("postgres").withEnv("POSTGRES_DB", "studytest");
```

## 네트워크 설정

> 일반적으로 Docker는 `HostPort:ContainerPort`설정을 잡는다면, Test Container의 경우 Port의 지정을 막아놓고, 임의의 Random Port를 매핑하도록 설정이 되어있다.

### withExposedPorts

> `withExposedPorts`는 컨테이너가 사용할 `내부 포트`를 설정하며, 기본값으로 각 모듈의 `기본 포트를 사용한다`

### getMappedPort

> Container의 내부포트 값을 넣으면, 호스트 영역에서 설정된 Port를 가져온다

### Sample Code

```java
@Container
static GenericContainer postgreSQLContainer = new GenericContainer("postgres").withExposedPorts(5432);


void getMappedTest() {
  postgreSQLContainer.getMappedPort(5432); //Container 내부 포트
}
```
