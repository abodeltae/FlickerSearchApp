package com.nazeer.flickerproject.photosearch;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepo;

public class PhotoSearchPresenter implements SearchPhotosContract.Presenter {

    private final SearchPhotosContract.View view;
    private final PhotosRepo repo;
    private long currentPage  = 1;
    private long totalPages = 1;
    private String currentQuery;
    private SuccessFailureCallBack<PhotoListResponse> runningRequestCallback;


    public PhotoSearchPresenter(SearchPhotosContract.View view , PhotosRepo repo){
        this.view = view ;
        this.repo = repo;
        view.setDelegates(createDelegates());
    }

    private SearchPhotosContract.delegates createDelegates() {
        return new SearchPhotosContract.delegates() {
            @Override
            public void search(String text) {
                if(runningRequestCallback != null )runningRequestCallback.cancel();
                view.showLoading();
                view.clearPhotos();
                currentQuery = text.toString();
                currentPage = 1;
                totalPages = 1;
                repo.getPhotos(currentPage,currentQuery,createSearchCallback());
            }

            @Override
            public void loadMore() {
                if(runningRequestCallback != null)return;
                if(totalPages>currentPage){
                    view.showLoadingMore();
                    repo.getPhotos(currentPage+1,currentQuery,createSearchCallback());
                }
            }
        };
    }

    private SuccessFailureCallBack<PhotoListResponse> createSearchCallback() {
        runningRequestCallback =  new SuccessFailureCallBack<PhotoListResponse>() {
            @Override
            public void onSuccess(PhotoListResponse data) {
                currentPage = data.getPage();
                totalPages = data.getTotalPages();
                view.appendPhotos(data.getPhotos());
                view.hideLoading();
                view.hideLoadingMore();
                runningRequestCallback = null;
            }

            @Override
            public void onFail(Throwable throwable) {
                view.hideLoading();
                view.hideLoadingMore();
                view.showErrorFetchingData();
                runningRequestCallback = null;
            }
        };
        return runningRequestCallback;
    }
}
