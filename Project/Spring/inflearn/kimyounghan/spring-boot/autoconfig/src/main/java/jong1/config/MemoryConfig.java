package jong1.config;

import memory.MemoryCondition;
import memory.MemoryController;
import memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

// ConditionalOnProperty의 havingValue는 대소문자를 구분하지 않으며
// 이유는 OnPropertyCondition의 match메소드에서 이미 EquaisIgnoreCase로 처리하고 있기 떄문임.

@Configuration
//@Conditional(MemoryCondition.class) // 이게 True여야만 Bean 등록
@ConditionalOnProperty(name = "memory", havingValue = "on") // VMOptions로 -Dmemory=on || -Dmenory=ON 인경우에만 True
public class MemoryConfig {
    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder());
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
