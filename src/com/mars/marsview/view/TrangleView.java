package com.mars.marsview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class TrangleView extends View {
	private final static int TRANGLEVIEW_COLOR = 0xFFCBC7C8;
	private final static int TRANGLEVIEW_WIDTH = 5;
	private Paint mPaint;
	private int mTrangleView_color = TRANGLEVIEW_COLOR;
	private int mTrangleView_height = dp2px(TRANGLEVIEW_WIDTH);
	private int mTrangleView_width = dp2px(TRANGLEVIEW_WIDTH);
	private float widthWeight = 1.0f;
	private float heightWeight = 1.0f;

	private boolean showInDymatic = true;

	/**
	 * @return the widthWeight
	 */
	public float getWidthWeight() {
		return widthWeight;
	}

	/**
	 * @param widthWeight
	 *            the widthWeight to set
	 */
	public void setWidthWeight(float widthWeight) {
		this.widthWeight = widthWeight;
		invalidate();
	}

	/**
	 * @return the heightWeight
	 */
	public float getHeightWeight() {
		return heightWeight;
	}

	/**
	 * @param heightWeight
	 *            the heightWeight to set
	 */
	public void setHeightWeight(float heightWeight) {
		this.heightWeight = heightWeight;
	}

	public TrangleView(Context context) {
		this(context, null);
	}

	/**
	 * @return the mTrangleView_color
	 */
	public int getmTrangleView_color() {
		return mTrangleView_color;
	}

	/**
	 * @param mTrangleView_color
	 *            the mTrangleView_color to set
	 */
	public void setmTrangleView_color(int mTrangleView_color) {
		this.mTrangleView_color = mTrangleView_color;
		invalidate();
	}

	/**
	 * @return the mTrangleView_width
	 */
	public int getmTrangleView_width() {
		return mTrangleView_width;
	}

	/**
	 * @param mTrangleView_width
	 *            the mTrangleView_width to set
	 */
	public void setmTrangleView_width(int mTrangleView_width) {
		this.mTrangleView_width = mTrangleView_width;
	}

	public TrangleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrangleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		obtainStyleAttrs(attrs);
	}

	private void obtainStyleAttrs(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.MyTrangleView);
		mTrangleView_color = ta
				.getColor(R.styleable.MyTrangleView_trangleview_color,
						mTrangleView_color);
		mTrangleView_width = (int) ta
				.getDimension(R.styleable.MyTrangleView_trangleview_width,
						mTrangleView_width);

		ta.recycle();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(mTrangleView_color);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mTrangleView_width = specSize;
			Utils.LogShow("mTrangleView_width", "EXACTLY");

		} else {
			mTrangleView_width = mTrangleView_width + getPaddingLeft()
					+ getPaddingRight();
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mTrangleView_height = specSize;
			Utils.LogShow("mTrangleView_width", "EXACTLY");
		} else {
			mTrangleView_height = mTrangleView_height + getPaddingLeft()
					+ getPaddingRight();
		}
		Utils.LogShow("mTrangleView_width", mTrangleView_width);
		Utils.LogShow("mTrangleView_height", mTrangleView_height);
		setMeasuredDimension(mTrangleView_width, mTrangleView_height);
	}

	private int mPaintTimes = 0;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaint();
		if (mTrangleView_width >= mTrangleView_height) {
			canvas.drawRect(0, 0, mTrangleView_width * widthWeight
					* mPaintTimes / drawTimes, mTrangleView_height * heightWeight,
					mPaint);
		} else {
			canvas.drawRect(0, 0, mTrangleView_width * widthWeight,
					mTrangleView_height * heightWeight * mPaintTimes / drawTimes,
					mPaint);
		}
		if (showInDymatic) {
			if (mPaintTimes++ < drawTimes) {
				invalidate(); // 实现动画的关键点
			}
		}
	}

	protected int dp2px(int dpVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, getResources().getDisplayMetrics());
	}

	protected int sp2px(int spVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, getResources().getDisplayMetrics());
	}

	public boolean isShowInDymatic() {
		return showInDymatic;
	}
	private int drawTimes = 50;
	/**
	 * @return the drawTimes
	 */
	public int getDrawTimes() {
		return drawTimes;
	}

	/**
	 * @param drawTimes the drawTimes to set
	 */
	public void setDrawTimes(int drawTimes) {
		this.drawTimes = drawTimes;
	}

	public void setShowInDymatic(boolean showInDymatic) {
		this.showInDymatic = showInDymatic;
		if (showInDymatic) {
			mPaintTimes = 0;
		} else {
			mPaintTimes = drawTimes;
		}
		invalidate();
	}

}
