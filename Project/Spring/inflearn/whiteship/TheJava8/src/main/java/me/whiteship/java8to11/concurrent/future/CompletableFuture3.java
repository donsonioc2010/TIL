package me.whiteship.java8to11.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class CompletableFuture3 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello "+ Thread.currentThread().getName());
			return "Hello";
		});

		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return " World";
		});

		//따로따로 실행하고 두개가 다 실행되어 결과를 받았을 경우 작업을 처리하고 싶은 경우 활용한다.
		CompletableFuture<String> future =  hello.thenCombine(world, (h, w) -> h + " " + w);
		System.out.println(future.get());

	}

	private static CompletionStage<String> getWorld(String s) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return s + " World";
		});
	}
}
