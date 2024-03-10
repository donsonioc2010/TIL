package jong1.aop.exam;

import jong1.aop.exam.annotation.Retry;
import jong1.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {
    private static int seq = 0;

    /**
     * 5번에  1번 실패하는 요청
     */
    @Trace @Retry(4)
    public String save(String itemId) {
        if (++this.seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }

}