package com.telstra.exercise.network;
import android.nfc.Tag;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.telstra.exercise.util.BitmapCache;
import com.telstra.exercise.MainApplication;

public class VolleyManager {

    private static final String TAG = "VolleyPatterns";
    private static VolleyManager volleyManager;
    private RequestQueue requestQueue;
    private BitmapCache cache;
    private ImageLoader imageLoader;

    private VolleyManager() {
        requestQueue = Volley.newRequestQueue(MainApplication.getInstance());

        cache = new BitmapCache();
        imageLoader = new ImageLoader(requestQueue, cache);
    }

    public static VolleyManager getInstance() {
        if(volleyManager == null) {
            volleyManager = new VolleyManager();
        }
        return volleyManager;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        // set the default tag if tag is empty
        addRequestQueue(request, TAG);
    }

    public <T> void addRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        requestQueue.add(request);
    }

    public void cancelPendingRequests() {
        cancelPendingRequests(TAG);
    }

    public void cancelPendingRequests(Object tag) {
        requestQueue.cancelAll(tag);
    }


    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, 0, 0);
    }

    public void loadImage(ImageView imageView, String url, int width, int height) {
        ImageListener listener = getImageListener(url, imageView, 0, 0);
        imageLoader.get(url, listener, width, height);
    }

    public static ImageListener getImageListener(final String url, final ImageView view,
                                                 final int defaultImageResId, final int errorImageResId) {
        return new ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null && view.getTag() != null &&
                        view.getTag().equals(url)) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }
        };
    }
}


