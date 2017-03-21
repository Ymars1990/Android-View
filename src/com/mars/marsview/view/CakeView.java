package com.mars.marsview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.entity.Cake;
import com.mars.marsview.utils.Utils;

public class CakeView extends View {

	private final static int CAKEVIEW_COLOR = 0xFFFFFFF;
	private final static int CAKEVIEW_RADIUS = 40;
	private final static int CAKEVIEW_DIVIDER_WIDTH = 5;
	private Cake mCake[];
	private int cakeview_radius = dp2px(CAKEVIEW_RADIUS);
	private int cakeview_divider_width = dp2px(CAKEVIEW_DIVIDER_WIDTH);
	private int cakeview_color = CAKEVIEW_COLOR;
	private Paint mPaint;
	private RectF cakeOval;
	private int cakeCenterX, cakeCenterY;

	/**
	 * @return the mCake
	 */
	public Cake[] getmCake() {
		return mCake;
	}

	public void setmCake(Cake[] mCake) {
		this.mCake = mCake;
		invalidate();
	}

	public CakeView(Context context) {
		this(context, null);
	}

	public CakeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		obtainStyleAttrs(attrs);
	}

	public CakeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		obtainStyleAttrs(attrs);
	}

	private void obtainStyleAttrs(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.MyCakeView);
		cakeview_color = ta.getColor(R.styleable.MyCakeView_cakeview_color,
				cakeview_color);
		cakeview_radius = (int) ta.getDimension(
				R.styleable.MyCakeView_cakeview_radius, cakeview_radius);

		intRacf();
		ta.recycle();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(cakeview_color);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth((float) cakeview_divider_width); // 设置线宽
		mPaint.setAntiAlias(true);
	}

	private void intRacf() {
		cakeOval = new RectF();
		cakeOval.left = cakeCenterX - cakeview_radius;
		cakeOval.top = cakeCenterY - cakeview_radius;
		cakeOval.right = cakeCenterX + cakeview_radius;
		cakeOval.bottom = cakeCenterY + cakeview_radius;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		int mWidth = 0;
		int mHeith = 0;
		if (specMode == MeasureSpec.EXACTLY) {
			mWidth = specSize;
			cakeCenterX = mWidth / 2;
			cakeview_radius = mWidth / 3;
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			mHeith = specSize;
			cakeCenterY = mHeith / 2;
		}
		setMeasuredDimension(mWidth, mHeith);
	}

	float sweepAngle = 0.0f;
	float startAngle = 0.0f;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaint();
		if (mCake != null && mCake.length > 0) {
			for (int i = 0; i < mCake.length; i++) {
				intRacf();
				mPaint.setColor(mCake[i].getColor());
				sweepAngle = mCake[i].getWeight() * 360;
				// - (float) (0.005f * 360);
				// changeRecf(startAngle, sweepAngle);
				canvas.drawArc(cakeOval, startAngle, sweepAngle * index / 100,
						true, mPaint);
				startAngle += sweepAngle * index / 100;
			}

			startAngle = 0.0f;
			mPaint.setColor(0xFFFFFFFF);
			for (int i = 0; i < mCake.length; i++) {
				intRacf();
				sweepAngle = mCake[i].getWeight() * 360;
				canvas.save();// 保存当前画布
				canvas.rotate(startAngle + 90, cakeCenterX, cakeCenterY);
				canvas.drawLine(cakeCenterX, cakeCenterY - cakeview_radius,
						cakeCenterX, cakeCenterY, mPaint);
				canvas.restore();//
				startAngle += sweepAngle;
			}

		} else {
			mPaint.setColor(0xFF11B2DC);
			canvas.drawCircle(cakeview_radius + getPaddingLeft(),
					cakeview_radius + getPaddingTop(), cakeview_radius, mPaint);
		}
		if (index < 100) {
			index++;
			Utils.LogShow("index", "" + index);
			invalidate();
		}
	}

	private int index = 100;
	private boolean showViewInAnimation = false;

	/**
	 * @return the showViewInAnimation
	 */
	public boolean isShowViewInAnimation() {
		return showViewInAnimation;
	}

	/**
	 * @param showViewInAnimation
	 *            the showViewInAnimation to set
	 */
	public void setShowViewInAnimation(boolean showViewInAnimation) {
		this.showViewInAnimation = showViewInAnimation;
		if (!showViewInAnimation) {
			index = 100;
		} else {
			index = 0;
		}
		invalidate();
	}

	protected int dp2px(int dpVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, getResources().getDisplayMetrics());
	}

	protected int sp2px(int spVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, getResources().getDisplayMetrics());
	}
}
