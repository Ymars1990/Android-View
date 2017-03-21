package com.mars.marsview.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

public class LruCacheUtils {
	/*
	 * ��ʼ��MemoryCache 
	 */
	public static LruCache<String, Drawable> mMemoryCache;
	
	public static void initMemoryCache(){
	    int maxMemory = (int) Runtime.getRuntime().maxMemory();//��ȡϵͳ�����Ӧ�õ����ڴ��С
        int mCacheSize = maxMemory / 8;//����ͼƬ�ڴ滺��ռ�ð˷�֮һ
        mMemoryCache = new LruCache<String, Drawable>(mCacheSize) {
            //������д�˷�����������Bitmap�Ĵ�С
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
     * ���Drawable���ڴ滺��
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
     * ���ڴ滺���л�ȡһ��Drawable
     *
     * @param key
     * @return
     */
    public static Drawable getDrawableFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
    /**
     * ���ڴ滺�����Ƴ�
     *
     * @param key
     */
    public void removeCacheFromMemory(String key) {
        mMemoryCache.remove(key);
    }
    /**
     * �����ڴ滺��
     */
    public void cleanMemoryCCache() {
        mMemoryCache.evictAll();
    }
}
