package com.jong1.typeconverter.formatter;

import com.jong1.typeconverter.converter.IpPortToStringConverter;
import com.jong1.typeconverter.converter.StringToIpPortConverter;
import com.jong1.typeconverter.type.IpPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormatterConversionServiceTest {
    //Formatter와 Converter를 모두 등록관리하는 ConversionService

    // 스프링 부트는 WebConversionService에서 보관해서 사용할 수 있다.
    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        // 컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 포매터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        Assertions.assertEquals(new IpPort("127.0.0.1", 8080), conversionService.convert("127.0.0.1:8080", IpPort.class));
        Assertions.assertEquals("127.0.0.1:8080", conversionService.convert(new IpPort("127.0.0.1", 8080), String.class));

        // 포매터 사용
        Assertions.assertEquals("1,000", conversionService.convert(1000, String.class));
        Assertions.assertEquals(1000L, conversionService.convert("1,000", Long.class));
    }
}
