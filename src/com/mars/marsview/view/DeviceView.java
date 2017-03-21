package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.mars.marsview.utils.Utils;

public class DeviceView extends View {
	private Paint mRectPaint;
	private String tips = "ÕýÔÚËÑË÷...";
	private int index = 0;
	/**
	 * @return the tips
	 */
	public String getTips() {
		return tips;
	}

	/**
	 * @param tips
	 *            the tips to set
	 */
	public void setTips(String tips) {
		this.tips = tips;
	}

	private Paint mPaintText;
	private boolean stopFlag = false;

	public DeviceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();

	}

	public DeviceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initPaint();

	}

	public DeviceView(Context context) {
		this(context, null);
		initPaint();
	}

	private void initPaint() {
		mRectPaint = new Paint();
		mRectPaint.setColor(Color.WHITE);
		mRectPaint.setStyle(Paint.Style.STROKE);
		mRectPaint.setStrokeWidth(3.0f);
		mRectPaint.setTextSize(30.0f);
		mRectPaint.setAntiAlias(true); // Ïû³ý¾â³Ý

		// ÎÄ×Ö
		mPaintText = new Paint();
		mPaintText.setColor(Color.WHITE);
		mPaintText.setTextAlign(Paint.Align.CENTER);
		mPaintText.setTextSize(30);
		mPaintText.setAntiAlias(true); // Ïû³ý¾â³Ý

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		/*
		 * // »­´ó¿ò RectF r1 = new RectF(); r1.left = width / 5f; r1.top =
		 * getPaddingTop(); r1.right = width * 4f / 5; r1.bottom = height -
		 * getPaddingTop() - getPaddingBottom(); canvas.drawRoundRect(r1, 30,
		 * 30, mRectPaint);
		 */

		FontMetrics fm = mPaintText.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2f;
		float textX = mPaintText.measureText(tips);
		canvas.drawText(tips, width / 2, getPaddingTop() + height / 6,
				mPaintText);
		Utils.LogShow("index", "" + index);
		// ËÑË÷»¡
		if (index % 4 == 3) {
			RectF r3 = new RectF();
			r3.left = width / 5f;
			r3.top = height - getPaddingTop() - getPaddingBottom() - width * 3f
					/ 5 / 2;
			r3.right = width * 4f / 5;
			r3.bottom = height - getPaddingTop() - getPaddingBottom() + width
					* 3f / 5 / 2;
			canvas.drawArc(r3, 240, 60, false, mRectPaint);
		}
		if (index % 4 == 2 || index % 4 == 3) {
			RectF r4 = new RectF();
			r4.left = width / 5 + width * 3f / 5 / 2 / 3;
			r4.top = height - getPaddingTop() - getPaddingBottom() - width * 3f
					/ 5 / 2 / 3 * 2;
			r4.right = width * 4f / 5 - width * 3f / 5 / 2 / 3;
			r4.bottom = height - getPaddingTop() - getPaddingBottom() + width
					* 3f / 5 / 2 / 3 * 2;
			canvas.drawArc(r4, 240, 60, false, mRectPaint);
		}
		if (index % 4 == 1 || index % 4 == 2 || index % 4 == 3) {
			RectF r5 = new RectF();
			r5.left = width / 5 + width * 3f / 5 / 2 / 3 * 2;
			r5.top = height - getPaddingTop() - getPaddingBottom() - width * 3f
					/ 5 / 2 / 3;
			r5.right = width * 4 / 5 - width * 3f / 5 / 2 / 3 * 2;
			r5.bottom = height - getPaddingTop() - getPaddingBottom() + width
					* 3f / 5 / 2 / 3;
			canvas.drawArc(r5, 240, 60, false, mRectPaint);
		}
		// canvas.drawCircle(width / 5+width * 3 / 5/2, r1.bottom, width * 3 /
		// 5/2, mRectPaint);
		if (stopFlag) {
			index++;
			handler.sendEmptyMessageDelayed(0x01, 500);
		}
	}

	public boolean isStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
		if(this.stopFlag)
		{
			index = 0;
		}else{
			index = 3;
		}
		invalidate();
	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x01:								
				invalidate();
				break;
			default:
				break;
			}
		}
	};

}
