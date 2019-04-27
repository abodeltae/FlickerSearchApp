package com.nazeer.flickerproject.DataLayer.network;

class WebRequestResult<T> {
    private boolean isSuccessful ;
    private Throwable throwable;
    private T result;

    WebRequestResult(boolean isSuccessful, T result) {
        this.isSuccessful = isSuccessful;
        this.result = result;
    }

    WebRequestResult(boolean isSuccessful, Throwable throwable) {
        this.isSuccessful = isSuccessful;
        this.throwable = throwable;
    }

    boolean isSuccessful() {
        return isSuccessful;
    }

    Throwable getThrowable() {
        return throwable;
    }

    T getResult() {
        return result;
    }
}
