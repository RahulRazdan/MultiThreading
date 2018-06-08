package com.multi.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSummation extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5422067445484197207L;

	private int low;
	private int high;
	private int sums[];

	public ForkJoinSummation(int low, int high, int[] sums) {
		super();
		this.low = low;
		this.high = high;
		this.sums = sums;
	}

	public int getMaxSequentially() {

		int max = 0;

		for (int i = low + 1; i < high; i++) {
			if (sums[i] > max)
				max = sums[i];
		}

		return max;
	}

	@Override
	protected Integer compute() {

		if ((high - low) < App.THREASHOLD) {
			return getMaxSequentially();
		} else {
			int middle = (low + high) / 2;

			ForkJoinSummation fork1 = new ForkJoinSummation(low, middle, sums);
			ForkJoinSummation fork2 = new ForkJoinSummation(middle + 1, high, sums);

			invokeAll(fork1, fork2);

			return Math.max(fork1.join(), fork2.join());
		}
	}
}
