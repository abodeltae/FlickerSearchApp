package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.network.BitmapDownloader.AsyncBitmapDownloader;

import java.util.HashMap;
import java.util.LinkedList;

public class ImageLoaderImpl implements ImageLoader {

    private static final int DEFAULT_MAX_ALLOWED_DOWNLOAD_REQUESTS = 8;
    private final Cache<Bitmap> cache;
    private final AsyncBitmapDownloader asyncBitmapDownloader;
    // these maps map to images and urls for current downloads
    private HashMap<ImageView, String> imageViewToUrlMap = new HashMap<>();
    private HashMap<String, ImageView> urlToImageViewMap = new HashMap<>();
    private static final String TAG = "ImageLoaderImpl";
    private LinkedList<LoadRequest> requestsHolder = new LinkedList<>();
    private int runningDownloadsCount = 0;
    private int maxAllowedRunningDownloads;

    public ImageLoaderImpl(Cache<Bitmap> cache, AsyncBitmapDownloader asyncBitmapDownloader) {
        this(cache, asyncBitmapDownloader, DEFAULT_MAX_ALLOWED_DOWNLOAD_REQUESTS);
    }

    public ImageLoaderImpl(Cache<Bitmap> cache, AsyncBitmapDownloader asyncBitmapDownloader, int maxAllowedRunningDownloads) {
        this.cache = cache;
        this.asyncBitmapDownloader = asyncBitmapDownloader;
        this.maxAllowedRunningDownloads = maxAllowedRunningDownloads;
    }


    public void displayImage(ImageView imageView, @DrawableRes int placeHolderRes, String url) {
        invalidateMaps(imageView, url);
        Bitmap cached = cache.get(url);
        if (cached != null) {
            imageView.setImageBitmap(cached);
            removeFromMaps(imageView, url);

        } else {
            imageView.setImageResource(placeHolderRes);
            if (runningDownloadsCount >= maxAllowedRunningDownloads) {
                requestsHolder.add(new LoadRequest(imageView, url, placeHolderRes));
                if (requestsHolder.size() > maxAllowedRunningDownloads) {
                    requestsHolder.removeFirst();
                }
            } else {
                showFromWeb(imageView, url, placeHolderRes);
            }

        }
    }

    private void showFromWeb(ImageView imageView, String url, @DrawableRes int placeHolderRes) {
        runningDownloadsCount++;
        invalidateMaps(imageView, url);
        addMapping(imageView, url);
        imageView.setImageResource(placeHolderRes);
        asyncBitmapDownloader.getBitMapFromUrl(url, new SuccessFailureCallBack<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data) {
                cache.put(url, data);
                ImageView target = urlToImageViewMap.get(url);
                if (target != null) {
                    target.setImageBitmap(data);
                    removeFromMaps(target, url);
                }
                if (imageViewToUrlMap.size() != urlToImageViewMap.size()) {
                    Log.w(TAG, String.format("on Success and i currently have different sizes for mappings this could be a leak :  %d mapped Images and %d mapped urls", imageViewToUrlMap.size(), urlToImageViewMap.size()));
                }
                runningDownloadsCount--;
                popRequests();
            }
            @Override
            public void onFail(Throwable throwable) {
                Log.w(TAG, "failed to download image for url : " + url);
                ImageView target = urlToImageViewMap.get(url);
                removeFromMaps(target, url);
                if (imageViewToUrlMap.size() != urlToImageViewMap.size()) {
                    Log.w(TAG, String.format("on fail and i currently have different sizes for mappings this could be a leak :  %d mapped Images and %d mapped urls", imageViewToUrlMap.size(), urlToImageViewMap.size()));
                }
                runningDownloadsCount--;
                popRequests();
            }
        });
    }

    private void popRequests() {
        int toPop = maxAllowedRunningDownloads - runningDownloadsCount;
        for (int i = 0; i < toPop && !requestsHolder.isEmpty(); i++) {
            LoadRequest request = requestsHolder.pollLast();
            showFromWeb(request.getImageView(), request.getUrl(), request.getResId());
        }
    }

    private void removeFromMaps(ImageView imageView, String url) {
        imageViewToUrlMap.remove(imageView);
        urlToImageViewMap.remove(url);
    }

    private void addMapping(ImageView imageView, String url) {
        imageViewToUrlMap.put(imageView, url);
        urlToImageViewMap.put(url, imageView);
    }

    /*
     * Remove mapping between images and urls to avoid call backs firing for views that has been recycled
     *
     * Also make sure to clear ImageViews saved for urls that has been assigned to different ImageViews to avoid leaks
     * */
    private void invalidateMaps(ImageView imageView, String url) {
        if (imageViewToUrlMap.containsKey(imageView)) { // there is a running request for this image view and we need to invalidate it first
            String oldUrl = imageViewToUrlMap.get(imageView);
            imageViewToUrlMap.remove(imageView);
            urlToImageViewMap.remove(oldUrl);

        } else if (urlToImageViewMap.containsKey(url)) {
            // the url could exist with a stale image view so we have to clear the image to avoid leaks (with screen rotations )
            ImageView oldImageView = urlToImageViewMap.get(url);
            imageViewToUrlMap.remove(oldImageView);
            urlToImageViewMap.remove(url);
        }
    }

}
