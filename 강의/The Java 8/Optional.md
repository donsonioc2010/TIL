# Optional

## Optional이란?

> `Optional<T>`에서의 T객체 단 한개가 들어 있을 수도, Null이 들어있을수도 있는 컨테이너 Object

## Optional을 활용하는 이유?

> **Null**에서 안전한 개발을 하기 위함이 가장 크다. 휴면 에러를 방지하기 위함.

### 휴면에러의 정의

> 기존의 코드의 경우 [Example Code](#example-code)에 기록된 대표적으로 생각해볼만한 3가지정도의 방법으로 Null 처리를 진행했다.
> 하지만 코드의 작성자는 **사람**이기 떄문에 **체크를 한다는 작업의**누락을 할 수도 있다.
> 이러한 작업의 누락으로 발생할 수 있는 실수를 **휴면에러**로 정의한다.

#### Example Code

- Getter에서 Exception을 던지기

```java
class a{
  private String b;

  public String getB() {
    if(b!= null){
      return b;
    }
    throw new RuntimeException();
  }
}
```

- Null을 그대로 Return

```java
class a{
  private String b;

  public String getB() {
    return b;
  }
}
```

- 로직에서 직접 Null처리

```java
class a{
  private String b;

  public String getB() {
    return b;
  }
}
class c {
  public static void main(String[] args) {
    if(new a().getB()==null){
      System.out.println("null이야~");
    }
  }
}
```

## Optional 의 주의사항

- Optional의 권장사항으로 **Return타입** 에만 사용한다. [Optional Getter만들기](#optional-getter-만들기)
  - Setter에도 매개변수로 줄 수도, Field로도 생성은 가능하다. 하지만 `주입 과정`에서 애초애 Null을 주입해버리면 존재자체가 Null이기 때문에 NPE가 발생할 수밖에 없다.
    - 이로인해 안전한 개발을 하기 위해서라면 return타입으로만 지정을 한다.
- Optional을 return하는 getter에서 `return null`로 return하지 말아야 한다.
  - 당연한 얘길;
- Primitive Type을 위한 Optinal은 따로 존재한다
  - OptionalInt, OptionalLong 등등
- `Collection`, `Map`, `Stream`, `Array`, `Optional`은 Optional로 감싸지 말자
  - `Optional<Optional<T>>`같은 방법으로 쓰지 말라는 것
  - 이유는 이미 그 자체가 비어있는지 안비었는지 **판단**을 할 수 있는 컨테이너 객체들 이기 때문이다.

## Optional 활용 방법

> 다음의 클래스가 있다고 모든 코드에 가정해본다.

```java
@Data
class human{
  public String name;
  public int key;
  public char gender;
  public String email;
}
```

### Optional Getter 만들기

> 간단한 Example로 name을 Return하는 방법을 만들어본다.

```java
// 기존에 Auto Complete되는 Getter방법
public String getName() {
  return name;
}

//Optional을 활용하는 방법
public Optional<String> getName(){
  return Optional.ofNullable(name);
}
```

### Optional 생성

| MethodName      | Description                                 |
| --------------- | ------------------------------------------- |
| of(T t)         | 무조건 객체가 존재해야 한다                 |
| ofNullable(T t) | Null이 들어갈 수도, 객체가 들어 갈 수도있다 |
| empty()         | 비어있는 Optional                           |

```java
Optional.of(new human());

Optional.ofNullable(new human());
Optional.ofNullable(null);

Optional.empty(null);
```

### Optional 객체 존재 여부 확인방법

| MethodName  | Description                                                        |
| ----------- | ------------------------------------------------------------------ |
| isPresent() | 객체가 존재하는 경우 True, 없으면 False를 반환한다.                |
| isEmpty()   | 비어있는 경우 True, 존재하면 False를 반환하며 JDK11부터 제공되었다 |

```java
Optional.ofNullable(new human()).isPresent();
Optional.ofNullable(new human()).isEmpty();
```

### Optional의 값을 가져올 떄

| MethodName | Description                                            |
| ---------- | ------------------------------------------------------ |
| get()      | 객체가 존재하는 경우 사용했던 T타입의 객체를 반환한다. |

```java
		Optional<String> a = Optional.ofNullable("abc");
		a.get();
```

### Optional 존재여부 검증 및 작업

| MethodName          | Description                                                         |
| ------------------- | ------------------------------------------------------------------- |
| ifPresent(Consumer) | 객체가 존재하는 경우 해당 객체를 파라미터 삼아 Consumer를 실행한다. |

```java
  Optional<String> a = Optional.ofNullable("abc");
  a.ifPresent(System.out::println);
```

### Optional에 값이 없는 경우 리턴

| MethodName                   | Description                                                                                       |
| ---------------------------- | ------------------------------------------------------------------------------------------------- |
| orElse(T t)                  | 값이 존재하는 경우 해당 값을, 없는 경우 t를 Return한다                                            |
| orElse(Supplier<Optional T>) | 값이 없는 경우 Lambda의 Supplier를 실행하고, return 타입으로 Optonal에 지정한 Type을 Return 한다. |

```java
  Optional<String> a = Optional.ofNullable("abc");
  a.orElse("없어");

  Optional<String> a = Optional.ofNullable("abc");
  a.orElseGet(() -> "wow!!!!");
```

### Optional에 값이 없는 경우 예외처리

| MethodName                | Description                                             |
| ------------------------- | ------------------------------------------------------- |
| orElseThrow()             | 값이 없는 경우 예외처리를 한다                          |
| orElseThrow(Supplier<EX>) | 값이 없는 경우 Supplier를 통해 Return된 예외처리를 한다 |

```java
  Optional<String> a = Optional.ofNullable("abc");
  a.orElseThrow();
  a.orElseThrow(() -> new RuntimeException("삐용삐용"));
```

### Optional Filter

| MethodName        | Description          |
| ----------------- | -------------------- |
| filter(Predicate) | True인 경우만 남긴다 |

```java
  Optional<String> a = Optional.ofNullable("abc");
  a.filter(s-> !s.equals("abc"));
```

### Optional 값 변환하기

| MethodName        | Description                                                                              |
| ----------------- | ---------------------------------------------------------------------------------------- |
| map(Predicate)    | True인 경우만 남긴다                                                                     |
| flatMap(Function) | Optional안에 들어있는 인스턴스가 Optional인 경우 활용하면 편하며 모든 Optional을 까준다. |

```java
  Optional<String> a = Optional.ofNullable("abc");
  a.map(s->1);
```
