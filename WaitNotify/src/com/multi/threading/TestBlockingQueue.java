package com.multi.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class firstWorker implements Runnable{
	
	private BlockingQueue<Integer> queue;
	
	public firstWorker(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		int counter = 0;
		while(true) {
			try {
				System.out.println("Adding Queue : "+counter);
				queue.put(counter);
				counter++;
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class secondWorker implements Runnable{
private BlockingQueue<Integer> queue;
	
	public secondWorker(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				int result = queue.take();
				System.out.println("removing Queue : "+ result);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class TestBlockingQueue {
	public static void main(String[] args) {
	
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		firstWorker first = new firstWorker(queue);
		secondWorker second = new secondWorker(queue);
		
		new Thread(first).start();
		new Thread(second).start();
	}
}
