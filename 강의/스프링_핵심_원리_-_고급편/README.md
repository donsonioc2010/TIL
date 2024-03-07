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

## AspectJ

> Spring은 자동 프록시생성기(`AnnotationAwareAspectJAutoProxyCreator`)가 Advisor를 자동으로 찾아와서 필요한곳에 적용을 해주게 된다.
> 여기에 추가로 AOP를 사용하게 될 경우 하나의 역할을 더 하게 되는데 바로 `@Aspect`를 찾아서 어드바이저(`Advisor`)로 변환을 해준다.

### SampleCode

> 아래의 코드처럼, 스프링은 `@Aspect`가 달린 빈을 컴포넌트 스캔으로 찾고 존재하는 경우 `@Around`를 포인트컷으로, 메소드로직을 `Advice`로 변환해 프록시를 생성하게 된다.

```java
@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* jong1.proxy.app..*(..))") //포인트컷 역할
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable {
        // 어드바이스 로직
    }
}
```

## Spring AOP

- **조인 포인트**(`Join Point`)
  - 어드바이스가 적용될 수 있는 위치, 메소드 실행, 생성자 호출, 필드 값 접근, Static메서드 접근 같은 프로그램 실행중 지점
  - 추상적인 개념으로, AOP를 적용할 수 있는 모든 지점이라 생각해야 한다.
  - 스프링 AOP는 프록시 방식을 사용하므로 조인포인트는 항상 메소드 실행지점으로 제한된다.
- **포인트컷**(`Pointcut`)
  - 조인포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능
  - 주로 AspectJ표현식을 사용해서 지정한다.
  - 프록시를 사용하는 스프링 AOP는 **메소드 실행 지점만** 포인트컷으로 선별이 가능하다
- **타켓**(`Target`)
  - 어드바이스를 받는 객체, 실행해야 할 메소드
- **어드바이스**(`Advice`)
  - 타겟에게 부가기능을 실행할 로직메소드
  - 특정 조인포인트에서 Aspect에 의해 취해지는 조치
  - 어드바이스의 종류로는 `@Around(타겟 실행전후)`, `@Before(타겟 실행전)`, `@After(타겟 실행후)`가 있음
- **에스펙트**(`Aspect`)
  - 어드바이스 + 포인트컷을 모듈화 한 것
    - `@Aspect`를 생각하면 된다.
  - 여러 어드바이스와 포인트컷이 함께 존재가 가능하다.
- **어드바이저**(`Advisor`)
  - 하나의 어드바이스와 하나의 포인트컷으로 구성된 객체를 의미
  - 스프링 AOP에서만 사용되는 용어이다
- **위빙**(`Weaving`)
  - 프인트컷으로 결정한 타켓의 조인포인트에 어드바이스를 적용하는 것을 의미
  - 위빙을 통해 핵심기능코드에 영향을 주지않고, 횡단의 부가로직적용이 가능
  - AOP적용을 위해 적용가능한 Aspect방법
    - 컴파일을 하면서 Aspect적용
    - 클래스를 로드하는 시점에 Aspect를 적용
    - 런타임시점에 Proxy를 통한 AOP적용, (스프링AOP는 해당방식을 사용한다.)
- **AOP프록시**
  - AOP기능을 구현하기 위해 만든 프록시 객체로, 스프링 AOP프록시 에서 JDK동적프록시 또는 CGLIB프록시를 사용한다.

### 어드바이스의 순서지정

> 어드바이스는 기본적으로 순서를 보장하지 않는다.
> 순서를 보장할 수 있는 단위는 `@Aspect`단위로 어드바이스를 적용이 가능하고 어드바이스 단위로는 할수 없다.
> 그렇기에 어드바이스 별로 클래스를 분리해서 Aspect로 만들어 적용해야 한다.  
> 순서를 적용하는 어노테이션은 `org.springframework.core.annotation.Order`이다.

#### Aspect단위 분리 코드 예제

변경 전

```java
@Slf4j
@Aspect
public class AspectV4Pointcut {

    @Around("jong1.aop.order.aop.Pointcuts.allOrder()")
    public Object advice1(ProceedingJoinPoint joinPoint) throws Throwable {
      // 어드바이스 1
    }

    @Around("jong1.aop.order.aop.Pointcuts.orderAndService()")
    public Object advice2(ProceedingJoinPoint joinPoint) throws Throwable {
      // 어드바이스 2
    }
}
```

변경 후

```java
@Slf4j
public class AspectV4Pointcut {

  @Aspect
  @Order(1)
  static class Advice1 {
    @Around("jong1.aop.order.aop.Pointcuts.allOrder()")
    public Object advice1(ProceedingJoinPoint joinPoint) throws Throwable {
      // 어드바이스 1
    }
  }

  @Aspect
  @Order(2)
  static class Advice2 {
    @Around("jong1.aop.order.aop.Pointcuts.orderAndService()")
    public Object advice2(ProceedingJoinPoint joinPoint) throws Throwable {
      // 어드바이스 2
    }
  }
}
```
