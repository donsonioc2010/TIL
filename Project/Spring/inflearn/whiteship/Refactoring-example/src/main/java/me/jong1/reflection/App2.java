package me.jong1.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.jong1.Book;

public class App2 {
	public static void main(String[] args) throws
		ClassNotFoundException,
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException, NoSuchFieldException {

		Class<?> book2class = Class.forName("me.jong1.reflection.Book2");
		Class<Book2> book2Class2 = Book2.class;

		// 기본 새생성자를 사용하고 싶은 경우
		Constructor<?> constructor = book2class.getConstructor(null);
		Book2 o = (Book2)constructor.newInstance(); //Instance를 생성하는 방법
		System.out.println(o);

		// 파라미터가 존재하는 생성자를 사용하고 싶은 경우, 해당 생성자가 받는 Type을 입력하면 된다.
		Constructor<?> constructor2 = book2class.getConstructor(String.class);
		Book2 o2 = (Book2)constructor2.newInstance("a"); // getConstructor당시 입력한 파라미터를 그대로 입력해야 한다.
		System.out.println(o2);

		// Field 객체 접근 방법
		// Instance 값 획득 방법
		Field b1 = Book2.class.getDeclaredField("B");
		Field b2 = o.getClass().getDeclaredField("B");
		b1.setAccessible(true);
		b2.setAccessible(true);
		System.out.println(b1.get(o));
		System.out.println(b2.get(o2));


		// static 값 수정 방법
		b1.set(o, "AAAA");
		System.out.println("b1 : "+b1.get(o));
		b2.set(o2, "BBBB");
		System.out.println("b2 : "+b2.get(o2));
		System.out.println("b1 : "+b1.get(o2));




		//Private Instance Methods접근 방법
		Method d = Book2.class.getDeclaredMethod("d");
		Method d2 = Book2.class.getDeclaredMethod("d", int.class);
		d.setAccessible(true);
		d2.setAccessible(true);
		d.invoke(o);
		d2.invoke(o, 5);

		Method staticA = Book2.class.getDeclaredMethod("staticA");
		Method staticABool = Book2.class.getDeclaredMethod("staticA", boolean.class);
		staticA.invoke(null);
		staticABool.invoke(null, true);
		staticABool.invoke(null, false);

	}
}
