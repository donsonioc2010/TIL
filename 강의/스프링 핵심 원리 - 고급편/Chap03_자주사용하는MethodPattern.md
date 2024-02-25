# Chap03 - 자주 사용하는 Method Pattern

## Template Method Pattern

> 템플릿 메소드 패턴,  
> 변하는 부분과 변하지 않는 부분을 분리하여, 변하지 않는 부분에 변하는 부분의 코드를 불러와서 실행하는 패턴이다.

### 템플릿 메소드 패턴이란

> 템플릿 메서드 패턴은 이름 그대로 템플릿을 사용하는 방식이다.
> 템플릿의 의미란 기준이 되는 거대한 틀이다. 이 틀에 변하지 않는 부분을 몰아 넣고, 변하는 일부만 호출하여 해결하는 방식이다.

### 템플릿 메소드 패턴의 장점

> 중구난방으로 모든 로직에다가 공통된 코드를 작성할 떄는 각자의 개별 로직부분에서 직접 정의를 하다보니, 공통된 코드여야 하는 부분이 잘못 작성 된다든가.작성을 누락한다든가 등의 휴면 에려가 발생하거나, 수정이 일어나야 하는 경우 수정점이 많아 진다는 단점이 존재한다.
> 그러나 템플릿 메소드 패턴을 적용하게 될 경우 단일책임원칙을 지키게 되다 보니, 공통된 로직부분에 대해서 수정이 필요하게 될 경우 메소드 패턴에 구현된 실행부만 수정을 하면 된다는 장점이 생긴것

### SampleCode

```java
// Template
@Slf4j
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        this.call();
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime >>> {}ms", resultTime);
    }

    protected abstract void call();
}

public class Logic1 extends AbstractTemplate {
  protected void call() {
    log.info("비즈니스 로직 1 실행")
  }
}

public class Logic2 extends AbstractTemplate {
  protected void call() {
    log.info("비즈니스 로직 2 실행")
  }
}
```

## Strategy Pattern

> 전략 패턴

### 전략패턴 이란?

> 템플릿 메서드 패턴은 부모 클래스에서 변하지 않는 템플릿을 두고, 변하는 부분을 자식 클래스에 두어서 `상속`을 **사용해 문제를 해결하는 방법**이었다. 그렇게 될 경우 부모클래스에 강한 결합이 발생하게 된다.
> 전략 패턴은 변하지 않는 부분을 Context에 두고, 변하는 부분을 Strategy라는 인터페이스를 제작한뒤, 해당 인터페이스를 구현하여 문제를 해결한다.
> `상속`이 아닌`위임`으로 문제를 해결하는 것이다. 전략패턴에서 Context는 변하지 않는 템플릿 역할을 하고, Strategy인터페이스는 변하는 알고리즘 역할을 하게 된다.

(이름은 다르게 줘도 상관없다...)

## Template Callback Pattern

> 템플릿 콜백 패턴
