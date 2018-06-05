package com.multi.threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class yourWorker implements Runnable {

	private int id;
	private CountDownLatch latch;

	public yourWorker(int id, CountDownLatch latch) {
		super();
		this.id = id;
		this.latch = latch;
	}

	@Override
	public void run() {
		dowork();
		latch.countDown();
	}

	private void dowork() {
		System.out.println("Performing my task id: " + id);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}
	}

}

public class TestCountdownLatch {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(5);
		CountDownLatch countdown = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			executor.execute(new yourWorker(i, countdown));
		}

		try {
			System.out.println("Waiting............");
			countdown.await();
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}

		System.out.println("Work completed");
		executor.shutdown();
	}
}
