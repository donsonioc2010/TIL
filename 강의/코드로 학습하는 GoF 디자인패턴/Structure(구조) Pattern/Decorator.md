# Decorator Pattern

## Decorator ?

> 기존 코드를 변경하지 않고 부가 기능을 추가하는 패턴이다.
>
> 상속이 아닌 위임을 사용해서 보다 유연하게 런타임에 부가기능의 추가하는 것도 가능하다

## 장단점

### 장점

- 새로운 클래스를 기존의 기능에서 상속받아 제작하지 않고, 기존 기능의 조합이 가능하다.
- 컴파일 타임이 아닌 런타임에 동적으로 기능의 변경이 가능하다.

### 단점

- 조합코드가 복잡해 질 수 있다.

## Source

### Before Source

#### Main

> 상속으로만 문제를 해결하려 하는 경우... 지속적으로 클래스를 상속받아 하위 클래스로 객체가 이동된다

```java
public class Client {
  private CommentService commentService;
  public Client(CommentService commentService) {
    this.commentService = commentService;
  }
  private void writeComment(String comment) {
    commentService.addComment(comment);
  }
  public static void main(String[] args) {
    Client client = new Client(new SpamFilteringCommentService());
    client.writeComment("오징어게임");
    client.writeComment("보는게 하는거 보다 재밌을 수가 없지...");
    client.writeComment("http://whiteship.me");
  }
}
```

#### Comment

```java
public class CommentService {
  public void addComment(String comment) {
    System.out.println(comment);
  }
}

public class SpamFilteringCommentService extends CommentService {
  @Override
  public void addComment(String comment) {
    boolean isSpam = isSpam(comment);
    if (!isSpam) { super.addComment(comment);}
  }

  private boolean isSpam(String comment) {
    return comment.contains("http");
  }
}

public class TrimmingCommentService extends CommentService {
  @Override
  public void addComment(String comment) {super.addComment(trim(comment));}

  private String trim(String comment) {return comment.replace("...", "");}
}
```

### After Source

> 원하는 기능을 작은단위로 제작한 이후, 조합을 해서 사용이 가능하다.

#### Main

```java
public class App {
  private static boolean enabledSpamFilter = true;
  private static boolean enabledTrimming = true;
  public static void main(String[] args) {
    CommentService commentService = new DefaultCommentService();

    if (enabledSpamFilter) {
        commentService = new SpamFilteringCommentDecorator(commentService);
    }

    if (enabledTrimming) {
        commentService = new TrimmingCommentDecorator(commentService);
    }

    Client client = new Client(commentService);
    client.writeComment("오징어게임");
    client.writeComment("보는게 하는거 보다 재밌을 수가 없지...");
    client.writeComment("http://whiteship.me");
  }
}

public class Client {
  private CommentService commentService;

  public Client(CommentService commentService) {
    this.commentService = commentService;
  }

  public void writeComment(String comment) {
    commentService.addComment(comment);
  }
}
```

#### Service

```java
public interface CommentService {
  void addComment(String comment);
}

public class DefaultCommentService implements CommentService {
    @Override
    public void addComment(String comment) {
        System.out.println(comment);
    }
}
```

#### Decorator

```java
public class CommentDecorator implements CommentService {
  private CommentService commentService;
  public CommentDecorator(CommentService commentService) {
    this.commentService = commentService;
  }

  @Override
  public void addComment(String comment) {
    commentService.addComment(comment);
  }
}

public class SpamFilteringCommentDecorator extends CommentDecorator {
  public SpamFilteringCommentDecorator(CommentService commentService) {
    super(commentService);
  }
  @Override
  public void addComment(String comment) {
    if (isNotSpam(comment)) {
      super.addComment(comment);
    }
  }
  private boolean isNotSpam(String comment) {
    return !comment.contains("http");
  }
}

public class TrimmingCommentDecorator extends CommentDecorator {
  public TrimmingCommentDecorator(CommentService commentService) {
    super(commentService);
  }

  @Override
  public void addComment(String comment) {
    super.addComment(trim(comment));
  }

  private String trim(String comment) {
    return comment.replace("...", "");
  }
}
```
