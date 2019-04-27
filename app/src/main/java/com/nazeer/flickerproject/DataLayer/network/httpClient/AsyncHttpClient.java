package com.nazeer.flickerproject.DataLayer.network.httpClient;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.json.JsonProcessor;

import java.util.Map;

public interface AsyncHttpClient {
	<T> void asyncCallAPiLink(final String link,
                          Map<String, Object> params,
                          final SuccessFailureCallBack<T> callBack,
                          final JsonProcessor<T> processor);
}
