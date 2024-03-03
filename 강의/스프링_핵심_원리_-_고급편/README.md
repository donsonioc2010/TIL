# README

> 기억해야 할 내용들 기록기록

## Reflection

### 종류

> Spring에서 편히 사용가능한 Reflection

- JDK의 Reflection
- CGLib

### JDK의 Reflection

> `Interface`에만 적용이 가능하다.

### CGLib

> `Interface`, `Class` 두개 모두 적용이 가능하다

## Reflection을 쉽게 구현하는 방법

> `org.aopalliance.intercept.MethodInterceptor`를 `implements`받아서 구체로 생성후 아래의 코드처럼 객체를 생성하면 된다.

```java
ServiceInterface target = new ServiceImpl();
ProxyFactory proxyFactory = new ProxyFactory(target);
proxyFactory.setProxyTargetClass(true); //매번 CGLib를 사용해서 프록시 객체를 생성하게 하는 옵션
proxyFactory.addAdvice(new TimeAdvice());
ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
```

## 포인트컷, 어드바이스, 어드바이저

- **포인트 컷**`(Pointcut)`
  - 어디에 부가 기능을 적용할지, 어디에 부가기능을 적용하지 않을 지 판단하는 필터링 로직으로, 주로 클래스와 메서드 이름으로 필터링 한다.
  - 이름 그대로 어떤 포인트(Point)에 기능을 적용할지 말지를 잘라서(Cut) 구분하는 것
- **어드바이스**`(Advice)`
  - 프록시가 호출하는 부가 기능으로, 단순하게 프록시가 실행해야 할 로직이라 생각하면 된다.
  - 어디에 로직을 적용해야 하는지 모른다. 포인트컷으로 알려줘야한다.
- **어드바이저**`(Advisor)`
  - 단순하게 하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것으로, 포인트 컷 1 + 어드바이스 1의 구조라 판단하면 된다.
  - 이미 어디에 어드바이스를 적용해야 하는지를 알고 있다.

### 포인트 컷

#### 포인트 컷 동작 방식

> 개발자가 직접 **제작한 포인트 컷을 적용하고자 하려는 경우**에는, *Spring의 Pointcut을 상속받아 직접 구현*해야 한다.  
> **매번 적용을 하고자 하는 경우에는 Pointcut.TRUE를 사용**하면 되나,
> 아닌 경우에는 아래의 Spring의 PointCut인터페이스에서 `getClassFilter`와 `getMethodMatcher` 두개의 메소드를 구현한 이후, 적용대상은 두개다 True를 줘야하며, 한개라도 False가 발생하는 경우 적용대상이 아니게 된다.

#### Spring 인터페이스 코드

```java
public interface Pointcut {
    Pointcut TRUE = TruePointcut.INSTANCE;

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
```

#### Spring이 구현해놓은 대표적인 Pointcut 종류들

- `NameMatchMethodPointcut`
  - 메서드 이름을 기반으로 매칭하며, 내부에서 `PatternMatchUtils`를 사용하기 떄문에 `save*`, `*xxx*`등 을 허용한다.
- `JDKRegexpMethodPointcut`
  - JDK의 정규 표현식 기반으로 포인트컷을 매칭한다.
- `TruePointcut`
  - 항상 참인 포인트컷
- `AnnotationMatchingPointcut`
  - 어노테이션을 기반으로 매칭한다.
- `AspectJExpressionPointcut`
  - `aspectJ`표현식을 바탕으로 매칭한다.

> [!NOTE]  
> 가장 많은 활용을 하는것은 `AspectJExpressionPointcut`으로, 사용하기 편하면서도 기능이 가장 많아 주로 사용을 하게 된다.

## 빈 후처리기 - BeanPostProcessor

> [!NOTE]  
> `@Bean`이나 `컴포넌트 스캔`으로 등록된 스프링 빈을 등록하면 스프링은 대상 객체를 생성하고, 내부의 빈 저장소에 빈을 등록한다.
> 이후 개발자들은 이 등록된 빈을 조회해서 주입받아 사용을 하게 된다.
> **빈 후처리기**는 `빈 저장소에 등록할 목적으로 생성된 객체`를 `빈 저장소에 등록하기 직전에 조작을 하고서 등록을 할 수 있는 기능`이다.

### 빈 후처리기의 실행 과정

1. 컴포넌트스캔에 발견된 모든 객체를 생성한다.
2. 생성된 객체를 빈 저장소에 등록하기 전에 빈 후처리기에 전달이 된다.
3. 빈 후처리기는 전달된 스프링 빈 객체들을 작성된 로직에 따라 기존의 빈을 등록할 수도, 조작하거나, 다른 객체로 바꿔치기를 할 수도 있다.
4. 빈후처리기를 통해 조작된 빈 객체들을 반환하며, 전달받은 빈은 빈 저장소에 등록된다.

### 빈 후처리기의 사용법

> `BeanPostProcessor`인터페이스를 구현하고, 스프링 빈으로 등록만 하면 된다.

- `postProcessBeforeInitialization`
  - 객체가 생성된 이후에 `@PostConstruct`같은 초기화가 발생하기 전에 호출되는 포스트 프로세서다.
- `postProcessAfterInitialization`
  - 객체가 생성된 이후에 `@PostCOnstruct`같은 초기화가 발생한 다음에 호출되는 포스트 프로세서다.

#### BeanPostProcessor Code

```java
package org.springframework.beans.factory.config;

public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

```
