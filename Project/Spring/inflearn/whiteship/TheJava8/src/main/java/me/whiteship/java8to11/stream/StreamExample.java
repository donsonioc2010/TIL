package me.whiteship.java8to11.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamExample {
	public static void main(String[] args) {
		new StreamExample().example();
	}
	public void example() {
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mvc", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));

		Stream<OnlineClass> streamVariable = springClasses.stream().filter(o -> o.getTitle().contains("spring"));

		System.out.println("====");
		streamVariable.map(o -> o.getTitle()).forEach(System.out::println);
	}
}
