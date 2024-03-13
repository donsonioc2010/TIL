package jong1.config;

import java.time.Duration;
import java.util.List;
import jong1.datasource.MyDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MyDataSourceValueConfig {

    @Value("${my.datasource.url}")
    private String url;
    @Value("${my.datasource.username}")
    private String username;
    @Value("${my.datasource.password}")
    private String password;
    @Value("${my.datasource.etc.max-connection}")
    private int maxConnection;
    @Value("${my.datasource.etc.timeout}")
    private Duration timeout;
    @Value("${my.datasource.etc.options}")
    private List<String> options;

    @Bean
    public MyDataSource myDataSource1() {
        return new MyDataSource(url, username, password, maxConnection, timeout, options);
    }

    @Bean
    public MyDataSource myDataSource2(
        @Value("${my.datasource.url}") String url,
        @Value("${my.datasource.username}") String username,
        @Value("${my.datasource.password}") String password,
        @Value("${my.datasource.etc.max-connection}") int maxConnection,
        @Value("${my.datasource.etc.timeout}") Duration timeout,
        @Value("${my.datasource.etc.options}") List<String> options,
        @Value("${not-found-value:5}") int value
    ) {
        log.info("속성이 주입이 되지 않는 경우 DefaultValue를 설정하는 방법 >>> {}",value);
        return new MyDataSource(url, username, password, maxConnection, timeout, options);
    }
}
