# Iterator Pattern

## Iterator

> 클라이언트 코드를 변경하지 않으면서도 집합 객체의 내부 구조를 노출시키지 않으면서 내부 객체를 순회하는 방법을 다양하게 제공하는 패턴

## 장단점

### 장점

- 집합객체가 가지고 있는 객체들에 쉽게 접근이 가능하다
  - Map, Set등 어떤 Collections인지 배열인지 객체 종류를 몰라도 접근이 가능한 것을 의미
- 일관된 인터페이스를 사용해 여러 형태의 집합 구조의 순회도 가능하다.

### 단점

- 클래스가 늘어나고 인터페이스의 상속이기 떄문에 복잡도는 증가한다.

## Source

### After Source

> Client레벨에서 List를 사용한다는 것을 알아야 사용이 가능한 전체의 소스가 되버린다.
> Client에서는 List인지 Set인지 어떤 객체를 쓰는지를 숨기고 싶은 것

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Board board = new Board();
    board.addPost("게시물 1");
    board.addPost("게시물 2");
    board.addPost("게시물 3");

    // TODO 들어간 순서대로 순회하기
    List<Post> posts = board.getPosts();
    for (int i = 0 ; i < posts.size() ; i++) {
        Post post = posts.get(i);
        System.out.println(post.getTitle());
    }

    // TODO 가장 최신 글 먼저 순회하기
    Collections.sort(posts, (p1, p2) -> p2.getCreatedDateTime().compareTo(p1.getCreatedDateTime()));
    for (int i = 0 ; i < posts.size() ; i++) {
        Post post = posts.get(i);
        System.out.println(post.getTitle());
    }
  }
}
```

#### VO

```java
public class Board {
  List<Post> posts = new ArrayList<>();
  public List<Post> getPosts() {
    return posts;
  }
  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }
  public void addPost(String content) {
    this.posts.add(new Post(content));
  }
}
public class Post {
  private String title;
  private LocalDateTime createdDateTime;
  public Post(String title) {
    this.title = title;
    this.createdDateTime = LocalDateTime.now();
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public LocalDateTime getCreatedDateTime() {
    return createdDateTime;
  }
  public void setCreatedDateTime(LocalDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }
}
```

### Before Source

> 사용자는 무슨 집합객체인지를 파악이 불가하고, Iterator를 통해서 순회하며 조회가 가능하다

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    Board board = new Board();
    board.addPost("게시물 1");
    board.addPost("게시물 2");
    board.addPost("게시물 3");

    // TODO 들어간 순서대로 순회하기
    List<Post> posts = board.getPosts();
    Iterator<Post> iterator = posts.iterator();
    System.out.println(iterator.getClass());

    for (int i = 0 ; i < posts.size() ; i++) {
        Post post = posts.get(i);
        System.out.println(post.getTitle());
    }

    // TODO 가장 최신 글 먼저 순회하기
    Iterator<Post> recentPostIterator = board.getRecentPostIterator();
    while(recentPostIterator.hasNext()) {
        System.out.println(recentPostIterator.next().getTitle());
    }
  }
}
```

#### VO

```java
public class Board {
  List<Post> posts = new ArrayList<>();
  public List<Post> getPosts() {
    return posts;
  }
  public void addPost(String content) {
    this.posts.add(new Post(content));
  }
  public Iterator<Post> getRecentPostIterator() {
    return new RecentPostIterator(this.posts);
  }
}

public class Post {
  private String title;
  private LocalDateTime createdDateTime;
  public Post(String title) {
    this.title = title;
    this.createdDateTime = LocalDateTime.now();
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public LocalDateTime getCreatedDateTime() {
    return createdDateTime;
  }
  public void setCreatedDateTime(LocalDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }
}
```

#### Logic

> Iterator는 `java.util.Iterator`을 상속받은것

```java
public class RecentPostIterator implements Iterator<Post> {

  private Iterator<Post> internalIterator;
  public RecentPostIterator(List<Post> posts) {
    Collections.sort(posts, (p1, p2) -> p2.getCreatedDateTime().compareTo(p1.getCreatedDateTime()));
    this.internalIterator = posts.iterator();
  }

  @Override
  public boolean hasNext() {
    return this.internalIterator.hasNext();
  }

  @Override
  public Post next() {
    return this.internalIterator.next();
  }
}
```
