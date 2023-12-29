package me.whiteship.java8to11.concurrent;

public class LambdaThread {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(()-> {
			System.out.println("Thread: " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
		});
		thread.start();
		System.out.println("hello Thread  " + Thread.currentThread().getName());
		thread.join();

		System.out.println(thread + " is finished");
	}
}
