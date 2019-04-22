package com.nazeer.flickerproject.photosearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.R;

import java.util.List;

public class PhotoSearchView extends RelativeLayout implements SearchPhotosContract.View , SearchView.OnQueryTextListener {
    private SearchView searchView;
    private ProgressBar loadingProgressBar ;
    private ProgressBar loadingMoreProgressBar;
    private RecyclerView recyclerView;
    private SearchPhotosContract.delegates delgates;

    public PhotoSearchView(Context context) {
        super(context);
        init(context);
    }

    public PhotoSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.photo_search_screen,this);
        searchView = findViewById(R.id.photo_search_screen_query_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        loadingMoreProgressBar = findViewById(R.id.photo_search_screen_query_load_more_progress_bar);
        loadingProgressBar = findViewById(R.id.photo_search_screen_query_progress_bar);
        recyclerView = findViewById(R.id.photo_search_screen_photos_rv);

    }



    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(GONE);

    }

    @Override
    public void showLoadingMore() {
        loadingMoreProgressBar.setVisibility(VISIBLE);

    }

    @Override
    public void hideLoadingMore() {
        loadingMoreProgressBar.setVisibility(GONE);

    }

    @Override
    public void appendPhotos(List<Photo> photoList) {

    }

    @Override
    public void clearPhotos() {

    }

    @Override
    public void setDelegates(SearchPhotosContract.delegates delegates) {
        this.delgates = delegates;
    }

    @Override
    public void showInvalidQueryError() {
        Toast.makeText(getContext(), R.string.invalid_query, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorFetchingData() {
        Toast.makeText(getContext(), R.string.error_fetching_photos, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
         delgates.search(query);
         return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
