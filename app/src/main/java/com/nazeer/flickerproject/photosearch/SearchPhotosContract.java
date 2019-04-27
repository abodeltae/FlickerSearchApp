package com.nazeer.flickerproject.photosearch;

import android.os.Bundle;

import com.nazeer.flickerproject.DataLayer.models.Photo;

import java.util.List;

public class SearchPhotosContract {
    public interface Presenter {
        void restoreUi();

        void setView(View view);

        void onSaveInstanceState(Bundle outState);

        void onRestoreState(Bundle savedState);
    }

    public interface View {
        void showLoading();

        void hideLoading();

        void showLoadingMore();


        void showPhotos(List<Photo> photoList);

        void clearPhotos();

        void setDelegates(delegates delegates);

        void showErrorFetchingData();

        void setQuery(String currentQuery);
    }

    public interface delegates {
        void search(String text);
        void loadMore();
    }
}
