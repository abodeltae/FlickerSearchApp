package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.lang.ref.SoftReference;

public class InMemoryCache implements Cache {
    private static final int DEFAULT_MAX_SIZE = 100;

    private SizedLinkedHashMap<String, SoftReference<Bitmap>> lruCache;

    public InMemoryCache() {
        this(DEFAULT_MAX_SIZE);
    }

    public InMemoryCache(int capacity) {
        lruCache = new SizedLinkedHashMap<>(DEFAULT_MAX_SIZE);
    }

    @Override
    public Bitmap getPhoto(String key) {
        if (lruCache.containsKey(key)) {
            Bitmap bitmap = lruCache.get(key).get();
            if (bitmap == null) { // bitmap has been garbage collected so free its spot for newer bitmaps
                lruCache.remove(key);
            }
            return bitmap;
        }
        return null;
    }

    @Override
    public void cachePhoto(@NonNull String key, @NonNull Bitmap bitmap) {
        lruCache.put(key, new SoftReference<>(bitmap));
    }
}
