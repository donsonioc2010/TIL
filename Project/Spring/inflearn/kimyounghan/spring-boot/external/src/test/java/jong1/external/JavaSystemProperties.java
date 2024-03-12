package jong1.external;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaSystemProperties {

    // -Dmemory=on 등 VM옵션을 읽는 방법
    // Spring의 경우 ApplicationContext에서 Enviorment에서 확인가능
    public static void main(String[] arg) {
        Properties properties = System.getProperties();
        properties.entrySet().forEach(entry -> {
            log.info("Prop key >>> {}, value >>> {}", entry.getKey(), entry.getValue());
        });

        // -Durl=devdb -Dusername=dev -Dpassword=devPasswd
        // 추가한 VM옵션 읽기
        String url = System.getProperty("url");
        String username = System.getProperty("username");
        String password = System.getProperty("password");

        log.info("url >>> {}", url);
        log.info("username >>> {}", username);
        log.info("password >>> {}", password);

        System.setProperty("hi_key", "hi_value");
        log.info("hi_key >>> {}", System.getProperty("hi_key"));
        log.info("hi_key >>> {}", System.getProperty("hi_key2")); // null 없는경우
    }
}
