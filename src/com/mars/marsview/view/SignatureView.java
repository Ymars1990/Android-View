package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.mars.marsview.utils.Utils;

public class SignatureView extends View {
	private float preX;
	private float preY;
	private Path path;
	private Paint paint = null;
	private Bitmap cacheBitmap = null;
	private Canvas cacheCanvas = null;
	private String textContext;
	private Paint mPaint;
	private FontMetrics fm;
	private final static int TEXTVIEW_HEIGHT = 10;
	private final static int TEXTVIEW_TEXTSZIE = 15;
	private final static int TEXTVIEW_WIDTH = TEXTVIEW_TEXTSZIE;
	private final static int TEXTVIEW_TEXTCOLOR = 0xFF000000;
	private int textview_width = dp2px(TEXTVIEW_WIDTH);
	private int textview_height = dp2px(TEXTVIEW_HEIGHT);
	private int textview_textszie = dp2px(TEXTVIEW_TEXTSZIE);
	private int textview_textcolor = TEXTVIEW_TEXTCOLOR;

	/**
	 * @param context
	 */
	public SignatureView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SignatureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
		mPaint.setTextSize(textview_textszie);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(final int width, final int height) {
		cacheCanvas = new Canvas();
		path = new Path();
		cacheCanvas.setBitmap(cacheBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888));
		cacheCanvas.drawColor(Color.WHITE);
		paint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
	}

	@Override
	public void layout(int l, int t, int r, int b) {
		super.layout(l, t, r, b);
		init(getWidth(), getHeight());
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

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(0xFFFFFFFF);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		fm = mPaint.getFontMetrics();
	}

	public void clear() {
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		cacheCanvas.drawPaint(paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		invalidate();
//		SignatureActivity.sinatureFlah = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(x, y);
			preX = x;
			preY = y;
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, x, y);
			preX = x;
			preY = y;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			cacheCanvas.drawPath(path, paint);
			path.reset();
			invalidate();
			break;
		}
//		SignatureActivity.sinatureFlah = true;
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(cacheBitmap, 0, 0, paint);
		canvas.drawPath(path, paint);
		initPaint();
		RectF mRect = new RectF();
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
		}
		
	}

	public Bitmap getPreviewImage() {
		return cacheBitmap;
	}

	public String getTextContext() {
		return textContext;
	}

	public void setTextContext(String textContext) {
		this.textContext = textContext;
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
