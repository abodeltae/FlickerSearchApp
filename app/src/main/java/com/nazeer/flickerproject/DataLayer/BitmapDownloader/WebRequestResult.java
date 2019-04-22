package com.nazeer.flickerproject.DataLayer.BitmapDownloader;

import android.graphics.Bitmap;

public class WebRequestResult<T> {
    private boolean isSuccessful ;
    private Throwable throwable;
    private T result;

     public WebRequestResult(boolean isSuccessful , T result) {
        this.isSuccessful = isSuccessful;
        this.result = result;
    }

    public WebRequestResult(boolean isSuccessful , Throwable throwable) {
        this.isSuccessful = isSuccessful;
        this.throwable = throwable;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public T getResult() {
        return result;
    }
}
