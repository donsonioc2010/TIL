package com.jong1.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    // 문자열을 Generic인 T타입으로 변환하는 파서
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text >>> {} locale >>> {}", text, locale);

        // 1,000 -> 1000
        return NumberFormat.getInstance(locale).parse(text);
    }

    // Generic인 T타입으로 들어온 객체를 문자열로 변환하는 프린터
    @Override
    public String print(Number object, Locale locale) {
        log.info("object >>> {} locale >>> {}", object, locale);

        return NumberFormat.getInstance(locale).format(object);
    }
}
