package me.whiteship.java8to11.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamHomework {
	public static void main(String[] args) {
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mvc", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));

		List<OnlineClass> javaClasses = new ArrayList<>();
		javaClasses.add(new OnlineClass(6, "The Java, Test", true));
		javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
		javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

		List<List<OnlineClass>> keesunEvents = new ArrayList<>();
		keesunEvents.add(springClasses);
		keesunEvents.add(javaClasses);


		System.out.println("spring 으로 시작하는 수업");
		springClasses.stream()
			.filter(o -> o.getTitle().startsWith("spring"))
			.forEach(o -> System.out.println(o.getTitle()));

		System.out.println("close 되지 않은 수업");
		springClasses.stream()
			.filter(o -> !o.isClosed())
			.forEach(o -> System.out.println(o.getTitle() + " " + o.isClosed()));

		springClasses.stream()
			.filter(Predicate.not(OnlineClass::isClosed))
			.forEach(o -> System.out.println(o.getTitle() + " " + o.isClosed()));

		System.out.println("수업 이름만 모아서 스트림 만들기");
		springClasses.stream()
			.map(o -> o.getTitle())
			.forEach(System.out::println);

		System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
		keesunEvents.stream()
			.flatMap(Collection::stream)
			.forEach(o -> System.out.println(o.getId()));

		System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
		Stream.iterate(10, i -> i + 1)
			.skip(10)
			.limit(10)
			.forEach(System.out::println);

		System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
		boolean test = javaClasses.stream()
			.anyMatch(oc-> oc.getTitle().contains("Test"));

		System.out.println(test);

		System.out.println("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
		List<OnlineClass> list = springClasses.stream().filter(o->o.getTitle().contains("spring")).collect(Collectors.toList());
		list.stream().forEach(o-> System.out.println(o.getTitle()));
	}
}
