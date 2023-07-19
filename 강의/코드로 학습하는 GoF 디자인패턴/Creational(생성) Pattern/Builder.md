# Builder Pattern

## Builder?

> 인스턴스를 제작 할 때 다양한 구성으로 만들어 질 수 있는데 다양한 구성으로 만들어지는 인스턴스를 프로세스를 통해서 만들 수 있는 방법

> Builder만으로 활용은 가능하나 Builder와 묶어서 Director를 두어 같은 객체 형식을 생성하는 메소드를 생성해서 편하게 제작도 가능하다.

## 장단점

### 장점

> 생성하기 복잡한 객체를 순차적이면서, 직관적으로 만들수 있는 방법을 제공 해 볼 수 있다.  
> 또한 복잡한 객체의 경우 생성되는 과정을 숨기면서, 로직도 분산시킬 수 있다.

### 단점

> 생성자를 사용할 때는 인스턴스를 간편히 생성할 수 있었지만, 빌더가 순차적인 프로세스를 강제하면서 반환객체가 이해가 힘들 수 있다.

## Source

### 코드 확인 전 주의점

> Builder Pattern의 경우 구현방법은 여러가지가 있을 수 있다.
> static inner class도있고, Interface를 활용할 수도있고...  
> 이번 구현은 Interface와 별도의 Class파일을 VO객체와 따로 두는 방식을 활용한다.

### Before, After 공통 VO

```java
public class DetailPlan {
  private int day;
  private String plan;
  public DetailPlan(int day, String plan) {
    this.day = day;
    this.plan = plan;
  }

  /* Getter, Setter 생략 */

  @Override
  public String toString() {
    return "DetailPlan{" +
            "day=" + day +
            ", plan='" + plan + '\'' +
            '}';
  }
}

public class TourPlan {
  private String title;
  private int nights;
  private int days;
  private LocalDate startDate;
  private String whereToStay;
  private List<DetailPlan> plans;

  public TourPlan() {}
  public TourPlan(String title, int nights, int days, LocalDate startDate, String whereToStay, List<DetailPlan> plans) {
    this.title = title;
    this.nights = nights;
    this.days = days;
    this.startDate = startDate;
    this.whereToStay = whereToStay;
    this.plans = plans;
  }

  @Override
  public String toString() {
    return "TourPlan{" +
            "title='" + title + '\'' +
            ", nights=" + nights +
            ", days=" + days +
            ", startDate=" + startDate +
            ", whereToStay='" + whereToStay + '\'' +
            ", plans=" + plans +
            '}';
  }

  public void addPlan(int day, String plan) {
    this.plans.add(new DetailPlan(day, plan));
  }
  /* Getter, Setter 생략 */
}
```

### Before Source

#### Main

```java
public class App {
  public static void main(String[] args) {
    TourPlan shortTrip = new TourPlan();
    shortTrip.setTitle("오레곤 롱비치 여행");
    shortTrip.setStartDate(LocalDate.of(2021, 7, 15));


    TourPlan tourPlan = new TourPlan();
    tourPlan.setTitle("칸쿤 여행");
    tourPlan.setNights(2);
    tourPlan.setDays(3);
    tourPlan.setStartDate(LocalDate.of(2020, 12, 9));
    tourPlan.setWhereToStay("리조트");
    tourPlan.addPlan(0, "체크인 이후 짐풀기");
    tourPlan.addPlan(0, "저녁 식사");
    tourPlan.addPlan(1, "조식 부페에서 식사");
    tourPlan.addPlan(1, "해변가 산책");
    tourPlan.addPlan(1, "점심은 수영장 근처 음식점에서 먹기");
    tourPlan.addPlan(1, "리조트 수영장에서 놀기");
    tourPlan.addPlan(1, "저녁은 BBQ 식당에서 스테이크");
    tourPlan.addPlan(2, "조식 부페에서 식사");
    tourPlan.addPlan(2, "체크아웃");
  }
}
```

### After Source

#### Builder Interface

```java
public interface TourPlanBuilder {
  TourPlanBuilder nightsAndDays(int nights, int days);
  TourPlanBuilder title(String title);
  TourPlanBuilder startDate(LocalDate localDate);
  TourPlanBuilder whereToStay(String whereToStay);
  TourPlanBuilder addPlan(int day, String plan);
  TourPlan getPlan();
}
```

#### Builder Interface의 구현체

```java
public class DefaultTourBuilder implements TourPlanBuilder {
  private String title;
  private int nights;
  private int days;
  private LocalDate startDate;
  private String whereToStay;
  private List<DetailPlan> plans;

  @Override
  public TourPlanBuilder nightsAndDays(int nights, int days) {
      this.nights = nights;
      this.days = days;
      return this;
  }

  @Override
  public TourPlanBuilder title(String title) {
      this.title = title;
      return this;
  }

  @Override
  public TourPlanBuilder startDate(LocalDate startDate) {
      this.startDate = startDate;
      return this;
  }

  @Override
  public TourPlanBuilder whereToStay(String whereToStay) {
      this.whereToStay = whereToStay;
      return this;
  }

  @Override
  public TourPlanBuilder addPlan(int day, String plan) {
      if (this.plans == null) {
          this.plans = new ArrayList<>();
      }

      this.plans.add(new DetailPlan(day, plan));
      return this;
  }

  @Override
  public TourPlan getPlan() {
      return new TourPlan(title, nights, days, startDate, whereToStay, plans);
  }
}

```

#### Main - Direction을 활용하지 않는 경우

> 객체의 생성을 다음과 같이 Chaining해서 생성이 가능하다.

```java
public class App {
  public static void main(String[] args) {
    TourPlan plan1 = new DefaultTourBuilder()
            .title("칸쿤 여행")
            .nightsAndDays(2, 3)
            .startDate(LocalDate.of(2020, 12, 9))
            .whereToStay("리조트")
            .addPlan(0, "체크인하고 짐 풀기")
            .addPlan(0, "저녁 식사")
            .getPlan();

    TourPlan plan2 = new DefaultTourBuilder()
            .title("롱비치")
            .startDate(LocalDate.of(2021, 7, 15))
            .getPlan();
  }
}
```

#### Main, Director - Direction을 활용하는 경우

> `Direction`을 활용하지 않는 경우의 Main의 객체들이 만약 자주 사용하는 내용의 객체라면, 다음과 같이 사전에 Direction에 만들고 사용해서 **코드 중복**을 피할 수도 있다.

```java
//Main
public class App {
  public static void main(String[] args) {
    TourDirector director = new TourDirector(new DefaultTourBuilder());
    TourPlan tourPlan = director.cancunTrip();
    TourPlan tourPlan1 = director.longBeachTrip();
  }
}

// Director
public class TourDirector {
  private TourPlanBuilder tourPlanBuilder;
  public TourDirector(TourPlanBuilder tourPlanBuilder) {
    this.tourPlanBuilder = tourPlanBuilder;
  }

  public TourPlan cancunTrip() {
    return tourPlanBuilder.title("칸쿤 여행")
            .nightsAndDays(2, 3)
            .startDate(LocalDate.of(2020, 12, 9))
            .whereToStay("리조트")
            .addPlan(0, "체크인하고 짐 풀기")
            .addPlan(0, "저녁 식사")
            .getPlan();
  }

  public TourPlan longBeachTrip() {
    return tourPlanBuilder.title("롱비치")
            .startDate(LocalDate.of(2021, 7, 15))
            .getPlan();
  }
}
```
