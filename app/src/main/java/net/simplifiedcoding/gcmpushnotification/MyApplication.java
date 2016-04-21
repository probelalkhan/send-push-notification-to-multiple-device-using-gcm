package net.simplifiedcoding.gcmpushnotification;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Belal on 4/20/2016.
 */

public class MyApplication extends Application {
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;
    private SharedPrefManager sharedPrefManager;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public SharedPrefManager getSharedPrefManager() {
        if (sharedPrefManager == null)
            sharedPrefManager = new SharedPrefManager(this);
        return sharedPrefManager;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}