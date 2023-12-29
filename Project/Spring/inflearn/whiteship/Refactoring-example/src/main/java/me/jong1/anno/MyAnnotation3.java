package me.jong1.anno;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import me.jong1.Book;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface MyAnnotation3 {
	String value() default "jong1";
	int number() default 100;
}

@MyAnnotation3
class InheritedA { }

class InheritedExtendsA extends InheritedA {
	public static void main(String[] args) {
		Arrays.stream(InheritedExtendsA.class.getAnnotations()).forEach(System.out::println);
	}
}

@MyAnnotation(name="jong1")
class InheritedExtendsADeclaredB extends InheritedA {
	public static void main(String[] args) {
		Arrays.stream(InheritedExtendsADeclaredB.class.getDeclaredAnnotations()).forEach(System.out::println);
	}
}

class GetFieldsAnnotations {
	public static void main(String[] args) {
		// getDeclaredFields() 도 가능하다. 상속정보 유무에 대한 차이
		Field[] fieldAry = Book.class.getFields();
		Arrays.stream(fieldAry).forEach(f->  {
			Annotation[] aAry = f.getAnnotations();
			Arrays.stream(aAry).forEach( anno -> {
				if(anno instanceof  MyAnnotation) {
					MyAnnotation myAnno = (MyAnnotation)anno;
					System.out.println(((MyAnnotation)anno).name());
					System.out.println(((MyAnnotation)anno).number());
				}
			});
		});

	}
}

class GetMethodsAnnotations {
	public static void main(String[] args) {
		// getDeclaredMethods()도 가능 하다. 상속 유무에 대한 차이 이다.
		Method[] methodAry = Book.class.getMethods();
		Arrays.stream(methodAry).forEach(method -> {
			Annotation[] aAry =  methodAry.getClass().getAnnotations();
			Arrays.stream(aAry).forEach( anno -> {
				if(anno instanceof  MyAnnotation) {
					MyAnnotation myAnno = (MyAnnotation)anno;
					System.out.println(((MyAnnotation)anno).name());
					System.out.println(((MyAnnotation)anno).number());
				}
			});
		});
	}
}