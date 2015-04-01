package com.telstra.exercise.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> cache;
	
	public BitmapCache() {
		int maxMemory = (int)(Runtime.getRuntime().maxMemory());
		int cacheSize = maxMemory / 8;
		cache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap value) {
//				return value.getRowBytes()*value.getHeight();
				return value.getByteCount();
			};
		};
	}
	
	@Override
	public Bitmap getBitmap(String url) {
        return cache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
	}

}
