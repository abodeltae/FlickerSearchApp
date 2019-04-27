package com.nazeer.flickerproject.DataLayer.network;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;


public class NetworkAsyncTask<Res> {

    private final ExecuteInBackGround<Res> executeFunction;
    private final SuccessFailureCallBack<Res> callBack;
    private final AsyncTask<Void, Void, WebRequestResult<Res>> task;

    public NetworkAsyncTask( ExecuteInBackGround<Res> executeFunction  , SuccessFailureCallBack<Res> callBack ){
        this.executeFunction = executeFunction;
        this.callBack = callBack;
        this.task = createTask();
    }

    public void execute() {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @SuppressLint("StaticFieldLeak")
    // no risk of leaking here as the inner class will reference only its wrapper
    private AsyncTask<Void, Void, WebRequestResult<Res>> createTask() {
        return new AsyncTask<Void, Void, WebRequestResult<Res>>() {
            @Override
            protected WebRequestResult<Res> doInBackground(Void... voids) {
                try {
                    return new WebRequestResult<>(true, executeFunction.execute());
                } catch (Exception e) {
                    return new WebRequestResult<>(false, e);
                }
            }

            @Override
            protected void onPostExecute(WebRequestResult<Res> res) {
                if (callBack.isCanceled()) return;
                if (res.isSuccessful()) {
                    callBack.onSuccess(res.getResult());
                } else {
                    callBack.onFail(res.getThrowable());
                }
            }
        };
    }


}

