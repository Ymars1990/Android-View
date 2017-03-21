package com.mars.marsview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class MyRoundImageView extends ImageView {
	private Paint paint;
	private Paint paintBorder;
	private Bitmap mSrcBitmap;
	private int roundRectwidth = 200;//���ÿ�͸�
	private int roundRectheight = 200;
	/**
	 * Բ�ǵĻ���
	 */
	private float mRadius;
	private boolean mIsCircle;

	public MyRoundImageView(final Context context) {
		this(context, null);
	}

	public MyRoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyRoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyRoundImageView, defStyle, 0);
		mRadius = ta.getDimension(
				R.styleable.MyRoundImageView_myroundimageview_radius, 0);
		roundRectwidth = (int) ta.getDimension(
				R.styleable.MyRoundImageView_myroundimageview_roundrectwidth,
				roundRectwidth);
		roundRectheight = (int) ta.getDimension(
				R.styleable.MyRoundImageView_myroundimageview_roundrectheight,
				roundRectheight);
		mIsCircle = ta.getBoolean(
				R.styleable.MyRoundImageView_myroundimageview_circle, false);
		int srcResource = attrs.getAttributeResourceValue(
				"http://schemas.android.com/apk/res/android", "src", 0);
		if (srcResource != 0) {
			mSrcBitmap = BitmapFactory.decodeResource(getResources(),
					srcResource);
		}
		ta.recycle();
		paint = new Paint();
		paint.setAntiAlias(true);
		paintBorder = new Paint();
		paintBorder.setStrokeWidth(1.0f);
		paintBorder.setColor(getResources().getColor(R.color.orange_ff));
		paintBorder.setAntiAlias(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Utils.LogShow("onDraw", "onDraw");

		Bitmap image = drawableToBitmap(getDrawable());
		if (mIsCircle) {
			Bitmap reSizeImage = reSizeImageC(image, roundRectwidth,
					roundRectheight);
			if (reSizeImage.getWidth() >= reSizeImage.getHeight()) {
				canvas.drawBitmap(
						createCircleImage(reSizeImage, roundRectwidth
								- getPaddingLeft() - getPaddingRight(),
								roundRectheight - getPaddingBottom()
										- getPaddingTop()),
						roundRectwidth / 2 - reSizeImage.getWidth() / 2
								+ getPaddingLeft(), roundRectheight / 2
								- reSizeImage.getWidth() / 2 + getPaddingTop(),
								paintBorder);
			} else {
				canvas.drawBitmap(
						createCircleImage(reSizeImage, roundRectwidth
								- getPaddingLeft() - getPaddingRight(),
								roundRectheight - getPaddingBottom()
										- getPaddingTop()), roundRectwidth / 2
								- reSizeImage.getHeight() / 2
								+ getPaddingLeft(),
						roundRectheight / 2 - reSizeImage.getHeight() / 2
								+ getPaddingTop(), paintBorder);
			}
			Utils.LogShow("��", "Բ");
			Utils.LogShow("roundRectheight", "" + roundRectheight);
			Utils.LogShow("roundRectwidth", "" + roundRectwidth);
			Utils.LogShow("reSizeImage.getWidth()", "" + reSizeImage.getWidth());
			Utils.LogShow("reSizeImage.getHeight()",
					"" + reSizeImage.getHeight());
		} else {
			Bitmap reSizeImage = reSizeImage(image, roundRectwidth,
					roundRectheight);
			canvas.drawBitmap(
					createRoundImage(reSizeImage, roundRectwidth,
							roundRectheight),
					roundRectwidth / 2 - reSizeImage.getWidth() / 2,
					roundRectheight / 2 - reSizeImage.getHeight() / 2, paintBorder);
			Utils.LogShow("��", "Բ��");
		}
	}

	/**
	 * ��Բ��
	 * 
	 * @param source
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap createRoundImage(Bitmap source, int width, int height) {
		Paint paint = new Paint();
		paint.setColor(0xff01ABF0);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rect, mRadius, mRadius, paint);
		// ���Ĵ���ȡ����ͼƬ�Ľ�������
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * ��Բ
	 * 
	 * @param source
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap createCircleImage(Bitmap source, int width, int height) {

		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.gray_selected));
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2,
				paint);
		// ���Ĵ���ȡ����ͼƬ�Ľ�������
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, (width - source.getWidth()) / 2,
				(height - source.getHeight()) / 2, paint);
		return target;

	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mSrcBitmap = BitmapFactory.decodeResource(getResources(), resId);
		Utils.LogShow("setImageResource", "setImageResource");
		Utils.LogShow("roundRectwidth", roundRectwidth);
		Utils.LogShow("roundRectheight", roundRectheight);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Utils.LogShow("onMeasure", "onMeasure");
		int mWidth = 0;
		int mHeight = 0;
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mWidth = specSize;
		} else {
			mWidth = roundRectwidth + getPaddingLeft() + getPaddingRight();
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mHeight = specSize;
		} else {
			mHeight = roundRectheight + getPaddingBottom() + getPaddingTop();
		}
		Utils.LogShow("roundRectwidth", roundRectwidth);
		Utils.LogShow("roundRectheight", roundRectheight);
		setMeasuredDimension(mWidth, mHeight);
	}

	/**
	 * drawableתbitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) {
			if (mSrcBitmap != null) {
				return mSrcBitmap;
			} else {
				return null;
			}
		} else if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * ����Bitmap�Ŀ��
	 * 
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	private Bitmap reSizeImage(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// ��������ű�
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ��������bitmap
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	/**
	 * ����Bitmap�Ŀ��
	 * 
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	private Bitmap reSizeImageC(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int x = (newWidth - width) / 2;
		int y = (newHeight - height) / 2;
		/*
		 * if (x > 0 && y > 0) {
		 * 
		 * return Bitmap.createBitmap(bitmap, 0, 0, width, height, null, true);
		 * }
		 */
		float scaleX = 1;
		float scaleY = 1;

		if (width >= height) {
			// ���տ�Ƚ��еȱ�����

		} else {
			// ���ո߶Ƚ��еȱ�����
			// ��������ű�

		}
		scaleY = ((float) newHeight) / height;
		scaleX = ((float) newWidth) / width;

		Utils.LogShow("scale", "" + scaleY);
		Utils.LogShow("scale", "" + scaleX);
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}
}