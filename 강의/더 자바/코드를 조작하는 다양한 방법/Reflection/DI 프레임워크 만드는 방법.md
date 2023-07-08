# 나만의 DI 프레임 워크 만들기

## 강의 목표

> `@Inject`라는 어노테이션을 사용해서 필드 주입해주는 컨테이너 서비스를 만드는 것을 목표로 한다.  
> `Reflection`을 연습하기 위한것이 목표.

## Example Code

### Architecture

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {}

public class BookService {
	@Inject
	BookRepository bookRepository;
}

public class BookRepository {
}
```

### Test Code

> `BookRepository`의 경우에는 `@Inject` 어노테이션이 존재하지 않기에 바로 주입이 가능하지만,  
> `BookService`의 경우에는 `@Inject`을 `BookRepository`를 받아야 하기 때문에 repository까지 같이 `NullCheck`를 진행해야 한다.

```java
public class ContainerServiceTest {
	@Test
	public void getObject_BookRepository() {
		BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
		Assertions.assertNotNull(bookRepository);
	}

	@Test
	public void getObject_BookService() {
		BookService bookService = ContainerService.getObject(BookService.class);
		Assertions.assertNotNull(bookService);
		Assertions.assertNotNull(bookService.bookRepository);
	}
}
```

### 구현 코드

```java
public class ContainerService {
	public static <T> T getObject(Class<T> classType) {
		T instance = createInstance(classType);
		// 각 Fields의 Annotation을 확인
		Arrays.stream(classType.getDeclaredFields()).forEach(f -> {
			if(f.getAnnotation(Inject.class) != null) {
				Object fieldInstance = createInstance(f.getType());
				f.setAccessible(true);
				try {
					f.set(instance, fieldInstance);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});
		return instance;
	}

	private static <T> T createInstance(Class<T> classType) {
		try {
			return classType.getConstructor(null).newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
```

## 만든 Framework를 사용하는 방법

> 강의 기준이 Maven이다 보니 ... 일단 Maven기준이긴 하나 Gradle이도 결곡 `.m2`(dependencies)들이 참고하는 로컬 경로로 들어가게만 하면 된다..

### Maven기준 확인해봐야 하는 경로

> .m2가 현재 나의 로컬 Maven 디렉토리다 다음과 같이 jar가 생성되면 된다.

```log
[INFO] Installing /Users/{HostName}/Desktop/Study/인프런/Refactoring-example/target/refactoring-example-1.0-SNAPSHOT.jar to /Users/{HostName}/.m2/repository/me/jong1/refactoring-example/1.0-SNAPSHOT/refactoring-example-1.0-SNAPSHOT.jar
[INFO] Installing /Users/{HostName}/Desktop/Study/인프런/Refactoring-example/pom.xml to /Users/{HostName}/.m2/repository/me/jong1/refactoring-example/1.0-SNAPSHOT/refactoring-example-1.0-SNAPSHOT.pom
```

### 타 프로젝트에서 Dependencies 추가 방법

> 기존에 생성했던 Project의 정보를 참조하는 프로젝트에서 기록해주면 된다.

```XML
<dependency>
    <groupId>me.jong1</groupId>
    <artifactId>refactoring-example</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 타 프로젝트에서 사용하는 Code

```java
public class AccountRepository {
	public void save() {
		System.out.println("Account Save Repository");
	}
}

public class AccountService {
	@Inject
	AccountRepository accountRepository;


	public void join() {
		System.out.println("AccountService.join");
		accountRepository.save();
	}
}

public class App {
  public static void main( String[] args ){
    AccountService accountService = ContainerService.getObject(AccountService.class);
    accountService.join();
  }
}

```
