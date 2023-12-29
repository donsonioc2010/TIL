package me.jong1;

import java.util.Arrays;

public class MethodExample {
	public static void main(String[] args) {
		Class<Book> bookClass = Book.class;
		Book book = new Book();

		// 정의한 내용 뿐 아니라 상속되는 Method들도 가져온다.
		System.out.println("=======Methods=======");
		Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

		// 생성자와 상위클래스들
		System.out.println("=======Constructor=======");
		Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.out::println);
	}
}
