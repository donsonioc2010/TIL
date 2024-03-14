package jong1;

import jong1.config.MyDataSourceConfigV1;
import jong1.config.MyDataSourceConfigV2;
import jong1.config.MyDataSourceEnvConfig;
import jong1.config.MyDataSourceValueConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;

//@Import({MyDataSourceEnvConfig.class})
//@Import({MyDataSourceValueConfig.class})
//@Import({MyDataSourceConfigV1.class})
@Import({MyDataSourceConfigV2.class})
@SpringBootApplication(scanBasePackages = "jong1.datasource")
//@ConfigurationPropertiesScan(basePackages = "jong1.datasource") //패키지를 제한하고 싶은경우
@ConfigurationPropertiesScan // 어노테이션 하위 패키지 스캔
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }

}
