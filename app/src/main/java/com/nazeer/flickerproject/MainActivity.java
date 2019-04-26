package com.nazeer.flickerproject;

import android.os.Bundle;
import android.os.PersistableBundle;

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
            if (savedInstanceState != null) {
                presenter.onRestoreState(savedInstanceState);
            }
        }else{
            presenter.setView(photoSearchView);
            presenter.restoreUi();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }



}
