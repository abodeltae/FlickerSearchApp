package com.nazeer.flickerproject.photosearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.R;
import com.nazeer.flickerproject.imageLoader.ImageLoader;

import java.util.ArrayList;

class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.VH> {

    private final Context context;
    private final ArrayList<Photo> items;
    private final ImageLoader imageLoader;
    private final LoadMoreCallBack loadMoreCallBack;

    public PhotosAdapter(@NonNull Context context, @NonNull ArrayList<Photo> displayedPhotos, ImageLoader imageLoader , LoadMoreCallBack loadMoreCallBack) {
        this.context = context;
        this.items = displayedPhotos;
        this.imageLoader = imageLoader;
        this.loadMoreCallBack = loadMoreCallBack;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item,parent,false);

        return new VH(view,imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(items.get(position));
        if(position == getItemCount() -1 ){
            loadMoreCallBack.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder{

        private final ImageView imageView;
        private final ImageLoader imageLoader;

        VH(@NonNull View itemView , ImageLoader imageLoader) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_item_image_view);
            this.imageLoader = imageLoader;
        }

        void bind ( Photo photo){
             imageLoader.displayImage(imageView,R.drawable.ic_baseline_image_24px,photo.getUrl());
        }
    }
}
