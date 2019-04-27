package com.nazeer.flickerproject.DataLayer.network.BitmapDownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.network.NetworkAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBitmapDownloaderImp implements AsyncBitmapDownloader {


    @Override
    public void getBitMapFromUrl(String url, SuccessFailureCallBack<Bitmap> callBack) {
        new NetworkAsyncTask<>(() -> getBitMapFromUrl(url), callBack).execute();
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
