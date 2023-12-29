package me.whiteship.java8to11.anno;

import java.util.Arrays;

public class ChickenSmaple {
	public static void main(String[] args) {

	}
	static class FeelLikeChicken<@Chicken(value="fuck") T> {
		//????????
		public static <@Chicken(value="fuck")  C> void print (C c) {
			Chicken[] chickens = FeelLikeChicken.class.getAnnotationsByType(Chicken.class);
			Arrays.stream(chickens).forEach(System.out::println);
			System.out.println("=======");
		}
	}
}
