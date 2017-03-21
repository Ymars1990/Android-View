package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class ScannerSpeedView extends View {
	private Paint circle01Paint;
	private Paint circle02Paint;
	private Paint circlePaint;
	private Paint textPaint;
	private int progress = 0;

	public ScannerSpeedView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public ScannerSpeedView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ScannerSpeedView(Context context) {
		super(context);

	}

	private void init() {
		circlePaint = new Paint();
		circlePaint.setColor(Color.BLACK);
		circlePaint.setAntiAlias(true);
		circlePaint.setStrokeWidth(2.0f);
		circlePaint.setStyle(Paint.Style.STROKE);

		circle01Paint = new Paint();
		circle01Paint.setColor(0xAA33FF33);
		circle01Paint.setAntiAlias(true);
		circle01Paint.setStyle(Paint.Style.STROKE);

		circle02Paint = new Paint();
		circle02Paint.setColor(0xAA33FF33);
		circle02Paint.setAntiAlias(true);
		circle02Paint.setStyle(Paint.Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(50.0f);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		init();
		int centerX = getWidth() / 2; // 获取圆心的x坐标
		int centerY = getHeight() / 2; // 获取圆心的x坐标
		float radius = centerX * 2 / 3 - 10;
		// 画弧线 半圆
		RectF mRect = new RectF(centerX - radius, centerY - radius, radius * 2
				+ (centerX - radius), centerY - radius + radius * 2);
		canvas.drawArc(mRect, 120, 300, false, circlePaint);

		RectF mRect01 = new RectF(centerX - radius - 20, centerY - radius - 20,
				(radius + 20) * 2 + (centerX - radius - 20), centerY - radius
						- 10 + (radius + 20) * 2);
		canvas.drawArc(mRect01, 120, 300, false, circle01Paint);

		canvas.drawCircle(centerX, centerY, radius - 50, circle02Paint);

		FontMetrics fm = textPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		float textX = textPaint.measureText("" + "96%");
		canvas.drawText("" + progress, centerX, centerY + textY, textPaint);
		textPaint.setTextSize(20.0f);
		textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText("%", centerX + textX / 2, centerY + textY, textPaint);
		textPaint.setTextSize(40.0f);
		canvas.drawText("点击加速", centerX, centerY + textY * 4, textPaint);
		// 画刻度
		for (int i = 1; i <= 100; i++) {
			float degrees = 360f / 100 * i;
			if ((degrees >= 0 && degrees <= 150)
					|| (degrees >= 210 && degrees <= 360)) {
				canvas.save();// 保存当前画布
				canvas.rotate(degrees, centerX, centerY);
				if (progress <= 50) {
					if (degrees >= 210
							&& degrees <= (210 + 150f * progress / 50)) {
						circlePaint.setColor(Color.GREEN);
					} else {
						circlePaint.setColor(Color.BLACK);
					}

				} else {
					if (degrees >= 0
							&& degrees <= (150f * (progress - 50) / 50)
							|| (degrees >= 210 && degrees <= 360f)) {
						circlePaint.setColor(Color.GREEN);
					} else {
						circlePaint.setColor(Color.BLACK);
					}

				}
				canvas.drawLine(centerX, centerY - radius + 20, centerX,
						centerY - radius + 35, circlePaint);
				canvas.restore();
			}
		}

		// 画起始文字
		float x1 = centerX - (radius + 30)
				* ((float) Math.sin(30 * Math.PI / 180));
		float y1 = centerY + (radius + 30)
				* ((float) Math.cos(30 * Math.PI / 180));
		canvas.drawLine(x1, y1, x1 - 50, y1, circle01Paint);
		float x2 = centerX + (radius + 30)
				* ((float) Math.sin(30 * Math.PI / 180));
		float y2 = centerY + (radius + 30)
				* ((float) Math.cos(30 * Math.PI / 180));
		canvas.drawLine(x2, y2, x2 + 50, y2, circle01Paint);
		textPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("1.5G", x1 - 50, y1, textPaint);
		textPaint.setTextAlign(Align.LEFT);
		canvas.drawText("2.8G", x2 + 50, y2, textPaint);
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}
}
