package me.whiteship.java8to11.concurrent.excutors;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorsAtFixedRateSample {
	public static void main(String[] args) {
		//여러개의 Thread
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
		//initialDelay는 최초 대기 시간값, period는 최초 실행 이후 주기적으로 실행을 하게 될 시간 초
		executorService.scheduleAtFixedRate(printMessage("Jong1"), 1, 1, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("J1"), 1,1, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("J12"), 5,4, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("SH"), 5,4, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("Bong"), 5,3, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("Young"), 4,3, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("sh1"), 4,5, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("mung"), 3,7, TimeUnit.SECONDS);
		executorService.scheduleAtFixedRate(printMessage("mizz"), 3,10, TimeUnit.SECONDS);

	}
	private static Runnable printMessage(String message) {
		return () -> System.out.println(message + "  " + Thread.currentThread().getName() + " " + LocalDateTime.now());
	}

}
