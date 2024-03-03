package jong1.proxy.pureproxy.proxy;

import jong1.proxy.pureproxy.proxy.code.CacheProxy;
import jong1.proxy.pureproxy.proxy.code.ProxyParrternClient;
import jong1.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyParrternClient client = new ProxyParrternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        CacheProxy proxySubject = new CacheProxy(new RealSubject());
        ProxyParrternClient client = new ProxyParrternClient(proxySubject);
        client.execute();
        client.execute();
        client.execute();
    }
}
