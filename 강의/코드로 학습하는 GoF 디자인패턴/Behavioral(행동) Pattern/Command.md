# Command Pattern

## Command

> 요청을 캡슐화 해서 요청자는 캡슐의 실행만 해도 되며, 내부에서 요청을 처리하는 방법이 바뀌어도 요청자의 코드가 변형되는 일이 없다.

> ...?

## 장단점

### 장점

- 기존 코드의 변경 없이 새로운 커맨드의 제작이 가능하다.
- 수신자의 코드가 변경되더라도 호출자의 코드는 변경이 없다.
- 커맨드 객체를 로깅, DB저장, 네트워크 전송등 다양하게 활용이 가능하다.

### 단점

- 타 패턴과 마찬가지로, 코드가 복잡해지고 클래스가 많아진다.

## Source

> Before의 소스는 객체간의 커플링이 타이트하게 이뤄져 있어, 수정이 발생할 경우 모든 부분의 수정이 필요하다.
> 하지만 After로 가면 Client Level에서는 Command라는 캡슐만 가지고 실행을 하게 되며, Command의 객체들만 수정하게 되는 `수정 범위의 축소`라는 이점이 발생한다.

### Before Source

#### Main, Client

```java
public class App {
  public static void main(String[] args) {
    GameApp game = new GameApp(new Game());
    game.press();
    game.press();
    game.press();
    game.press();

    Button button = new Button(new Light());
    button.press();
    button.press();
    button.press();
    button.press();
  }
}
public class GameApp {
  private Game game;
  public GameApp(Game game) {
    this.game = game;
  }

  public void press() {
    game.start();
  }
}

public class Button {
  private Light light;
  public Button(Light light) {
    this.light = light;
  }

  public void press() {
    light.off();
  }
}
```

#### Receiver Logic

```java
public class Game {
  private boolean isStarted;
  public void start() {
    System.out.println("게임을 시작합니다.");
    this.isStarted = true;
  }

  public void end() {
    System.out.println("게임을 종료합니다.");
    this.isStarted = false;
  }

  public boolean isStarted() {
    return isStarted;
  }
}

public class Light {
  private boolean isOn;

  public void on() {
    System.out.println("불을 켭니다.");
    this.isOn = true;
  }

  public void off() {
    System.out.println("불을 끕니다.");
    this.isOn = false;
  }

  public boolean isOn() {
    return this.isOn;
  }
}
```

### After Source

#### Main, Client

```java
public class App {
  public static void main(String[] args) {
    GameApp gameApp = new GameApp(new GameStartCommand(new Game()));
    gameApp.press();
    gameApp.undo();
  }
}
// 이런식으로 쓸 수도?
public class GameApp {
  private Command command;

  public GameApp(Command command) {
    this.command = command;
  }

  public void press() {
    command.execute();
  }
  public void undo() {command.undo();}
}
```

```java
//이런 식으로 사용 할 수도?
public class Button {
  private Stack<Command> commands = new Stack<>();
  public void press(Command command) {
    command.execute();
    commands.push(command);
  }

  public void undo() {
    if (!commands.isEmpty()) {
      Command command = commands.pop();
      command.undo();
    }
  }

  public static void main(String[] args) {
    Button button = new Button();
    button.press(new GameStartCommand(new Game()));
    button.press(new LightOnCommand(new Light()));
    button.undo();
    button.undo();
  }
}
```

#### Command Logic

```java
public interface Command {
  void execute();
  void undo();
}

public class GameStartCommand implements Command {
  private Game game;
  public GameStartCommand(Game game) {
    this.game = game;
  }

  @Override
  public void execute() {
    game.start();
  }

  @Override
  public void undo() {
    new GameEndCommand(this.game).execute();
  }
}

public class GameEndCommand implements Command {
  private Game game;
  public GameEndCommand(Game game) {
    this.game = game;
  }

  @Override
  public void execute() {
    game.end();
  }

  @Override
  public void undo() {
    new GameStartCommand(this.game).execute();
  }
}

public class LightOnCommand implements Command {
  private Light light;
  public LightOnCommand(Light light) {
    this.light = light;
  }

  @Override
  public void execute() {
    light.on();
  }

  @Override
  public void undo() {
    new LightOffCommand(this.light).execute();
  }
}

public class LightOffCommand implements Command {
  private Light light;
  public LightOffCommand(Light light) {
    this.light = light;
  }

  @Override
  public void execute() {
    light.off();
  }

  @Override
  public void undo() {
    new LightOnCommand(this.light).execute();
  }
}

```
