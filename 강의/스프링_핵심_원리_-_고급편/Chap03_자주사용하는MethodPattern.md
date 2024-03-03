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

### Sample Code

#### Context

```java
// V1
@Slf4j
public class ContextV1 {
    private final Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {

        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        this.strategy.call();
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime >>> {}ms", resultTime);
    }
}

// V2
@Slf4j
public class ContextV2 {
    public void execute(Strategy strategy) {

        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        strategy.call();
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime >>> {}ms", resultTime);
    }
}
```

#### 전략과 전략의 구현체

```java
public interface Strategy {
    void call();
}

@Slf4j
public class StrategyLogic1 implements Strategy {
    @Override
    public void call() {
        log.info("비즈니스 로직1 실행");
    }
}
```

#### 실행의 예제

```java
void strategyV1() {
    ContextV1 context1 = new ContextV1(new StrategyLogic1());
    context1.execute();

    ContextV1 context2 = new ContextV1(new StrategyLogic2());
    context2.execute();
}

void strategyV2() {
    ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
    context1.execute();

    ContextV1 context2  = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
    context2.execute();
}


void strategyV1() {
    ContextV2 contextV2 = new ContextV2();
    contextV2.execute(new StrategyLogic1());
    contextV2.execute(new StrategyLogic2());
}

void strategyV2() {
    new ContextV2().execute(new StrategyLogic1());
    new ContextV2().execute(new StrategyLogic2());
}

void strategyV3() {
    new ContextV2().execute(() -> log.info("비즈니스 로직1 실행"));
    new ContextV2().execute(() -> log.info("비즈니스 로직2 실행"));
}
```

## Template Callback Pattern

> 템플릿 콜백 패턴

### 콜백이란?

> 프로그래밍에서 `CallBack`또는 `CallAfter Function`은 다른코드를 인수로서 넘겨주는 실행 가능한 코드를 의미한다.
> 콜백으로 넘겨받는 코드는 이 콜백을 필요에 따라 즉시 실행할 수도, 나중에 실행할 수도 있다.  
> 즉 코드가 호출은 되는데 코드를 넘겨준 곳(back)에서 실행이 된다는 의미이다.
