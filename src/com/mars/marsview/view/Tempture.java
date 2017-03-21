package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Tempture extends View {
	/**
	 * 外壳画笔
	 */
	private Paint shellPaint;
	/**
	 * 液体画笔
	 */
	private Paint liquidPaint;
	/**
	 * 外壳颜色
	 */
	private int shellColor = 0xFF11B2DC;
	/**
	 * 液体颜色
	 */
	private int liquidColor = 0xFF11B2DC;
	/**
	 * 步长
	 */
	private float step;
	/**
	 * 宽度
	 */
	private int width = 40;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 外壳宽度
	 */
	private float shellWidth;
	/**
	 * 下标
	 */
	private int index = 100;
	/**
	 * 当前最大量
	 */
	private int max = 100;

	private boolean showAsAnimagtion = false;

	public Tempture(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		shellPaint = new Paint();
		shellPaint.setAntiAlias(true);
		shellPaint.setStyle(Paint.Style.STROKE);
		shellPaint.setColor(shellColor);

		liquidPaint = new Paint();
		liquidPaint.setAntiAlias(true);
		liquidPaint.setStyle(Paint.Style.FILL);
		liquidPaint.setColor(liquidColor);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			width = specSize;
		} else {
			width = width + getPaddingLeft() + getPaddingRight();
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			height = specSize;
		} else {
			height = height + getPaddingTop() + getBottom();
		}
		setMeasuredDimension(width, height);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		shellWidth = (float) (width * 0.15f);
		shellPaint.setStrokeWidth(shellWidth);
		step = (height - shellWidth) / 100;
		/**
		 * 计算液体高度
		 */
		float liquidHeight = (100 - index) * step;
		if (liquidHeight < shellWidth / 2) {
			liquidHeight = shellWidth / 2;
		}
		if (liquidHeight > height - width / 2) {
			liquidHeight = height - width / 2;
		}

		/**
		 * 画外壳
		 */
		RectF rect = new RectF(width * 0.2f + shellWidth / 2, shellWidth / 2,
				width * 0.8f - shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width * 0.3f, width * 0.3f, shellPaint);
		canvas.drawCircle(width / 2, height - width / 2,
				(width - shellWidth) / 2, shellPaint);
		/**
		 * 画液体
		 */
		rect = new RectF(width * 0.2f + shellWidth / 2, liquidHeight, width
				* 0.8f - shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width * 0.3f, width * 0.3f, liquidPaint);
		canvas.drawCircle(width / 2, height - width / 2,
				(width - shellWidth) / 2, liquidPaint);

		/*
		 * if(index>=max){ index--; invalidate(); }
		 */
		setIndex(index);
		if (showAsAnimagtion) {
			if (index < max) {
				index++;
				invalidate();
			}
			if (index > 60) {
				liquidPaint.setColor(0xFFFF0000);
			}
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Tempture(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Tempture(Context context) {
		this(context, null);
	}

	/**
	 * 设置液体最高位置 每一份代表温度计高度的百分之一 理论最高值为100
	 * 
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
		invalidate();
	}

	public boolean isShowAsAnimagtion() {
		return showAsAnimagtion;
	}

	public void setShowAsAnimagtion(boolean showAsAnimagtion) {
		this.showAsAnimagtion = showAsAnimagtion;
		if(!showAsAnimagtion){
			this.index = 100;
		}else{
			this.index = 0;
		}
	}
}
