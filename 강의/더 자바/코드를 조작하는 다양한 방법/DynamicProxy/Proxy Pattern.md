# Proxy Pattern

## Proxy?

> 프록시의 사전적 의미는 `대리자`라는 뜻으로, 실제 패턴의 경우도 대리자를 세워서 실행이 되는 방식으로 이뤄진다

---

## Proxy Architecture

> 프록시패턴의 구조는 다음과 같다. (참조는 강의에서 제공한 이미지 그대로 썻다..)

![Proxy Pattern Architecture](./img/Proxy%20Pattern%20Architecture.png)

다음과 같은 과정으로 개발이 이뤄진다.

1. 인터페이스인 `서브젝트`를 생성한다
2. 구현체(클래스)인 `리얼 서브젝트`를 구현한다
3. 구현체(클래스)인 `프록시`를 제작은 하나 `리얼 서브젝트`를 참조한다.
4. 서브젝트의 실제 사용 객체는 `프록시`로 사용을 하게 된다.

---

## Usage Flow UML

> [Proxy Architecture](#proxy-architecture)의 실제 사용 흐름도는 다음과 같이 이루어 지게 된다.

![Proxy Pattern Flow](img/Proxy%20Pattern%20Flow.png)

1. `클라이언트`의 요청이 들어온다.
2. `프록시`로 요청이 들어온다.
3. 요청이 실제로 필요한 경우 `리얼 서브젝트로`, 필요하지 않은 요청인 경우 `프록시`객체에서 처리를 할 수도 있다.

---

## 사용처

> `리얼 서브젝트`는 **자신이 해야 할 일(SRP)**만 하면서 프록시를 사용해서 **부가적인 가능(접근 제한, 로깅, 트랜잭션 등)**을 제공해야 할 때 자주 사용한다.

---

## 문제점

> 일반적으로 `Interface`와 `Class`로 구현을 하게 될 경우 `Interface`에 구현해야 할 Method가 추가가 될 때마다 `Proxy Class`와 `Class` **양 측에 모두 구현을 해야 한다는 불편함**이 발생하게 된다.
>
> 해당 문제를 해결하는 방법이 동적으로 Runtime시에 생성해 내는 방법을 사용하며 해당 방법을 [Dynamic Proxy](./Dynamic%20Proxy.md)라고 한다.
