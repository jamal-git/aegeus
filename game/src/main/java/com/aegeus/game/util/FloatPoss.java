package com.aegeus.game.util;

public class FloatPoss {
	private float min = 0;
	private float max = 0;

	public FloatPoss() {

	}

	public FloatPoss(float value) {
		this.min = value;
		this.max = value;
	}

	public FloatPoss(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public float get() {
		return min == max ? min : (Util.rFloat() * (max - min)) + min;
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
