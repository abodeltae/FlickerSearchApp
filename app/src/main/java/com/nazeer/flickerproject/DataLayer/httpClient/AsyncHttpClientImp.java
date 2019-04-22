package com.nazeer.flickerproject.DataLayer.httpClient;

import android.net.Uri;
import android.os.AsyncTask;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.JsonProcessor;
import com.nazeer.flickerproject.DataLayer.NetworkAsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class AsyncHttpClientImp implements AsyncHttpClient {

    @Override
    public <T> void asyncCallAPiLink(final String link,
                                     Map<String, Object> params,
                                     final SuccessFailureCallBack<T> callBack,
                                     final JsonProcessor<T> processor) {
        new NetworkAsyncTask<T>(() -> readFromApiLink(link, params, processor), callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private <T> T readFromApiLink(String link, Map<String, Object> params, JsonProcessor<T> processor) throws IOException, JSONException {
        URL url = buildUrl(link, params);
        String response = makeGetRequest(url);
        return processor.process(response);

    }

    private String makeGetRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private URL buildUrl(String link, Map<String, Object> params) throws MalformedURLException {
        Uri.Builder builder = Uri.parse(link).buildUpon();
        if (params != null) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value != null) {
                    builder.appendQueryParameter(key, value.toString());
                }
            }
        }
        Uri uri = builder.build();
        return new URL(uri.toString());
    }


}
