package com.jong1.typeconverter.converter;

import com.jong1.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public IpPort convert(String source) {
        log.info("StringToIpPortConverter Source={}", source);
        String[] split = source.split(":");

        if(source == null || split.length != 2) {
            throw new IllegalArgumentException("잘못된 형식의 요청입니다.");
        }

        // String "127.0.0.1:8080" → IpPort
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new IpPort(ip, port);
    }
}
