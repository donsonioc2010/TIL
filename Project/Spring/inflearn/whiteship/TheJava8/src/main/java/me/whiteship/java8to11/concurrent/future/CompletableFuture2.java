package me.whiteship.java8to11.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class CompletableFuture2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello "+ Thread.currentThread().getName());
			return "Hello";
		});

		//hello가 실행된 이후 getWorld가 실행된다
		System.out.println(hello.thenCompose(CompletableFuture2::getWorld).get());
	}

	private static CompletionStage<String> getWorld(String s) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return s + " World";
		});
	}
}
