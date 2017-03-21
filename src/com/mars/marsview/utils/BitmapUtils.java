package com.mars.marsview.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;

public class BitmapUtils {

	/**
	 * ��ȡ���ź�ı���ͼƬ
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param width
	 *            ��
	 * @param height
	 *            ��
	 * @return
	 */
	public static Bitmap readBitmapFromFileDescriptor(String filePath,
			int width, int height) {
		FileInputStream fis = null;
		BitmapFactory.Options options = null;
		try {
			fis = new FileInputStream(filePath);
			options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
			float srcWidth = options.outWidth;
			float srcHeight = options.outHeight;
			int inSampleSize = 1;

			if (srcHeight > height || srcWidth > width) {
				if (srcWidth > srcHeight) {
					inSampleSize = Math.round(srcHeight / height);
				} else {
					inSampleSize = Math.round(srcWidth / width);
				}
			}

			options.inJustDecodeBounds = false;
			options.inSampleSize = inSampleSize;
			return BitmapFactory.decodeFileDescriptor(fis.getFD(), null,
					options);
		} catch (Exception ex) {
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * BitmapFactory.decodeResource ���ص�ͼƬ���ܻᾭ�����ţ�������Ŀǰ�Ƿ��� java �����ģ�Ч�ʱȽϵͣ�������Ҫ����
	 * java ����ڴ档��ˣ��������ʹ�øýӿڼ���ͼƬ�����׵���OOM����
	 */
	public static Bitmap readBitmapFromResource1(Resources resources,
			int resourcesId, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources, resourcesId, options);
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		int inSampleSize = 1;

		if (srcHeight > height || srcWidth > width) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / height);
			} else {
				inSampleSize = Math.round(srcWidth / width);
			}
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;

		return BitmapFactory.decodeResource(resources, resourcesId, options);
	}
	/*
	 *  ����������ص�ͼƬ�������ţ����֮��ռ���ڴ��٣�Ч�ʸ���
	 */
	public static Bitmap readBitmapFromResource2(Resources resources,
			int resourcesId, int width, int height) {
		InputStream ins = resources.openRawResource(resourcesId);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(ins, null, options);
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		int inSampleSize = 1;

		if (srcHeight > height || srcWidth > width) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / height);
			} else {
				inSampleSize = Math.round(srcWidth / width);
			}
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;

		return BitmapFactory.decodeStream(ins, null, options);
	}
	/*
	 * �Ӷ��������ݶ�ȡͼƬ
	 */
	public static Bitmap readBitmapFromByteArray(byte[] data, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
	
	/**
     * ��assets�ļ���ȡͼƬ
     */
    public static Bitmap readBitmapFromAssetsFile(Context context, String filePath) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(filePath);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    /*
     * bitmap �����ļ�
     */
    public static void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        try {
            File desFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(desFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * ͼƬѹ��
     */
    public static Bitmap compressImage(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(isBm);
            return bitmap;
        } catch (OutOfMemoryError e) {
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }
    
    /**
     * ����scale����һ��ͼƬ
     *
     * @param bitmap
     * @param scale  �ȱ�����ֵ
     * @return
     */
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // ���Ϳ�Ŵ���С�ı���
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    
    /**
     * ��ȡ��Ƭexif��Ϣ�е���ת�Ƕ�
     *
     * @param path ��Ƭ·��
     * @return�Ƕ�
     */
    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
        }
        return degree;
    }
    
    /*
     *��ͼƬ��ת 
     * 
     */
    public static Bitmap rotateBitmap(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        return rotaBitmap;
    }
    /*
     * bitmap ת2����
     */
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    
    /*
     * BitmapתDrawable
     */
    public static Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
        Drawable drawable = new BitmapDrawable(resources, bm);
        return drawable;
    }
    /*
     * DrawableתBitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
