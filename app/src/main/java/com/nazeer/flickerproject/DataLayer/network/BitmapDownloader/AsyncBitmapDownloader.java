package com.nazeer.flickerproject.DataLayer.network.BitmapDownloader;

import android.graphics.Bitmap;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;

public interface AsyncBitmapDownloader {
	void getBitMapFromUrl(String url, SuccessFailureCallBack<Bitmap> callBack);
}
