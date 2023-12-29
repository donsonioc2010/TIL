package me.whiteship.java8to11.functional;

import java.util.function.Function;

// 첫번째 Generic이 입력값의 Type, 두번째 Generic이 Return값의 타입이다.
public class Plus10 implements Function<Integer, Integer> {
	@Override
	public Integer apply(Integer integer) {
		return integer + 10;
	}
}
