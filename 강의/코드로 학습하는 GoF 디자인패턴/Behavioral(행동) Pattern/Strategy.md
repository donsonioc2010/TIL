# Strategy(전략) Pattern

## Strategy

> 여러 분기, 알고리즘을 캡슐화하고, 상호 교환 가능하면서 확장에 자유로운 패턴이다.

## 장단점

### 장점

- 새로운 전략(로직)을 추가하더라도 기존의 코드의 수정이 없다
  - 개방폐쇄 원칙
- 상속대신 위임의 사용이 가능하다
- 런타임에서 전략의 변경이 가능하다

### 단점

- 복잡도 증기
- 클라이언트 코드가 `구체적인 전략`을 파악하고 있어야 한다.

## Source

### Before Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    BlueLightRedLight blueLightRedLight = new BlueLightRedLight(3);
    blueLightRedLight.blueLight();
    blueLightRedLight.redLight();
  }
}
```

#### Logic

```java
public class BlueLightRedLight {
  private int speed;
  public BlueLightRedLight(int speed) {
    this.speed = speed;
  }

  public void blueLight() {
    if (speed == 1) {
      System.out.println("무 궁 화    꽃   이");
    } else if (speed == 2) {
      System.out.println("무궁화꽃이");
    } else {
      System.out.println("무광꼬치");
    }
  }

  public void redLight() {
    if (speed == 1) {
      System.out.println("피 었 습 니  다.");
    } else if (speed == 2) {
      System.out.println("피었습니다.");
    } else {
      System.out.println("피어씀다");
    }
  }
}
```

### After Source

#### Main, Client

> 방법은 많이있는듯... 객체 생성자에 Speed를 주입하는 방법도 가능하고..  
> 전략이 추가될 떄마다 Speed를 추가해서 넣으면 적용이 된다...!

```java
public class Client {
  public static void main(String[] args) {
    BlueLightRedLight game = new BlueLightRedLight();
    game.blueLight(new Normal());
    game.redLight(new Fastest());


    game.blueLight(new Speed() {
      @Override
      public void blueLight() {
        System.out.println("blue light");
      }

      @Override
      public void redLight() {
        System.out.println("red light");
      }
    });
  }
}
```

#### Logic

```java
public class BlueLightRedLight {
  public void blueLight(Speed speed) {
    speed.blueLight();
  }

  public void redLight(Speed speed) {
    speed.redLight();
  }
}

public interface Speed {
  void blueLight();
  void redLight();
}

public class Normal implements Speed {
  @Override
  public void blueLight() {
    System.out.println("무 궁 화    꽃   이");
  }

  @Override
  public void redLight() {
    System.out.println("피 었 습 니  다.");
  }
}

public class Faster implements Speed {
  @Override
  public void blueLight() {
    System.out.println("무궁화꽃이");
  }

  @Override
  public void redLight() {
    System.out.println("피었습니다.");
  }
}

public class Fastest implements Speed{
  @Override
  public void blueLight() {
    System.out.println("무광꼬치");
  }

  @Override
  public void redLight() {
    System.out.println("피어씀다.");
  }
}

```
