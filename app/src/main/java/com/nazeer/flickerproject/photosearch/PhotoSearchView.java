package com.nazeer.flickerproject.photosearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.DependenciesManager;
import com.nazeer.flickerproject.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoSearchView extends RelativeLayout implements SearchPhotosContract.View , SearchView.OnQueryTextListener {
    private ProgressBar loadingProgressBar ;
    private ProgressBar loadingMoreProgressBar;
    private SearchPhotosContract.delegates delegates;
    private final ArrayList<Photo> displayedPhotos = new ArrayList<>();
    private  PhotosAdapter adapter;
    private SearchView searchView;

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
        RecyclerView recyclerView = findViewById(R.id.photo_search_screen_photos_rv);
        adapter = new PhotosAdapter(getContext(),displayedPhotos, DependenciesManager.getImageLoader(),this::loadMore);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(adapter);
    }

    private void loadMore() {
        delegates.loadMore();
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
    public void showPhotos(List<Photo> photoList) {
       displayedPhotos.clear();
       displayedPhotos.addAll(photoList);
       adapter.notifyDataSetChanged();


    }

    @Override
    public void clearPhotos() {
        displayedPhotos.clear();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setDelegates(SearchPhotosContract.delegates delegates) {
        this.delegates = delegates;
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
    public void setQuery(String currentQuery) {
        searchView.setQuery(currentQuery, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        delegates.search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
