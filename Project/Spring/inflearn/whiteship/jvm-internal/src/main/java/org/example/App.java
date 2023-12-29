package org.example;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.io.File;
import java.io.IOException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

public class App {
	public static void main(String[] args) {

		ClassLoader classLoader = Masulsa.class.getClassLoader();
		TypePool typePool = TypePool.Default.of(classLoader);
		try {
			new ByteBuddy()
				.redefine(
					typePool.describe("org.example.Moja").resolve(),
					ClassFileLocator.ForClassLoader.of(classLoader)
				)
				.method(named("pullOut"))
				.intercept(FixedValue.value("Rabbit!"))
				.make()
				.saveIn(new File("/Users/kimjongwon/Desktop/Study/인프런/jvm-internal/target/classes/"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println(new Moja().pullOut());
	}

	public String helloWorld() {
		return "Hello World!";
	}
}
