package com.jong1.advanced.trace.threadlocal;

import com.jong1.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {
    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main Start");
        Runnable userA = () -> fieldService.logic("userA");
        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
//        sleep(2000);         // 동시성 문제가 발생하지 않는 Sample
        sleep(100);         // 동시성 문제가 발생하는 Sample
        threadB.start();

        sleep(3000); // 메인 쓰레드 종료 대기
        log.info("main exit");

    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
