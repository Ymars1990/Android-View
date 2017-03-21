package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class CircleMenuView extends View {
	private Paint innerCircle;
	private Paint outCircle;
	float sweepAngle = 0.0f;
	float startAngle = 0.0f;

	private Bitmap wxBitmap;
	private Bitmap aliBitmap;
	private Bitmap jdBitmap;
	private CircleMenuOnClicker mOnclicker;
	private int position = 0;

	/**
	 * @param mOnclicker
	 *            the mOnclicker to set
	 */
	public void setmOnclicker(CircleMenuOnClicker mOnclicker) {
		this.mOnclicker = mOnclicker;
	}

	private Rect mRectSelectColor = new Rect();
	private float sweepangle = 0;

	public CircleMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CircleMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleMenuView(Context context) {
		this(context, null);
	}

	private void init() {
		innerCircle = new Paint();
		innerCircle.setColor(Color.RED);
		innerCircle.setStyle(Paint.Style.FILL);
		innerCircle.setStrokeWidth(2.0f); // 设置线宽
		innerCircle.setAntiAlias(true);

		outCircle = new Paint();
		outCircle.setColor(Color.YELLOW);
		outCircle.setStyle(Paint.Style.FILL);
		outCircle.setStrokeWidth(2.0f); // 设置线宽
		outCircle.setAntiAlias(true);

		wxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wx);
		aliBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.ali);
		jdBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jd);
	}

	private int cx;
	private int cy;
	private int innerRadius;
	private int outRadius;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * int specMode = MeasureSpec.getMode(widthMeasureSpec); int specSize =
		 * MeasureSpec.getSize(widthMeasureSpec);
		 * 
		 * int mWidth = 0; int mHeith = 0; if (specMode == MeasureSpec.EXACTLY)
		 * { mWidth = specSize;
		 * 
		 * } specSize = MeasureSpec.getSize(heightMeasureSpec); specMode =
		 * MeasureSpec.getMode(heightMeasureSpec); if (specMode ==
		 * MeasureSpec.EXACTLY) { mHeith = specSize;
		 * 
		 * } setMeasuredDimension(mWidth, mHeith);
		 */
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		cx = getWidth() / 2;
		innerRadius = getWidth() / 10;
		outRadius = getWidth() / 4;
		cy = getHeight() / 2;

		// 画等分弧形
		outCircle.setColor(0xFFD0E0F0);
		RectF outRctf = new RectF(cx - outRadius, cy * 2 - outRadius, cx
				+ outRadius, cy * 2 + outRadius);
		canvas.drawArc(outRctf, 180, 180, false, outCircle);
		// 画间隔线条
		startAngle = 270.0f;
		innerCircle.setColor(Color.WHITE);
		innerCircle.setStrokeWidth(10.0f);
		for (int i = 1; i < 3; i++) {
			canvas.save();// 保存当前画布
			canvas.rotate(startAngle + 60 * i, cx, cy * 2);
			canvas.drawLine(cx, cy * 2 - outRadius, cx, cy * 2, innerCircle);
			canvas.restore();
//			startAngle += sweepAngle;
		}
		canvas.save();
		// 画bitmap
		// 1
		mRectSelectColor.set((int) (cx - outRadius * 3 / 4 * Math.cos((90-sweepangle)* Math.PI / 180))- wxBitmap.getWidth()/2,
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((90-sweepangle)* Math.PI / 180)) - wxBitmap.getHeight() / 2,
				(int) (cx - outRadius* 3 / 4 * Math.cos((90-sweepangle) * Math.PI / 180))+ wxBitmap.getWidth() / 2,
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((90-sweepangle)* Math.PI / 180)) +wxBitmap.getHeight() / 2);
		canvas.drawBitmap(wxBitmap, null, mRectSelectColor, null);
		// 2
		mRectSelectColor.set(
				(int) (cx - outRadius * 3 / 4 * Math.cos((30+sweepangle)* Math.PI / 180))- aliBitmap.getWidth() / 2,
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((30+sweepangle)* Math.PI / 180)) - aliBitmap.getHeight() / 2,
				(int) (cx - outRadius* 3 / 4 * Math.cos((30+sweepangle) * Math.PI / 180))+ aliBitmap.getWidth() / 2,
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((30+sweepangle)* Math.PI / 180)) +aliBitmap.getHeight() / 2);
		canvas.drawBitmap(aliBitmap, null, mRectSelectColor, null);

		// 3
		mRectSelectColor.set(
				(int) (cx + outRadius * 3 / 4 * Math.cos((30-sweepangle) * Math.PI / 180))- jdBitmap.getWidth() / 2, 
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((30-sweepangle)* Math.PI / 180)) - jdBitmap.getHeight() / 2,
				(int) (cx + outRadius * 3 / 4 * Math.cos((30-sweepangle) * Math.PI / 180))+jdBitmap.getWidth() / 2, 
				(int)( cy * 2 - outRadius * 3 / 4*Math.sin((30-sweepangle)* Math.PI / 180)) +jdBitmap.getHeight() / 2);
		canvas.drawBitmap(jdBitmap, null, mRectSelectColor, null);
		canvas.restore();
		innerCircle.setStrokeWidth(2.0f); // 设置线宽
		innerCircle.setColor(Color.WHITE);
		// 画外间隔弧形
		RectF deivde = new RectF(cx - innerRadius - 10, cy * 2 - innerRadius
				- 10, cx + innerRadius + 10, cy * 2 + innerRadius + 10);
		canvas.drawArc(deivde, 180, 180, false, innerCircle);
		// 画内半圆
		innerCircle.setColor(Color.RED);
		RectF mInnerRectf = new RectF(cx - innerRadius, cy * 2 - innerRadius,
				cx + innerRadius, cy * 2 + innerRadius);
		canvas.drawArc(mInnerRectf, 180, 180, false, innerCircle);
	}

	private float mLastX;
	private float mLastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastX = event.getX();
			mLastY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float startAngle = getAngle(mLastX, mLastY);
			float endAngle = getAngle(x, y);
			sweepangle = startAngle - endAngle;
			invalidate();
		case MotionEvent.ACTION_UP:

			float mTouchAngle = (float) (Math.atan((cy * 2 - y) / (x - cx))
					/ Math.PI * 180);
			Utils.LogShow("点击", "y：" + y);
			Utils.LogShow("点击", "x：" + x);
			Utils.LogShow("点击", "角度：" + mTouchAngle);
			if (Math.sqrt((cy * 2 - y) * (cy * 2 - y) + (x - cx) * (x - cx)) > innerRadius) {
				if (mTouchAngle >= 0 && mTouchAngle <= 60) {
					Utils.LogShow("点击", "最右");
					position = 3;
				} else if (mTouchAngle >= -60 && mTouchAngle <= 0) {
					Utils.LogShow("点击", "最左");
					position = 1;
				} else {
					Utils.LogShow("点击", "中间");

					position = 2;
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		if (mOnclicker != null)
			mOnclicker.getOnClickerPosition(2);
		return super.onTouchEvent(event);
	}

	/**
	 * 获取触摸点的角度
	 */
	private float getAngle(float x, float y) {
		float deltaX = x - cx;
		float deltaY = y - cy * 2;
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return (float) (Math.asin(deltaY / distance) * 180 / Math.PI);
	}

	public interface CircleMenuOnClicker {
		void getOnClickerPosition(int pos);
	}

}
