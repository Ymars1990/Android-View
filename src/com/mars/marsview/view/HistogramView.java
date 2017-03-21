package com.mars.marsview.view;

import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mars.marsview.utils.Utils;

public class HistogramView extends View {

	private Paint mPaint;// 坐标画笔
	private int padding = 15;
	private int width;
	private int height;
	private Context context;
	private int xCounter = 10; // X轴 10等分
	private int yCounter = 10; // Y轴 10等分
	private int year[] = new int[] { 2006, 2007, 2008, 2009, 2010, 2011, 2012,
			2013, 2014, 2015, 2016 };
	private Paint pointPaint;
	private Paint linePaint;
	private Path mPath;
	private Map<Integer, Float> dataMap;

	public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initPaint();
	}

	public HistogramView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public HistogramView(Context context) {
		this(context, null);
		this.context = context;
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.FILL);
		mPaint.setStrokeWidth(2.0f);
		mPaint.setTextSize(20.0f);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		pointPaint = new Paint();
		pointPaint.setColor(Color.parseColor("#11B2DC"));
		pointPaint.setStyle(Style.FILL);
		pointPaint.setAntiAlias(true);
		pointPaint.setStrokeWidth(2.0f);
		pointPaint.setDither(true);

		linePaint = new Paint();
		linePaint.setColor(Color.BLUE);
		linePaint.setStyle(Style.FILL);
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(2.0f);
		linePaint.setStyle(Style.STROKE);
		linePaint.setDither(true);

		mPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画Y轴
		canvas.drawLine(0 + 2 * Utils.dip2px(context, padding),
				0 + Utils.dip2px(context, padding),
				0 + 2 * Utils.dip2px(context, padding),
				height - 2 * Utils.dip2px(context, padding), mPaint);
		// 画X 轴
		canvas.drawLine(0 + 2 * Utils.dip2px(context, padding), height - 2
				* Utils.dip2px(context, padding),
				width - Utils.dip2px(context, padding),
				height - 2 * Utils.dip2px(context, padding), mPaint);
		mPaint.setTextAlign(Align.LEFT);
		canvas.drawText("K/月", 2.5f * Utils.dip2px(context, padding),
				Utils.dip2px(context, padding), mPaint);
		mPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("年份", width - Utils.dip2px(context, padding), height
				- 2.5f * Utils.dip2px(context, padding), mPaint);
		// Y轴刻度
		float perX = (width - 3 * Utils.dip2px(context, padding)) / xCounter;
		float perY = (height - 3 * Utils.dip2px(context, padding)) / yCounter;
		mPaint.setTextAlign(Align.CENTER);
		FontMetrics fm = mPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		for (int i = 0; i < 11; i++) {
			if (i % 5 == 0) {
				mPaint.setStrokeWidth(1.0f);
				if (i != 0) {
					canvas.drawLine(1 * Utils.dip2px(context, padding), height
							- 2 * Utils.dip2px(context, padding) - i * perY,
							2 * Utils.dip2px(context, padding),
							height - 2 * Utils.dip2px(context, padding) - i
									* perY, mPaint);
				}
				mPaint.setTextAlign(Align.RIGHT);
				canvas.drawText("" + i * 2, 1 * Utils.dip2px(context, padding),
						height - 2 * Utils.dip2px(context, padding) - i * perY
								+ textY, mPaint);

			} else {
				canvas.drawLine(1.5f * Utils.dip2px(context, padding), height
						- 2 * Utils.dip2px(context, padding) - i * perY,
						2 * Utils.dip2px(context, padding),
						height - 2 * Utils.dip2px(context, padding) - i * perY,
						mPaint);

			}
			mPaint.setTextAlign(Align.CENTER);
			if (i > 0) {
				canvas.drawText("" + year[i],
						2 * Utils.dip2px(context, padding) + i * perX,
						height - 1.0f * Utils.dip2px(context, padding) + textY,
						mPaint);
			}
			drawPoint(canvas, perX, perY);
		}
	}

	private void drawPoint(Canvas canvas, float perX, float perY) {
		float x = 0.0f;
		float y = 0.0f;
		linePaint.setAntiAlias(true);
		pointPaint.setAntiAlias(true);
		if (dataMap != null && dataMap.size() > 0) {
			for (int i = 1; i <year.length; i++) {
				if (dataMap.containsKey(year[i])) {
					canvas.drawCircle(2 * Utils.dip2px(context, padding) + i
							* perX, height - 2 * Utils.dip2px(context, padding)
							- perY / 2f * dataMap.get(year[i]), 10.0f,
							pointPaint);
					/*x = 2 * Utils.dip2px(context, padding) + i * perX;
					y = height - 2 * Utils.dip2px(context, padding) - perY / 2f
							* dataMap.get(year[i]);

					if(i==1)
						mPath.moveTo(x, y);
					else
					mPath.lineTo(x, y);*/
					if (x != 0.0f && y != 0.0f) {
						canvas.drawLine(x, y,
								2 * Utils.dip2px(context, padding) + i * perX,
								height - 2 * Utils.dip2px(context, padding)
										- perY / 2f * dataMap.get(year[i]),
								linePaint);
					}	
					x = 2 * Utils.dip2px(context, padding) + i * perX;
					y = height - 2 * Utils.dip2px(context, padding) - perY / 2f
							* dataMap.get(year[i]);
				}
			}
//			 canvas.drawPath(mPath, linePaint);
		}
	}

	public Map<Integer, Float> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<Integer, Float> dataMap) {
		this.dataMap = dataMap;
		invalidate();
	}
}
