package memory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

@Slf4j
public class MemoryFinderTest {

    @Test
    void get() {
        MemoryFinder memoryFinder = new MemoryFinder();
        Memory memory = memoryFinder.get();
        log.info("MemoryFinderTest.get() memory>>> {} ", memory);
        Assertions.assertNotNull(memory);
        Assertions.assertTrue(StringUtils.hasText(String.valueOf(memory.getUsed())));
        Assertions.assertTrue(StringUtils.hasText(String.valueOf(memory.getMax())));
    }
}
