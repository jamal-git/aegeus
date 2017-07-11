package com.aegeus.game.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Action<T> {
	public static <T> List<Action<T>> loop(List<Action<T>> list, T input) {
		List<Action<T>> newList = new ArrayList<>(list);
		list.sort(Comparator.comparingInt(Action::getPriority));
		list.forEach(a -> {
			if (a.canActivate(input)) {
				a.activate(input);
				if (a.removeOnActivate()) newList.remove(a);
			}
		});
		return newList;
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
