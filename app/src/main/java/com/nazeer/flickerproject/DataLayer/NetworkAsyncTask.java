package com.nazeer.flickerproject.DataLayer;

import android.os.AsyncTask;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.BitmapDownloader.WebRequestResult;
import com.nazeer.flickerproject.DataLayer.httpClient.ExecuteInBackGround;



public class NetworkAsyncTask<Res> extends AsyncTask<Void, Void, WebRequestResult<Res>> {

    private final ExecuteInBackGround<Res> executeFunction;
    private final SuccessFailureCallBack<Res> callBack;

    public NetworkAsyncTask( ExecuteInBackGround<Res> executeFunction  , SuccessFailureCallBack<Res> callBack ){
        this.executeFunction = executeFunction;
        this .callBack = callBack;
    }
    @Override
    protected WebRequestResult<Res> doInBackground(Void... voids) {
        try{
            return new WebRequestResult<>(true,executeFunction.execute());
        }catch (Exception e){
            return new WebRequestResult<>(false, e);
        }
    }

    @Override
    protected void onPostExecute(WebRequestResult<Res> res) {
        if(res.isSuccessful()){
            callBack.onSuccess(res.getResult());
        }else {
            callBack.onFail(res.getThrowable());
        }
    }
}

