package com.aegeus.game.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class IntRange {
    private int min = 0;
    private int max = 1;

    public IntRange() {
    }

    public IntRange(int i) {
        this(i, i);
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
        return get(Util.RANDOM.nextFloat());
    }

    public int get(float a) {
        return Math.round(min + ((max - min) * a));
    }

    public void set(int min, int max) {
        setMin(min);
        setMax(max);
    }

    public static class Adapter implements JsonDeserializer<IntRange> {
        @Override
        public IntRange deserialize(JsonElement src, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
            JsonArray array = src.getAsJsonArray();
            return new IntRange(array.get(0).getAsInt(), array.get(1).getAsInt());
        }
    }
}
