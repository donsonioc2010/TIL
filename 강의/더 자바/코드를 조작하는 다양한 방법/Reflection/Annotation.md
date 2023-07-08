# Annotation

> 기본적으로 Annotation은 주석이다.

## Annotation의 선언 방법

> Annotation도 결국 Java 파일이다~

```java
public @interface MyAnnotation{}
```

---

## Annotation의 범위 설정

> `@Retention`으로 설정가능하며 Default는 `RetentionPolicy의 CLASS`이다

```java
@Retention(RetentionPolicy.RUNTIME)
@Retention(RetentionPolicy.CLASS)
@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation{}
```

### RetentionPolicy의 생존 범위

> [좋은 레퍼런스](https://jeong-pro.tistory.com/234)였다...

| Enum Name | Description                                                                |
| --------- | -------------------------------------------------------------------------- |
| SOURCE    | `소스코드.java`까지만 어노테이션이 살아있는다.                             |
| CLASS     | `소스코드.class`(바이트 코드)까지만 어노테이션이 살아 있는다.              |
| RUNTIME   | 런타임까지 생존한다.(끝까지 간다, 즉 어플리케이션을 실행해도 남아 있는다.) |

#### 생존 범위와 Reflection의 관계

```java
@MyAnnotation
public class a{
  public static void main(String[] args){
    Arrays.stream(a.class.getAnootations()).forEach(System.out::println);
  }
}
```

> 다음과 같은 예제가 있을 때 IDE에서 또는 java를 활용하여 바이트 코드를 실행하게 될 때 `SOURCE`와 `CLASS`는 Runtime시 어노테이션이 모두 누락되어 있다.  
> 그렇기에 Stream을 진행할때 forEach를 실행하면서 출력을 하는 Annotation이 존재하지 않게 된다.
> 하지만 `Runtime`으로 실행하게 될 경우 실행을 할때까지도 Annotation이 존재하기 때문에 MyAnnotation의 정보가 print가 가능해지게 된다.

---

---

## Annotation의 범위 지정

> `@Target`으로 지정이 가능하며 `ElementType`의 Enum을 객체로 받는다.  
> 배열로도 설정이 가능하다.

> 자세한 설정은 ` java.lang.annotation`의

```java
@Target({ElementType.PARAMETER,ElementType.FIELD})
public @interface MyAnnotation{}

@Target(ElementType.TYPE)
public @interface MyAnnotation{}
```

---

## Annotation의 Value

> Annotation은 자체적으로 Value를 가질 수 있다 하지만 조건은 다음과 같다.

- Premitive Type과 Premitive Type의 WrapperClass Type만 가질 수 있다.

### Variable 선언 방법

> 아래의 코드와 같을때 MyAnnotation의 name은 "abc"를 가지게 된다.

```java
public @interface MyAnnotation {
	String name();
}

@MyAnnotation(name="abc")
class a{}
```

### default

> 변수를 생성했으나 default를 주지 않는 경우 꼭 값을 넣어야 하지만 default를 설정하면 변수값을 넣지 않는 경우 default의 value가 설정된다.

> 아래의 코드에서 Class a는 "jong1"이라는 Value가 들어가지만 Class b는 "abc"가 들어가게 된다.

```java
public @interface MyAnnotation {
	String name() default "jong1";
}

@MyAnnotation
class a{}
@MyAnnotation(name="abc")
class b{}
```

### 무시 가능한 VariableName?

- 아래의 코드처럼 변수명이 `value`인 경우에는 MyAnnotation에서 값을 입력할때 key값을 넣지 않아도 된다.
- 복수개를 설정해야 하는 경우에는 `value`라는 key값을 명시 해야 한다.

```java
public @interface MyAnnotation {
	String value();
  int number() default 5;
}

@MyAnnotation("abc")
class a{}
@MyAnnotation(value="abc", number=3)
class b{}
```

---

## `@Inherited`

> 상속받은 클래스에서도 슈퍼클래스에 선언된 Annotations정보를 가져오게 하고 싶은 경우 사용해야 한다.

### `@Inherited` Example Code

#### `@Inherited`가 없는 경우

> 다음의 코드의 경우 실제 out으로 출력되는 문구가 없다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface MyAnnotation3 {
	String value() default "jong1";
	int number() default 100;
}

@MyAnnotation3
class InheritedA { }

class InheritedExtendsA extends InheritedA {
	public static void main(String[] args) {
		Arrays.stream(InheritedExtendsA.class.getAnnotations()).forEach(System.out::println);
	}
}
```

#### `@Inherited`가 있는 경우

> 아래의 코드를 실행하게 될때, Console로는 `@me.jong1.anno.MyAnnotation3(value="jong1", number=100)` 다음의 출력을 하게 된다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface MyAnnotation3 {
	String value() default "jong1";
	int number() default 100;
}

@MyAnnotation3
class InheritedA { }

class InheritedExtendsA extends InheritedA {
	public static void main(String[] args) {
		Arrays.stream(InheritedExtendsA.class.getAnnotations()).forEach(System.out::println);
	}
}
```

### `@Inherited`의 존재 여부와 상관 없이 존재하는 Annotations만 보고싶은 경우

> `getDeclaredAnnotations`를 활용할 경우 해당 클래스의 정보만 가져올 수 있게 된다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface MyAnnotation3 {
	String value() default "jong1";
	int number() default 100;
}

@MyAnnotation3
class InheritedA { }

class InheritedExtendsADeclaredB extends InheritedA {
	public static void main(String[] args) {
		Arrays.stream(InheritedExtendsADeclaredB.class.getDeclaredAnnotations()).forEach(System.out::println);
	}
}
```

---

## `Field`와 `Methods`등에 붙은 Annotations정보를 가져오는 방법

### `Field`의 Annotations를 획득하는 Example Code

```java
class GetFieldsAnnotations {
	public static void main(String[] args) {
		// getDeclaredFields() 도 가능하다. 상속정보 유무에 대한 차이
		Field[] fieldAry = Book.class.getFields();
		Arrays.stream(fieldAry).forEach(f->  {
			Annotation[] aAry = f.getAnnotations();
			Arrays.stream(aAry).forEach( anno -> {
				if(anno instanceof  MyAnnotation) {
					MyAnnotation myAnno = (MyAnnotation)anno;
					System.out.println(((MyAnnotation)anno).name());
					System.out.println(((MyAnnotation)anno).number());
				}
			});
		});
	}
}
```

### `Method`의 Annotations를 획득하는 Example Code

```java
class GetMethodsAnnotations {
	public static void main(String[] args) {
		// getDeclaredMethods()도 가능 하다. 상속 유무에 대한 차이 이다.
		Method[] methodAry = Book.class.getMethods();
		Arrays.stream(methodAry).forEach(method -> {
			Annotation[] aAry =  methodAry.getClass().getAnnotations();
			Arrays.stream(aAry).forEach( anno -> {
				if(anno instanceof  MyAnnotation) {
					MyAnnotation myAnno = (MyAnnotation)anno;
					System.out.println(((MyAnnotation)anno).name());
					System.out.println(((MyAnnotation)anno).number());
				}
			});
		});
	}
}
```
