package com.nazeer.flickerproject.DataLayer.httpClient;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.JsonProcessor;

import java.util.Map;

public interface AsyncHttpClient {
	<T> void asyncCallAPiLink(final String link,
                          Map<String, Object> params,
                          final SuccessFailureCallBack<T> callBack,
                          final JsonProcessor<T> processor);
}
