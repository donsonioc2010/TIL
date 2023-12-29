package me.jong1.proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;


class BookServiceTest {
/*	BookService bookService = (BookService)Proxy.newProxyInstance(
		BookService.class.getClassLoader(), //불러올
		new Class[] {BookService.class},
		new InvocationHandler() {
			BookService bookService = new DefaultBookService();

			@Override //해당 Proxy의 어떤 Method가 호출이 될 때  해당 Method를 어떻게 처리할 지
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("rend")) {
					System.out.println("aaaa");
					Object invoke = method.invoke(bookService, args);
					System.out.println("bbbb");
					return invoke;
				}
				return method.invoke(bookService, args);
			}
		});*/

/*	@Test
	public void di() {
		ProxyBook book = new ProxyBook();
		book.setTitle("Spring");
		bookService.rent(book);
		bookService.returnBook(book);
	}*/

	@Test
	public void di2() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
	}
}