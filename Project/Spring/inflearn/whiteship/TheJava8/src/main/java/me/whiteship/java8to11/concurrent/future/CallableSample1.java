package me.whiteship.java8to11.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableSample1 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Callable<String> hello = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				System.out.println("callable~");
				return "Hello";
			}
		};
		Future<String> helloFuture = executorService.submit(hello);
		System.out.println("Started");
		while(!helloFuture.isDone()) {
			System.out.println(helloFuture.isDone());
			Thread.sleep(500);
		}
		System.out.println(helloFuture.isDone());
		System.out.println(helloFuture.get()); //블록킹!!!!
		System.out.println("End");
		executorService.shutdown();
	}
}
