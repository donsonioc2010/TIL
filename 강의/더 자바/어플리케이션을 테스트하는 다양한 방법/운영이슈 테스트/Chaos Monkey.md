# Chaos Monkey

## Chaos Engineering

> 운영환경에서 자주 발생하진 않지 않고, 간간히 발생하지만 발생시 문제가 크거나 복구가 매우 힘든 문제들을 미리 확인해 볼 수 있는 방법

### 불확실한 예시들

- 네트워크의 지연
- 서버 장애
- 디스크 오작동
- 메모리 누수
- 그 외?

## Chaos Monkey란

> Chaos Engineering을 테스트 해볼 수 있는 대표적인 Tool중 하나로...`Netflix`제작

### Chaos Monkey For Spring Boot Reference

- https://codecentric.github.io/chaos-monkey-spring-boot/
- https://www.baeldung.com/spring-boot-chaos-monkey

## Chaos Monkey For SpringBoot 주요 개념

### 공격 대상 종류 (Watcher)

> 다음의 어노테이션이 달린 Class의 `public` 메소드들한테 공격을 가해 볼 수 있다.

- `@RestController`
- `@Controller`
- `@Service`
- `@Repository`
- `@Component`

### 공격 유형 (Assaults)

- 응답 지연 (Latency Assault)
- 예외 발생 (Exception Assault)
- 어플리케이션 종료 (AppKiller Assault)
- 메모리 누수 (Memory Assault)
