package me.whiteship.java8to11.functional;

// Lambda Functional

@FunctionalInterface
public interface RunSomething {
	void doit();

	static void printNAme(String name) {
		System.out.println(name);
	}

	default void printAge(int age) {
		System.out.println(age);
	}
}
