package jong1.external;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandLingV1 {
    // dataAAA dataBBB key=a "abcde feg"
    public static void main(String[] args) {
        Arrays.stream(args).forEach(arg -> log.info("arg >>> {}", arg));
    }
}
