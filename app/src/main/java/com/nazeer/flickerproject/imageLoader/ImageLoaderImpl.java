package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.BitmapDownloader.AsyncBitmapDownloader;

import java.util.HashMap;

public class ImageLoaderImpl implements ImageLoader{

    private final Cache cache;
    private final AsyncBitmapDownloader asyncBitmapDownloader;
    private HashMap<ImageView,String> imageViewToUrlMap = new HashMap<>();
    private HashMap<String,ImageView> urlToImageViewMap = new HashMap<>();
    private static final String TAG = "ImageLoaderImpl";

    public ImageLoaderImpl(Cache cache , AsyncBitmapDownloader asyncBitmapDownloader){
        this.cache = cache;
        this.asyncBitmapDownloader = asyncBitmapDownloader;
    }

    @Override
    public void displayImage(ImageView imageView, @DrawableRes int placeHolderRes, String url) {
        if(imageViewToUrlMap.containsKey(imageView)){ // there is a running request for this image view and we need to invalidate it first
            String oldUrl = imageViewToUrlMap.get(imageView);
            urlToImageViewMap.remove(oldUrl);
        }else if (urlToImageViewMap.containsKey(url)){
             // the url could exist with a stale image view so we have to clear the image to avoid leaks (rotating a screen)
            ImageView oldImageView = urlToImageViewMap.get(url);
            imageViewToUrlMap.remove(oldImageView);
        }
        imageViewToUrlMap.put(imageView,url);
        urlToImageViewMap.put(url,imageView);
        Bitmap cached = cache.getPhoto(url);
        if(cached != null){
            imageView.setImageBitmap(cached);
            imageViewToUrlMap.remove(imageView);
            urlToImageViewMap.remove(url);
        }else {
            imageView.setImageResource(placeHolderRes);
            asyncBitmapDownloader.getBitMapFromUrl(url, new SuccessFailureCallBack<Bitmap>() {
                @Override
                public void onSuccess(Bitmap data) {
                    cache.cachePhoto(url,data);
                    ImageView target = urlToImageViewMap.get(url);
                    if(target != null){
                        target.setImageBitmap(data);
                    }
                    imageViewToUrlMap.remove(target);
                    urlToImageViewMap.remove(url);
                    Log.w(TAG, String.format("on Success and i currently have %d mapped Images and %d mapped urls", imageViewToUrlMap.size(),urlToImageViewMap.size()));

                }

                @Override
                public void onFail(Throwable throwable) {
                    Log.w(TAG,"failed to download image for url : " + url);
                    ImageView target = urlToImageViewMap.get(url);
                    imageViewToUrlMap.remove(target);
                    urlToImageViewMap.remove(url);
                    Log.w(TAG, String.format("on fail and i currently have %d mapped Images and %d mapped urls", imageViewToUrlMap.size(),urlToImageViewMap.size()));


                }
            });
        }
    }
}
