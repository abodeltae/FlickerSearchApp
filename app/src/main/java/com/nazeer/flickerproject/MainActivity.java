package com.nazeer.flickerproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nazeer.flickerproject.photosearch.PhotoSearchView;
import com.nazeer.flickerproject.photosearch.SearchPhotosContract;


public class MainActivity extends AppCompatActivity {

    private SearchPhotosContract.Presenter presenter;
    private PhotoSearchView photoSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoSearchView = new PhotoSearchView(this);
        setContentView(photoSearchView);
        presenter = (SearchPhotosContract.Presenter) getLastCustomNonConfigurationInstance();
        if(presenter == null){
            presenter = DependenciesManager.createPhotoSearchPresenter(photoSearchView);
        }

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }



}
