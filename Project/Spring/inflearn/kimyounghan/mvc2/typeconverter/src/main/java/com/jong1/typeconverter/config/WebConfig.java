package com.jong1.typeconverter.config;

import com.jong1.typeconverter.converter.IntegerToStringConverter;
import com.jong1.typeconverter.converter.IpPortToStringConverter;
import com.jong1.typeconverter.converter.StringToIntegerConverter;
import com.jong1.typeconverter.converter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Converter에서 확장된 기능으로, Converter를 추가시에는 addFormatter를 사용한다.
    // Spring에서 Converter 적용방법
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
    }
}
