# CM4SB Install

## Docs

- https://codecentric.github.io/chaos-monkey-spring-boot/latest/

## Install 작업

### Dependencies 추가

> Version은 변경 가능

- `implementation 'de.codecentric:chaos-monkey-spring-boot:3.0.1'`
  - Spring Boot에 ChaosMonkey를 추가하는 의존성
- `implementation group: 'org.springframework.boot', name: 'spring-boot-actuator', version: '3.0.1'`
  - 액추에이터는 실행 중인 애플리케이션의 내부를 볼 수 있게 하고, 어느 정도까지는 애플리케이션의 작동 방법을 제어할 수 있게 한다

### application.yml 추가

- `spring.profiles.active=chaos-monkey`
  - `spring.profiles.active=dev, local`등의 그 `spring.profiles.active`가 맞음
- `management.endpoint.chaosmonkey.enabled=true`
- `management.endpoints.web.exposure.include=health,info,chaosmonkey`
