package me.whiteship.java8to11.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class LocalDateTimeDuration {
	public static void main(String[] args) throws  Exception {
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(2023, Month.AUGUST, 22);
		Period period = Period.between(today, birthday);

		//기간차이 구분
		System.out.println(period.getDays());
		Period period2 = today.until(birthday);
		System.out.println(period2.get(ChronoUnit.DAYS));

		//Duration
		Instant now = Instant.now();
		Instant plus = now.plus(10, ChronoUnit.DAYS);
		Duration between = Duration.between(now, plus);
		System.out.println(between.getSeconds());


		Optional<String> a = Optional.ofNullable("abc");
		a.map(s->1);
		a.filter(s-> !s.equals("abc"));
		a.orElseGet(() -> "wow!!!!");
		a.orElse("없어");
		a.orElseThrow();
		a.orElseThrow(() -> new RuntimeException("삐용삐용"));
		a.ifPresent(System.out::println);
	}

}
