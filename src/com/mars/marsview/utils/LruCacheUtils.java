package com.mars.marsview.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

public class LruCacheUtils {
	/*
	 * 初始化MemoryCache 
	 */
	public static LruCache<String, Drawable> mMemoryCache;
	
	public static void initMemoryCache(){
	    int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int mCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        mMemoryCache = new LruCache<String, Drawable>(mCacheSize) {
            //必须重写此方法，来测量Bitmap的大小
            @Override
            protected int sizeOf(String key, Drawable value) {
                if (value instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) value).getBitmap();
                    return bitmap == null ? 0 : bitmap.getByteCount();
                }
                return super.sizeOf(key, value);
            }
        };
	}
	
	/**
     * 添加Drawable到内存缓存
     *
     * @param key
     * @param drawable
     */
	public static void addDrawableToMemoryCache(String key, Drawable drawable) {
        if (getDrawableFromMemCache(key) == null && drawable != null) {
            mMemoryCache.put(key, drawable);
        }
    }
	
	/**
     * 从内存缓存中获取一个Drawable
     *
     * @param key
     * @return
     */
    public static Drawable getDrawableFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
    /**
     * 从内存缓存中移除
     *
     * @param key
     */
    public void removeCacheFromMemory(String key) {
        mMemoryCache.remove(key);
    }
    /**
     * 清理内存缓存
     */
    public void cleanMemoryCCache() {
        mMemoryCache.evictAll();
    }
}
