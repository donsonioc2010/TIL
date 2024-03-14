package jong1.datasource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

// 생성자 주입방식
@Getter
@ConfigurationProperties("my.datasource")
public class MyDataSourcePropertiesV2 {

    private String url;
    private String username;
    private String password;
    private Etc etc;

    // private만 사용불가능
    public MyDataSourcePropertiesV2(String url, String username, String password, @DefaultValue Etc etc) {
        this.url = url;
        this.username = username;
        this.password = password;

        // @DefaultValue가 있으면, 객체에 들어가는 값을 Null, 0으로 초기화한다.
        this.etc = etc;
    }

    @Getter
    public static class Etc {
        private int maxConnection;
        private Duration timeout;
        private List<String> options = new ArrayList<>();

        public Etc(int maxConnection, Duration timeout, @DefaultValue("DEFAULT") List<String> options) {
            this.maxConnection = maxConnection;
            this.timeout = timeout;

            // @DefaultValue가 있으면, List에 원소를 추가해 놓는다
            this.options = options;
        }
    }

}
