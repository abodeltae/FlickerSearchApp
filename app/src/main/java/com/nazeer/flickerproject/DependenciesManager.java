package com.nazeer.flickerproject;

import com.nazeer.flickerproject.DataLayer.json.PhotosListResponseProcessor;
import com.nazeer.flickerproject.DataLayer.network.ApiConfigs;
import com.nazeer.flickerproject.DataLayer.network.BitmapDownloader.AsyncBitmapDownloader;
import com.nazeer.flickerproject.DataLayer.network.BitmapDownloader.AsyncBitmapDownloaderImp;
import com.nazeer.flickerproject.DataLayer.network.httpClient.AsyncHttpClient;
import com.nazeer.flickerproject.DataLayer.network.httpClient.AsyncHttpClientImp;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepo;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoClient;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepoImp;
import com.nazeer.flickerproject.DataLayer.repo.RemotePhotosRepoClient;
import com.nazeer.flickerproject.imageLoader.Cache;
import com.nazeer.flickerproject.imageLoader.ImageLoader;
import com.nazeer.flickerproject.imageLoader.ImageLoaderImpl;
import com.nazeer.flickerproject.imageLoader.InMemoryLRUCache;
import com.nazeer.flickerproject.photosearch.PhotoSearchPresenter;
import com.nazeer.flickerproject.photosearch.SearchPhotosContract;

public class DependenciesManager {

    private  static DependenciesManager INSTANCE ;
    private DependenciesManager (){}

    static DependenciesManager getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new DependenciesManager();
        }
        return INSTANCE;
    }

    private static PhotosRepoImp repo;
    private static ImageLoaderImpl imageLoader;


    private   PhotosRepo getPhotosRepo(){
        if(repo != null)return repo;
        ApiConfigs apiConfigs = new ApiConfigs();
        AsyncHttpClient httpClient = new AsyncHttpClientImp();
        PhotosListResponseProcessor processor =new PhotosListResponseProcessor();
        PhotosRepoClient remoteClient = new RemotePhotosRepoClient(apiConfigs.getApiKey(), httpClient, processor, apiConfigs.getBaseUrl());
        repo = new PhotosRepoImp(remoteClient);
        return repo;
    }

    public static ImageLoader getImageLoader(){
        if(imageLoader != null) return imageLoader;
        Cache cache = new InMemoryLRUCache();
        AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloaderImp();
         imageLoader = new ImageLoaderImpl(cache, asyncBitmapDownloader);
        return imageLoader;
    }

    SearchPhotosContract.Presenter createPhotoSearchPresenter(SearchPhotosContract.View view) {
        return new PhotoSearchPresenter(view,getPhotosRepo());
    }
}
