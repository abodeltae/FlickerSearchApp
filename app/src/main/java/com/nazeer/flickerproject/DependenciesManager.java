package com.nazeer.flickerproject;

import com.nazeer.flickerproject.DataLayer.DataLayerConfigs;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.PhotosListResponseProcessor;
import com.nazeer.flickerproject.DataLayer.httpClient.AsyncHttpClient;
import com.nazeer.flickerproject.DataLayer.httpClient.AsyncHttpClientImp;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepo;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoClient;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoImp;
import com.nazeer.flickerproject.DataLayer.repo.RemoteRecipeRepoClient;
import com.nazeer.flickerproject.photosearch.PhotoSearchPresenter;
import com.nazeer.flickerproject.photosearch.SearchPhotosContract;

public class DependenciesManager {

    private static PhotosRepoImp repo;

    public static PhotosRepo getPhotosRepo(){
        if(repo != null)return repo;
        DataLayerConfigs dataLayerConfigs =new DataLayerConfigs();
        AsyncHttpClient httpClient = new AsyncHttpClientImp();
        PhotosListResponseProcessor processor =new PhotosListResponseProcessor();
        PhotosRepoClient remoteClient = new RemoteRecipeRepoClient(dataLayerConfigs.getApiKey(),httpClient,processor, dataLayerConfigs.getBaseUrl());
        repo = new PhotosRepoImp(remoteClient);
        return repo;
    }

    public static SearchPhotosContract.Presenter createPhotoSearchPresenter(SearchPhotosContract.View view) {
        return new PhotoSearchPresenter(view,getPhotosRepo());
    }
}
