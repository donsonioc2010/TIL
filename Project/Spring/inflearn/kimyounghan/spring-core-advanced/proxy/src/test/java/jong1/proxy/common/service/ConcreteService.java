package jong1.proxy.common.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteService {

    public void call() {
        log.info("ConcreteService call 호출");
    }

}
