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

#### 헬스정보

> [!NOTE]  
> 헬스 정보를 사용하면 어플리케이션에 문제가 발생시 문제를 빠르게 인지가 가능하며 엔드포인트는 `/actuator/health`이다.
>
> 헬스정보는 단순히 어플리케이션이 요청에 응답할 수 있는지를 판단하는 것을 넘어 어플리케이션이 사용하는 DB가 응답중인디, 디스크 사용량에 문제는 없는지 등 다양한 정보를 함께 포함해서 만들어진다.

##### 헬스 설정

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
