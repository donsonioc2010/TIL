package me.whiteship.java8to11.concurrent.excutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsSample {
	public static void main(String[] args) {
		//Thread를 한개만 사용하는 Executor
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Thread " + Thread.currentThread().getName());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		// executorService.shutdown(); // 우아하게 현재 작업을 마치고 죽인다.
		executorService.shutdownNow();
	}
}
