# Visitor Pattern

## Visitor

> 기존코드의 변경없이, 새로운 기능을 추가하는 방법 (근데 사실 대부분이 다 그런거 같다)

### 구현시 주의사항

- Element의 Interface가 존재하고 하위 클래스를 구현한다.
- Visitor가 Interface로 존재하고, Visitor는 각각의 Element를 참조받는 VisitMethod가 존재하며, Element별로 `Overloading`되어 있다.
- 예제의 Visitor 는 같은 Method를 Overloading을 하여 진행하였지만, 굳이 같은 명칭을 칭하지 않아도 되며, 다른명칭으로 하는게 가독성에는 더 좋을 수도 있다.

## Pattern Structure

![Visitor Pattern Structure](./img/Visitor%20Structure.png)

## 장단점

### 장점

- 기존코드의 변경없이 새로운 코드, 기능의 추가가 가능함
- 각각의 Visitor에서 기능을 한곳에 몰아 넣어 둘 수 있다.

### 단점

- 더블 디스패치라는 복잡함
- 새로운 Element의 추가 또는 제거시 많은량의 Visitor코드가 수정되어야 한다.

## Source

### Before Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Shape rectangle = new Rectangle();
    Device device = new Phone();
    rectangle.printTo(device);
  }
}

```

#### Others

```java
public interface Shape {
  void printTo(Device device);
}

public class Rectangle implements Shape {
  @Override
  public void printTo(Device device) {
    if (device instanceof Phone) {
      System.out.println("print Rectangle to phone");
    } else if (device instanceof Watch) {
      System.out.println("print Rectangle to watch");
    }
  }
}

public class Triangle implements Shape {
  @Override
  public void printTo(Device device) {
    if (device instanceof Phone) {
      System.out.println("print Triangle to Phone");
    } else if (device instanceof Watch) {
      System.out.println("print Triangle to Watch");
    }
  }
}

public class Circle implements Shape {
  @Override
  public void printTo(Device device) {
    if (device instanceof Phone) {
      System.out.println("print Circle to phone");
    } else if (device instanceof Watch) {
      System.out.println("print Circle to watch");
    }
  }
}

public interface Device {}

public class Watch implements Device{}

public class Phone implements Device{}

```

### After Source

> 실행이 되는 과정을 `더블 디스패치`가 발생한다고 한다. 그 이유는, 처음 Client Code에서 Shape의 `accept`가 어느 클래스인지를 찾고 Rectangle의 accept를 찾아과는 과정 1번, Reactangle.accept에서 shape.print가 어떠한 Shape클래스인지, 그리고 해당 클래스의 print를 찾아가는 과정 1번 총2번이 발생해서 더블 디스패치라 함.

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Shape rectangle = new Rectangle();
    Device device = new Pad();
    rectangle.accept(device);
  }
}
```

#### Elements

```java
public interface Shape {
  void accept(Device device);
}
public class Triangle implements Shape {
  @Override
  public void accept(Device device) {
    device.print(this);
  }
}
public class Rectangle implements Shape {
  @Override
  public void accept(Device device) {
    device.print(this);
  }
}
public class Circle implements Shape {
  @Override
  public void accept(Device device) {
    device.print(this);
  }
}
```

#### Visitors

```java
public interface Device {
  void print(Circle circle);
  void print(Rectangle rectangle);
  void print(Triangle triangle);
}

public class Phone implements Device {

  @Override
  public void print(Circle circle) {
    System.out.println("Print Circle to Phone");
  }

  @Override
  public void print(Rectangle rectangle) {
    System.out.println("Print Rectangle to Phone");
  }

  @Override
  public void print(Triangle triangle) {
    System.out.println("Print Triangle to Phone");
  }
}

public class Pad implements Device {
  @Override
  public void print(Circle circle) {
    System.out.println("Print Circle to Pad");
  }

  @Override
  public void print(Rectangle rectangle) {
    System.out.println("Print Rectangle to Pad");
  }

  @Override
  public void print(Triangle triangle) {
    System.out.println("Print Triangle to Pad");
  }
}

public class Watch implements Device {
  @Override
  public void print(Circle circle) {
    System.out.println("Print Circle to Watch");
  }

  @Override
  public void print(Rectangle rectangle) {
    System.out.println("Print Rectangle to Watch");
  }

  @Override
  public void print(Triangle triangle) {
    System.out.println("Print Triangle to Watch");
  }
}
```
