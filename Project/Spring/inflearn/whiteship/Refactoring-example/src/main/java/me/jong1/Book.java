package me.jong1;

import me.jong1.anno.MyAnnotation3;

@MyAnnotation3
public class Book {
	private static String B = "BOOK";
	private static final String C = "BOOK";

	private  String a ="a";
	public String d = "d";
	protected String e = "e";

	public Book() {
	}

	public Book(String a, String d, String e) {
		this.a = a;
		this.d = d;
		this.e = e;
	}

	private void f() {
		System.out.println("F");
	}
	public void g() {
		System.out.println("G");
	}

	public int h() {
		return 100;
	}


}