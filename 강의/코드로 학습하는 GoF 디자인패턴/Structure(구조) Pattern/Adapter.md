# Adapter Pattern

## Adapter?

> 클라이언트의 코드와 내가 구현한 코드가 맞지 않는 경우, 두개를 서로 변환해 줄수 있는 인터페이스를 의미한다.

> 좋은 예시로는 220V콘센트를 110V로 변환하거나 역으로 110V를 220V로 변환하는 콘센트 변환기가 있다.

## 장단점

### 장점

- 기존코드의 변경 없이 원하는 인터페이스의 구현체를 생성해 재사용
  - `개방 폐쇄 원칙`
- 기존코드가 하던 일과 특정 인터페이스 구현체로 변환하는 작업을 각기 다른 클래스로 분리 및 관리
  - `단일 책임 원칙`

### 단점

- 새 클래스가 생겨 복잡도 증가 가능성
  - 기존코드가 접근 가능하다면 해당 인터페이스를 기존코드가 구현하도록 수정하는게 좋을 수도 있음

## Usage?

> 그냥 강의에서 말을 해줘버렸는데.. Interface를 상속받는 코드를 먼저 본 순간 이걸 어디에서 사용할까 라는 생각을 해보고 다음의 가설을 세워봤다.
>
> > - Interface를 주입받는다
> > - 내가 수정할 수 없는 코드이다.
> > - 사용되는 클래스의 호출이 가능하다
>
> 이런 경우가 딱 하나 떠올랐는데.. **라이브러리 주입받은경우.. 하지만 해당 라이브러리를 제대로 사용하기 위한 경우**에 내가 Interface생성해서 직접 만들고 클래스로 주입해서 Instance를 생성후 사용한다.  
> 결과는 정답입니다~

> 만약 코드수정이 가능하면 굳이 Adapter패턴 사용할 필요없이 기존코드를 수정하면 된다..

## Source

### Before Source

#### Client Code

```java
public class LoginHandler {
  UserDetailsService userDetailsService;
  public LoginHandler(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public String login(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUser(username);
    if (userDetails.getPassword().equals(password)) {
        return userDetails.getUsername();
    } else {
        throw new IllegalArgumentException();
    }
  }
}
```

#### Target Interface

```java
public interface UserDetails {
  String getUsername();
  String getPassword();
}

public interface UserDetailsService {
  UserDetails loadUser(String username);
}
```

#### Adapter Code

```java
public class Account {
  private String name;
  private String password;
  private String email;
  /* Getter, Setter */
}

public class AccountService {

  public Account findAccountByUsername(String username) {
    Account account = new Account();
    account.setName(username);
    account.setPassword(username);
    account.setEmail(username);
    return account;
  }
  public void createNewAccount(Account account) {}
  public void updateAccount(Account account) {}
}
```

### After Source

> `Account`와 `AccountService`, `Target Code`, `Client Code`는 동일하다.

#### Adapter Code

> Target Code(Interface)를 구현한 Adapter Code

```java
public class AccountUserDetailsService implements UserDetailsService {
  private AccountService accountService;
  public AccountUserDetailsService(AccountService accountService) {
    this.accountService = accountService;
  }
  @Override
  public UserDetails loadUser(String username) {
    return new AccountUserDetails(accountService.findAccountByUsername(username));
  }
}

public class AccountUserDetails implements UserDetails {
  private Account account;
  public AccountUserDetails(Account account) {
    this.account = account;
  }
  @Override
  public String getUsername() {
    return account.getName();
  }
  @Override
  public String getPassword() {
    return account.getPassword();
  }
}
```

#### Main

> 존경합니다 백기선님

```java
public class App {
  public static void main(String[] args) {
    AccountService accountService = new AccountService();
    UserDetailsService userDetailsService = new AccountUserDetailsService(accountService);
    LoginHandler loginHandler = new LoginHandler(userDetailsService);
    String login = loginHandler.login("keesun", "keesun");
    System.out.println(login);
  }
}
```
