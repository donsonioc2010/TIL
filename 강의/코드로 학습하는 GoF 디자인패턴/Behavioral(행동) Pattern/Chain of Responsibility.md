# Chain of Responsibility(책임 연쇄 패턴) Pattern

## Chain Of Responsibility

> 핸들러 체인을 활용해서 요청을 보내는 (Sender, Request)와 요청을 처리하는 (Receiver)를 분리하는 패턴이다.
> 엄밀히 말하면 클라이언트(Sender)가 내부의 변경과정을 모르게(신경을 쓰지 않고) 요청을 처리하는과정을 만드는것.

## 장단점

### 장점

- 클라이언트의 코드는 변경하지 않은 채 새롭게 핸들러 체인의 작성과 추가가 가능하다
- 각각의 체인은 자신의 해야하는 일만 한다
  - 단일책임원칙
- 체인을 다양한 방법으로 구성이 가능하다

### 단점

- 디버깅이 힘들어진다.

## Source

### Before Source

#### Client

```java
public class Client {
  public static void main(String[] args) {
    Request request = new Request("무궁화 꽃이 피었습니다.");
    RequestHandler requestHandler = new LoggingRequestHandler();
    requestHandler.handler(request);
  }
}
```

#### Request

```java
public class Request {
  private String body;
  public Request(String body) {
    this.body = body;
  }
  // Getter, Setter
}

public class RequestHandler {
  public void handler(Request request) {
    System.out.println(request.getBody());
  }
}
```

#### RequestHandler에서 패턴을 활용하지 않고 기능 추가의 방법

```java
public class LoggingRequestHandler extends RequestHandler {
  @Override
  public void handler(Request request) {
    System.out.println("로깅");
    super.handler(request);
  }
}

public class AuthRequestHandler extends RequestHandler {
  public void handler(Request request) {
    System.out.println("인증이 되었나?");
    System.out.println("이 핸들러를 사용할 수 있는 유저인가?");
    super.handler(request);
  }
}
```

### After Source

#### Main, Client

```java
public class App {
  public static void main(String[] args) {
    RequestHandler chain = new AuthRequestHandler(new LoggingRequestHandler(new PrintRequestHandler(null)));
    Client client = new Client(chain);
    client.doWork();
  }
}
public class Client {
  private RequestHandler requestHandler;
  public Client(RequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }

  public void doWork() {
    Request request = new Request("이번 놀이는 뽑기입니다.");
    requestHandler.handle(request);
  }
}
```

#### Request

```java
public abstract class RequestHandler {
  private RequestHandler nextHandler;
  public RequestHandler(RequestHandler nextHandler) {
    this.nextHandler = nextHandler;
  }
  public void handle(Request request) {
    if (nextHandler != null) {
      nextHandler.handle(request);
    }
  }
}

public class AuthRequestHandler extends RequestHandler {
  public AuthRequestHandler(RequestHandler nextHandler) {
    super(nextHandler);
  }

  @Override
  public void handle(Request request) {
    System.out.println("인증이 되었는가?");
    super.handle(request);
  }
}

public class LoggingRequestHandler extends RequestHandler {
  public LoggingRequestHandler(RequestHandler nextHandler) {
    super(nextHandler);
  }

  @Override
  public void handle(Request request) {
    System.out.println("로깅");
    super.handle(request);
  }
}

public class PrintRequestHandler extends RequestHandler {
  public PrintRequestHandler(RequestHandler nextHandler) {
    super(nextHandler);
  }

  @Override
  public void handle(Request request) {
    System.out.println(request.getBody());
    super.handle(request);
  }
}
```
