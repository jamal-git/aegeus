package com.aegeus.game.util;

public abstract class Condition<T> {
	public boolean isComplete(T t) {
		return true;
	}

	public abstract void onComplete(T t);

	public Condition<T>[] addOnComplete() {
		return null;
	}

	public boolean removeOnComplete() {
		return true;
	}
}