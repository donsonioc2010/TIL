package me.jong1.anno;

import java.util.Arrays;

//@MyAnnotation
public class AnnotationAppSample {
	@MyAnnotation(name="abc")
	public int a = 0;

	@MyAnnotation(name="cde", number = 30)
	private int b = 1;

	@MyAnnotation2(value = "abc", number = 30)
	protected String c;

	@MyAnnotation2("abc")
	protected String d;

	public static void main(String[] args) {
		// Annotation의 Retention의 value가 RUNTIME이 아니면 읽는게 불가능하다
		Arrays.stream(AnnotationAppSample.class.getAnnotations()).forEach(System.out::println);
	}
}
