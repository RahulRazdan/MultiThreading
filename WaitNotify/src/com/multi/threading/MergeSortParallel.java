package com.multi.threading;

import java.util.Random;

public class MergeSortParallel {
	
	public static void main(String[] args) {
		Random random = new Random();
		int num[] = new int[300];
		
		
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("numberOfThreads ["+numberOfThreads+"]");
		
		for(int i =0 ; i < num.length ; i++)
			num[i] = random.nextInt(1000);
		
		System.out.println("Starting Parallel Merge Sort");
		MergeSortParallelLocal merge = new MergeSortParallelLocal(num);
		long start = System.currentTimeMillis();
		merge.mergeSort(0,num.length - 1,numberOfThreads);
		System.out.println("Total time : " + (System.currentTimeMillis() - start));
		merge.showResults();
		
		System.out.println("Starting Sequential Merge Sort");
		start = System.currentTimeMillis();
		MergeSortSequential merge2 = new MergeSortSequential();
		merge2.main(new String [] {});
		System.out.println("Total time : " + (System.currentTimeMillis() - start));
	}
}

class MergeSortParallelLocal{
	
	private int num[];
	private int tempArray[];
	
	public MergeSortParallelLocal(int[] num) {
		this.num = num;
		this.tempArray = new int[num.length];
	}
	
	public void mergeSort(int low, int high) {
		
		if(low >= high)
			return;
		
		int middle = (low+high)/2;
		
		mergeSort(0,middle);
		mergeSort(middle+1,high);
		merge(low,middle,high);
	}
	
	private Thread mergeSortThread(int low, int high,int numberOfThreads) {
		return new Thread() {
			public void run() {
				mergeSort(low,high,numberOfThreads);
				
			}
		};
	}
	
	public void mergeSort(int low, int high,int numberOfThreads) {
			
		if(numberOfThreads<=1) {
			mergeSort(low,high);
			return;
		}
		
		if(low >= high)
			return;
		
		int middle = (low+high)/2;
		
		Thread rightThread = mergeSortThread(low,middle,numberOfThreads);
		Thread leftThread = mergeSortThread(middle+1,high,numberOfThreads);

		rightThread.start();
		leftThread.start();
		
		try {
			rightThread.join();
			leftThread.join();
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
		merge(low,middle,high);
	}
	
	public void merge(int low, int middle , int high) {
		
		for(int i=low;i<=high;i++)
			tempArray[i] = num[i];
		
		int i = low;
		int j = low;
		int k= middle+1;
		
		
		while((i<=middle) && (k<=high)) {
			if(tempArray[i] <= tempArray[k]) {
				num[j] = tempArray[i];
				i++;
			}else {
				num[j] = tempArray[k];
				k++;
			}
			j++;
		}
		
		while(i<=middle) {
			num[j] = tempArray[i];
			i++;
			j++;
		}
		
		while(k<=high) {
			num[j] = tempArray[k];
			k++;
			j++;
		}
	}
	
	public void showResults() {
		for(int i =0 ; i<num.length ; i++)
			System.out.print(num[i] +" , ");
	}
}
