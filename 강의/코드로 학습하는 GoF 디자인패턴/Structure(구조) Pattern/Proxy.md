# Proxy Pattern

## Proxy

> 특정 객체에 대한 접근을 제어하거나, 기능을 추가 할 수 있는 패턴

> 초기화를 지연한다던가, 접근저에, 로깅, 캐싱등 정말 다양하게 진짜 활용은 가능하다..

### 주의사항

- Interface를 사용할 수도, 상속을 사용 할 수도 있다.

## 장단점

### 장점

- 기존 코드를 변경하지 않고 기능의 추가가 가능하다
- 기존 코드는 기존코드로서 해야하는 일만 유지가 가능하다.
- 기능추가 및 초기화 지연, 실행시간 측정 등 매우 다양한 활용이 가능

### 단점

- 기존의 코드에 비해서 복잡도 상승한다

## Source

### Before Source

#### Main

```java
public class Client {
  public static void main(String[] args) throws InterruptedException {
    GameService gameService = new GameService();
    gameService.startGame();
  }
}
```

#### Logic

```java
public class GameService {
  public void startGame() {
    System.out.println("이 자리에 오신 여러분을 진심으로 환영합니다.");
  }
}

```

### After Source

> Before의 Main과 Logic의 수정없이 코드를 변경해야한다.

#### Main

```java
public class Client {
  public static void main(String[] args) {
    GameService gameService = new GameServiceProxy();
    gameService.startGame();
  }
}
```

#### Logic

```java
public interface GameService {
  void startGame();
}

public class DefaultGameService implements GameService {
  @Override
  public void startGame() {
    System.out.println("이 자리에 오신 여러분을 진심으로 환영합니다.");
  }
}

public class GameServiceProxy implements GameService {
  private GameService gameService;
  @Override
  public void startGame() {
    long before = System.currentTimeMillis();
    if (this.gameService == null) {
        this.gameService = new DefaultGameService();
    }

    gameService.startGame();
    System.out.println(System.currentTimeMillis() - before);
  }
}
```
