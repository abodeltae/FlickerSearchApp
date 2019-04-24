package com.nazeer.flickerproject.imageLoader;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

public interface ImageLoader {
    void displayImage(ImageView imageView, @DrawableRes int placeHolderRes, String url);
}
