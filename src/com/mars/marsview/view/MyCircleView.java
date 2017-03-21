package com.mars.marsview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class MyCircleView extends View {

	private int titleColor = 0xFF000000;
	private String titleText;

	/**
	 * @return the titleText
	 */
	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
		invalidate();
	}

	private int titleBackgroundColor = Color.parseColor("#B6B6B6");
	private int titleSize;
	private Paint mPaint;
	private Rect mBound;

	public MyCircleView(Context context) {
		this(context, null);
	}

	public MyCircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyCircleview);
		titleColor = ta.getColor(R.styleable.MyCakeView_cakeview_color,
				titleColor);
		titleBackgroundColor = (int) ta.getDimension(
				R.styleable.MyCakeView_cakeview_radius, titleBackgroundColor);

	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(titleSize);
		Log.i("titleSize", "" + titleSize);
		/**
		 * 得到自定义View的titleText内容的宽和高
		 */
		mBound = new Rect();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		init();
		if (Utils.isNotEmpty(titleText)) {
			mPaint.setColor(titleBackgroundColor);
		}else{
			mPaint.setColor(0xFFFFFFFF);
		}
		canvas.drawCircle(getWidth() / 2f, getWidth() / 2f, getWidth() / 2f,
				mPaint);
		if (Utils.isNotEmpty(titleText)) {
			mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
			mPaint.setColor(titleColor);
			canvas.drawText(titleText, getWidth() / 2 - mBound.width() / 2,
					getHeight() / 2 + mBound.height() / 2, mPaint);
		}
	}
}
