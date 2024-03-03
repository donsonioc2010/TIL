package jong1.proxy;

import jong1.proxy.config.AppV1Config;
import jong1.proxy.config.AppV2Config;
import jong1.proxy.config.v1_proxy.ConcreteProxyConfig;
import jong1.proxy.config.v1_proxy.InterfaceProxyConfig;
import jong1.proxy.trace.logtrace.LogTrace;
import jong1.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)
//@Import({InterfaceProxyConfig.class, AppV2Config.class})
//@Import({InterfaceProxyConfig.class})
@Import({ConcreteProxyConfig.class})
@SpringBootApplication(scanBasePackages = "jong1.proxy.app.v3") // 컴포넌트 스캔대상 v3한정, v1, v2는 config로 하기위함
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
