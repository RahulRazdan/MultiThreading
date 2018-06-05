package com.multi.threading;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum DOWNLOADER {

	INSTANCE;

	private Semaphore sema = new Semaphore(3, true);

	public void downloadData() {
		try {
			sema.acquire();
			downloading();
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}finally {
			sema.release();
		}
	}

	private void downloading() {
		try {
			System.out.println("Dowloading content ....");
			Thread.sleep(2000);
			System.out.println("Dowloaded content ++++");
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
		}
	}
}

public class TestSemaphores {

	public static void main(String[] args) {
		//ExecutorService executor = Executors.newSingleThreadExecutor();
		//ExecutorService executor = Executors.newFixedThreadPool(3);
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 30; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					DOWNLOADER.INSTANCE.downloadData();
				}
			});
		}
		
		executor.shutdown();
	}
}
