package me.whiteship.java8to11.concurrent;

import java.time.LocalDateTime;

public class ExtendsThread {

	public static void main(String[] args) throws Exception{
		MyThread myThread = new MyThread();

		myThread.start();
		System.out.println("Thread!");
	}
	// Thread를 생성하는 방법 1. 상속받는다.
	static class MyThread extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println("Hello: " + Thread.currentThread().getName() + "  "+ LocalDateTime.now());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}


}

