# Memento(메멘토 ) Pattern

## Memento

> 캡슐화를 유지하면서 `객체 내부 상태`를 `외부에 저장`하고, `외부에 저장`된 상태를 `복원`하고싶을 때 사용하는 패턴

> Detail하게 외부에 객체 상태를 공개하는게 아닌, 캡슐화 된상태만 내보내는것

## 장단점

### 장점

- 캡슐화를 지키면서 상태 객체 상태의 스냅샷을 생성할 수 있다.
- 객체 상태를 저장하고 복원하는 역할을 CareTaker에게 위임이 가능하다.
- 객체 상태가 바뀌어도 클라이언트 코드는 변경이 안된다.

### 단점

- 많은 정보를 저장하는 Mementor를 자주 생성하는 경우에는 메모리의 사용량에 `많은 영향`을 줄 수 있다

## 구현방법

> `Originator`는 `Memento`객체를 내뱉는 메소드, `Memento`를 매개변수로 받아 복구하는 메소드가 필요하며, `Memento`객체는 `Originator`의 정보를 받아 Immutable한 불변의 객체로 수정불가능하게 가지고 있어야 한다.

## Source

### After Source

#### Main, Client

> Main, Client에서 Originator에 해당하는 state값을 가지게 되는 순간 `캡슐화가 꺠졌다`고 정의가 가능하다.

```java
public class Client {
  public static void main(String[] args) {
    Game game = new Game();
    game.setRedTeamScore(10);
    game.setBlueTeamScore(20);

    int blueTeamScore = game.getBlueTeamScore();
    int redTeamScore = game.getRedTeamScore();

    Game restoredGame = new Game();
    restoredGame.setBlueTeamScore(blueTeamScore);
    restoredGame.setRedTeamScore(redTeamScore);
  }
}
```

#### VO

```java
public class Game implements Serializable {
  private int redTeamScore;
  private int blueTeamScore;
  public int getRedTeamScore() {
    return redTeamScore;
  }

  public void setRedTeamScore(int redTeamScore) {
    this.redTeamScore = redTeamScore;
  }

  public int getBlueTeamScore() {
    return blueTeamScore;
  }

  public void setBlueTeamScore(int blueTeamScore) {
    this.blueTeamScore = blueTeamScore;
  }
}
```

### Before Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Game game = new Game();
    game.setBlueTeamScore(10);
    game.setRedTeamScore(20);

    GameSave save = game.save();
    game.setBlueTeamScore(12);
    game.setRedTeamScore(22);

    game.restore(save);

    System.out.println(game.getBlueTeamScore());
    System.out.println(game.getRedTeamScore());
  }
}
```

#### Originator, Memento

```java
public class Game {
  private int redTeamScore;
  private int blueTeamScore;
  // Score Getter, Setter

  public GameSave save() {
    return new GameSave(this.blueTeamScore, this.redTeamScore);
  }

  public void restore(GameSave gameSave) {
    this.blueTeamScore = gameSave.getBlueTeamScore();
    this.redTeamScore = gameSave.getRedTeamScore();
  }
}

//한번 생성한 순간 Immutable
public final class GameSave {
  private final int blueTeamScore;
  private final int redTeamScore;
  public GameSave(int blueTeamScore, int redTeamScore) {
    this.blueTeamScore = blueTeamScore;
    this.redTeamScore = redTeamScore;
  }

  public int getBlueTeamScore() {
    return blueTeamScore;
  }

  public int getRedTeamScore() {
    return redTeamScore;
  }
}
```
