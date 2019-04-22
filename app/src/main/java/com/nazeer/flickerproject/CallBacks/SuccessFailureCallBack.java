package com.nazeer.flickerproject.CallBacks;

public abstract class SuccessFailureCallBack<T> {
	public abstract void  onSuccess(T data);
	public abstract void onFail(Throwable throwable);
}
