package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View {
	private Paint mCirclePaint;
	private Paint mLoadingPaint;
	private int index = 0;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0x01:
				index++;
				if (index % 24 == 0) {
					index = 0;
				}
				invalidate();// 告诉UI主线程重新绘制
				handler.sendEmptyMessageDelayed(0x01, 50);
				break;
			default:
				break;
			}
		}
	};

	public LoadingView(Context context) {
		super(context);
		initPaint();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.GRAY);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(2.0f);
		mCirclePaint.setAntiAlias(true); // 消除锯齿

		mLoadingPaint = new Paint();
		mLoadingPaint.setColor(Color.WHITE);
		mLoadingPaint.setStyle(Paint.Style.STROKE);
		mLoadingPaint.setStrokeWidth(3.0f);
		mLoadingPaint.setAntiAlias(true); // 消除锯齿
	
		
		handler.sendEmptyMessageDelayed(0x01, 100);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int centerX = getWidth() / 2; // 获取圆心的x坐标
		int centerY = getHeight() / 2; // 获取圆心的x坐标
		float radius = centerX * 4 / 5;
	
		canvas.drawCircle(centerX, centerY, radius, mCirclePaint);
		RectF oval = new RectF(centerX - radius, centerY - radius, centerX
				+ radius, centerY + radius);
		for(int i=0;i<90;i++){
			mLoadingPaint.setColor(0xFFEA5BBE-1*i);
			canvas.drawArc(oval, i+index*15, 1, false, mLoadingPaint);
		}
		/*canvas.drawArc(oval, 0 + index * 15, 15, false, mLoadingPaint);
		mLoadingPaint.setColor(0xFFEDDDDF);
		canvas.drawArc(oval, 15 + index * 15, 15, false, mLoadingPaint);
		mLoadingPaint.setColor(0xFFEDBAB7);
		canvas.drawArc(oval, 30 + index * 15, 15, false, mLoadingPaint);
		mLoadingPaint.setColor(0xFFEC9298);
		canvas.drawArc(oval, 45 + index * 15, 15, false, mLoadingPaint);
		mLoadingPaint.setColor(0xFFEA6168);
		canvas.drawArc(oval, 60 + index * 15, 15, false, mLoadingPaint);
		mLoadingPaint.setColor(0xFFEA5B63);
		canvas.drawArc(oval, 75 + index * 15, 15, false, mLoadingPaint);*/
	}

}
