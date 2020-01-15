package com.aegeus.game.util;

public abstract class Action<T> {
    public abstract void execute(T t);

    public boolean canExecute(T t) {
        return true;
    }

    public boolean removeOnExecute() {
        return true;
    }

    public int getPriority() {
        return 0;
    }
}
