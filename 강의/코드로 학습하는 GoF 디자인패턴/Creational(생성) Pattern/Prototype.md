# Prototype Pattern

## Prototype?

> 기존의 인스턴스를 `clone()`을 활용해서 `복제`해서 `새로운`인스턴스를 생성하는 방법이다.

## 장단점

### 장점

- 복잡한 객체를 손쉽게 새 인스턴스로 만드는 **과정을 숨길** 수 있다.
- **기존 객체를 복제하는 과정**이 **새 인스턴스를 생성**하는 것보다 **비용**(시간 또는 메모리등 리소스)적인 면에서 효율 적일 수 있다
- 추상타입 리턴도 가능하다

### 단점

- 복제한 객체를 만드는 과정 자체가 복잡할 가능성이 존재한다.
  - `clone`메소드를 만드는 과정 자체가 복잡해 질 수 있다는 의미

## Source

### Before Source

#### Main

> 하위 코드에서 `repository.clone() == repository`는 **false**, `repository.equals(repository.clone())`는 **True**가 출력되야 한다.
> 이는 `githubIssue`로 실행해도 마찬가지.

```java
public class App {
  public static void main(String[] args) {
    GithubRepository repository = new GithubRepository();
    repository.setUser("whiteship");
    repository.setName("live-study");

    GithubIssue githubIssue = new GithubIssue(repository);
    githubIssue.setId(1);
    githubIssue.setTitle("1주차 과제: JVM은 무엇이며 자바 코드는 어떻게 실행하는 것인가.");

    String url = githubIssue.getUrl();
    System.out.println(url);
  }
}
```

#### VO

> 현재의 코드는 object에 존재하는 `clone()`를 그대로 사용이 불가능하다. `Before Source`에서 변경된 코드를 확인하자.

```java
public class GithubIssue {
  private int id;
  private String title;
  private GithubRepository repository;

  public GithubIssue(GithubRepository repository) {
    this.repository = repository;
  }

  /* Getter, Setter*/

  public String getUrl() {
    return String.format("https://github.com/%s/%s/issues/%d",
            repository.getUser(),
            repository.getName(),
            this.getId());
  }
}

public class GithubRepository {
  private String user;
  private String name;

  /* Getter, Setter*/
}
```

### After Source

> `Cloneable` Interface를 상속받은 이후 `Clone()`을 구현한다.
> Before source를 기준으로는 `GithubIssue`만 변경하였다.

#### Example1 (Shallow Copy)

> 내용이 동일한지 확인을 위해서는 Equals를 구현.. 굳이 `동일`확인이 필요없으면 구현은 필요없음
>
> 자바에서 기본적으로 제공하는 Clone의 경우에는 Shallow Copy(얕은 복사)이다.

```java
//VO
public class GithubIssue implements Cloneable {
  private int id;
  private String title;
  private GithubRepository repository;
  public GithubIssue(GithubRepository repository) {
      this.repository = repository;
  }
  /* Getter, Setter */

  public String getUrl() {
    return String.format("https://github.com/%s/%s/issues/%d",
            repository.getUser(),
            repository.getName(),
            this.getId());
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GithubIssue that = (GithubIssue) o;
    return id == that.id && Objects.equals(title, that.title) && Objects.equals(repository, that.repository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, repository);
  }
}

//Main
public class App {
  public static void main(String[] args) {
    GithubIssue githubIssue = new GithubIssue(repository);
    githubIssue.setId(1);
    githubIssue.setTitle("1주차 과제: JVM은 무엇이며 자바 코드는 어떻게 실행하는 것인가.");

    Object clone = githubIssue.clone();
  }
}
```

#### Example2 (Deep Copy)

> 해당 코드는 [Example1](#example1-shallow-copy)과 동일하나, `GithubIssue`의 `clone`메소드만 재정의 하였기에 해당 코드만 작성한다.

> 아래와 같이 직접 Instance 객체를 clone 메소드에서 생성하고 직접 생성한 객체를 Return 한다.

```java
@Override
protected Object clone() throws CloneNotSupportedException {
    GithubRepository repository = new GithubRepository();
    repository.setUser(this.repository.getUser());
    repository.setName(this.repository.getName());

    GithubIssue githubIssue = new GithubIssue(repository);
    githubIssue.setId(this.id);
    githubIssue.setTitle(this.title);

    return githubIssue;
}
```
