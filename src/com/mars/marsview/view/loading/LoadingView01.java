package com.mars.marsview.view.loading;

import com.mars.marsview.utils.Utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LoadingView01 extends View {
	private Paint mPaint;
	private int scaleColor = 0xFFFFFFFF;
	private int index = 1;
	private long duration = 200;
	private int scaleType = 1;
	private int drawFlag = 1;// 1、 ValueAnimator 2、index 分布绘制

	/**
	 * @return the scaleType
	 */
	public int getScaleType() {
		return scaleType;
	}

	/**
	 * @param scaleType
	 *            the scaleType to set
	 */
	public void setScaleType(int scaleType) {
		this.scaleType = scaleType;
		switch (scaleType) {
		case 1:
			mPaint.setStyle(Paint.Style.STROKE);
			break;
		case 2:
			mPaint.setStyle(Paint.Style.FILL);
			break;
		default:
			break;
		}
		invalidate();
	}

	public LoadingView01(Context context) {
		super(context);
		initPaint();
	}

	public LoadingView01(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public LoadingView01(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(scaleColor);
		switch (scaleType) {
		case 1:
			mPaint.setStyle(Paint.Style.STROKE);
			break;
		case 2:
			mPaint.setStyle(Paint.Style.FILL);
			break;
		default:
			break;
		}
		mPaint.setStrokeWidth(5.0f);
		mPaint.setAntiAlias(true); // 消除锯齿
		switch (drawFlag) {
		case 1:
			startAnimator();
			break;
		case 2:
			handler.sendEmptyMessageDelayed(0x01, duration);
			break;
		default:
			break;
		}

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
		float radius = centerX * 2 / 3 - 10;
		for (int i = 1; i < 13; i++) {
			canvas.save();// 保存当前画布
			canvas.rotate(360 / 12 * i, centerX, centerY);
			drawScale(canvas, centerX, centerY, radius, i);
			canvas.restore();//
		}
		if(drawFlag==1&&index==13){
			startAnimator();
		}
	}

	private void drawScale(Canvas canvas, int centerX, int centerY,
			float radius, int i) {
		if (i != index) {
			mPaint.setColor(0xFFFFFFFF);
			switch (scaleType) {
			case 1:
				mPaint.setStrokeWidth(5.0f);
				canvas.drawLine(centerX, centerY - radius, centerX, centerY
						- radius + 15, mPaint);
				break;
			case 2:
				mPaint.setStrokeWidth(5.0f);
				canvas.drawCircle(centerX, centerY - radius, 5, mPaint);
				break;
			}
		} else {
			mPaint.setColor(0xFF11B2DC);
			switch (scaleType) {
			case 1:
				mPaint.setStrokeWidth(10.0f);
				canvas.drawLine(centerX, centerY - radius - 5, centerX, centerY
						- radius + 20, mPaint);
				break;
			case 2:
				mPaint.setStrokeWidth(5.0f);
				canvas.drawCircle(centerX, centerY - radius, 10, mPaint);
				break;
			}
		}
	}

	private void startAnimator() {
		ValueAnimator animator = ValueAnimator.ofInt(1, 13);
		animator.setDuration(duration*12);
		animator.setInterpolator(new LinearInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				index = (int) animation.getAnimatedValue();
				Utils.LogShow("LoadingView01 startAnimator()", index);
				invalidate();
			}
		});
		animator.start();
	}

	// 每隔一秒，在handler中调用一次重新绘制方法
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x01:
				index = index % 13;
				++index;
				invalidate();
				handler.sendEmptyMessageDelayed(0x01, duration);
				break;
			default:
				break;
			}
		}
	};
}
