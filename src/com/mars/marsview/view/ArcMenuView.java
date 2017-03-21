package com.mars.marsview.view;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ArcMenuView extends View {
	private int menuTotal = 3; // 默认3个
	private int width;
	private int height;
	private Paint borderPaint;
	private OnMenuSelected mSelectListener;
	private Bitmap wxBitmap;
	private Bitmap aliBitmap;
	private Bitmap jdBitmap;
	private Rect mRectSelectColor = new Rect();
	/**
	 * 中心点的坐标
	 */
	private int mCenterXY;
	private int centerX;
	private int centerY;
	private float radius;
	private float mLastX;
	private float mLastY;
	private float mStartAngle = 0;
	private float mTouchAngle = 0;
	private int mPosition = 0;
	/**
	 * 每个菜单对应的角度
	 */
	private float sPerAngle = 360f / menuTotal;

	public ArcMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ArcMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArcMenuView(Context context) {
		this(context, null);
	}

	private void init() {
		borderPaint = new Paint();
		borderPaint.setColor(Color.BLACK);
		borderPaint.setStyle(Paint.Style.STROKE);
		borderPaint.setAntiAlias(true); // 消除锯齿
		borderPaint.setStrokeWidth(1.0f);

		wxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wx);
		aliBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.ali);
		jdBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jd);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			width = specSize;
		} else {
			width = getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
		}

		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			height = specSize + 5;
		} else {
			height = getMeasuredHeight() + getPaddingBottom() + getPaddingTop();
		}
		int layoutSize = Math.max(width, height);
		mCenterXY = (int) (layoutSize / 2.0f);
		setMeasuredDimension(width, height);
		centerX = width / 2; // 获取圆心的x坐标
		centerY = height; // 获取圆心的x坐标
		radius = height;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		borderPaint.setColor(Color.BLACK);
		// 先画一个矩形
		canvas.drawRect(0, height / 2, width, height, borderPaint);
		// 画圆弧

		RectF mRect = new RectF(centerX - radius, centerY - radius, radius * 2
				+ (centerX - radius), centerY - radius + radius * 2);
		canvas.drawArc(mRect, 210, 120, false, borderPaint);
		canvas.save();
		switch (mPosition) {
		case 0:
			mRectSelectColor.set(centerX - wxBitmap.getWidth() / 2, centerY / 4
					- wxBitmap.getHeight() / 2, centerX + wxBitmap.getWidth()
					/ 2, centerY / 4 + wxBitmap.getHeight() / 2);
			canvas.drawBitmap(wxBitmap, null, mRectSelectColor, null);
			break;
		case 1:
			mRectSelectColor.set(centerX - aliBitmap.getWidth() / 2, centerY
					/ 4 - aliBitmap.getHeight() / 2,
					centerX + aliBitmap.getWidth() / 2,
					centerY / 4 + aliBitmap.getHeight() / 2);
			canvas.drawBitmap(aliBitmap, null, mRectSelectColor, null);
			break;
		case 2:
			mRectSelectColor.set(centerX - jdBitmap.getWidth() / 2, centerY / 4
					- jdBitmap.getHeight() / 2, centerX + jdBitmap.getWidth()
					/ 2, centerY / 4 + jdBitmap.getHeight() / 2);
			canvas.drawBitmap(jdBitmap, null, mRectSelectColor, null);
			break;

		default:
			break;
		}
		canvas.rotate(mTouchAngle, centerX, centerY / 2);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// return super.onTouchEvent(event);
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
			int quadrant = getQuadrant(x, y);
			switch (quadrant) {
			case 1:
			case 4:
				mTouchAngle += endAngle - startAngle;
				break;
			case 2:
			case 3:
				mTouchAngle += startAngle - endAngle;
				break;
			}
			// 对角度范围进行处理
			mTouchAngle = mTouchAngle % 360;

			int pos = getPositionWhenRotate(mTouchAngle);
			// 当前选择的位置
			Utils.LogShow("pos", "当前选择的位置：" + pos);
			Utils.LogShow("pos", "上次选择的位置：" + mPosition);
			mPosition = pos;
			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int position = getPositionWhenRotate(mTouchAngle);
			int index = (int) (mTouchAngle / 45.0f);
			if (index % 2 == 0) {
				mTouchAngle = index * 45;
			} else {
				if (index >= 0) {
					mTouchAngle = (index + 1) * 45;
				} else {
					mTouchAngle = (index - 1) * 45;
				}
			}
			if (mSelectListener != null) {
				mSelectListener.menuSelect(position);
				mPosition = position;
			}
			break;
		}
		invalidate();
		return true;
	}

	/**
	 * 获取触摸点的角度
	 */
	private float getAngle(float x, float y) {
		float deltaX = x - centerX;
		float deltaY = y - centerY / 2;
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return (float) (Math.asin(deltaY / distance) * 180 / Math.PI);
	}

	/**
	 * 获取触摸点所在的象限
	 */
	private int getQuadrant(float touchX, float touchY) {
		float deltaX = touchX - centerX;
		float deltaY = touchY - centerY / 2;
		if (deltaX >= 0) {
			return deltaY >= 0 ? 4 : 1;
		} else {
			return deltaY >= 0 ? 3 : 2;
		}
	}

	/**
	 * 当在旋转的过程中获得当前的位置 如果顺时针旋转角度超过45度就直接进入下一个菜单 反之，还是回到原来的菜单位置
	 */
	private int getPositionWhenRotate(float touchAngle) {
		int index = (int) (touchAngle / 45.0f);
		if (index % 2 == 0) {
			touchAngle = index * 45;
		} else {
			if (index >= 0) {
				touchAngle = (index + 1) * 45;
			} else {
				touchAngle = (index - 1) * 45;
			}
		}
		int position = (int) (touchAngle / sPerAngle);
		if (position <= 0) {
			return Math.abs(position);
		} else {
			return 3 - position;
		}
	}

	/**
	 * 菜单按钮滑动的事件
	 */
	public interface OnMenuSelected {
		void menuSelect(int position);
	}
}
