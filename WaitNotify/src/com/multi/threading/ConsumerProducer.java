package com.multi.threading;
import java.util.ArrayList;
import java.util.List;

class Processor{
	
	private List<Integer> myList = new ArrayList<>();
	private final int BOTTOM = 0;
	private final int LIMIT = 5;
	private int value = 0 ;
	
	Object lock = new Object();
	
	public void producer() throws InterruptedException {
		synchronized (lock) {
			while(true) {
				if(myList.size() ==  LIMIT) {
					System.out.println("wait for list clean up");
					lock.wait();
				}else {
					myList.add(value++);
					System.out.println("Adding value : " + value);
					lock.notify();
				}
				Thread.sleep(500);
			}
				
		}
	}
	
	public void consumer() throws InterruptedException {
		synchronized (lock) {
			while(true) {
				if(myList.size() ==  BOTTOM) {
					System.out.println("wait for list to increase");
					lock.wait();
				}else {
					myList.remove(--value);
					System.out.println("Removing value : " + value);
					lock.notify();
				}
				Thread.sleep(500);
			}
				
		}
	} 
}
public class ConsumerProducer {

	private static Processor p = new Processor();
	
	public static void main(String args[]) {
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					p.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					p.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		
		t1.start();
		t2.start();
		
	}
}
