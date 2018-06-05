package com.multi.threading;

class Producer {

	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Starting produce..");
			wait();
			System.out.println("Completed produce..");

		}
	}

	public void consumers() throws InterruptedException {

		Thread.sleep(1000);

		synchronized (this) {
			System.out.println("Starting consumer..");
			notifyAll();
			Thread.sleep(3000);
		}
	}
}

public class waitNotify {

	public static void main(String args[]) {
		
		Producer p = new Producer();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					p.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					p.consumers();
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
		
		
	}
}
