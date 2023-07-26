# Observer(옵저버) Pattern

## Observer

> 다수의 객체가 특정 객체 상태의 변화를 감지와, 알림을 받는 패턴으로 발행(pub)-구독(sub)관계를 구현할 떄 특히 유용하게 사용이 가능하다.

### Structure

![Observer Structure](img/Observer%20Structure.png)

## 장단점

### 장점

- 상태를 변경하는 객체(`Pub`)과 변경을 감지하는 객체(`Sub`)의 관계를 느슨하게 유지하는게 가능하다
- `Subject(ChatServer)`의 상태 변경을 주기적으로 조회하지 않고도 자동으로 감지가 가능하다.
- 런타임에 옵저버를 추가 또는 제거가 가능하다

### 단점

- 복잡도가 증가
- 다수의 Observer객체를 등록 이후 해지하지 않는다면, Memory Leak의 발생 가능성이 높다.

## Source

### Before Source

> Before의 문제점은 사용자가 getMessage를 하지 않는다면, 추가적으로 발송된 채팅 내역이 Update가 되지 않는 문제가 있다.

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer();

    User user1 = new User(chatServer);
    user1.sendMessage("디자인패턴", "이번엔 옵저버 패턴입니다.");
    user1.sendMessage("롤드컵2021", "LCK 화이팅!");

    User user2 = new User(chatServer);
    System.out.println(user2.getMessage("디자인패턴"));

    user1.sendMessage("디자인패턴", "예제 코드 보는 중..");
    System.out.println(user2.getMessage("디자인패턴"));
  }
}
```

#### VO

```java
public class User {
  private ChatServer chatServer;
  public User(ChatServer chatServer) {
    this.chatServer = chatServer;
  }

  public void sendMessage(String subject, String message) {
    chatServer.add(subject, message);
  }

  public List<String> getMessage(String subject) {
    return chatServer.getMessage(subject);
  }
}
```

#### Logic

```java
public class ChatServer {
  private Map<String, List<String>> messages;
  public ChatServer() {
    this.messages = new HashMap<>();
  }

  public void add(String subject, String message) {
    if (messages.containsKey(subject)) {
        messages.get(subject).add(message);
    } else {
        List<String> messageList = new ArrayList<>();
        messageList.add(message);
        messages.put(subject, messageList);
    }
  }

  public List<String> getMessage(String subject) {
    return messages.get(subject);
  }
}
```

### After Source

> Logic(`ChatServer`)에서 메세지를 수신받는 경우 모든 Sub에게 발송을 해줘서 Update해야하는 정보가 있는 경우 모든 구독자가 알 수 있다.

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer();
    User user1 = new User("keesun");
    User user2 = new User("whiteship");

    chatServer.register("오징어게임", user1);
    chatServer.register("오징어게임", user2);

    chatServer.register("디자인패턴", user1);

    chatServer.sendMessage(user1, "오징어게임", "아.. 이름이 기억났어.. 일남이야.. 오일남");
    chatServer.sendMessage(user2, "디자인패턴", "옵저버 패턴으로 만든 채팅");

    chatServer.unregister("디자인패턴", user2);

    chatServer.sendMessage(user2, "디자인패턴", "옵저버 패턴 장, 단점 보는 중");
  }
}
```

#### VO

```java
public interface Subscriber {
  void handleMessage(String message);
}

public class User implements Subscriber {
  private String name;
  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public void handleMessage(String message) {
    System.out.println(message);
  }
}

```

#### Logic

```java
public class ChatServer {
  private Map<String, List<Subscriber>> subscribers = new HashMap<>();

  public void register(String subject, Subscriber subscriber) {
    if (this.subscribers.containsKey(subject)) {
      this.subscribers.get(subject).add(subscriber);
    } else {
      List<Subscriber> list = new ArrayList<>();
      list.add(subscriber);
      this.subscribers.put(subject, list);
    }
  }

  public void unregister(String subject, Subscriber subscriber) {
    if (this.subscribers.containsKey(subject)) {
      this.subscribers.get(subject).remove(subscriber);
    }
  }

  public void sendMessage(User user, String subject, String message) {
    if (this.subscribers.containsKey(subject)) {
      String userMessage = user.getName() + ": " + message;
      this.subscribers.get(subject).forEach(s -> s.handleMessage(userMessage));
    }
  }
}
```
