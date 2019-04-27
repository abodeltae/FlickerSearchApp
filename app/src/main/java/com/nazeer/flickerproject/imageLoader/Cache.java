package com.nazeer.flickerproject.imageLoader;

import androidx.annotation.Nullable;

public interface Cache<T> {

    @Nullable
    T get(String key);

    void put(String key, T value);
}
