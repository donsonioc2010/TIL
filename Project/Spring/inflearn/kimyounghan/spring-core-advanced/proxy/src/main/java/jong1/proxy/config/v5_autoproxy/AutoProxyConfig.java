package jong1.proxy.config.v5_autoproxy;

import jong1.proxy.config.AppV1Config;
import jong1.proxy.config.AppV2Config;
import jong1.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import jong1.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {
    //@Bean
    public Advisor advisor1(LogTrace logTrace) {
        //PointCut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    //@Bean
    public Advisor advisor2(LogTrace logTrace) {
        //PointCut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* jong1.proxy.app..*(..))"); //app..은 app하위 모든패키지를 의미하며, *(..)은 모든 메소드에 파라미터는 구분하지 않는 의미

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //PointCut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // noLog 메소드는 로그를 찍지 않는다.
        pointcut.setExpression("execution(* jong1.proxy.app..*(..)) && !execution(* jong1.proxy.app..noLog(..))");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
