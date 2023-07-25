# Facade Pattern

## Facade

> 복잡한 서브 시스템 의존성을 최소화 하는 패턴
>
> > 클라이언트가 사용해야 하는 복잡한 서브 시스템의 의존성을 간단한 인터페이스로 추상화를 할 수 있다.

## 장단점

### 장점

- 서브 시스템에 대한 의존성을 한곳에 몰아 놓을 수가 있다.

### 단점

- Facade자체가 서브시스템에 대한 의존성을 모두 가지게 된다.

## Source

### Before Source

#### Client

```java
public class Client {
  public static void main(String[] args) {
    String to = "keesun@whiteship.me";
    String from = "whiteship@whiteship.me";
    String host = "127.0.0.1";

    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);

    Session session = Session.getDefaultInstance(properties);

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Test Mail from Java Program");
        message.setText("message");

        Transport.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}

```

### After Source

#### Client

```java
public class Client {
  public static void main(String[] args) {
    EmailSettings emailSettings = new EmailSettings();
    emailSettings.setHost("127.0.0.1");

    EmailMessage emailMessage = new EmailMessage();
    emailMessage.setFrom("keesun");
    emailMessage.setTo("whiteship");
    emailMessage.setCc("일남");
    emailMessage.setSubject("오징어게임");
    emailMessage.setText("밖은 더 지옥이더라고..");

    EmailSender emailSender = new EmailSender(emailSettings);
    emailSender.sendEmail(emailMessage);
  }
}
```

#### Message

> EmailSender의 try문의 MimeMessage를 EmailMessage에서 작성하면 어떨까? 싶었는데..
> 외부 API를 사용하게 될 경우 MimeMessage를 안쓸 수도 있겠다는 생각이 들어서.. Sender에 있는게 맞겠다는 생각이 든다..

```java
public class EmailSettings {
  private String host;
  public String getHost() {return host;}
  public void setHost(String host) {this.host = host;}
}

public class EmailSender {
  private EmailSettings emailSettings;
  public EmailSender(EmailSettings emailSettings) {
      this.emailSettings = emailSettings;
  }

  public void sendEmail(EmailMessage emailMessage) {
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", emailSettings.getHost());

    Session session = Session.getDefaultInstance(properties);

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(emailMessage.getFrom()));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailMessage.getTo()));
      message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailMessage.getCc()));
      message.setSubject(emailMessage.getSubject());
      message.setText(emailMessage.getText());

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}

public class EmailMessage {
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
    // Getter, Setter
}
```
