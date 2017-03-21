package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DownloadView extends View {
	private Paint mPaint;
	private int percent = 0;
	private float startAngle = 0.0f;
	private float sweepAngle = 0.0f;
	private float startX = 0.0f;
	private float startY = 0.0f;
	private float endX = 0.0f;
	private float endY = 0.0f;
	private Paint sinPaint;
	private Path triPath;
	private int index = 0;
	private int count = 20;
	private Path iconPath;
	float degrees = 0.0f;
	private boolean isError = false;

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * @param isError
	 *            the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
		invalidate();
	}

	public DownloadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public DownloadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DownloadView(Context context) {
		this(context, null);
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(0xFFF1F1F1);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);

		sinPaint = new Paint();
		sinPaint.setStyle(Paint.Style.FILL);
		sinPaint.setColor(Color.parseColor("#1FF421"));
		sinPaint.setAntiAlias(true);
		sinPaint.setStrokeWidth(2);

		triPath = new Path();
		iconPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.save();
		int width = getWidth();
		int height = getHeight();
		float radius = 0.0f;
		if (width < height) {
			radius = width / 2.0f - 10;
		} else {
			radius = height / 2.0f - 10;
		}
		// 画圆
		mPaint.setStrokeWidth(5.0f);
		if (isError) {
			mPaint.setColor(Color.RED);
		} else {
			mPaint.setColor(0xFFF1F1F1);
		}
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(width / 2, height / 2, radius, mPaint);

		// 画弧形
		mPaint.setColor(0xFFA7C9F0);
		RectF oval = new RectF(width / 2 - radius, height / 2 - radius, width
				/ 2 + radius, height / 2 + radius);
		if (!isError) {
		canvas.drawArc(oval, -90.0f, sweepAngle, false, mPaint);
		}
		if (!isError) {
			if (percent != 100) {
				mPaint.setTextSize(30);
				mPaint.setStyle(Paint.Style.FILL);
				mPaint.setStrokeWidth(1.0f);
				FontMetrics fm = mPaint.getFontMetrics();
				float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
				String prct = ("" + percent).concat("%");
				float textX = mPaint.measureText(prct);
				mPaint.setColor(Color.GREEN);
				canvas.drawText(prct, width / 2 - textX / 2,
						height / 2 + textY, mPaint);
				if (percent <= 50) {
					startX = (float) (width / 2 + 5 - Math.sqrt(radius * radius
							- ((1 - percent / 50f) * radius)
							* ((1 - percent / 50f) * radius)));
					startY = height / 2 + (1 - percent / 50f) * radius;
					endX = (float) (width / 2 - 5 + Math.sqrt(radius * radius
							- ((1 - percent / 50f) * radius)
							* ((1 - percent / 50f) * radius)));
					endY = height / 2 + (1 - percent / 50f) * radius;
				} else {
					startX = (float) (width / 2 + 5 - Math.sqrt(radius * radius
							- (((percent - 50) / 50f) * radius)
							* (((percent - 50) / 50f) * radius)));
					startY = height / 2 - ((percent - 50) / 50f) * radius;
					endX = (float) (width / 2 - 5 + Math.sqrt(radius * radius
							- (((percent - 50) / 50f) * radius)
							* (((percent - 50) / 50f) * radius)));
					endY = height / 2 - ((percent - 50) / 50f) * radius;
				}
				canvas.drawLine(startX, startY, endX, endY, sinPaint);
  
			  
			} else {
				mPaint.setStyle(Paint.Style.STROKE);
				mPaint.setColor(Color.GREEN);
				drawRightIcon(canvas, mPaint, width / 2, height / 2, radius);
				if (index < count) {
					index++;
					invalidate();
				}
			}
		} else {
			// 如果出错，可以调用一下方法
			if (index < count) {
				index++;
				invalidate();
			}
			mPaint.setColor(Color.RED);

			drawErrorIcon(canvas, mPaint, 2, width / 2, height / 2, radius);
		}
	}

	private void drawRightIcon(Canvas canvas, Paint mIconPaint, float centerX,
			float centerY, float circleRadius) {
		float x1 = centerX - circleRadius * 3 / 7;
		float y1 = centerY;

		float x2 = centerX - circleRadius * 3 / 7 + circleRadius * 2 / 7
				* index * 2 / count;
		float y2 = centerY + circleRadius * 2 / 7 * index * 2 / count;

		if (index * 2 <= count) {
			iconPath.moveTo(x1, y1);
			iconPath.lineTo(x2, y2);
		} else {
			x2 = centerX - circleRadius / 7;
			y2 = centerY + circleRadius * 2 / 7;
			// iconPath.quadTo(x2, y3, x3, y3);
			float x3 = centerX - circleRadius / 7 + circleRadius * 4 / 7
					* (index * 2.0f / count - 1);
			float y3 = centerY + circleRadius * 2 / 7 - circleRadius * 4 / 7
					* (index * 2.0f / count - 1);
			iconPath.moveTo(x1, y1);
			iconPath.lineTo(x2, y2);
			iconPath.lineTo(x3, y3);
		}
		canvas.drawPath(iconPath, mIconPaint);
	}

	private void drawErrorIcon(Canvas canvas, Paint mIconPaint, int tpye,
			float centerX, float centerY, float circleRadius) {
		// canvas.drawCircle(centerX, centerY, circleRadius / 2.0f, mIconPaint);

		switch (tpye) {
		case 1:
			for (int i = 1; i < 5; i++) {
				degrees = (360 / 4 * i + 45);
				canvas.save();// 保存当前画布
				canvas.rotate(degrees, centerX, centerY);
				canvas.drawLine(centerX, centerY - circleRadius / 2f * index
						/ count, centerX, centerY + circleRadius / 2f * index
						/ count, mIconPaint);
				canvas.restore();
			}
			break;
		case 2:

			if (index * 2 <= count) {
				// 按照人写字的习惯
				canvas.drawLine(
						centerX - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI)) / 2f,
						centerY - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI)) / 2f,
						centerX + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI)) / 2f
								* index * 2f / count, centerY + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI)) / 2f
								* index * 2f / count, mIconPaint);
			} else {
				canvas.drawLine(
						centerX - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f,
						centerY - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f,
						centerX + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f,
						centerY + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f, mIconPaint);

				canvas.drawLine(
						centerX + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f,
						centerY - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f,
						centerX - circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f * (index * 2.0f / count - 1),
						centerY + circleRadius
								* ((float) Math.sin(45f / 180 * Math.PI))
								/ 2.0f * (index * 2.0f / count - 1), mIconPaint);

			}
			break;
		default:
			break;
		}
	}

	public double Sin(int i) {
		double result = 0;
		// 在这里我是写sin函数，其实可以用cos，tan等函数的，不信大家尽管试试
		// result = Math.cos(i * Math.PI / 180);
		result = Math.sin(i * Math.PI / 180);
		// result = Math.tan(i * Math.PI / 180);
		return result;
	}

	public double Cos(int i) {
		double result = 0;
		// 在这里我是写sin函数，其实可以用cos，tan等函数的，不信大家尽管试试
		// result = Math.cos(i * Math.PI / 180);
		result = Math.cos(i * Math.PI / 180);
		// result = Math.tan(i * Math.PI / 180);
		return result;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		sweepAngle = percent * 3.6f;
		this.percent = percent;
		invalidate();
	}
}
