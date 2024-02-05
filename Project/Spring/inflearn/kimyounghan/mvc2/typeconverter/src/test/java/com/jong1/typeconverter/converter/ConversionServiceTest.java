package com.jong1.typeconverter.converter;

import com.jong1.typeconverter.type.IpPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        // Bean같은 곳에 등록후 사용하면 편함, ISP원칙, Client는 인터페이스만 알면 됨
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // String to Integer
        Assertions.assertEquals(10, conversionService.convert("10", Integer.class));
        Assertions.assertEquals("10", conversionService.convert(10, String.class));
        Assertions.assertEquals(new IpPort("127.0.0.1", 8080), conversionService.convert("127.0.0.1:8080", IpPort.class));
        Assertions.assertEquals("127.0.0.1:8080", conversionService.convert(new IpPort("127.0.0.1", 8080), String.class));
    }
}
