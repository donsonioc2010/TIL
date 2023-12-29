package me.whiteship.java8to11.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateSample {

	public static void main(String[] args) {
		System.out.println("기계용 시간");
		Instant instant = Instant.now();
		System.out.println(instant); // 그리니치 평균시를 기준으로 시간을 가져옴
		System.out.println(instant.atZone(ZoneId.of("UTC")));

		ZoneId zone = ZoneId.systemDefault();
		System.out.println(zone);
		ZonedDateTime zonedDateTime = instant.atZone(zone);
		System.out.println(zonedDateTime); // 현재 Zone의 시간
		System.out.println("==============================");

		System.out.println("휴면용 시간");
		LocalDateTime now = LocalDateTime.now(); //나의 Zone에 맞는 시간으로 가져온다.
		System.out.println(now);
		LocalDateTime myBirthday = LocalDateTime.of(1995,8, 22, 0,0,0 );
		LocalDateTime myBirthday2 = LocalDateTime.of(1995, Month.AUGUST, 22, 0,0,0 );
		System.out.println(myBirthday + " "+myBirthday2);

		ZonedDateTime nowInLA = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println(ZoneId.of("America/Los_Angeles") + " " + nowInLA);

	}
}
