package com.multi.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class App {

	static int THREASHOLD = 0 ;
	
	public static void main(String[] args) {
		
		int sums [] = new int[300000];
		Random random = new Random();
		
		for(int i=0;i<sums.length;i++)
			sums[i] = random.nextInt(10000);
		
		for(int i : sums)
			System.out.print(i+",");
		
		System.out.println();
		
		THREASHOLD = sums.length / Runtime.getRuntime().availableProcessors();
		
		long start = System.currentTimeMillis();
		System.out.println("Starting Sequentially..... ");
		ForkJoinSummation fork2 = new ForkJoinSummation(0, sums.length, sums);
		System.out.println(fork2.getMaxSequentially());
		System.out.println("Finished Sequential ... " + (System.currentTimeMillis() - start) +"msec");
		
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		
		long start1 = System.currentTimeMillis();
		System.out.println("Starting Parallel ..... ");
		ForkJoinSummation fork1 = new ForkJoinSummation(0, sums.length, sums);
		
		System.out.println(pool.invoke(fork1));
		
		System.out.println("Finished Sequential ... " + (System.currentTimeMillis() - start1) +"msec");
		
		
		
		
	}
}
