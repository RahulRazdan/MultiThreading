package com.multi.threading;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Worker {
	Lock lock = new java.util.concurrent.locks.ReentrantLock();
	Condition condition = lock.newCondition();

	public void producer() throws InterruptedException {
		try {
			lock.lock();
			System.out.println("Producer method");
			condition.await();
			System.out.println("Producer continued");
		} finally {
			lock.unlock();
		}
	}

	public void consumer() throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(2000);
			System.out.println("Consumer method");
			condition.signal();
			System.out.println("Consumer method continued");
		} finally {
			lock.unlock();
		}
	}
}

public class ReentrantLock {
	public static void main(String[] args) {
		Worker worker = new Worker();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					worker.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					worker.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Reentrant complete");
	}
}
