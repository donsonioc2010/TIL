package jong1;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandLineBean {

    private final ApplicationArguments arguments;

    public CommandLineBean(ApplicationArguments arguments) {
        this.arguments = arguments;
    }
    // --url=devdb --url=devdb2 --username=dev_user --password=devpw mode=on
    @PostConstruct
    public void init() {
        log.info("source >>> {}", List.of(arguments.getSourceArgs()));
        log.info("OptionNames >>> {}", arguments.getOptionNames());

        arguments.getOptionNames().forEach(optionName -> {
            log.info("OptionName >>> {} , OptionValues >>> {}", optionName, arguments.getOptionValues(optionName));
        });
    }
}

