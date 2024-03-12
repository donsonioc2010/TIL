package jong1.external;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

@Slf4j
public class CommandLineV2 {

    //--url=devdb --url=devdb2 --username=dev_user --password=dev_pw mode=on
    public static void main(String[] args) {
        for (String arg : args) {
            log.info("arg >>> {}", arg);
        }

        // 스프링에서 제작한 인수 로드
        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        // [--url=devdb, --url=devdb2, --username=dev_user, --password=dev_pw, mode=on]
        log.info("SourceArgs >>> {}", List.of(appArgs.getSourceArgs()));

        //[mode=on]
        log.info("NonOptionsArgs >>> {}", appArgs.getNonOptionArgs());

        // [password, url, username]
        log.info("OptionNames >>> {}", appArgs.getOptionNames());

        // OptionName >>> password , OptionValues >>> [dev_pw]
        // OptionName >>> url , OptionValues >>> [devdb, devdb2]
        // OptionName >>> username , OptionValues >>> [dev_user]
        Set<String> optionNames = appArgs.getOptionNames();
        for (String optionName : optionNames) {
            log.info("OptionName >>> {} , OptionValues >>> {}", optionName, appArgs.getOptionValues(optionName));
        }

        /*
        url >>> [devdb, devdb2]
        username >>> [dev_user]
        password >>> [dev_pw]
        mode >>> null
         */
        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> mode = appArgs.getOptionValues("mode");
        log.info("url >>> {}", url);
        log.info("username >>> {}", username);
        log.info("password >>> {}", password);
        log.info("mode >>> {}", mode);
    }
}
