package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

	private Paint mPaint;
	private Paint mOutPaint;
	private Paint mInsidePaint;
	private Paint mthirdPaint;
	private Paint scanPaint;
	private int paintColor = 0xFFFFFFFF;
	private int outCircleColor = 0xFF9DBCDA;
	private int insideCircleColor = 0xFF4DBCDA;
	private int thirdCircleColor = 0x99000000;
	private int ScanColor = 0x55FF3300;
	private int scaneIndex = 0;

	public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public RingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initPaint();
	}

	public RingView(Context context) {
		this(context, null);
		initPaint();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(paintColor);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(5.0f);
		mPaint.setStyle(Paint.Style.FILL);

		mOutPaint = new Paint();
		mOutPaint.setColor(outCircleColor);
		mOutPaint.setAntiAlias(true);
		mOutPaint.setStyle(Paint.Style.FILL);

		mInsidePaint = new Paint();
		mInsidePaint.setColor(insideCircleColor);
		mInsidePaint.setAntiAlias(true);
		mInsidePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		mthirdPaint = new Paint();
		mthirdPaint.setColor(thirdCircleColor);
		mthirdPaint.setAntiAlias(true);
		mthirdPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		// 高速
		scanPaint = new Paint();
		scanPaint.setColor(ScanColor);
		scanPaint.setStyle(Paint.Style.FILL);
		scanPaint.setAntiAlias(true); // 消除锯齿

		handler.sendEmptyMessageDelayed(0x01, 1000);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		int x =width;
		if(width>height)
			x =height;
		int mOutRadius = x / 3;
		int mInsideRadius = x / 4;
		int mthirdRadius = x / 5;
		
		canvas.drawCircle(width / 2, height / 2, mOutRadius, mOutPaint);
		canvas.drawCircle(width / 2, height / 2, mInsideRadius, mInsidePaint);
		canvas.drawCircle(width / 2, height / 2, mthirdRadius, mthirdPaint);

		for (int i = 1; i < 5; i++) {
			float degrees = 360 / 4 * i;
			canvas.save();// 保存当前画布
			canvas.rotate(degrees, width / 2, height / 2);
			canvas.drawLine(width / 2, height / 2 - mOutRadius, width / 2, height / 2,
					mPaint);
			canvas.restore();
		}
		float degrees = 3.6f*scaneIndex;
		canvas.save();// 保存当前画布
		canvas.rotate(degrees, width / 2, height / 2);
		RectF oval = new RectF(width / 2 - mOutRadius, height / 2 - mOutRadius,
				width / 2 + mOutRadius, height / 2 + mOutRadius);
		canvas.drawArc(oval, degrees, 45, true, scanPaint);
		canvas.restore();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x01:
				scaneIndex++;
				if (scaneIndex > 100) {
					scaneIndex = 0;
				}
				invalidate();// 告诉UI主线程重新绘制
				handler.sendEmptyMessageDelayed(0x01,50);
				break;
			default:
				break;
			}
		}
	};

}
