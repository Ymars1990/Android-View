package com.mars.marsview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class MyTextView extends View {
	private final static int TEXTVIEW_TRIANGLECOLOR = 0xFFFFFFFF;
	private final static int TEXTVIEW_LINECOLOR = 0xFF0000FF;
	private final static int TEXTVIEW_BGCOLOR = 0xFF11B2DC;
	private final static int TEXTVIEW_HEIGHT = 10;
	private final static int TEXTVIEW_RADIUS = 10;
	private final static int TEXTVIEW_TEXTSZIE = 15;
	private final static int TEXTVIEW_WIDTH = TEXTVIEW_TEXTSZIE;
	private final static int TEXTVIEW_TEXTCOLOR = 0xFF000000;
	private final static int TEXTVIEW_TRIANGLEWIDTH = 10;
	private boolean isShowIndicatorTriangle = false;
	private boolean isShowIndicatorLine = false;
	private int textview_bgcolor = TEXTVIEW_BGCOLOR;
	private int textview_width = dp2px(TEXTVIEW_WIDTH);
	private int textview_height = dp2px(TEXTVIEW_HEIGHT);
	private int textview_radius = dp2px(TEXTVIEW_RADIUS);
	private int textview_textszie = dp2px(TEXTVIEW_TEXTSZIE);
	private int textview_textcolor = TEXTVIEW_TEXTCOLOR;
	private int textview_trianglewidth = dp2px(TEXTVIEW_TRIANGLEWIDTH);
	private int textview_triangleheight = dp2px(TEXTVIEW_TRIANGLEWIDTH);
	private int textview_trianglecolor = TEXTVIEW_TRIANGLECOLOR;
	private String textContext;
	private Paint mPaint;
	private FontMetrics fm;
	
	public int getTextview_bgcolor() {
		return textview_bgcolor;
	}

	/**
	 * @param textview_bgcolor
	 *            the textview_bgcolor to set
	 */
	public void setTextview_bgcolor(int textview_bgcolor) {
		this.textview_bgcolor = textview_bgcolor;
		invalidate();
	}



	public MyTextView(Context context) {
		this(context, null);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		obtainStyleAttrs(attrs);
	}

	private void obtainStyleAttrs(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.MyTextView);
		textview_bgcolor = ta.getColor(R.styleable.MyTextView_textview_bgcolor,
				textview_bgcolor);
		textview_width = (int) ta.getDimension(
				R.styleable.MyTextView_textview_width, textview_width);
		textview_height = (int) ta.getDimension(
				R.styleable.MyTextView_textview_height, textview_height);
		textview_radius = (int) ta.getDimension(
				R.styleable.MyTextView_textview_radius, textview_radius);
		textview_textszie = (int) ta.getDimension(
				R.styleable.MyTextView_textview_textsize, textview_textszie);
		textview_textcolor = ta.getColor(
				R.styleable.MyTextView_textview_textcolor, textview_textcolor);
		textview_trianglewidth = (int) ta.getDimension(
				R.styleable.MyTextView_textview_trianglewidth,
				textview_trianglewidth);
		textview_trianglecolor = ta.getColor(
				R.styleable.MyTextView_textview_trianglecolor,
				textview_trianglecolor);
		ta.recycle();
		initPaint();
		mPaint.setTextSize(textview_textszie);
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(textview_bgcolor);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		fm = mPaint.getFontMetrics();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			textview_width = specSize;
		} else {
			textview_width = textview_width + getPaddingLeft()
					+ getPaddingRight();
		}

		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			textview_height = specSize;
		} else {
			textview_height = (int) (textview_height + getPaddingTop()
					+ getPaddingBottom() + fm.bottom - fm.top);
		}
		setMeasuredDimension(textview_width, textview_height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaint();
		RectF mRect = new RectF(0, 0, textview_width, textview_height);
		canvas.drawRoundRect(mRect, 0, 0, mPaint);

		if (Utils.isNotEmpty(textContext)) {
			mPaint.setColor(textview_textcolor);
			mPaint.setTextSize(textview_textszie);
			float textHeight = fm.bottom - fm.top;
			float textBaseY = textview_height - (textview_height - textHeight)
					/ 2 + fm.bottom;
			canvas.drawText(textContext,
					textview_width / 2 - (int) mPaint.measureText(textContext)
							/ 2, textBaseY, mPaint);
			// 画三角形
			if (isShowIndicatorTriangle) {
				initTriangle();
				mPaint.setColor(TEXTVIEW_TRIANGLECOLOR);
				canvas.drawPath(mPath, mPaint);
			}
			//画横线
			if(isShowIndicatorLine){
				mPaint.setColor(TEXTVIEW_LINECOLOR);
				mPaint.setStrokeWidth(10.0f);
				canvas.drawLine(0, textview_height, textview_width, textview_height, mPaint);
			}
		}

	}

	private Path mPath = null;

	/**
	 * 
	 */
	private void initTriangle() {
		mPath = new Path();
		mPath.moveTo(textview_width / 2 - textview_trianglewidth / 2,
				textview_height);// 此点为多边形的起点
		mPath.lineTo(textview_width / 2, textview_height
				- textview_triangleheight*0.6f);
		mPath.lineTo(textview_width / 2 + textview_trianglewidth / 2,
				textview_height);
		mPath.close();
	}

	protected int dp2px(int dpVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, getResources().getDisplayMetrics());
	}

	protected int sp2px(int spVaule) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, getResources().getDisplayMetrics());
	}

	public String getTextContext() {
		return textContext;
	}

	public void setTextContext(String textContext) {
		this.textContext = textContext;
		invalidate();
	}

	public boolean isShowIndicatorTriangle() {
		return isShowIndicatorTriangle;
	}

	public void setShowIndicatorTriangle(boolean isShowIndicatorTriangle) {
		this.isShowIndicatorTriangle = isShowIndicatorTriangle;
		invalidate();
	}

	public boolean isShowIndicatorLine() {
		return isShowIndicatorLine;
	}

	public void setShowIndicatorLine(boolean isShowIndicatorLine) {
		this.isShowIndicatorLine = isShowIndicatorLine;
		invalidate();
	}

}
