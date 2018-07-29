package com.aegeus.game.util;

public class IntRange {
	public int min = 0;
	public int max = 1;

	public IntRange() {}

	public IntRange(int i) {
		min = i;
		max = i;
	}

	public IntRange(int min, int max) {
		this.min = min;
		this.max = max;
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

	public int get() {
		return Util.randInt(min, max);
	}

	public void set(int min, int max) {
		setMin(min);
		setMax(max);
	}
}
