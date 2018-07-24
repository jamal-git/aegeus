package com.aegeus.game.util;

public abstract class Action<T> {
	public abstract void act(T t);

	public boolean canAct(T t) {
		return true;
	}

	public boolean removeOnAct() {
		return true;
	}

	public int getPriority() {
		return 0;
	}
}
