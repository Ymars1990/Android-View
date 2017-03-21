package com.mars.marsview.view;

import com.mars.marsview.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class SpeedView extends View {
	private Paint circlePaint;
	private Paint circleCenterPaint;
	private Paint mPaintPoint;
	private Paint mPaintText;
	private int drawMode = 1;// 1、线 2、不规则
	private Paint lowSpeedPaint;
	private Paint midSpeedPaint;
	private Paint highSpeedPaint;
	private float speed = 0.0f;
	private int lowSpeed = 0xFF9DBCDA;
	private int midpeed = 0xAA33FF33;
	private int highSpeed = 0xAAFF3300;

	public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SpeedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SpeedView(Context context) {
		super(context);
		init();
	}

	private void init() {
		circlePaint = new Paint();
		circlePaint.setColor(Color.BLACK);
		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Paint.Style.STROKE);

		circleCenterPaint = new Paint();
		circleCenterPaint.setColor(getResources().getColor(R.color.bgblur));
		circleCenterPaint.setAntiAlias(true);
		circleCenterPaint.setStyle(Paint.Style.FILL);

		// 时针
		mPaintPoint = new Paint();
		mPaintPoint.setStrokeWidth(4);
		mPaintPoint.setColor(Color.RED);
		mPaintPoint.setAntiAlias(true); // 消除锯齿

		mPaintText = new Paint();
		mPaintText.setColor(Color.BLUE);
		mPaintText.setTextAlign(Paint.Align.CENTER);
		mPaintText.setTextSize(25);
		mPaintText.setAntiAlias(true); // 消除锯齿
		// 慢速
		lowSpeedPaint = new Paint();
		lowSpeedPaint.setColor(lowSpeed);
		lowSpeedPaint.setStyle(Paint.Style.FILL);
		lowSpeedPaint.setAntiAlias(true); // 消除锯齿
		// 中速
		midSpeedPaint = new Paint();
		midSpeedPaint.setColor(midpeed);
		midSpeedPaint.setStyle(Paint.Style.FILL);
		midSpeedPaint.setAntiAlias(true); // 消除锯齿
		// 高速
		highSpeedPaint = new Paint();
		highSpeedPaint.setColor(highSpeed);
		highSpeedPaint.setStyle(Paint.Style.FILL);
		highSpeedPaint.setAntiAlias(true); // 消除锯齿
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		circlePaint.setColor(Color.BLACK);
		int centerX = getWidth() / 2; // 获取圆心的x坐标
		int centerY = getHeight() / 2; // 获取圆心的y坐标
		float radius = centerX * 2 / 3 - 10;
		// 画弧线 半圆
		RectF mRect = new RectF(centerX - radius, centerY - radius, radius * 2
				+ (centerX - radius), centerY - radius + radius * 2);
		canvas.drawArc(mRect, 180, 180, false, circlePaint);

		FontMetrics fm = mPaintText.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		float xoffset = 0.0f;
		float yoffset = 0.0f;

		// 画刻度
		for (int i = 1; i < 61; i++) {
			float degrees = 360 / 60 * i;
			if ((degrees >= 0 && degrees <= 90)
					|| (degrees >= 270 && degrees <= 360)) {
				canvas.save();// 保存当前画布
				canvas.rotate(degrees, centerX, centerY);
				if (i % 5 == 0) {
					canvas.drawLine(centerX, centerY - radius, centerX, centerY
							- radius + 30, circlePaint);
				}
				canvas.drawLine(centerX, centerY - radius, centerX, centerY
						- radius + 15, circlePaint);
				canvas.restore();
			}
		}
		// 数字刻度
		for (int i = 1; i < 8; i++) {
			float degrees = 30 * i;
			if (i < 4) {
				xoffset = (float) ((radius - 50) * Math.sin((double) (degrees)
						* Math.PI / 180));
				yoffset = (float) ((radius - 50) * Math.cos((double) (degrees)
						* Math.PI / 180));
				canvas.drawText("" + ((int) degrees + 90), centerX + xoffset,
						centerY + textY - yoffset, mPaintText);
			} else {
				xoffset = (float) ((radius - 50) * Math
						.sin((double) (degrees + 150) * Math.PI / 180));
				yoffset = (float) ((radius - 50) * Math
						.cos((double) (degrees + 150) * Math.PI / 180));
				canvas.drawText("" + ((int) degrees - 120), centerX + xoffset,
						centerY + textY - yoffset, mPaintText);
			}
		}
		// 画圆心
		circlePaint.setColor(Color.BLUE);
		canvas.drawCircle(centerX, centerY, 20, circleCenterPaint);
		if (speed >0 && speed <= 60) {
			RectF mlowRect = new RectF(centerX - radius/3+30, centerY - radius/3+30, (radius/3-30) * 2
					+ (centerX - radius/3+30), centerY - radius/3+30 + (radius/3-30) * 2);
			canvas.drawArc(mlowRect, 180, 180, true, lowSpeedPaint);
		} else if (speed > 60 && speed <= 120) {
			RectF mmidRect = new RectF(centerX - radius* 2/3+30, centerY - radius* 2/3+30, (radius* 2/3-30) * 2
					+ (centerX - radius* 2/3+30), centerY - radius* 2/3+30 + (radius* 2/3-30) * 2);
			canvas.drawArc(mmidRect, 180, 180, true, midSpeedPaint);
		} else if (speed > 120 && speed <= 180) {
			RectF mhighRect = new RectF(centerX - radius+30, centerY - radius+30, (radius-30) * 2
					+ (centerX - radius+30), centerY - radius+30 + (radius-30) * 2);
			canvas.drawArc(mhighRect, 180, 180, true, highSpeedPaint);
		}

		// 画指针
		switch (drawMode) {
		case 1:
			drawPointRegular(canvas, centerX, centerY, radius, speed);
			break;
		case 2:
			drawPointIrregular(canvas, centerX, centerY, radius, speed);
			break;

		default:
			break;
		}
		canvas.drawPoint(centerX, centerY, circlePaint);
	}

	private void drawPointRegular(Canvas canvas, int centerX, int centerY,
			float radius, float speedDegree) {
		if (speedDegree > 0 && speedDegree <= 90) {
			speedDegree += 270;
		} else {
			speedDegree -= 90;
		}
		canvas.save();
		canvas.rotate(speedDegree, centerX, centerY);
		canvas.drawLine(centerX, centerY, centerX, centerY - (radius - 10),
				mPaintPoint);
		canvas.restore();

	}

	private void drawPointIrregular(Canvas canvas, int centerX, int centerY,
			float radius, float speedDegree) {
		if (speedDegree > 0 && speedDegree <= 90) {
			speedDegree += 270;
		} else {
			speedDegree -= 90;
		}
		canvas.save();
		canvas.rotate(speedDegree, centerX, centerY);
		float hourX1 = centerX - 5;
		float hourY1 = centerY;
		float hourX2 = centerX;
		float hourY2 = centerY + 20;
		/*
		 * float hourY2 = (float) (centerY + 20 Math.sin(60 * Math.PI / 180));
		 */
		float hourX3 = centerX + 5;
		float hourY3 = centerY;
		float hourX4 = centerX;
		float hourY4 = centerY - (radius - 10);
		Path hourpath = new Path();
		hourpath.moveTo(hourX1, hourY1);
		hourpath.lineTo(hourX2, hourY2);
		hourpath.lineTo(hourX3, hourY3);
		hourpath.lineTo(hourX4, hourY4);
		hourpath.close();
		canvas.drawPath(hourpath, mPaintPoint);
		canvas.restore();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		if (this.speed > 180) {
			this.speed = 180;
		}
		invalidate();
	}

}
