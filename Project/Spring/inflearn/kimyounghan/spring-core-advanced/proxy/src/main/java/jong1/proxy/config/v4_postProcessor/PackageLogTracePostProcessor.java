package jong1.proxy.config.v4_postProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("Param BeanName >>> {}, Bean >>> {}", beanName, bean.getClass());

        //프록시 적용대상 여부 체크 (패키지 위치로 구분한다)
        //프록시 적용대상이 아니면 원본을 그대로 진행한다.
//        Package packageName = bean.getClass().getPackage();
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(this.basePackage)) {
            return bean;
        }

        // 프록시 대상인 경우
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(this.advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("CreateProxy : target >>> {}, proxy >>> {}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
