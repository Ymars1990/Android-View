package com.mars.marsview.view;

import com.mars.marsview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;

public class RoundProgressBar extends HorizontalProgressBar {

	private int radius = dp2px(30);
	private int mMaxPaintWidth;

	public RoundProgressBar(Context context) {
		super(context);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mReachHeight = (int) (mUnReachHeight * 2.5f);
		TypedArray taArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);
		radius = (int) taArray.getDimension(
				R.styleable.RoundProgressBar_progress_radius, radius);
		taArray.recycle();
		mPaint.setTextSize(mTextSize);
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		mMaxPaintWidth = Math.max(mUnReachHeight, mReachHeight);
		int expect = radius * 2 + mMaxPaintWidth + getPaddingLeft()
				+ getPaddingRight();

		int width = resolveSize(expect, widthMeasureSpec);
		int height = resolveSize(expect, heightMeasureSpec);
		int realWidth = Math.min(width, height);
		radius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;
		setMeasuredDimension(realWidth, realWidth);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		String text = getProgress() + "%";
		float textWidth = mPaint.measureText(text);
		float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
		canvas.save();
		canvas.translate(getPaddingLeft()+mMaxPaintWidth/2,getPaddingTop()+mMaxPaintWidth/2);
		
		mPaint.setStyle(Style.STROKE);
		//draw unreachbar
		mPaint.setColor(mUnReachColor);
		mPaint.setStrokeWidth(mUnReachHeight);
		canvas.drawCircle(radius, radius, radius, mPaint);
		//draw reachbar
		mPaint.setColor(mReachColor);
		mPaint.setStrokeWidth(mReachHeight);
		float sweepAngle = getProgress()*1.0f/getMax()*300;
		canvas.drawArc(new RectF(0, 0, radius*2, radius*2), 120, sweepAngle,false,mPaint);
		//draw text
		mPaint.setColor(mTextColor);
		mPaint.setStyle(Style.FILL);
		canvas.drawText(text, radius-textWidth/2,radius-textHeight/2, mPaint);
		canvas.restore();
	}
}
