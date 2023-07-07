# Date와 Time API

## 기존의 Date API의 문제점

> 다음의 문제로 인해 `Joda Time`을 자주 사용하곤 했으나, 이게 Java8 LocalDate시리즈로 들어오게 되었다.

- `muttable`객체
  - 기존에 Date Instance를 생성을 하고, 시간을 수정하는 작업등을 하게 될때 항상 `기존의 Instance`의 값이 수정되는 문제
  - `thread safe`하지가 않다.
- 시간이 직관적이지가 않는 문제
  - Month를 입력한다든가 할때 7월을 표기하고 싶은 경우 6을 입력해야 하는 등.
    - 참고자료 : https://d2.naver.com/helloworld/645609

## 시간 객체를 받는법

### 기계용 시간을 받는 법

```java
Instant instant = Instant.now();
System.out.println(instant); // 그리니치 평균시를 기준으로 시간을 가져옴
System.out.println(instant.atZone(ZoneId.of("UTC")));

ZoneId zone = ZoneId.systemDefault();
System.out.println(zone);
ZonedDateTime zonedDateTime = instant.atZone(zone);
System.out.println(zonedDateTime); // 현재 Zone의 시간
```

### 사람이 보기 편한 시간을 표현하는 방법

```java
LocalDateTime now = LocalDateTime.now(); //나의 Zone에 맞는 시간으로 가져온다.
System.out.println(now);
LocalDateTime myBirthday = LocalDateTime.of(1995,8, 22, 0,0,0 );
LocalDateTime myBirthday2 = LocalDateTime.of(1995, Month.AUGUST, 22, 0,0,0 );
System.out.println(myBirthday + " "+myBirthday2);
```

### 다른 Zone의 시간을 가져오는 방법

```java
ZonedDateTime nowInLA = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
System.out.println(ZoneId.of("America/Los_Angeles") + " " + nowInLA);
```

## 기간의 차이

```java
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
```

## 시간 Formatting

```java
LocalDateTime now = LocalDateTime.now();
DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
System.out.println(now.format(format));

//Parsing
LocalDate parse = LocalDate.parse("08/22/1995", format);
System.out.println(parse);
```

### 이미 포메팅된 종류들

https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#predefined
