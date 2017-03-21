package com.mars.marsview.view.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LoadingView02 extends View {
	private Paint mPaint;
	private int scaleColor = 0xFFFFFFFF;
	private int index = 1;
	private long duration = 300;
	private int drawFlag = 1;// 1、 ValueAnimator 2、index 分步绘制

	public LoadingView02(Context context) {
		super(context);
		initPaint();
	}

	public LoadingView02(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public LoadingView02(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(scaleColor);
		mPaint.setStyle(Paint.Style.FILL);
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
		int cx = getWidth() / 2; // 获取圆心的x坐标
		int cy = getHeight() / 2; // 获取圆心的x坐标
		float radius = cx / 6 - 15;
		for (int i = 1; i < 6; i++) {
			canvas.save();// 保存当前画布
			if (index == i) {
				mPaint.setColor(0xFF11B2DC);
				canvas.drawCircle(cx / 3 * i, cy, radius + 5, mPaint);
			} else {
				mPaint.setColor(0xFFFFFFFF);
				canvas.drawCircle(cx / 3 * i, cy, radius, mPaint);

			}
			canvas.restore();//
		}
		if (drawFlag == 1 && index == 6) {
			startAnimator();
		}
	}

	private void startAnimator() {
		ValueAnimator animator = ValueAnimator.ofInt(1, 6);
		animator.setDuration(duration * 5);
		animator.setInterpolator(new LinearInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				index = (int) animation.getAnimatedValue();
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
				index = index % 6;
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
