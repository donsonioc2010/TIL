package com.jong1.typeconverter.config;

import com.jong1.typeconverter.converter.IntegerToStringConverter;
import com.jong1.typeconverter.converter.IpPortToStringConverter;
import com.jong1.typeconverter.converter.StringToIntegerConverter;
import com.jong1.typeconverter.converter.StringToIpPortConverter;
import com.jong1.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Converter에서 확장된 기능으로, Converter를 추가시에는 addFormatter를 사용한다.
    // Spring에서 Converter 적용방법
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //Formatter를 추가하는 방법
        // Converter가 Formatter보다 우선순위가 높다.
        registry.addFormatter(new MyNumberFormatter()); // 숫자를 문자로 변경하는 포메터다 보니 위의 두개는 주석처리함
    }
}
