package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class IconView extends View {
	private int circleColor = 0xFF9DBCDA;
	private int iconColor = 0xFF3CB371;
	private int circleRadius = 100;
	private Paint mPaint;
	private Paint mIconPaint;
	private Path iconPath;
	private int iconTpye = 1;// 1、right 2、error 3、warning
	private int centerX = 0;
	private int centerY = 0;
	private boolean isDrawFinish = false;
	private int index = 0;

	public enum INDEX_TYPE {
		INDEX_TYPE_FINAL, INDEX_TYPE_X;
	};

	float degrees = 0.0f;
	private int count = 20;

	public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public IconView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

	public IconView(Context context) {
		this(context, null);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setColor(iconColor);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(10.0f);
		mPaint.setStyle(Paint.Style.STROKE);

		mIconPaint = new Paint();
		mIconPaint.setColor(iconColor);
		mIconPaint.setAntiAlias(true);
		mIconPaint.setStrokeWidth(5.0f);
		mIconPaint.setStyle(Paint.Style.STROKE);

		iconPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		canvas.drawCircle(centerX, centerY, circleRadius, mPaint);
		drawIcon(canvas, iconTpye, mIconPaint);
		if (index < count) {
			index++;
			invalidate();
		}
	}

	private void drawIcon(Canvas canvas, int iconTpye, Paint mIconPaint) {
		switch (iconTpye) {
		case 1:
			drawPathRightIcon(canvas, mIconPaint);
			// drawLineRightIcon(canvas, mIconPaint);
			break;
		case 2:
			drawErrorIcon(canvas, mIconPaint, 2);
			break;
		case 3:
			drawWarningIcon(mIconPaint);
			break;
		default:
			break;
		}

	}

	private void drawWarningIcon(Paint mIconPaint) {

	}

	private void drawErrorIcon(Canvas canvas, Paint mIconPaint, int tpye) {
//		canvas.drawCircle(centerX, centerY, circleRadius / 2.0f, mIconPaint);

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

	private void drawPathRightIcon(Canvas canvas, Paint mIconPaint) {
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

	private void drawLineRightIcon(Canvas canvas, Paint mIconPaint) {
		float x1 = centerX - circleRadius * 3 / 7;
		float y1 = centerY;
		float x2 = centerX - circleRadius * 3 / 7 + circleRadius * 2 / 7
				* index * 2 / count;
		float y2 = centerY + circleRadius * 2 / 7 * index * 2 / count;
		if (index * 2 <= count) {
			canvas.drawLine(x1, y1, x2, y2, mIconPaint);
		} else {
			x2 = centerX - circleRadius / 7;
			y2 = centerY + circleRadius * 2 / 7;
			float x3 = centerX - circleRadius / 7 + circleRadius * 4 / 7
					* (index * 2.0f / count - 1);
			float y3 = centerY + circleRadius * 2 / 7 - circleRadius * 4 / 7
					* (index * 2.0f / count - 1);
			canvas.drawLine(x1, y1, x2, y2, mIconPaint);
			canvas.drawLine(x2, y2, x3, y3, mIconPaint);
		}

	}

	public int getCircleColor() {
		return circleColor;
	}

	public void setCircleColor(int circleColor) {
		this.circleColor = circleColor;
		invalidate();
	}

	public int getIconTpye() {
		return iconTpye;
	}

	public void setIconTpye(int iconTpye) {
		this.iconTpye = iconTpye;
		switch (iconTpye) {
		case 1:
			iconColor = 0xFF3CB371;
			break;
		case 2:
			iconColor = 0xAAFF0000;
			break;
		case 3:
			iconColor = 0xAAFFD700;
			break;
		default:
			break;
		}
		if (mIconPaint != null) {
			mIconPaint.setColor(iconColor);
		}
		if (mPaint != null) {
			mPaint.setColor(iconColor);
		}
		invalidate();
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(INDEX_TYPE index_type) {
		if (index_type == INDEX_TYPE.INDEX_TYPE_FINAL) {
			index = count;
		} else {
			index = 0;
		}
		invalidate();
	}

}
