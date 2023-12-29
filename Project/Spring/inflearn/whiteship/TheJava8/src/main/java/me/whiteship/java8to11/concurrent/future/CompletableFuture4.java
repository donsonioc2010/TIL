package me.whiteship.java8to11.concurrent.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFuture4 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<String> eat = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "밥먹는 세환이";
		});

		CompletableFuture<String> noEat = CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "밥 안먹는 세환이";
		});

		String result = (String)CompletableFuture.anyOf(eat, noEat).get();

		List<CompletableFuture<String>> futures = Arrays.asList(eat, noEat);
		CompletableFuture<List<String>> results = CompletableFuture.anyOf(
				futures.toArray(new CompletableFuture[futures.size()]))
			.thenApply(v -> futures.stream()
				.map(CompletableFuture::join) //join의 이유는 Uncheck Exception예외처리를 Join이 다 해주기때문, get은 안하고..
				.collect(Collectors.toList()));

		results.get().forEach(System.out::println);
	}

	private static CompletionStage<String> getWorld(String s) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return s + " World";
		});
	}
}
