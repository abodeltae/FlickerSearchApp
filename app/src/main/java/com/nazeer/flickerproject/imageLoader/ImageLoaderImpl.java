package com.nazeer.flickerproject.imageLoader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.nazeer.flickerproject.CallBacks.SuccessFailureCallBack;
import com.nazeer.flickerproject.DataLayer.network.BitmapDownloader.AsyncBitmapDownloader;
import com.nazeer.flickerproject.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class ImageLoaderImpl implements ImageLoader {

    private static final int DEFAULT_MAX_ALLOWED_DOWNLOAD_REQUESTS = 8;
    private final Cache<Bitmap> cache;
    private final AsyncBitmapDownloader asyncBitmapDownloader;
    // these maps map to images and urls for current downloads
    private HashMap<UUID, String> imageViewTagUuidToUrlMap = new HashMap<>();
    private HashMap<String, WeakReference<ImageView>> urlToImageViewMap = new HashMap<>();
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
                if (urlToImageViewMap.containsKey(url)) {
                    ImageView target = urlToImageViewMap.get(url).get();
                    if (target != null) {
                        target.setImageBitmap(data);
                        removeFromMaps(target, url);
                    }
                }
                runningDownloadsCount--;
                popRequests();
            }
            @Override
            public void onFail(Throwable throwable) {
                Log.w(TAG, "failed to download image for url : " + url);
                if (urlToImageViewMap.containsKey(url)) {
                    ImageView target = urlToImageViewMap.get(url).get();
                    if (target != null) {
                        removeFromMaps(target, url);
                    }
                }
                runningDownloadsCount--;
                popRequests();
            }
        });
    }

    private void popRequests() {
        while (runningDownloadsCount < maxAllowedRunningDownloads && !requestsHolder.isEmpty()) {
            LoadRequest request = requestsHolder.pollFirst();
            ImageView imageView = request.getImageView();
            if (imageView != null) {
                showFromWeb(request.getImageView(), request.getUrl(), request.getResId());
            }
        }
    }

    private void removeFromMaps(@NonNull ImageView imageView, String url) {
        UUID uuid = getOrAssignUUID(imageView);
        imageViewTagUuidToUrlMap.remove(uuid);
        urlToImageViewMap.remove(url);
    }

    private void addMapping(@NonNull ImageView imageView, String url) {
        imageViewTagUuidToUrlMap.put(getOrAssignUUID(imageView), url);
        urlToImageViewMap.put(url, new WeakReference<>(imageView));
    }

    /*
     * Remove mapping between images and urls to avoid call backs firing for views that has been recycled
     *
     * Also make sure to clear ImageViews saved for urls that has been assigned to different ImageViews to avoid leaks
     * */
    private void invalidateMaps(@NonNull ImageView imageView, String url) {
        UUID uuid = getOrAssignUUID(imageView);
        if (imageViewTagUuidToUrlMap.containsKey(uuid)) { // there is a running request for this image view and we need to invalidate it first
            String oldUrl = imageViewTagUuidToUrlMap.get(uuid);
            imageViewTagUuidToUrlMap.remove(uuid);
            urlToImageViewMap.remove(oldUrl);

        } else if (urlToImageViewMap.containsKey(url)) {
            // the url could exist with a stale image view so we have to clear the image to avoid leaks (with screen rotations )
            ImageView oldImageView = urlToImageViewMap.get(url).get();
            if (oldImageView != null) {
                imageViewTagUuidToUrlMap.remove(getOrAssignUUID(oldImageView));
            }
            urlToImageViewMap.remove(url);
        }
    }

    private UUID getOrAssignUUID(@NonNull ImageView imageView) {
        UUID tag = (UUID) imageView.getTag(R.id.image_loader_tag_id);
        if (tag == null) {
            tag = UUID.randomUUID();
            imageView.setTag(R.id.image_loader_tag_id, tag);
        }
        return tag;
    }

}
