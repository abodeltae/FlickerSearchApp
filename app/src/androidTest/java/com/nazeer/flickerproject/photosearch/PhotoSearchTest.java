package com.nazeer.flickerproject.photosearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.nazeer.flickerproject.DataLayer.models.Photo;
import com.nazeer.flickerproject.DataLayer.network.BitmapDownloader.AsyncBitmapDownloader;
import com.nazeer.flickerproject.imageLoader.Cache;
import com.nazeer.flickerproject.imageLoader.ImageLoaderImpl;
import com.nazeer.flickerproject.imageLoader.InMemoryLRUCache;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class PhotoSearchTest {
    @Test
    public void PhotoParcelTest() {
        Photo photo = new Photo("1233333234", "13f34", 1233333, "7gf13");
        Parcel parcel = Parcel.obtain();
        photo.writeToParcel(parcel, photo.describeContents());
        parcel.setDataPosition(0);
        Photo created = Photo.CREATOR.createFromParcel(parcel);

        assertEquals(photo, created);
    }

    @Test
    public void PersistenceStateParcelTest() {
        Photo photo1 = new Photo("1233333234", "13f34", 1233333, "7gf13");
        Photo photo2 = new Photo("34", "cv", 152, "23");
        ArrayList<Photo> photos = new ArrayList<>(Arrays.asList(photo1, photo2));
        PersistenceState state = new PersistenceState(photos, 2, 123, "kittens");
        Parcel parcel = Parcel.obtain();
        state.writeToParcel(parcel, state.describeContents());
        parcel.setDataPosition(0);
        PersistenceState created = PersistenceState.CREATOR.createFromParcel(parcel);
        assertEquals(state, created);
    }

    /*
     * Assure that image loader respects given limit of concurrent downloads
     **/
    @Test
    public void testImageLoaderMaximumConcurrentDownloads() {
        Context appContext = androidx.test.core.app.ApplicationProvider.getApplicationContext();
        int maxRunning = 10;
        int allowedToComplete = 2;
        Counter runningDownloads = new Counter();
        Counter completedTasks = new Counter();
        Counter requestedDownloads = new Counter();
        BooleanContainer exceededMaxRunning = new BooleanContainer();
        BooleanContainer reachedAllowedConcurrentTasks = new BooleanContainer();

        Cache cache = new Cache() {
            @Nullable
            @Override
            public Bitmap get(String key) {
                return null;
            }

            @Override
            public void put(String key, Bitmap bitmap) {

            }
        };
        AsyncBitmapDownloader bitmapDownloader = (url, callBack) -> {
            requestedDownloads.count++;
            runningDownloads.count++;
            if (runningDownloads.count == allowedToComplete) {
                reachedAllowedConcurrentTasks.res = true;
            }
            exceededMaxRunning.res = runningDownloads.count > maxRunning;
            if (completedTasks.count < allowedToComplete) {
                callBack.onSuccess(null);
                completedTasks.count++;
            }
        };
        ImageLoaderImpl imageLoader = new ImageLoaderImpl(cache, bitmapDownloader, maxRunning);
        ImageView imageView = new ImageView(appContext) {
            @Override
            public void setImageBitmap(Bitmap bm) {
                runningDownloads.count--;
            }
        };
        for (int i = 0; i < 100; i++) {
            imageLoader.displayImage(imageView, 0, "00");

        }
        assertFalse("image loader executed more concurrent tasks than it should ", exceededMaxRunning.res);
        assertTrue("image loader didn't reach allowed concurrent tasks  ", reachedAllowedConcurrentTasks.res);
        assertEquals("image loader completed more or less  tasks than that it should", allowedToComplete, completedTasks.count);
        assertEquals("image loader fired  more or less  tasks than that it should", maxRunning + allowedToComplete, requestedDownloads.count);

    }

    @Test
    public void testInMemoryCache() {
        InMemoryLRUCache cache = new InMemoryLRUCache(3);
        Bitmap.Config config = Bitmap.Config.ALPHA_8;
        Bitmap bitmap = Bitmap.createBitmap(2, 2, config);
        cache.put("key1", bitmap);
        cache.put("key2", bitmap);
        cache.put("key3", bitmap);
        cache.put("key4", bitmap);
        assertFalse("cache contains item that should've been pushed out due to lru rules and capacity", cache.contains("key1"));
        cache.get("key2");
        cache.put("key5", null);// this a hacky test to test how the cache behaves for cleared soft references
        assertFalse("cache didn't reorder item as it was accessed", cache.contains("key3"));
        assertTrue("cache doesn't contain last item inserted ", cache.contains("key5"));
        cache.get("key5");
        assertFalse("cache didn't remove accessed item that had null value", cache.contains("key5"));
    }



    static class Counter {
        int count = 0;
    }

    static class BooleanContainer {
        boolean res;
    }
}

