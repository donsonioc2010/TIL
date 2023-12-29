package me.whiteship.java8to11.concurrent.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableSample2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Callable<String> hello = () -> {
			Thread.sleep(2000);
			return "Hello";
		};
		Callable<String> java = () -> {
			Thread.sleep(3000);
			return "java";
		};
		Callable<String> jong1 = () -> {
			Thread.sleep(4000);
			return "Jong1";
		};

		// 한번에 모든 작업을 처리하는 방법
		/*List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, java, jong1));
		for (Future<String> f : futures) {
			System.out.println(f.get());
		}*/

		String s = executorService.invokeAny(Arrays.asList(hello, java, jong1));
		System.out.println(s);
		executorService.shutdown();
	}
}
