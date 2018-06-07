package com.multi.threading;

import java.util.Random;

public class MergeSortSequential {
	
	public static void main(String[] args) {
		Random random = new Random();
		int num[] = new int[50];
		
		for(int i =0 ; i < num.length ; i++)
			num[i] = random.nextInt(1000)-500;
		
		MergeSortLocal merge = new MergeSortLocal(num);
		merge.mergeSort(0,num.length - 1);
		merge.showResults();
	}
}

class MergeSortLocal{
	
	private int num[];
	private int tempArray[];
	
	public MergeSortLocal(int[] num) {
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
