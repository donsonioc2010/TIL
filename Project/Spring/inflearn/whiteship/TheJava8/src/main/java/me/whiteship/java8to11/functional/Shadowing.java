package me.whiteship.java8to11.functional;

import java.util.function.Consumer;

public class Shadowing {
	public static void main(String[] args) {
		new Shadowing().test();
	}
	public void test() {
		int a = 5;
		class LocalClass implements Consumer<String> {
			@Override
			public void accept(String unused) {
				int a = 999;
				System.out.println(a);
			}
		}
		
		Consumer<String> anonymousClass = new Consumer<String>() {
			@Override
			public void accept(String unused) {
				int a = 111;
				System.out.println(a);
			}
		};

		Consumer<String> lambdaClass = s-> System.out.println(a);

		new LocalClass().accept("a");
		anonymousClass.accept("a");
		lambdaClass.accept("a");
	}
}
