# README

## Actuator

### 엔드포인트 설정

> 엔드포인트를 사용시에는 2가지 과정이 필요하다
>
> > 1. 엔드포인트 활성화
> > 2. 엔드포인트 노출

> [!NOTE] > **엔드포인트를 활성화** 한다는 것은 해당 기능 자체를 사용할지 말지 `on`, `off` 하는것을 의미한다.
> **엔드포인트를 노출**한다는 것을 활성화된 엔드포인트를 HTTP에 노출할지 JMX에 노출할지 선택하는 것.
> 엔드포인트를 활성화하고 추가로 HTTP를 통해서 웹에 노출할지, JMX를 통해서 노출할지 두위치 모두 노출할지 지정해 줘야한다.

### 자주 사용하는 엔드포인트의 목록

> [!NOTE] > `/actuator`는 전제츨 확인가능하다
> `/actuator/{아래의 엔드포인트 목록}` 을 입력시 해당 항목만 확인이 가능하다.

- `beans` : 스프링 컨테이너에 등록된 스프링 빈을 보여준다.
- `conditions` : `condition` 을 통해서 빈을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유를 표시한다.
- `configprops` : `@ConfigurationProperties` 를 보여준다.
- `env` : `Environment` 정보를 보여준다.
- `health` : 애플리케이션 헬스 정보를 보여준다.
- `httpexchanges` : HTTP 호출 응답 정보를 보여준다. `HttpExchangeRepository` 를 구현한 빈을 별도로 등록해야 한다.
- `info` : 애플리케이션 정보를 보여준다.
- `loggers` : 애플리케이션 로거 설정을 보여주고 변경도 할 수 있다.
- `metrics` : 애플리케이션의 메트릭 정보를 보여준다.
- `mappings` : `@RequestMapping` 정보를 보여준다.
- `threaddump` : 쓰레드 덤프를 실행해서 보여준다.
- `shutdown` : 애플리케이션을 종료한다. 이 기능은 **기본으로 비활성화** 되어 있다.

> [!NOTE]
>
> **전체 엔드포인트는 다음 공식 메뉴얼을 참고하자.**  
> https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints

### 헬스정보

> [!NOTE]  
> 헬스 정보를 사용하면 어플리케이션에 문제가 발생시 문제를 빠르게 인지가 가능하며 엔드포인트는 `/actuator/health`이다.
>
> 헬스정보는 단순히 어플리케이션이 요청에 응답할 수 있는지를 판단하는 것을 넘어 어플리케이션이 사용하는 DB가 응답중인디, 디스크 사용량에 문제는 없는지 등 다양한 정보를 함께 포함해서 만들어진다.

#### 헬스 설정

> [!NOTE]
> 엑츄에이터는 `db`, `mongo`, `redis`, `diskspace`, `ping`과 같이 수많은 헬스 기능이 기본 제공되며,하나라도 Down인경우 종합 Status는 Down으로 표기된다.

- 공식 메뉴얼 :
  - https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.health.auto-configured-health-indicators
- 커스텀 헬스정보 추가 메뉴얼 :
  - https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.health.writing-custom-health-indicators

```yaml
management:
  endpoint:
    shutdown:
    health:
      #   헬스정보를 세부적으로 확인하고 싶은 경우
      show-details: always
      # 헬스정보를 컴포넌트별로 상태만 확인하고 싶은 경우
      show-components: always
```

### 어플리케이션 정보

> [!NOTE]
>
> `info` 엔드포인트는 어플리케이션의 기본 정보를 노출하며, `/actuator/info`가 엔드포인트 이다.

- 기본제공 기능
  - `java`: 자바 런타임정보
  - `os` : OS 정보
  - `env` : `Environment` 에서 `info.` 로 시작하는 정보
  - `build` : 빌드 정보, `META-INF/build-info.properties` 파일이 필요하다.
  - `git` : `git` 정보, `git.properties` 파일이 필요하다.
- `env` , `java` , `os` 는 기본으로 비활성화 되어 있다.

#### `java`, `os`활성화 설정

```yaml
management:
  info:
    java:
      enabled: true
    os:
      enabled: true
```

#### `env`설정

다음과 같이 설정하면, info로 들어가게 될 경우, app하위의 정보를 확인가능하다

```yaml
management:
  info:
    env:
      enabled: true
info:
  app:
    name: hello-actuator
    company: yh
```

#### Build정보 설정

> [!NOTE]  
> 빌드정보를 확인하고 싶은경우, `build.gradle`에 아래의 작업을 추가만 하면 된다.
> 이렇게 하고 빌드를 해보면 `build` 폴더안에 `resources/main/META-INF/build-info.properties` 파일 을 확인할 수 있다.

```groovy

springBoot{
    buildInfo()
}

```

#### git설정

> [!WARNING]
> 먼저 우선순위는 깃이 프로젝트로 관리가 되야한다.

> [!NOTE]  
> 깃 확인하고 싶은경우, `build.gradle`에 플러그인을 추가하면 끝이다.

```groovy
plugins {
    id "com.gorylenko.gradle-git-properties" version "2.4.1" //git info
}
```

세부적인 항목을 더 추가로 보고싶은경우 아래의 설정을 추가하면된다.

```yaml
management:
  info:
    git:
      mode: full
```

### 로거설정

> Logger의 엔드포인트는 `/actuator/loggers`이며, 각각의 패키지들이 어떠한 로그레벨을 가지고 있는지를 확인할 때 사용하며, 모든 엔드포인트를 개방해 두었다면 따로 설정할 부분은 없다.

#### 서버 운영중 로그레벨변경이 필요한 경우

> `jong1.controller` 패키지경로가 존재한다고 할 경우를 가정시
> 아래처럼 Request를 발송하게 될 경우 운영상황이 유지가 되면서 로거레벨이`DEBUG`레벨로 변경이 된다

```curl
POST {Host}/actuator/loggers/jong1.controller

JSON
{
  "configuredLevel" : "DEBUG"
}
```

### HTTP 요청 응답 기록(HttpExcanges)

> [!NOTE]
> HTTP 요청과 응답의 과거 기록을 확인하고 싶은 경우에는 `httpexchanges`엔드포인트를 활용하면 된다.

#### 설정방법

> [!NOTE]  
> `httpexchanges`는 인터페이스 구현체를 빈으로 등록을하여 엔드포인트를 사용할 수 있으며, 빈을 등록하지 않는 경우 엔드포인트가 활성화가 되지않는다.
>
> > 스프링 부트는 기본으로 `InMemoryHttpExchangeRepository`구현체를 제공한다.

```java
// 다음과 같이 빈을 추가해야한다.
@Bean
public InMemoryHttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
}
```

### Actuator의 보안

#### Actuator의 포트번호 변경방법

```yaml
management:
  server:
    port: 9999
```

#### Actuator의 엔드포인트 변경법

> `/actuator/{}`에서 `/manage/{}`로 변경된다.

```yaml
management:
  endpoints:
    web:
      base-path: "/manage"
```

#### 포트를 변경하지 못하는 상황

> [!NOTE]
> Actuatordml URL경로에 인증을 추가한다.
> `/actuator`패스에 서블릿 필터, 인터셉터, 시큐리티등을 설정하고 인증된 사용자만 접근하도록 추가개발을 해야 함.
