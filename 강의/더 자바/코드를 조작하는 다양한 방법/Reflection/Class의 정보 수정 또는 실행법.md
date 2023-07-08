# Reflection API2부, 클래스의 정보 수정 또는 실행

## 정보를 담을 클래스 Example Code

```java
public class Book2 {
	public static String A = "A";
	private String B = "B";
	public Book2() {}
	public Book2(String b) {this.B = b;}
	public void c() {System.out.println("C");}
	private void d() {System.out.println("D");}
	private void d(int a) {System.out.println("D" + " " + a);}
	public int sum(int left, int right) {return left + right;}
	public static void staticA() {System.out.println("뭘구분해?");}
	public static void staticA(boolean a) {
		if (a) System.out.println("TRUE!!!");
		else System.out.println("FALSE!!!");
	}
}

```

---

## Class의 로드 방법

> 두개 모두 Class의 정보를 접근하고 획득하는 방법이다.

```java
Class<?> book2class = Class.forName("me.jong1.reflection.Book2");
Class<Book2> book2Class2 = Book2.class;
```

---

## 인스턴스 객체를 생성하는 방법

> 인스턴스 객체를 생성하려면 결국 `생성자`를 실행해야 한다.  
> 실행하는 방법은 크게 두가지 분류로 다음과 같다.

### 매개변수가 필요없는 생성자 사용법

```java
// 기본 새생성자를 사용하고 싶은 경우
Constructor<?> constructor = book2class.getConstructor(null);
Book2 o = (Book2)constructor.newInstance(); //Instance를 생성하는 방법
System.out.println(o);
```

### 매개변수가 존재하는 생성자 사용법

```java
// 파라미터가 존재하는 생성자를 사용하고 싶은 경우, 해당 생성자가 받는 Type을 입력하면 된다.
Constructor<?> constructor2 = book2class.getConstructor(String.class);
Book2 o2 = (Book2)constructor2.newInstance("a"); // getConstructor당시 입력한 파라미터를 그대로 입력해야 한다.
System.out.println(o2);
```

---

## Fields

> 코드의 경우 위의 코드들에 영향을 받는다.

### Static Field

> `Field` Object의 경우에는 값의 획득 또는 수정을 할 때 `get`, `set`을 바탕으로 이뤄지며, Object obj매개변수 영역에 이미 생성이 된 객체를 제공해야하나, Static Field의 경우 JVM이 실행되고 ClassLoader를 통해 호출이 되어서 JVM에 존재하면 읽을 수 있기 때문에 바로 접근이 가능하다.  
> `get`또는 `set`에 매개변수로 `null`을 넣어도 상관없는 이유가 이미 `로드가 되고 접근이 가능한 상태`이기 떄문이다.
>
> > Static Field를 호출 할 떄 이미 생성된 Instance객체를 매개변수로 집어넣어도 정상적인 값이 나오므로 상관은 없다 (둘다 같은 메모리를 호출하는 것이기 떄문...)

#### 객체 획득(접근)

> Field의 객체 획득 방법

```java
Field a1 = Book2.class.getDeclaredField("A");
Field a2 = o.getClass().getDeclaredField("A");
```

#### 값 획득

> 위의 [객체 획득](#객체-획득접근)에서 가져온 객체를 바탕으로 실행하였다.

```java
System.out.println(a1.get(null));
System.out.println(a2.get(null));
```

#### 수정

> 위의 [객체 획득](#객체-획득접근)에서 가져온 객체를 바탕으로 실행하였다.

```java
a1.set(null, "AAAA");
System.out.println("a1 : "+a1.get(null));
a2.set(null, "BBBB");
System.out.println("a2 : "+a2.get(null));
System.out.println("a1 : "+a1.get(null));
```

### Instance Field

> Instance Field를 접근하기 위해서는 사전에 인스턴스의 생성이 필요하다.  
> 인스턴스의 경우 [인스턴스 생성코드 1](#매개변수가-필요없는-생성자-사용법), [인스턴스 생성코드 2](#매개변수가-존재하는-생성자-사용법)를 통해 생성하였다고 가정하겠다.

### Private Field

> Private Field의 경우 접근제어자에서부터 접근이 불가능하기에 추가적인 설정이 필요하다.

#### 필드 접근 방법 및 사용가능하도록 수정하는 방법

```java
Field b1 = Book2.class.getDeclaredField("B");
Field b2 = o.getClass().getDeclaredField("B");
b1.setAccessible(true);
b2.setAccessible(true);
```

#### Field 값 획득 방법

```java
System.out.println(b1.get(o));
System.out.println(b2.get(o2));
```

#### Field 값 수정 방법

> 파라미터로 제공하는 `Instance`의 값을 가져오는 것이다...
> 그렇기에 `Instance`로 제공되는 객체의 값을 가져온다

```java
b1.set(o, "AAAA");
System.out.println("b1 : "+b1.get(o));
b2.set(o2, "BBBB");
System.out.println("b2 : "+b2.get(o2));
System.out.println("b1 : "+b1.get(o2));
```

---

## Method

### Static Method

#### Method 획득

> `접근 제어자`의 구분 없이 획득이 가능하다. 만약 `private`, `protected`등 **사용이 불가능한 접근 제어자**인 경우 `setAccessible`설정을 해주자

```java
Method staticA = Book2.class.getDeclaredMethod("staticA");
Method staticABool = Book2.class.getDeclaredMethod("staticA", boolean.class);
```

#### 실행

```java
staticA.invoke(null);
staticABool.invoke(null, true);
staticABool.invoke(null, false);
```

### Private Method 접근 방법

#### Method 획득

> getDeclaredMethod가 Private을 가져올 수 있기 떄문, Public이어도 같이 사용 가능하다.

```java
Method d = Book2.class.getDeclaredMethod("d");
Method d2 = Book2.class.getDeclaredMethod("d", int.class);
```

#### 사용가능하도록 수정방법

```java
d.setAccessible(true);
d2.setAccessible(true);
```

#### 실행 방법

> 메소드의 실행은 `invoke`로 가능하며, 요구하는 매개변수는 `invoke(Object, Parameter...)`와 같다.

```java
d.invoke(o);
d2.invoke(o, 5);
```
