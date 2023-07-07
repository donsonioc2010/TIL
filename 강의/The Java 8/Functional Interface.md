# Functional Interface

> 함수형 인터페이스와 람다 표현식에 대한 활용법

## Functional Interface란?

> 함수형 인터페이스란 Interface에서 **단 한개**의 추상 메소드만 선언한 인터페이스를 의미한다.
> 여기서 메소드가 단 한개라는 의미는 Interface내부에 몇개의 메소드가 있는지는 상관으며 실제 구현해야할 추상메소드가 단 한개만 있으면 함수형 인터페이스다.
> Interface에서 사용가능한 메소드의 종류에 대해서는 [Interface](./Interface.md)토픽에서 에서 자세히 보자

### `@FunctionalInterface`란?

> 사실 해당 어노테이션이 없다고 해도, 결국 단 한개의 추상메소드만 있으면 함수형 인터페이스이기 떄문에
> 무조건적으로 필요하지는 않으나 아래의 이유들로 인해 선언을 하는게 좋다 정도라 말할수 있을 듯하다.

- Interface 위에 선언이 가능하다
  - 해당 어노테이션을 선언하게 될 경우 추상메소드를 **복수개로 생성**하게 되면 **컴파일 에러**를 안내한다.
  - 사용을 하는 이유는 위와 같이 컴파일 에러를 발생시켜 주기 떄문에 견고하게 함수형 인터페이스의 관리가 가능해진다.

### Java API에서 제공하는 기본적으로 생성한 Functional Interface?

> 일단 기본적으로 `java.util.function` 패키지에서 추가적으로 확인이 가능하므로, 필요할떄마다 보면 되며 자주사용되는 항목은 다음과 같다.

- `Function<T, R>`
- `BiFunction<T, U, R>`
- `Consumer<T>`
- `Supplier<T>`
- `Predicate<T>`
- `UnaryOperator<T>`
- `BinaryOperator<T>`

#### `Function<T, R>`

> T 타입을 받아서 R 타입을 Return하는 Functional Interface

- 정의된 메소드
  - R apply(T t)
- 함수 조합용 메소드
  - b.andThen(FunctionInterface a)
    - b Function을 먼저 실행한 이후 Return된 R타입을 기반으로 a를 실행한다.
  - b.compose(FunctionInterface a)
    - a Function을 먼저 실행한 이후 Return된 R타입을 기반으로 b를 실행한다.

#### `BiFunction<T, U, R>`

> 두개의 타입 T, U를 받아서 R 타입을 Return하는 Functional Interface

- 정의된 메소드
  - R apply(T t, U u)
- 함수 조합용 메소드
  - `b.andThen(Function<? super R>, <? extends V>)`
    - BiFunction을 실행할때 받은 R을 제공해서 V타입을 Return한다.
    - b라는 BiFunction변수를 만든경우 b를 먼저 실행한 이후 인자값으로 제공한 Function 객체를 실행해서 V타입을 Return받는다.

#### `Consumer<T>`

> T타입을 받아서 Return Type은 Void인 Functional Interface이다.

- 정의된 메소드
  - void Accept(T t)
- 조합용 메소드
  - `andThen(Consumer<? super T>)`
    - 먼저 생성한 Functional Interface를 실행한 이후 인자값으로 제공한 Consumer 인터페이스를 실행한다
    - 처음에 생성한 T를 그대로 사용한다.

#### `Supplier<T>`

> T타입의 값을 Return하는 Functional Interface로, 매개변수 Type은 없다.

- 정의된 메소드
  - T get()
- 조합용 메소드
  - 없음

#### `Predicate<T>`

> T타입의 값을 받아서 boolean Type을 Return하는 Functional Interface

- 정의된 메소드
  - boolean test(T t)
- 조합용 메소드
  - 세개의 메소드 모두 Predicate<T>를 Return Type으로 반환한다. boolean으로 반환하지 않는다.
  - `and(Predicate <T>)`
    - 인자값으로 제공한 Predicate와 기존의 Predicate 모두 True인 경우 True를 Return하는 Predicate를 생성한다
  - `or(Predicate <T>)`
    - 인자값으로 제공한 Predicate와 기존의 Predicate중 하나라도 true인 경우 true를 Return하는 Predicate를 생성한다
  - `negate()`
    - 기존의 결과가 true인 경우 false를, false는 true를 리턴하는 새로운 Predicate를 생성한다.

#### `UnaryOperator<T>`

> [Function<T, R>](#functiont-r)의 특수한 형태로, 입력값과 출력값이 모두 T타입인 경우 활용하면 편하다
> `Function<T, T>`를 사용하는 것이다. Function을 상속받았다.

#### `BinaryOperator<T>`

> `UnaryOperator`처럼 [BiFunction<T,U,R>](#bifunctiont-u-r)의 특수한 형태로, 2개의 입력 타입과 출력이 모두 T인 경우 활용하면 된다.
> 내부에서 `BiFunction<T, T, T>`로 상속중이다.

## Lambda Expression

### 사용전 주의사항

> 기존의 Lambda의 경우 로컬클래스, 익명클래스의 확장기능이지만 변수의 참조에 대해서 주의하도록 하자.
> 기존의 로컬, 익명 클래스의 경우 [Shadowing](#shadowing이란) 이 발생하지만 Lambda에서는 발생하지 않는다.
> 로컬 클래스와 익명 클래스는 내부에서 새로운 Scope이 발생해 자신만의 영역이 있는 반면에 Lambda의 경우 사용이 되고있는 해당 Scope에 존재하기 떄문에 같은 변수명을 새로 Shadowing해서 활용이 불가능하다.

### Shadowing이란?

> 로컬클래스 , 익명클래스의 경우에는 같은 변수명으로 활용하게 될 때 기존의 변수명이 Shadowing현상에 의해서 가려진다.
> 즉 외부의 선언이 있다고 해도 내부 클래스의 재선언으로 인해 상위 변수가 가려지는 것을 의미한다.

#### Shadowing 참조코드

> 아래의 코드의 결과는 어떻게 될까?
> 전부 a라는 변수를 System.out.println을 진행을 하고 있다, 하지만 모두 결과값은 다르다.
> 아래의 상황이 Shadowing이다.

```java
public class Shadowing {
	public static void main(String[] args) {
		new Shadowing().test();
	}
	public void test() {
		int a = 5;
		class LocalClass implements Consumer<String> {
			@Override
			public void accept(String unused) {
				int a = 999;
				System.out.println(a);
			}
		}

		Consumer<String> anonymousClass = new Consumer<String>() {
			@Override
			public void accept(String unused) {
				int a = 111;
				System.out.println(a);
			}
		};

		Consumer<String> lambdaClass = s-> System.out.println(a);

		new LocalClass().accept("a"); // 999
		anonymousClass.accept("a");   // 111
		lambdaClass.accept("a");      // 5
	}
}
```
