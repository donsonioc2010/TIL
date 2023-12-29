package me.whiteship.java8to11.functional;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionalSample {
	public static void main(String[] args) {
		RunSomething r ;
		r = ()-> System.out.println("wow");
		r.doit();

		// @FunctiuonalInterface는 생략을 해도 상관 없다.
		RunSomething2 r2;
		r2 = System.out::println;
		r2.doit("wow22");

		System.out.println(new Plus10().apply(10));
		Function<Integer, Integer> plus10 = (i)-> i+10;
		Function<Integer, Integer> multiply2 = (i)-> i*2;

		//함수의 결합, Multiply2를 먼저 진행하고 이후 값으로 plus10을 진행한다..
		//Function<Integer,Integer> multiply2AndPlus10 = plus10.compose(multiply2);
		Function<Integer,Integer> multiply2AndPlus10 = plus10.compose((i)->i*2);
		System.out.println(multiply2AndPlus10.apply(2));

		IntConsumer a = (i)->System.out.println(i);
		a = a.andThen(i -> System.out.println(i + i)).andThen(i -> System.out.println(i * 3));
		a.accept(7);
	}


	public static void compileTest() {
		Consumer<String> a = System.out::println;

		Supplier<String> ss = () -> "abc";

	}
}
