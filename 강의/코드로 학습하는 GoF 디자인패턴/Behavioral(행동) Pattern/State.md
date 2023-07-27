# State(상태) Pattern

## State

> 특정한 상태에 따라서 객체의 행동이 달라지도록 하는 패턴이다.
> 상태에 특화된 행동으로 분리를 할 수 있으며, 새로운 행동을 추가하더라도 다른 행동에 영향을 주지 않도록 해야한다.

### 주의점

> Structure는 디자인패턴마다 약간의 변경이 있을 수 있지만, 해당 패턴에서는 Interface또는 Abstract Class의 존재가 `무조건 필수`이다.

## 장단점

### 장점

- 상태에 따른 동작을 개별 클래스로 옮겨서 관리가 가능하다.
- 기존의 특정 상태에 따른 동작을 변경하지 않고 새로운 상태에 다른 동작의 추가가 가능하다.
- 코드복잡도를 낮출 수 있다.

### 단점

- 클래스가 증가하기 떄문에 구조의 복잡도가 증가된다.

## Source

### Before Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Student student = new Student("whiteship");
    OnlineCourse onlineCourse = new OnlineCourse();

    Student keesun = new Student("keesungod...");
    keesun.addPrivateCourse(onlineCourse);

    onlineCourse.addStudent(student);
    onlineCourse.changeState(OnlineCourse.State.PRIVATE);

    onlineCourse.addStudent(keesun);

    onlineCourse.addReview("hello", student);

    System.out.println(onlineCourse.getState());
    System.out.println(onlineCourse.getStudents());
    System.out.println(onlineCourse.getReviews());
  }
}
```

#### Logic

```java
public class Student {
  private String name;
  public Student(String name) {
    this.name = name;
  }

  private List<OnlineCourse> privateCourses = new ArrayList<>();

  public boolean isEnabledForPrivateClass(OnlineCourse onlineCourse) {
    return privateCourses.contains(onlineCourse);
  }

  public void addPrivateCourse(OnlineCourse onlineCourse) {
    this.privateCourses.add(onlineCourse);
  }

  @Override
  public String toString() {
    return "Student{" +
            "name='" + name + '\'' +
            '}';
  }
}

public class OnlineCourse {

  public enum State {
    DRAFT, PUBLISHED, PRIVATE
  }

  private State state = State.DRAFT;
  private List<String> reviews = new ArrayList<>();
  private List<Student> students = new ArrayList<>();

  public void addReview(String review, Student student) {
    if (this.state == State.PUBLISHED) {
      this.reviews.add(review);
    } else if (this.state == State.PRIVATE && this.students.contains(student)) {
      this.reviews.add(review);
    } else {
      throw new UnsupportedOperationException("리뷰를 작성할 수 없습니다.");
    }
  }

  public void addStudent(Student student) {
    if (this.state == State.DRAFT || this.state == State.PUBLISHED) {
      this.students.add(student);
    } else if (this.state == State.PRIVATE && availableTo(student)) {
      this.students.add(student);
    } else {
      throw new UnsupportedOperationException("학생을 해당 수업에 추가할 수 없습니다.");
    }

    if (this.students.size() > 1) {
      this.state = State.PRIVATE;
    }
  }

  public void changeState(State newState) {
    this.state = newState;
  }
  private boolean availableTo(Student student) {
    return student.isEnabledForPrivateClass(this);
  }
  public State getState() {
    return state;
  }
  public List<String> getReviews() {
    return reviews;
  }
  public List<Student> getStudents() {
    return students;
  }
}
```

### After Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    OnlineCourse onlineCourse = new OnlineCourse();
    Student student = new Student("whiteship");
    Student keesun = new Student("keesun");
    keesun.addPrivate(onlineCourse);

    onlineCourse.addStudent(student);

    onlineCourse.changeState(new Private(onlineCourse));

    onlineCourse.addReview("hello", student);

    onlineCourse.addStudent(keesun);

    System.out.println(onlineCourse.getState());
    System.out.println(onlineCourse.getReviews());
    System.out.println(onlineCourse.getStudents());
  }
}
```

#### Logic

```java
public class OnlineCourse {

  private State state = new Draft(this);
  private List<Student> students = new ArrayList<>();
  private List<String> reviews = new ArrayList<>();

  public void addStudent(Student student) {
    this.state.addStudent(student);
  }

  public void addReview(String review, Student student) {
    this.state.addReview(review, student);
  }

  public State getState() {
    return state;
  }

  public List<Student> getStudents() {
    return students;
  }

  public List<String> getReviews() {
    return reviews;
  }

  public void changeState(State state) {
    this.state = state;
  }
}

public class Student {
  private String name;
  public Student(String name) {
    this.name = name;
  }

  private Set<OnlineCourse> onlineCourses = new HashSet<>();

  public boolean isAvailable(OnlineCourse onlineCourse) {
    return onlineCourses.contains(onlineCourse);
  }

  public void addPrivate(OnlineCourse onlineCourse) {
    this.onlineCourses.add(onlineCourse);
  }

  @Override
  public String toString() {
    return "Student{" +
            "name='" + name + '\'' +
            '}';
  }
}
```

#### State

```java
public interface State {
  void addReview(String review, Student student);
  void addStudent(Student student);
}

public class Draft implements State {
  private OnlineCourse onlineCourse;
  public Draft(OnlineCourse onlineCourse) {
    this.onlineCourse = onlineCourse;
  }

  @Override
  public void addReview(String review, Student student) {
    throw new UnsupportedOperationException("드래프트 상태에서는 리뷰를 남길 수 없습니다.");
  }

  @Override
  public void addStudent(Student student) {
    this.onlineCourse.getStudents().add(student);
    if (this.onlineCourse.getStudents().size() > 1) {
        this.onlineCourse.changeState(new Private(this.onlineCourse));
    }
  }
}

public class Private implements State {

  private OnlineCourse onlineCourse;

  public Private(OnlineCourse onlineCourse) {
    this.onlineCourse = onlineCourse;
  }

  @Override
  public void addReview(String review, Student student) {
    if (this.onlineCourse.getStudents().contains(student)) {
      this.onlineCourse.getReviews().add(review);
    } else {
      throw new UnsupportedOperationException("프라이빗 코스를 수강하는 학생만 리뷰를 남길 수 있습니다.");
    }
  }

  @Override
  public void addStudent(Student student) {
    if (student.isAvailable(this.onlineCourse)) {
      this.onlineCourse.getStudents().add(student);
    } else {
      throw new UnsupportedOperationException("프라이빛 코스를 수강할 수 없습니다.");
    }
  }
}

public class Published implements State {
  private OnlineCourse onlineCourse;
  public Published(OnlineCourse onlineCourse) {
    this.onlineCourse = onlineCourse;
  }
  @Override
  public void addReview(String review, Student student) {
    this.onlineCourse.getReviews().add(review);
  }
  @Override
  public void addStudent(Student student) {
    this.onlineCourse.getStudents().add(student);
  }
}

```
