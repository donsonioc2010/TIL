package me.whiteship.java8to11.functional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionalConstructor {
	public static void main(String[] args) {
		Function<String, Greeting> stringLambda =  Greeting::new;
		Supplier<Greeting> noNameLabmda = Greeting::new;

		Greeting noName = noNameLabmda.get();
		Greeting yesName= stringLambda.apply("Jong1");

		UnaryOperator<String> hello = Greeting::getHi;
		UnaryOperator<String> hello2 =noName::getHello;
		UnaryOperator<String> hello3 =yesName::getHello;
		Supplier<String> hello4 = noName::getHello;
		Supplier<String> hello5 = yesName::getHello;

		System.out.println(hello.apply("jong1"));
		System.out.println(hello2.apply("jong1"));
		System.out.println(hello3.apply("jong1"));
		System.out.println(hello4.get());
		System.out.println(hello5.get());

	}
}


class Greeting {
	private String name;

	public Greeting() {
		System.out.println("No Constructor");
	}

	public Greeting(String name) {
		this.name = name;
		System.out.println("String Constructor");
	}

	public String getName() {
		return name;
	}

	public String getHello() {
		return "hello " + this.name;
	}

	public String getHello(String name) {
		return "hello " + name;
	}

	public static String getHi(String name) {
		return "Hi " + name;
	}
}