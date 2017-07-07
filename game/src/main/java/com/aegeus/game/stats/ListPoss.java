package com.aegeus.game.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class ListPoss<T> extends ArrayList<T> {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	public ListPoss(int initialCapacity) {
		super(initialCapacity);
	}

	public ListPoss() {
		super();
	}

	public ListPoss(Collection<? extends T> c) {
		super(c);
	}

	public T get() {
		return isEmpty() || size() <= 0 ? null : size() == 1 ? get(0) : get(random.nextInt(size()));
	}

}
