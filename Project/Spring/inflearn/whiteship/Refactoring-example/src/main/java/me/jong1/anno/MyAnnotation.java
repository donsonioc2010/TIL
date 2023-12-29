package me.jong1.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.SOURCE)
@Target(
	{// Type과 Field만
		ElementType.TYPE,
		ElementType.PARAMETER,
		ElementType.FIELD
	}
)
public @interface MyAnnotation {
	//Annotation도 값을 가질 수는 있으나 Premitive Type과 Premitive Type의 WrapperClass Type만 가질 수 있다.
	String name();
	//default를 선언하지 않으면 무조건 해당 어노테이션의 value를 지정해줘야 하며, Default를 선언한 경우 해당 Value를 받지 않으면 default의 value가 설정된다
	int number() default 100;
}
