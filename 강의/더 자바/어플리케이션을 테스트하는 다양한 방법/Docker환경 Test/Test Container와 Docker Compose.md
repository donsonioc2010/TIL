# Test Container와 Docker Compose

## Docker Compose란?

> Docker Container를 여러개띄워서 사용해야 하는 경우 해당 컨테이너들의 관계, 네트워크, Volume설정, 컨테이너 부트 순서등의 컨테이너 환경을 관리하는 도구

### docker-compose.yml path

> `root`경로의 docker-compose.yml을 지정이 가능은 하지만 권장하는것은 `src/test/resources/docker-compose.yml`로 생성을 권장.

> compose파일의 환경별 구분이 가능하기 떄문에

### Container객체 생성방법

> `Test` Class에서 직접 생성한다.

```java
@Container
static DockerComposeContainer composeContainer =
        new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));
```

### 주의사항

> 컨테이너가 많은 경우 아래처럼 `wait`를 사용하는 설정을 만들기를 권장하며, 그 이유로는 아직 컨테이너가 뜨지도 않은 상태에서 테스트가 이루어 질 수 있는 상황을 방지 할 수 있기 떄문

```java
@Container
public static DockerComposeContainer environment =
    new DockerComposeContainer(new File("src/test/resources/compose-test.yml"))
            .withExposedService("redis_1", REDIS_PORT, Wait.forListeningPort())
            .withExposedService("elasticsearch_1", ELASTICSEARCH_PORT,
                Wait.forHttp("/all")
                    .forStatusCode(200)
                    .forStatusCode(401)
                    .usingTls());
```

### docs

- https://java.testcontainers.org/modules/docker_compose/
