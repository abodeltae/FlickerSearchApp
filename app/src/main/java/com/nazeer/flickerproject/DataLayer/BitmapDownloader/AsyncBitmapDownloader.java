package com.nazeer.flickerproject.DataLayer.BitmapDownloader;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;

import java.io.IOException;

public interface AsyncBitmapDownloader {
	void getBitMapFromUrl(String url, SuccessFailureCallBack<Bitmap> callBack);
}
