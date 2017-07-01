package com.aegeus.game.stats;

import java.util.concurrent.ThreadLocalRandom;

public class FloatPossible {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private float min = 0;
	private float max = 0;

	public FloatPossible() {

	}

	public FloatPossible(float value) {
		this.min = value;
		this.max = value;
	}

	public FloatPossible(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public float get() {
		return min == max ? min : (random.nextFloat() * (max - min)) + min;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
}
