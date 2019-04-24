package com.nazeer.flickerproject;

import com.nazeer.flickerproject.DataLayer.BitmapDownloader.AsyncBitmapDownloader;
import com.nazeer.flickerproject.DataLayer.BitmapDownloader.AsyncBitmapDownloaderImp;
import com.nazeer.flickerproject.DataLayer.DataLayerConfigs;
import com.nazeer.flickerproject.DataLayer.JsonProcessors.PhotosListResponseProcessor;
import com.nazeer.flickerproject.DataLayer.httpClient.AsyncHttpClient;
import com.nazeer.flickerproject.DataLayer.httpClient.AsyncHttpClientImp;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepo;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoClient;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoImp;
import com.nazeer.flickerproject.DataLayer.repo.RemoteRecipeRepoClient;
import com.nazeer.flickerproject.imageLoader.Cache;
import com.nazeer.flickerproject.imageLoader.ImageLoader;
import com.nazeer.flickerproject.imageLoader.ImageLoaderImpl;
import com.nazeer.flickerproject.imageLoader.InMemoryCache;
import com.nazeer.flickerproject.photosearch.PhotoSearchPresenter;
import com.nazeer.flickerproject.photosearch.SearchPhotosContract;

public class DependenciesManager {

    private  static DependenciesManager INSTANCE ;
    private DependenciesManager (){}

    public static DependenciesManager getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new DependenciesManager();
        }
        return INSTANCE;
    }

    private static PhotosRepoImp repo;
    private static ImageLoaderImpl imageLoader;


    private   PhotosRepo getPhotosRepo(){
        if(repo != null)return repo;
        DataLayerConfigs dataLayerConfigs =new DataLayerConfigs();
        AsyncHttpClient httpClient = new AsyncHttpClientImp();
        PhotosListResponseProcessor processor =new PhotosListResponseProcessor();
        PhotosRepoClient remoteClient = new RemoteRecipeRepoClient(dataLayerConfigs.getApiKey(),httpClient,processor, dataLayerConfigs.getBaseUrl());
        repo = new PhotosRepoImp(remoteClient);
        return repo;
    }

    public static ImageLoader getImageLoader(){
        if(imageLoader != null) return imageLoader;
        Cache cache = new InMemoryCache();
        AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloaderImp();
         imageLoader = new ImageLoaderImpl(cache, asyncBitmapDownloader);
        return imageLoader;
    }

    public  SearchPhotosContract.Presenter createPhotoSearchPresenter(SearchPhotosContract.View view) {
        return new PhotoSearchPresenter(view,getPhotosRepo());
    }
}
