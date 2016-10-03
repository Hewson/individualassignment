package com.example.hewson.individualassignment.network;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.application.MyPokedex;

/**
 * Created by Hewson on 9/28/2016.
 * Adapted code from Android Developer on how to create a Singleton class
 * This class exists application wide and allows for queueing of requests through the
 * Volley method of data transfer
 */

public class VolleySingleton extends Application {
    //declaration of variables: imageLoader, requestQueue
    private static VolleySingleton sInstance = null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    //constructor that instantiates a requestQueue and imageLoader with the context as the application wide context
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyPokedex.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    //method that returns an instance of the volleySingleton object
    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    //getters for the 2 objects
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
