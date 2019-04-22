package com.nazeer.flickerproject.DataLayer.BitmapDownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.NetworkAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapDownloaderImp implements BitmapDownloader {


    @Override
    public void getBitMapFromUrl(String url, SuccessFailureCallBack<Bitmap> callBack) {
        new NetworkAsyncTask<>(()->getBitMapFromUrl(url) ,callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static Bitmap getBitMapFromUrl(String src) throws IOException {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
    }




}
