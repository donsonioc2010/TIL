# Mediator(중재자) Pattern

## Mediator

> 여러 객체들이 소통하는 방법을 추상화, 캡슐화를 시켜서, 컴포넌트들간의 의존성(결합도)를 `중재자`를 통해 낮추 는 패턴이다.

> 주요 목표는 한개의 클래스(중재자 클래스)한테 모든 의존성을 몰아주는게 목표

## 장단점

### 장점

- 컴포넌트 코드의 변경이 없이 새로운 중재자를 만들어 활용이 가능하다.
- 각각의 컴포넌트 코드를 보다 간결하게 유지가 가능하다

### 단점

- 중재자 클래스의 복잡도와 결합도가 매우 증가한다.

## Source

### After Source

#### Main, Client

> 사실상 Client(`Guest`)에서 모든 의존성을 알아야 해당 컴포넌트의 사용이 가능한것

```java
public class Hotel {
  public static void main(String[] args) {
    Guest guest = new Guest();
    guest.getTower(3);
    guest.dinner();

    Restaurant restaurant = new Restaurant();
    restaurant.clean();
  }
}

public class Guest {
  private Restaurant restaurant = new Restaurant();
  private CleaningService cleaningService = new CleaningService();
  public void dinner() {
    restaurant.dinner(this);
  }
  public void getTower(int numberOfTower) {
    cleaningService.getTower(this, numberOfTower);
  }
}
```

#### Logic

```java
public class CleaningService {
  public void clean(Gym gym) {
    System.out.println("clean " + gym);
  }
  public void getTower(Guest guest, int numberOfTower) {
    System.out.println(numberOfTower + " towers to " + guest);
  }
  public void clean(Restaurant restaurant) {
    System.out.println("clean " + restaurant);
  }
}

public class Restaurant {
  private CleaningService cleaningService = new CleaningService();
  public void dinner(Guest guest) {
    System.out.println("dinner " + guest);
  }

  public void clean() {
    cleaningService.clean(this);
  }
}

public class Gym {
  private CleaningService cleaningService;
  public void clean() {
    cleaningService.clean(this);
  }
}

```

### Before Source

#### Main, Client

> 사실상 모든 요청이 FrontDesk만 알고 있다고 해도 가능해진다.

```java
public class Guest {

  private Integer id;
  private FrontDesk frontDesk = new FrontDesk();
  public void getTowers(int numberOfTowers) {
    this.frontDesk.getTowers(this, numberOfTowers);
  }
  private void dinner(LocalDateTime dateTime) {
    this.frontDesk.dinner(this, dateTime);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
```

#### Logic

> FrontDesk에서는 모든 컴포넌트의 의존성을 가지고 있어야 할 필요가 존재하며, 각각의 기능에는 최소한의 정보만 제공하는것이 중요하다

```java
public class FrontDesk {
  private CleaningService cleaningService = new CleaningService();
  private Restaurant restaurant = new Restaurant();
  public void getTowers(Guest guest, int numberOfTowers) {
    cleaningService.getTowers(guest.getId(), numberOfTowers);
  }
  public String getRoomNumberFor(Integer guestId) {
    return "1111";
  }
  public void dinner(Guest guest, LocalDateTime dateTime) {
    restaurant.dinner(guest.getId(), dateTime);
  }
}

public class Restaurant {
  public void dinner(Integer id, LocalDateTime dateTime) {
  }
}

public class CleaningService {
  private FrontDesk frontDesk = new FrontDesk();
  public void getTowers(Integer guestId, int numberOfTowers) {
    String roomNumber = this.frontDesk.getRoomNumberFor(guestId);
    System.out.println("provide " + numberOfTowers + " to " + roomNumber);
  }
}
```
