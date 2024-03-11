package jong1.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class ImportSelectorTest {
    // 고정된 정적의 테스트 방식
    @Test
    void staticConfig() {
        AnnotationConfigApplicationContext appContext =
            new AnnotationConfigApplicationContext(StaticConfig.class);

        HelloBean helloBean = appContext.getBean(HelloBean.class);
        Assertions.assertNotNull(helloBean);
    }

    // 동적인 방식의 테스트 방식
    @Test
    void selectorConfig() {
        AnnotationConfigApplicationContext appContext =
            new AnnotationConfigApplicationContext(SelectorConfig.class);

        HelloBean helloBean = appContext.getBean(HelloBean.class);
        Assertions.assertNotNull(helloBean);
    }

    @Configuration
    @Import(HelloConfig.class)
    public static class StaticConfig {

    }

    @Configuration
    @Import(HelloImportSelector.class)
    public static class SelectorConfig {

    }
}
