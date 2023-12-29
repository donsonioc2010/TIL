package me.jong1.reflection;

public class Book2 {
	public static String A = "A";
	private String B = "B";

	public Book2() {

	}

	public Book2(String b) {
		B = b;
	}

	public void c() {
		System.out.println("C");
	}

	private void d() {
		System.out.println("D");
	}

	private void d(int a) {
		System.out.println("D" + " " + a);
	}

	public int sum(int left, int right) {
		return left + right;
	}

	public static void staticA() {
		System.out.println("뭘구분해?");
	}

	public static void staticA(boolean a) {
		if (a)
			System.out.println("TRUE!!!");
		else
			System.out.println("FALSE!!!");
	}
}
