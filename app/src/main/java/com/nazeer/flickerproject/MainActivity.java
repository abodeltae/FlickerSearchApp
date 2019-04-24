package com.nazeer.flickerproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nazeer.flickerproject.photosearch.PhotoSearchView;
import com.nazeer.flickerproject.photosearch.SearchPhotosContract;


public class MainActivity extends AppCompatActivity {

    private SearchPhotosContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoSearchView photoSearchView = new PhotoSearchView(this);
        setContentView(photoSearchView);
        presenter = (SearchPhotosContract.Presenter) getLastCustomNonConfigurationInstance();
        if(presenter == null){
            presenter = DependenciesManager.getINSTANCE().createPhotoSearchPresenter(photoSearchView);
        }else{
            presenter.setView(photoSearchView);
            presenter.restore();
        }

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }



}
