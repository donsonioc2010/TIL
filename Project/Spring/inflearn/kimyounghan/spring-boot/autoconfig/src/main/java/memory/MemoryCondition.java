package memory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class MemoryCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // VMOptions로 -Dmemory=on || -Dmenory=ON 인경우에만 True
        String memory = context.getEnvironment().getProperty("memory");
        log.info("Memory >>> {}", memory);
        return "on".equalsIgnoreCase(memory);
    }
}
