package com.nazeer.flickerproject.DataLayer.repo;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.PhotosListResponseProcessor;
import com.nazeer.flickerproject.DataLayer.httpClient.AsyncHttpClient;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;

import java.util.HashMap;
import java.util.Map;

public class RemoteRecipeRepoClient implements PhotosRepoClient {

    private static final Object API_METHOD = "flickr.photos.search";
    private final String api_key;
    private final AsyncHttpClient httpClient;
    private final PhotosListResponseProcessor processor;
    private final String baseUrl;
    private  Map<String, Object> staticParams ;

    private static final String PAGE_KEY = "page";
    private static final String QUERY_KEY = "text";


    public RemoteRecipeRepoClient(String apiKey, AsyncHttpClient httpClient, PhotosListResponseProcessor processor, String baseUrl) {
        this.httpClient = httpClient;
        this.processor = processor;
        this.baseUrl = baseUrl;
        this.api_key = apiKey;
        staticParams = getStaticParamsMap();
    }

    @Override
    public void getPhotos(long page, String query, SuccessFailureCallBack<PhotoListResponse> callBack) {
        HashMap<String, Object> map = new HashMap<>(staticParams);
        map.put(PAGE_KEY, page);
        map.put(QUERY_KEY, query);
        httpClient.asyncCallAPiLink(baseUrl, map, callBack, processor);
    }

    private Map<String, Object> getStaticParamsMap() {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("nojsoncallback", 1);
        ret.put("format", "json");
        ret.put("api_key",api_key);
        ret.put("method",API_METHOD);
        return ret;
    }
}
