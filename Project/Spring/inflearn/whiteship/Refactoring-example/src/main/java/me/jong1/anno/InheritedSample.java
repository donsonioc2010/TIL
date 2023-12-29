package me.jong1.anno;

import java.util.Arrays;

import me.jong1.MyBook;

public class InheritedSample {
	public static void main(String[] args) {
		Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);
	}
}
