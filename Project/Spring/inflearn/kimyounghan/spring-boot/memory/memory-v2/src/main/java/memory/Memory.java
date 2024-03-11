package memory;

public class Memory {
    private long used; //현재 사용중인 메모리량
    private long max; // 최대 사용가능한 메모리량

    public Memory(long used, long max) {
        this.used = used;
        this.max = max;
    }

    public long getUsed() {
        return used;
    }

    public long getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "Memory{" +
            "used=" + used +
            ", max=" + max +
            '}';
    }
}
