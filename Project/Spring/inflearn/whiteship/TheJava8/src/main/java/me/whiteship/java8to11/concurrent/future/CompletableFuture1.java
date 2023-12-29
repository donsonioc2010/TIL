package me.whiteship.java8to11.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFuture1 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("Hello " + Thread.currentThread().getName());
		});

		future.get(); //get을 할때 실행이 된다.
		//future.join(); // Exception의 선언이 굳이 필요가 없다.

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello222 " + Thread.currentThread().getName());
			return "Hello";
		}).thenApply((s) -> {
			System.out.println(s + " " + Thread.currentThread().getName());
			return s.toUpperCase() + " zzzzzz";
		});
		System.out.println(future2.get());

		CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello222 " + Thread.currentThread().getName());
			return "Hello";
		}).thenAccept((s) -> {
			System.out.println(s + " zzzzzzzz Return이 없어? " + Thread.currentThread().getName());
		});

		future3.join();

		CompletableFuture<Void> future4 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello222 " + Thread.currentThread().getName());
			return "Hello";
		}).thenRun(() ->  {
			System.out.println("WOWWWWWW~");
		});

		future4.get();
	}
}
