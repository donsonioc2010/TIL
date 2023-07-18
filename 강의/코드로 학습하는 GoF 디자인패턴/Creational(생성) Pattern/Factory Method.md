# Factory Method Pattern

## Factory Method Pattern?

> 구체적으로 어떤 인스턴스로 만드는지는 Sub Class가 정하는 방법이다.

> 다양한 구현체 클래스가 존재하고, 그중에서 특정한 구현체를 만들 수 있는 팩토리를 제공하는 기법

> `개방 폐쇄원칙`(확장에 Open, 변경에 Close)에 주의하며 작성하자

## 장단점?

### 장점

> **Note**
> 개방폐쇄 원칙에 의거해서 기존코드의 수정 없이 새로운 인스턴스를 얼마든지 확장이 가능한 하다

### 단점

> **Note**
> 클래스의 역할을 나눠야 하다보니 클래스가 많아진다는 단점이 존재한다

## 패턴을 적용하는 과정

### Before Source

#### Main

```java
public class Client {
  public static void main(String[] args) {
    Ship whiteship = ShipFactory.orderShip("Whiteship", "keesun@mail.com");
    System.out.println(whiteship);

    Ship blackship = ShipFactory.orderShip("Blackship", "keesun@mail.com");
    System.out.println(blackship);
  }
}
```

#### Ship

```java
public class Ship {
  private String name;
  private String color;
  private String logo;

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}
  public String getColor() {return color;}
  public void setColor(String color) {this.color = color;}
  public String getLogo() {return logo;}
  public void setLogo(String logo) {this.logo = logo;}

  @Override
  public String toString() {
    return "Ship{" +
            "name='" + name + '\'' +
            ", color='" + color + '\'' +
            ", logo='" + logo + '\'' +
            '}';
  }
}
```

#### ShipFactory

```java
public class ShipFactory {
  public static Ship orderShip(String name, String email) {
    // validate
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("배 이름을 지어주세요.");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("연락처를 남겨주세요.");
    }

    prepareFor(name);

    Ship ship = new Ship();
    ship.setName(name);

    // Customizing for specific name
    if (name.equalsIgnoreCase("whiteship")) {
      ship.setLogo("\uD83D\uDEE5️");
    } else if (name.equalsIgnoreCase("blackship")) {
      ship.setLogo("⚓");
    }

    // coloring
    if (name.equalsIgnoreCase("whiteship")) {
      ship.setColor("whiteship");
    } else if (name.equalsIgnoreCase("blackship")) {
      ship.setColor("black");
    }

    // notify
    sendEmailTo(email, ship);
    return ship;
  }

  private static void prepareFor(String name) {
    System.out.println(name + " 만들 준비 중");
  }

  private static void sendEmailTo(String email, Ship ship) {
    System.out.println(ship.getName() + " 다 만들었습니다.");
  }
}
```

### 1차 수정 After Source

> Interface에 몰아넣는 방법을 생각해봤으나... 예제로 작성하신 코드 이상의 깔끔함을 뽑아 볼 수가 없었다...

#### Main

```java
public class Client {
  public static void main(String[] args) {
    Ship whiteship = new WhiteshipFactory().orderShip("Whiteship", "keesun@mail.com");
    System.out.println(whiteship);

    Ship blackship = new BlackshipFactory().orderShip("Blackship", "keesun@mail.com");
    System.out.println(blackship);
  }
}
```

#### ShipFactory

> Interface, Abstract Class로 분리  
> orderShip()의 코드가 직관적인 `역할만 존재하는 코드`로 정리 된다

> `Abstract Class`로 분리된 이유는, JDK버전을 고려한 예시로, JDK버전이 9이상인 경우에는 `Interface`에서 모두 작성하고 Interface를 상속해도 무방하다.

```java
public interface ShipFactory {
  default Ship orderShip(String name, String email) {
    validate(name, email);
    prepareFor(name);
    Ship ship = createShip();
    sendEmailTo(email, ship);
    return ship;
  }

  void sendEmailTo(String email, Ship ship);

  Ship createShip();

  private void validate(String name, String email) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("배 이름을 지어주세요.");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("연락처를 남겨주세요.");
    }
  }

  private void prepareFor(String name) {
    System.out.println(name + " 만들 준비 중");
  }
}

public abstract class DefaultShipFactory implements ShipFactory {
    @Override
    public void sendEmailTo(String email, Ship ship) {
        System.out.println(ship.getName() + " 다 만들었습니다.");
    }
}

public class WhiteshipFactory extends DefaultShipFactory {
  @Override public Ship createShip() {return new Whiteship();}
}

public class BlackshipFactory extends DefaultShipFactory {
  @Override public Ship createShip() {return new Blackship();}
}
```

#### Ship

> 변경사항 Before Source에서 없다. 하지만 `ship`을 상속받아 생성하는 메서드가 추가되었다.

```java
public class Blackship extends Ship {
  public Blackship() {
    setName("blackship");
    setColor("black");
    setLogo("⚓");
  }
}
public class Whiteship extends Ship {
  public Whiteship() {
    setName("whiteship");
    setLogo("\uD83D\uDEE5️");
    setColor("white");
  }
}
```

### 2차 수정 After Source

> 사실 1차 수정에서 끝난다고 생각은 든다. 매 새로운 방식이 생성될때마다 코드의 변경없이 확장만 존재하니..
> 강의의 예시의 경우 Client의 변경마저 불편한 경우 다음과 같이 주입을 통해 해결 할 수 있는 방법이있다.

#### Main

```java
public class Client {

  public static void main(String[] args) {
    Client client = new Client();
    client.print(new WhiteshipFactory(), "whiteship", "keesun@mail.com");
    client.print(new BlackshipFactory(), "blackship", "keesun@mail.com");
  }

  private void print(ShipFactory shipFactory, String name, String email) {
    System.out.println(shipFactory.orderShip(name, email));
  }
}
```
