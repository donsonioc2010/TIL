# junit-platform properties설정

> `junit-platform.properties`의 경우에는 [Test의 Instance](./Test%EC%9D%98%20Instance.md)에서 먼저 말이 나왔었다.
>
> Instance생성 단위를 Class로 만드는 방법과 관련한 설명이었으니, 다른것과 함께 추가로 적어놓는다.

## 파일 생성 위치

> 최초에 Instance와 관련된 md를 기록할 당시에는 그냥 `main.resources`하위에 생성하면 될 줄 알았다.
> 해당 설정들은 TestCase에서 필요한 Junit의 설정이기 떄문에 `test.resources`하위에 `junit-platform.properties`파일을 생성해야 한다.

### 설정 순서

1. `test.resource`경로 생성
2. `junit-platform.properties`파일 생성
3. Intellij기준 Project Settins에서 `test.resources`경로를 `Test Resource`로 설정

## 대표적인 설정 종류들

- `junit.jupiter.testinstance.lifecycle.default=per_class`
  - Test 인스턴스 Class단위로 생성 설정
- `junit.jupiter.extensions.autodetection.enabled=true`
  - Test 모듈의 확장기능 자동 감지(detect) 설정
- `junit.jupiter.conditions.deactivate = org.junit.*DisabledCondition`
  - TestCase에 선언한 `@Disabled`를 무시하고 실행한다.
  - `DisabledOnOs`, `DisabledOnJre`를 설정하고 싶은 경우
    - `org.junit.*DisabledOnOsCondition`, `org.junit.*DisabledOnJreCondition`등으로 기록하면 된다.
    - 그냥 Disabled클래스 파일 적어주면 된다.
  - DisabledCondition의 경우 junit-jupiter-engine에, OS, JRE의 경우에는 junit-jupiter-api에 존재한다
    - 전부 클래스 파일이다..
- `junit.jupiter.displayname.generator.default = org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores`
  - 테스트 DisplayName 표기 전략 설정
  - 마찬가지로 클래스 파일 경로임
