package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public interface Cache {

    @Nullable
    Bitmap get(String key);

    void put(String key, Bitmap bitmap);
}
