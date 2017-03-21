package com.mars.marsview.view;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CircleView extends View {
	private final static int CIRCLEVIEW_COLOR = 0xFFCBC7C8;
	private final static int CIRCLEVIEW_RADIUS = 10;
	private Paint mPaint;
	private int mCircleview_color = CIRCLEVIEW_COLOR;
	private int mCircleview_radius = dp2px(CIRCLEVIEW_RADIUS);

	/**
	 * @return the mCircleview_color
	 */
	public int getmCircleview_color() {
		return mCircleview_color;
	}

	/**
	 * @param mCircleview_color the mCircleview_color to set
	 */
	public void setmCircleview_color(int mCircleview_color) {
		this.mCircleview_color = mCircleview_color;
		invalidate();
	}

	/**
	 * @return the mCircleview_radius
	 */
	public int getmCircleview_radius() {
		return mCircleview_radius;
	}

	/**
	 * @param mCircleview_radius the mCircleview_radius to set
	 */
	public void setmCircleview_radius(int mCircleview_radius) {
		this.mCircleview_radius = mCircleview_radius;
		invalidate();
	}

	public CircleView(Context context) {
		this(context, null);
	}

	public CircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		obtainStyleAttrs(attrs);
	}

	private void obtainStyleAttrs(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.CircleView);
		mCircleview_color = ta.getColor(
				R.styleable.CircleView_circleview_color, mCircleview_color);
		mCircleview_radius = (int) ta.getDimension(
				R.styleable.CircleView_circleview_radius, mCircleview_radius);
		
		ta.recycle();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(mCircleview_color);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		Utils.LogShow("mCircleview_color", mCircleview_color);
		Utils.LogShow("mCircleview_radius", mCircleview_radius);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int mWidth = 0;
		int mHeith = 0;
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mWidth = specSize;
		} else {
			mWidth = mCircleview_radius * 2 + getPaddingLeft()
					+ getPaddingRight();
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mHeith = specSize;
		} else {
			mHeith = mCircleview_radius * 2 + getPaddingTop();
		}
		setMeasuredDimension(mWidth, mHeith);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Utils.LogShow("View", "onDraw");
		initPaint();
		canvas.drawCircle(mCircleview_radius + getPaddingLeft(),
				mCircleview_radius + getPaddingTop(), mCircleview_radius,
				mPaint);
	}

	protected int dp2px(int dpVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, getResources().getDisplayMetrics());
	}

	protected int sp2px(int spVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, getResources().getDisplayMetrics());
	}

}
