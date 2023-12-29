package me.whiteship.java8to11.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatting {
	//미리 정의해둔 포맷팅 문서
	// https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#predefined
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		System.out.println(now.format(format));

		//Parsing
		LocalDate parse = LocalDate.parse("08/22/1995", format);
		System.out.println(parse);
	}
}
