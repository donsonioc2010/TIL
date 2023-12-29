package me.whiteship.java8to11.concurrent.excutors;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorsSample {
	public static void main(String[] args) {
		//여러개의 Thread
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
		executorService.schedule(printMessage("Jong1"), 1, TimeUnit.SECONDS);
		executorService.schedule(printMessage("J1"), 1, TimeUnit.SECONDS);
		executorService.schedule(printMessage("J12"), 5, TimeUnit.SECONDS);
		executorService.schedule(printMessage("SH"), 5, TimeUnit.SECONDS);
		executorService.schedule(printMessage("Bong"), 5, TimeUnit.SECONDS);
		executorService.schedule(printMessage("Young"), 4, TimeUnit.SECONDS);
		executorService.schedule(printMessage("sh1"), 4, TimeUnit.SECONDS);
		executorService.schedule(printMessage("mung"), 3, TimeUnit.SECONDS);
		executorService.schedule(printMessage("mizz"), 3, TimeUnit.SECONDS);

		executorService.shutdown();
	}
	private static Runnable printMessage(String message) {
		return () -> System.out.println(message + "  " + Thread.currentThread().getName() + " " + LocalDateTime.now());
	}

}
