package com.example.hewson.individualassignment;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hewson on 9/28/2016.
 */

public class VolleySingleton extends Application {
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private static VolleySingleton mInstance = null;

    private VolleySingleton(){
        mRequestQueue=Volley.newRequestQueue(MyPokedex.getAppContext());
        mImageLoader=new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
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
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static VolleySingleton getmInstance() {
        if(mInstance==null){
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public ImageLoader getmImageLoader() {

        return mImageLoader;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;

    }
}
