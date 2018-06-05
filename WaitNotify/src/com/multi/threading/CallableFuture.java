package com.multi.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyWorker implements Callable<String> {

	private int id;

	public MyWorker(int id) {
		super();
		this.id = id;
	}


	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return "id: "+id;
	}
	
}

public class CallableFuture {

	public static void main(String[] args) {
		
		List<Future<String>> future = new ArrayList<>();
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0;i<9;i++) {
			future.add(executor.submit(new MyWorker(i)));
		}
			
		executor.shutdown();
		
		for(Future<String> f : future) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
