package memory;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryFinder {

    public Memory get() {
        long max = Runtime.getRuntime().maxMemory(); // JVM이 사용 할 수 있는 최대 메모리, 이 수치를 넘어가면 OOM 발생
        long total = Runtime.getRuntime().totalMemory(); // JVM이 확보한 전체 메모리, 처음부터 max치만큼 확보하는게 아니라 필요에 따라 늘어남
        long free = Runtime.getRuntime().freeMemory();  // total메모리 중에 사용하지 않은 메모리
        long used = total - free;

        return new Memory(used, max);
    }

    @PostConstruct
    public void init() {
        log.info("Init MemoryFinder");
    }
}
