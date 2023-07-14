# Spring에서 Container 정보 참조하는 방법

> Test Container를 그냥 실행하게 될경우 Spring Test를 진행할 때 container정보를 모른채, 또는 환경설정이 없이 실행을 하게 될 경우도 있다.
> 하지만 Container의 정보들이 필요한 경우 Spring의 기능들을 활용해서 Context에 해당 정보들을 설정하는 방법이 존재한다.

## 적용방법

- [Spring에서 Container 정보 참조하는 방법](#spring에서-container-정보-참조하는-방법)
  - [적용방법](#적용방법)
    - [Test Container 생성](#test-container-생성)
    - [`ApplicationContextInitializer`구현체 생성](#applicationcontextinitializer구현체-생성)
    - [`@ContextConfiguration`을 통한 구현체 지정](#contextconfiguration을-통한-구현체-지정)
    - [설정값 사용법](#설정값-사용법)
      - [`Environment`를 활용한 방법](#environment를-활용한-방법)
      - [`@Value`를 활용한 방법](#value를-활용한-방법)

### Test Container 생성

```java
@Container
static GenericContainer postgreSQLContainer = new GenericContainer("postgres")
        .withExposedPorts(5432)
        .withEnv("POSTGRES_DB", "sampleTest");
```

### `ApplicationContextInitializer`구현체 생성

> 해당 구현체는 `Container`를 생성한 Test Class에 생성한다.

```java
static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432))
                .applyTo(context.getEnvironment());
    }
}
```

### `@ContextConfiguration`을 통한 구현체 지정

```java
@ContextConfiguration(initializers = SampleTest.ContainerPropertyInitializer.class)
class SampleTest {
}
```

### 설정값 사용법

#### `Environment`를 활용한 방법

```java
@Autowired Environment environment;
void test() {
  System.out.println(environment.getProperty("container.port"));
}
```

#### `@Value`를 활용한 방법

> `@Value("${container.port}") int port;`를 통해 주입받아 port 변수 활용
