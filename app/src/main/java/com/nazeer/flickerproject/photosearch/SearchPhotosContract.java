package com.nazeer.flickerproject.photosearch;

import com.nazeer.flickerproject.DataLayer.models.Photo;

import java.util.List;

public class SearchPhotosContract {
    public interface Presenter {

    }

    public interface View {
        void showLoading();

        void hideLoading();

        void showLoadingMore();

        void hideLoadingMore();

        void appendPhotos(List<Photo> photoList);

        void clearPhotos();

        void setDelegates(delegates delegates);

        void showInvalidQueryError();

        void showErrorFetchingData();
    }

    public interface delegates {
        void search(String text);

        void loadMore();
    }
}
