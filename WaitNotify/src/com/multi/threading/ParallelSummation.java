package com.multi.threading;

import java.util.Random;

public class ParallelSummation {

	public static void main(String[] args) throws InterruptedException {
		
		int sum[] = new int[15];
		Random random = new Random();
		
		for(int i=0;i<sum.length;i++)
			sum[i] = random.nextInt(1000);
		
		int numberofThreads = Runtime.getRuntime().availableProcessors();
		
		for(int i : sum)
			System.out.print(i+",");
		
		System.out.println();
		System.out.println("numberofThreads : "+numberofThreads);
		MainParallel parallel = new MainParallel(numberofThreads);
		System.out.println(parallel.getSum(sum));
		
	}
}

class MainParallel {
	
	private ParallelWorker [] workers;
	private int numberOfThreads;
	private int totalSum;
	
	public MainParallel(int numberofThreads) {
		super();
		this.numberOfThreads = numberofThreads;
		this.workers = new ParallelWorker[numberofThreads];
	}
	
	public int getSum(int sum[]) throws InterruptedException {
		
		int steps = (int) Math.ceil(sum.length*1.0/numberOfThreads);
		
		for(int i =0;i<numberOfThreads;++i) {
			workers[i] = new ParallelWorker(i*steps, (i+1) * steps, sum);
			workers[i].start();
		}
		
		for(ParallelWorker _worker : workers)
			_worker.join();
		
		for(ParallelWorker _worker : workers)
			totalSum += _worker.getPartialSum();
		
		return totalSum;
	}
	
	
}
class ParallelWorker extends Thread{
	
	private int partialSum;
	private int low;
	private int high;
	private int sums[];
	
	public ParallelWorker(int low, int high, int[] sums) {
		super();
		this.low = low;
		this.high = high;
		this.sums = sums;
	}

	public int getPartialSum() {
		return partialSum;
	}

	@Override
	public void run() {
		
		partialSum =0 ;
		
		for(int i = low ; i < high;i++)
			partialSum += sums[i];
	}
}
