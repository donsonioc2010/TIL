package com.jong1.advanced;

import com.jong1.advanced.trace.logtrace.FieldLogTrace;
import com.jong1.advanced.trace.logtrace.LogTrace;
import com.jong1.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
//        return new FieldLogTrace();
        return new ThreadLocalLogTrace();
    }
}
