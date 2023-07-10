# Dynamic Proxy

## Dynamic Proxy?

> Application이 실행되는 도중에(Runtime시) 특정 Interface들을 구현하는 클래스 또는 인스턴스를 만드는 기술

## Reflection Instance

> `Proxy`의 `newProxyInstance(ClassLoader, Class<?>[](구현해야 하는 인터페이스 타입), InvocationHandler)`를 필요로 하며 `InvocationHandler`에서 `Object`타입을 반환한다.
> 하지만 두번째 인자인 인터페이스 타입을 클래스로 제공하기 때문에 형변환이 가능하다.

> Proxy를 통해 동적으로 작업을 추가하고 싶은 일을 InvocationHandler에서 추가한다.

```java
BookService bookService = (BookService) Proxy.newProxyInstance(
		BookService.class.getClassLoader(), //불러올
		new Class[] {BookService.class},
		new InvocationHandler() {
			BookService bookService = new DefaultBookService();

			@Override //해당 Proxy의 어떤 Method가 호출이 될 때  해당 Method를 어떻게 처리할 지
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if(method.getName().equals("rend")) {
					System.out.println("aaaa");
					Object invoke = method.invoke(bookService, args);
					System.out.println("bbbb");
					return invoke;
				}
				return method.invoke(bookService, args);
			}
		});
```

## 제약사항

> 자바의 **Dynamic Proxy Class**의 두번째 인자가 **Class**타입을 읽지를 못한다. **무조건 인터페이스 타입**이어야 한다.
>
> > 클래스로 밖에 존재하지 않으면 사용을 못한다.
