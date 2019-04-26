package com.nazeer.flickerproject.photosearch;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.DataLayer.models.PhotoListResponse;
import com.nazeer.flickerproject.DataLayer.repo.PhotosRepo;

import java.util.ArrayList;

public class PhotoSearchPresenter implements SearchPhotosContract.Presenter {

    private static final String SAVED_STATE_KEY = "SAVED_STATE_KEY";
    private SearchPhotosContract.View view;
    private final PhotosRepo repo;
    private final ArrayList<Photo> photos = new ArrayList<>();
    private long currentPage = 1;
    private long totalPages = 1;
    private String currentQuery;
    private SuccessFailureCallBack<PhotoListResponse> runningRequestCallback;
    private static final String TAG = "PhotoSearchPresenter";


    public PhotoSearchPresenter(SearchPhotosContract.View view, PhotosRepo repo) {
        this.view = view;
        this.repo = repo;
        view.setDelegates(createDelegates());
    }

    private SearchPhotosContract.delegates createDelegates() {
        return new SearchPhotosContract.delegates() {
            @Override
            public void search(String text) {
                if (runningRequestCallback != null) runningRequestCallback.cancel();
                view.showLoading();
                view.clearPhotos();
                photos.clear();
                currentQuery = text;
                currentPage = 1;
                totalPages = 1;
                repo.getPhotos(currentPage, currentQuery, createSearchCallback());
            }

            @Override
            public void loadMore() {
                Log.i(TAG, "loading more for query " + currentQuery + "with page " + (currentPage + 1));
                if (runningRequestCallback != null) return;
                if (totalPages > currentPage) {
                    view.showLoadingMore();
                    repo.getPhotos(currentPage + 1, currentQuery, createSearchCallback());
                }
            }
        };
    }

    @Override
    public void restoreUi() {
        Log.i(TAG, "restoring UI  ");

        view.setDelegates(createDelegates());
        view.setQuery(currentQuery);
        view.showPhotos(photos);
    }

    @Override
    public void setView(SearchPhotosContract.View view) {
        this.view = view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_STATE_KEY, new State(photos, currentPage, totalPages, currentQuery));
    }

    @Override
    public void onRestoreState(Bundle savedState) {
        Log.i(TAG, "restoring state ");
        State state = savedState.getParcelable(SAVED_STATE_KEY);
        if (state != null && state.currentQuery != null && state.photos.size() > 0) {


            photos.clear();
            photos.addAll(state.photos);
            currentQuery = state.currentQuery;
            currentPage = state.currentPage;
            totalPages = state.totalPages;
            restoreUi();
            Log.i(TAG, "restoring state Success");
        } else {
            Log.i(TAG, "state wasn't found");

        }
    }

    private SuccessFailureCallBack<PhotoListResponse> createSearchCallback() {
        runningRequestCallback = new SuccessFailureCallBack<PhotoListResponse>() {
            @Override
            public void onSuccess(PhotoListResponse data) {
                currentPage = data.getPage();
                totalPages = data.getTotalPages();
                photos.addAll(data.getPhotos());
                view.showPhotos(photos);
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

    private static class State implements Parcelable {
        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
        private final ArrayList<Photo> photos;
        private final long currentPage;
        private final long totalPages;
        private final String currentQuery;

        State(ArrayList<Photo> photos, long currentPage, long totalPages, String currentQuery) {
            this.photos = photos;
            this.currentPage = currentPage;
            this.totalPages = totalPages;
            this.currentQuery = currentQuery;
        }

        State(Parcel in) {
            photos = in.createTypedArrayList(Photo.CREATOR);
            currentPage = in.readLong();
            totalPages = in.readLong();
            currentQuery = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(photos);
            dest.writeLong(currentPage);
            dest.writeLong(totalPages);
            dest.writeString(currentQuery);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
