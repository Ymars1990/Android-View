package com.mars.marsview.view;

import com.mars.marsview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

public class HorizontalProgressBar extends ProgressBar {

	private static final int DEFAULT_TEXT_SIZE = 10;// sp
	private static final int DEFAULT_TEXT_COLOR = 0xFFFF6408;
	private static final int DEFAULT_UNREACH_COLOR = 0xFF316AB2;
	private static final int DEFAULT_UNREACH_HEIGHT = 2;// dp
	private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
	private static final int DEFAULT_REACH_HEIGHT = 2;// dp
	private static final int DEFAULT_TEXT_OFFSET = 10;// dp

	protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
	protected int mTextColor = DEFAULT_TEXT_COLOR;
	protected int mUnReachColor = DEFAULT_UNREACH_COLOR;
	protected int mUnReachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
	protected int mReachColor = DEFAULT_REACH_COLOR;
	protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
	protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

	protected Paint mPaint = new Paint();
	protected int mRealWidth;//progressbar 真是宽度

	protected int dp2px(int dpVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, getResources().getDisplayMetrics());
	}

	protected int sp2px(int spVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, getResources().getDisplayMetrics());
	}

	public HorizontalProgressBar(Context context) {
		this(context, null);
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		obtainStyleAttrs(attrs);
	}

	/*
	 * 获取自定义属性
	 */
	private void obtainStyleAttrs(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.HorizontalProgressBar);
		mTextSize = (int) ta
				.getDimension(
						R.styleable.HorizontalProgressBar_progress_text_szie,
						mTextSize);
		mTextColor = ta.getColor(
				R.styleable.HorizontalProgressBar_progress_text_color,
				mTextColor);
		mTextOffset = (int) ta.getDimension(
				R.styleable.HorizontalProgressBar_progress_text_offset,
				mTextOffset);

		mUnReachColor = ta.getColor(
				R.styleable.HorizontalProgressBar_progress_unreach_color,
				mUnReachColor);
		mUnReachHeight = (int) ta.getDimension(
				R.styleable.HorizontalProgressBar_progress_unreach_height,
				mUnReachHeight);

		mReachColor = ta.getColor(
				R.styleable.HorizontalProgressBar_progress_reach_color,
				mReachColor);
		mReachHeight = (int) ta.getDimension(
				R.styleable.HorizontalProgressBar_progress_reach_height,
				mReachHeight);
		ta.recycle();
		mPaint.setTextSize(mTextSize);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		// int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthVal = MeasureSpec.getSize(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthVal, height);

		mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
	}

	// 测量高度
	private int measureHeight(int heightMeasureSpec) {
		int result = 0;
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		int size = MeasureSpec.getSize(heightMeasureSpec);
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			int textHeight = (int) (mPaint.descent() - mPaint.ascent());
			result = getPaddingBottom()
					+ getPaddingTop()
					+ Math.max(Math.max(mReachHeight, mUnReachHeight),
							Math.abs(textHeight));
			if (mode == MeasureSpec.AT_MOST) {
				result = Math.min(result, size);
			}
		}
		return result;
	}

	// 绘制
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		canvas.save();
		canvas.translate(getPaddingLeft(), getHeight() / 2);
		// draw getProgress
		boolean noNeedUnReach = false;
		String text = getProgress() + "%";
		int textWidth = (int) mPaint.measureText(text);
		float radio = getProgress() * 1.0f / getMax();
		float progressX = radio * mRealWidth;
		if (progressX + textWidth > mRealWidth) {
			progressX = mRealWidth - textWidth;
			noNeedUnReach = true;
		}
		float endX = progressX-mTextOffset / 2;
		if (endX > 0) {
			mPaint.setColor(mReachColor);
			mPaint.setStrokeWidth(mReachHeight);
			canvas.drawLine(0, 0, endX, 0, mPaint);
		}
		// draw text
		mPaint.setColor(mTextColor);
		int y = (int) -((mPaint.descent() + mPaint.ascent()) / 2);
		canvas.drawText(text, progressX, y, mPaint);

		// draw unreachbar
		if (!noNeedUnReach) {
			float startX= progressX+mTextOffset/2+textWidth;
			mPaint.setStrokeWidth(mUnReachHeight);
			mPaint.setColor(mUnReachColor);
			canvas.drawLine(startX, 0, mRealWidth, 0, mPaint);
		}
		canvas.restore();
	}
}
