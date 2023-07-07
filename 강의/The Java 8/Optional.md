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

- Optional의 권장사항으로 **Return타입** 에만 사용한다.
  - Setter에도 매개변수로 줄 수도, Field로도 생성은 가능하다. 하지만 `주입 과정`에서 애초애 Null을 주입해버리면 존재자체가 Null이기 때문에 NPE가 발생할 수밖에 없다.
    - 이로인해 안전한 개발을 하기 위해서라면 return타입으로만 지정을 한다.
- Optional을 return하는 getter에서 `return null`로 return하지 말아야 한다.
  - 당연한 얘길;
- Primitive Type을 위한 Optinal은 따로 존재한다
  - OptionalInt, OptionalLong 등등
- `Collection`, `Map`, `Stream`, `Array`, `Optional`은 Optional로 감싸지 말자
  - `Optional<Optional<T>>`같은 방법으로 쓰지 말라는 것
  - 이유는 이미 그 자체가 비어있는지 안비었는지 **판단**을 할 수 있는 컨테이너 객체들 이기 때문이다.
