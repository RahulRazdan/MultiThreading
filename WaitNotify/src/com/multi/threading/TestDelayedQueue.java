package com.multi.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class MyDelayed implements Delayed {

	private long duration;
	private String message;
	
	public MyDelayed(long duration, String message) {
		super();
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
	}

	@Override
	public int compareTo(Delayed o) {
		if(duration < ((MyDelayed)o).getDuration()) {
			return -1;
		}
		if(duration > ((MyDelayed)o).getDuration()) {
			return +1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(duration-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MyDelayed [duration=" + duration + ", message=" + message + "]";
	}
	
}
public class TestDelayedQueue {

	public static void main(String[] args) {
	
		BlockingQueue<MyDelayed>  queue = new DelayQueue<>();
		
		queue.add(new MyDelayed(1000,"This is first message"));
		queue.add(new MyDelayed(10000,"This is second message"));
		queue.add(new MyDelayed(4000,"This is thirst message"));
		
		while(!queue.isEmpty()) {
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
