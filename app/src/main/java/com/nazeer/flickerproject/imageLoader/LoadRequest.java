package com.nazeer.flickerproject.imageLoader;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import java.lang.ref.WeakReference;

class LoadRequest {
    private WeakReference<ImageView> imageViewRef;
    private String url;
    @DrawableRes
    private int resId;

    LoadRequest(ImageView imageView, String url, int resId) {
        this.imageViewRef = new WeakReference<>(imageView);
        this.url = url;
        this.resId = resId;
    }

    ImageView getImageView() {
        return imageViewRef.get();
    }

    String getUrl() {
        return url;
    }

    int getResId() {
        return resId;
    }
}