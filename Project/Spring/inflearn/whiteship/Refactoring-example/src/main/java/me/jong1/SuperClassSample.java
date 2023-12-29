package me.jong1;

import java.util.Arrays;

public class SuperClassSample {
	public static void main(String[] args) {
		Class<? super MyBook> myBook = MyBook.class.getSuperclass();
		System.out.println(myBook.getName());
		Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
	}
}
