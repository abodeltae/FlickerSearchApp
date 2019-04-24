package com.nazeer.flickerproject.imageLoader;

import java.util.LinkedHashMap;
import java.util.Map;

public class SizedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final int maxSize;

    public SizedLinkedHashMap(int maxSize) {
        super(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);
        this.maxSize = maxSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
