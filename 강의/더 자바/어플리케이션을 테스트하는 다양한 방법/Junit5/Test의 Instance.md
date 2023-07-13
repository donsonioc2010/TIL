# TestClass Instance

## JUnit5 이전

> 각 TestCase별로 간섭이 불가능했다. 이유는 각 테스트 케이스들은 `서로 다른 인스턴스에서 실행`을 했기 때문이며 테스트 인스턴스의 생성 기본 단위는 `메소드`이기 때문이다.
> 해당 설정은 현재 JUnit5에서도 Default설정이다.

### 단점?

> 각각의 테스트 케이스마다 `인스턴스를 생성`하기 떄문에 성능적인 부분에서 손해가 있을 수 있다.

## JUnit5의 Instance 설정 변경방법?

> 방법은 크게 두가지로 Annotation, Properties를 통한 변경이 있다.

### 어노테이션을 사용한 설정

> Class의 상위에 `@TestInstance(TestInstance.LifeCycle.Type)`을 제공해야 한다.

> Enum타입은 2종류밖에 없다.

- `PER_CLASS` : 테스트 인스턴스의 기본 생성 단위를 CLASS로 설정
- `PER_METHOD` : 테스트 인스턴스의 기본 생성 단위를 Method로 설정

```java
public @interface TestInstance {
    Lifecycle value();
    public static enum Lifecycle {
        PER_CLASS,
        PER_METHOD;

        private Lifecycle() {
        }
    }
}
```

### Properties파일을 통한 설정 변경

> 해당 방법은 테스트 클래스 전체의 인스턴스를 `PER_CLASS`로 변경하고 싶은 경우 활용한다.

1. `resources/junit-platform.properties` 파일 생성
2. `junit.jupiter.testinstance.lifecycle.default=per_class` 설정 선언

## 테스트 인스턴스의 단위를 변경할 경우 이점

- 메소드케이스 마다 클래스 인스턴스를 생성하지 않아 약간의 성능 이점을 가질 수 있다.
- 메소드간에 영향이 있어야 하는 경우, 테스트 케이스간에 결과영향을 줄 수 있게 되서 단위테스트를 하는데 이점이 생긴다.
