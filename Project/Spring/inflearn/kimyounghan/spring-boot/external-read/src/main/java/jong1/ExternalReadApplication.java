package jong1;

import jong1.config.MyDataSourceEnvConfig;
import jong1.config.MyDataSourceValueConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import({MyDataSourceEnvConfig.class})
@Import({MyDataSourceValueConfig.class})
@SpringBootApplication(scanBasePackages = "jong1.datasource")
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }

}
