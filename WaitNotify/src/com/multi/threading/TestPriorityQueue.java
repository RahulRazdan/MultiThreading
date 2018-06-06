package com.multi.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class MyPriorityQueue implements Runnable{
	private BlockingQueue<String> queue ;

	public MyPriorityQueue(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public String toString() {
		return "MyPriorityQueue [queue=" + queue + "]";
	}


	@Override
	public void run() {
		try {
			System.out.println("Adding Items");
			Thread.sleep(5000);
			queue.add("Raz");
			queue.add("Abhi");
			queue.add("Dil");
			Thread.sleep(1000);
			queue.add("Aadu");
			Thread.sleep(3000);
			queue.add("Zeel");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class MySecondPriorityQueue implements Runnable{
	private BlockingQueue<String> queue ;

	public MySecondPriorityQueue(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public String toString() {
		return "MyPriorityQueue [queue=" + queue + "]";
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			System.out.println("Removed : "+queue.take());
			System.out.println("Removed : "+queue.take());
			System.out.println("Removed : "+queue.take());
			Thread.sleep(1000);
			System.out.println("Removed : "+queue.take());
			Thread.sleep(3000);
			System.out.println("Removed : "+queue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


public class TestPriorityQueue {
 public static void main(String[] args) {
	BlockingQueue<String> queue = new PriorityBlockingQueue<>();
	
	new Thread(new MyPriorityQueue(queue)).start();
	new Thread(new MySecondPriorityQueue(queue)).start();
}
	
}
