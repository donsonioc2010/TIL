package jong1.external;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OsEnv {

    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv();
        getenv.entrySet().forEach(entry -> {
            log.info("Env key >>> {}, value >>> {}", entry.getKey(), entry.getValue());
        });
    }
}
