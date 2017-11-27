package com.aegeus.game.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ListPoss<T> extends ArrayList<T> {
	public ListPoss(int initialCapacity) {
		super(initialCapacity);
	}

	public ListPoss() {
		super();
	}

	public ListPoss(Collection<? extends T> c) {
		super(c);
	}

	@SafeVarargs
	public ListPoss(T... ts) {
		this(Arrays.asList(ts));
	}

	public T get() {
		return isEmpty() || size() <= 0 ? null : size() == 1 ? get(0) : get(Util.rInt(size()));
	}

}
