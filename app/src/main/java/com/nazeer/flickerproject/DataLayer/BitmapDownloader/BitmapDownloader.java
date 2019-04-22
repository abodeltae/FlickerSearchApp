package com.nazeer.flickerproject.DataLayer.BitmapDownloader;

import android.graphics.Bitmap;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;

import java.io.IOException;

public interface BitmapDownloader {
	void getBitMapFromUrl(String url, SuccessFailureCallBack<Bitmap> callBack)throws IOException;
}
