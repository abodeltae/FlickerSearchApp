package com.nazeer.flickerproject.CallBacks;

public abstract class SuccessFailureCallBack<T> {
	private boolean canceled;
	public abstract void  onSuccess(T data);
	public abstract void onFail(Throwable throwable);
	public synchronized void cancel(){
		canceled = true;
	}
	public synchronized boolean isCanceled(){
		return canceled;
	}
}
