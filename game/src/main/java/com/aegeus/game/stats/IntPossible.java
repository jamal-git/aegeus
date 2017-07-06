package com.aegeus.game.stats;

import java.util.concurrent.ThreadLocalRandom;

public class IntPossible {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private int min = 0;
	private int max = 1;

	public IntPossible() {

	}

	public IntPossible(int value) {
		this.min = value;
		this.max = value;
	}

	public IntPossible(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int get() {
		return min == max ? min : random.nextInt(min, max);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getDiff() {
		return max - min;
	}
}
