package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public interface Cache {

    @Nullable
    Bitmap getPhoto(String key );

    void cachePhoto (String key , Bitmap bitmap);
}
