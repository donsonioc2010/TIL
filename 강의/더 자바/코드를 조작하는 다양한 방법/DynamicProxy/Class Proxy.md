# Class의 Dynamic Proxy

## 방법

- `CGLib`
  - Spring Framework, JPA에서도 ByteCode를 조작하는 방법이 CGLib을 사용한다.
  - `JDK16`부터 오류가 발생한다.. `VMOptions`에 `--add-opens java.base/java.lang=ALL-UNNAMED`를 추가해야 한다...
- `ByteBuddy`

## CGLib를 통한 구현

```java
MethodInterceptor handler = new MethodInterceptor() {
  OnlyBookService bookService = new OnlyBookService();

  @Override
  public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

    if(method.getName().equals("rent")) {
      System.out.println("aaaa");
      Object invoke =  method.invoke(bookService, args);
      System.out.println("bbbbb");
      return invoke;
    }
    return   method.invoke(bookService, args);
  }
};
OnlyBookService bookService = (OnlyBookService)Enhancer.create(OnlyBookService.class, handler);

ProxyBook book = new ProxyBook();
book.setTitle("Spring");
bookService.rent(book);
bookService.returnBook(book);
```

## ByteBuddy를 통한 구현방법

```java
Class<? extends OnlyBookService> proxyClass =
  new ByteBuddy().subclass(OnlyBookService.class)
  .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
      OnlyBookService onlyBookService = new OnlyBookService();
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("aaaa");
        Object invoke = method.invoke(onlyBookService, args);
        System.out.println("Bbbb");
        return invoke;
      }
    }))
  .make()
  .load(OnlyBookService.class.getClassLoader()).getLoaded();
OnlyBookService bookService = proxyClass.getConstructor(null).newInstance();
ProxyBook book = new ProxyBook();
book.setTitle("Spring");
bookService.rent(book);
bookService.returnBook(book);
```

## 주의사항

> final을 활용해 추가적인 상속을 못하게 한다던가, 생성자가 Private한 생성자 밖에 없는 경우 불가능하다.
