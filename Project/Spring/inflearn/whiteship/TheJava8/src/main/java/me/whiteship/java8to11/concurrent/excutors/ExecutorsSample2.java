package me.whiteship.java8to11.concurrent.excutors;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsSample2 {
	public static void main(String[] args) {
		//여러개의 Thread
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(printMessage("Jong1"));
		executorService.submit(printMessage("J1"));
		executorService.submit(printMessage("J12"));
		executorService.submit(printMessage("SH"));
		executorService.submit(printMessage("Bong"));
		executorService.submit(printMessage("Young"));
		executorService.submit(printMessage("sh1"));
		executorService.submit(printMessage("mung"));
		executorService.submit(printMessage("mizz"));

		executorService.shutdown();
	}
	private static Runnable printMessage(String message) {
		return () -> System.out.println(message + "  " + Thread.currentThread().getName() + " " + LocalDateTime.now());
	}

}
