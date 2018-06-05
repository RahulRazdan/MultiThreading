package com.multi.threading;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class cyclicWorker implements Runnable {
	private int id;
	private CyclicBarrier barrier;
	private Random random;

	public cyclicWorker(int id, CyclicBarrier barrier) {
		this.id = id;
		this.barrier = barrier;
		this.random = new Random();
	}

	@Override
	public void run() {
		dowork();
	}

	private void dowork() {
		System.out.println("Starting working for id : " + id);

		try {
			Thread.sleep(random.nextInt(3000));
			System.out.println("Finished working for id : " + id);
			barrier.await();
			System.out.println("Start more work for id: "+id);
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}

public class TestCyclicBarrier {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		CyclicBarrier countdown = new CyclicBarrier(5,new Runnable() {
			
			@Override
			public void run() {
				System.out.println("All task completed");	
			}
		});

		for (int i = 0; i < 5; i++) {
			executor.execute(new cyclicWorker(i, countdown));
		}

		executor.shutdown();

	}
}
