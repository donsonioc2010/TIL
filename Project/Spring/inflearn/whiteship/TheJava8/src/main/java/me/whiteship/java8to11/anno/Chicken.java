package me.whiteship.java8to11.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_PARAMETER) //어노테이션의 사용 위치 , 8이후 ElemetType의 TYPE_PARAMETE와 TYPE_USE항목이 추가됨.
@Retention(RetentionPolicy.RUNTIME) //언제 까지 Annotation이 생존해 있는지를 조절
@Repeatable(ChickenContainer.class)
public @interface Chicken {
	String value();
}
