package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.lang.ref.SoftReference;

public class InMemoryLRUCache implements Cache<Bitmap> {
    private static final int DEFAULT_MAX_SIZE = 100;

    private SizedLinkedHashMap<String, SoftReference<Bitmap>> lruCache;

    public InMemoryLRUCache() {
        this(DEFAULT_MAX_SIZE);
    }

    public InMemoryLRUCache(int capacity) {
        lruCache = new SizedLinkedHashMap<>(capacity);
    }

    @Override
    public Bitmap get(String key) {
        if (lruCache.containsKey(key)) {
            Bitmap bitmap = lruCache.get(key).get();
            if (bitmap == null) { // bitmap has been garbage collected so free its spot for newer bitmaps
                lruCache.remove(key);
            }
            return bitmap;
        }
        return null;
    }

    public boolean contains(String key) {
        return lruCache.containsKey(key);
    }

    @Override
    public void put(@NonNull String key, @NonNull Bitmap bitmap) {
        lruCache.put(key, new SoftReference<>(bitmap));
    }
}
