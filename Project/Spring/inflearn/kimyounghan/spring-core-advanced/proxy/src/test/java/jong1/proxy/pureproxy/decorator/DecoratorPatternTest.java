package jong1.proxy.pureproxy.decorator;

import jong1.proxy.pureproxy.decorator.code.Component;
import jong1.proxy.pureproxy.decorator.code.DecoratorPatterClient;
import jong1.proxy.pureproxy.decorator.code.MessageDecorator;
import jong1.proxy.pureproxy.decorator.code.RealComponent;
import jong1.proxy.pureproxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatterClient client = new DecoratorPatterClient(realComponent);
        client.execute();
    }

    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatterClient client = new DecoratorPatterClient(messageDecorator);
        client.execute();
    }

    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatterClient client = new DecoratorPatterClient(timeDecorator);
        client.execute();
    }

}
