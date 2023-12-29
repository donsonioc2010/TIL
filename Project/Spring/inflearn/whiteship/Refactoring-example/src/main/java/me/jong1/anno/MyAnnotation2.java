package me.jong1.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation2 {
	String value(); //field명이 value인 경우에는 value="abc"에서 value의 생략이 가능하다 여러개의 속성을 설정하는 경우는 value=도 입력해야 한다.
	int number() default 5;
}
