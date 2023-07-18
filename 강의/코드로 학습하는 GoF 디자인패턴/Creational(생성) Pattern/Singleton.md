# Singleton Pattern

## Singleton Pattern?

> 클래스의 인스턴스를 `오직 한개만`제공하며, 해당 `인스턴스를 글로벌`하게 접근 할 수 있는 방법이 필요하다.

## 생성 방법

### Example1

> `Private 생성자`와 `Static 메소드`를 통한 구현

```java
public class Singleton {
  private static Singleton instance;
  private Singleton(){}
  public static Singleton getInstance(){
    if(instance==null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```

#### Quest

1. 생성자를 private으로 만든 이유?
   1. 객체의 생성을 외부에서 하지 못하게 막기 위함.
2. getInstance() 메소드를 static으로 선언한 이유?
   1. 내부에서 직접 생성한 단 한개의 같은 객체를 반환하기 위해 접근을 하고, 메소드 콜을 할 수 있도록 하기 위함
3. getInstance()가 멀티쓰레드 환경에서 안전하지 않은 이유?
   1. 객체가 생성되어 있지 않은 상태에서 복수개의 Thread가 `동시에 getInstance`를 요청하게 될 경우 **서로 다른 두개 이상의** 인스턴스가 생성 될 수 있다.

### Example2

> Thread-Safe하지 않은 부분에 대해서 보완한 방법으로 Thread-Safe하게 작성됨
> 하지만 `synchronized`답게 동기화 처리하는 작업으로 인하여 성능상 불이익이 발생 할 수 있다.

```java
public class Singleton {
  private static Singleton instance;
  private Singleton(){}
  public static synchronized Singletom getInstance(){
    if(instance==null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```

#### Quest

1. 자바의 동기화 블럭 처리 방법은?
   1. 자원에 대해서 이미 Thread가 선점을 하게 되는 경우 다른 Thread가 접근을 하지 못하게 객체(this)에 Lock을 거는것
2. getInstance() 메소드 동기화시 사용하는 락(lock)은 인스턴스의 락인가 클래스의 락인가? 그 이유는?
   1. 헤롱헤롱...

### Example3

> Example2의 동기화 처리작업으로 인한 성능상 불이익을 해결하는 방법으로 **이른 초기화(Eager Initialization)**이라 한다.
> 다음의 상황은 이 **객체가 꼭 나중에 만들지 않아도 되고, 비용이 비싸지 않은 경우** 사용하면 유용하다.

```java
public class Singleton {
  private static final Singleton INSTANCE = new Singleton();
  private Singleton(){}
  public static synchronized Singletom getInstance(){
    return INSTANCE;
  }
}
```

#### Quest

1. 이른 초기화가 단점이 될 수도 있는 이유?
   1. 객체 생성이 길고 메모리가 많이 사용되는데 사용이 안된다면 사용하지 않는 객체를 로딩 할 떄 만들게 되는 것이 바로 성능의 문제로 이어진다.
2. 만약에 생성자에서 checked 예외를 던진다면 이 코드를 어떻게 변경해야 할까요?
   1. 헤롱헤롱

### Example4

> Example3의 경우에는 이른 초기화를 하게 될 경우, 최초 생성시 성능상 호출될떄 생성이 되도록 하면서 `Thread-Safe`한 생성을 하고 싶은 경우 사용할 수 있다.
> 해당 방법을 `double checked locking`이라고 부른다.
> 최초의 if문에서 2개의 thread가 동시에 통과한다고 해도, synchronized에서 Locking이 되고, 이후 다시한번 if문으로 체크를 하기 떄문에 Thread Safe하게 생성이 가능하다.

```java
public class Singleton {
  private static volatile Singleton instance = new Singleton();
  private Singleton(){}
  public static Singletom getInstance(){
    if(instance == null) {
      synchronized(Singleton.class) {
        if(instance == null ){
          instance = new Singleton();
        }
      }
    }
    return instance;
  }
}
```

#### More About synchronized

> 해당 코드가 Method에 synchronized를 선언하는 것보다는 성능에 이슈가 있는 이유는, Method에 선언시에는 Method가 끝날때까지 Lock이 발생하는 반면에,
> 해당 코드는 `if`절이 실행된 경우에만 Lock이 발생하기 때문

### Example 5

> 가장 권장하는 기법으로 , `static inner class`를 활용하는 기법이다.
> 1.4이하에서도 가능하며, 변수를 `volatile`으로 선언하지도 않아도 되며 Lazy한 깔끔한 방법중 하나이다.

```java
public class Singleton {
  private Singleton(){}
  private static class SingletonHolder{
    private static final Singleton SETTINGS = new Singleton();
  }
  public static Singletom getInstance(){
    return SingletonHolder.SETTINGS;
  }
}
```

### Example 6

> Enum을 활용하는 방법으로, Reflection에 안전하면서 위와 동일한 코드가 된다.
> 왜 Reflection에서 안전하냐, Enum의 경우 ClassLoader로 올려서 생성자를 가져와 생성하고자 해도, Reflection자체에서 Enum의 생성을 막아놨기에 안전할 수밖에 없는것.

> 또한 Enum은 이미 직렬화 역직렬화를 구현하였으며, 직렬화 역직렬화를 이용해서 싱글톤 기법을 꺠려고 해도 이미 구현된 직렬, 역직렬화에서 같은 객체를 보장하도록 구현되어있다.

> 단점은 미리 만들어지는것과, 상속을 사용하지 못하는 단점이 존재한다.

```java
public enum Singleton{
  INSTANCE;
}
```

## 구현된 싱글톤 패턴을 깨트리는 방법

> [생성 방법](#생성-방법)을 통해 구현을 하였다고 가정

### Reflection을 활용한 방법

#### Main Code

```java
Singleton obj1 = Singleton.getInstance();

Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
constructor.setAccessible(true); //생성자가 Private이기 떄문에 접근을 위해 필요함.
Singleton obj2 = constructor.newInstance();
System.out.println(obj1==obj2); //true?false
```

### 직렬화와 역직렬화를 활용한 방법

> 역 직렬화를 할 때는 반드시 생성자를 사용해서 생성자를 만들어 준다.
> 그렇게 생성된 객체는 `다른 객체`를 생성하게 된다.

#### Singleton Class

> 기존의 코드를 다음과 같이 직렬화가 가능하게 변경한다

```java
//Serializable ID는 그냥 생략해도됨
public class Singleton implements Serializable{
  private Singleton(){}
  private static class SingletonHolder{
    private static final Singleton SETTINGS = new Singleton();
  }
  public static Singletom getInstance(){
    return SingletonHolder.SETTINGS;
  }
}
```

#### Main Code

```java
Singleton obj1 = Singleton.getInstance();
Singleton obj2 = null
try(ObjectOutput out = new ObjectOutputStream(new FileOutputStream("FileName"))) {
  out.writeObject(obj1);
}catch(Exception e) {
  e.printStackTrace();
}

try(ObjectInput in = new ObjectInputStream(new FileInputStream("FileName"))) {
  obj2 = (Singleton)in.readObject();
}catch(Exception e) {
  e.printStackTrace();
}

System.out.println(obj1==obj2); //true?false?
```
