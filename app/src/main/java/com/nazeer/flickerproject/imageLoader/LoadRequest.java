package com.nazeer.flickerproject.imageLoader;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

public class LoadRequest {
    private ImageView imageView;
    private String url;
    @DrawableRes
    private int resId;

    public LoadRequest(ImageView imageView, String url, int resId) {
        this.imageView = imageView;
        this.url = url;
        this.resId = resId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getUrl() {
        return url;
    }

    public int getResId() {
        return resId;
    }
}