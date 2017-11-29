package com.aegeus.game.util;

import java.util.ArrayList;
import java.util.List;

public abstract class Action<T> {
	public static <T> List<Action<T>> loop(List<Action<T>> list, T input) {
		for (Action<T> a : new ArrayList<>(list)) {
			if (a.canActivate(input)) {
				a.activate(input);
				list.removeIf(Action::removeOnActivate);
			}
		}
		return list;
	}

	public abstract void activate(T t);

	public boolean canActivate(T t) {
		return true;
	}

	public boolean removeOnActivate() {
		return true;
	}

	public int getPriority() {
		return 0;
	}
}
