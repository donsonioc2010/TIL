package me.whiteship.java8to11.functional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Spliterator {
	public static void main(String[] args) {
		List<String> names = List.of("jong1", "Bong", "SH", "Eom");
		java.util.Spliterator<String> spliterator = names.spliterator();
		while (spliterator.tryAdvance(System.out::println))
			;// elements가 존재하는 경우 true를 반환한다.

		System.out.println("============================");

		Stream<String> stream = names.stream().map(s -> {
			s = s.toUpperCase();
			System.out.println(s);
			return s;
		});
		System.out.println("===========Stream 종료 오퍼레이션 실행전, 후=================");
		List<String> names2 = stream.collect(Collectors.toList());

	}
}
