package jong1.proxy.config.v6_aop;

import jong1.proxy.config.AppV1Config;
import jong1.proxy.config.AppV2Config;
import jong1.proxy.config.v6_aop.aspect.LogTraceAspect;
import jong1.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jong
 * @date 2024/03/06 8:37
 */
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
