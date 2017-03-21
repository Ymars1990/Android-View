package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mars.marsview.utils.Utils;

public class ScratchcardView extends View {
	// �ν�����
	private Paint scratchPaint;
	// ��Ʒ����
	private Paint prizePaint;
	private String mText = "��500,0000";
	private Rect mTextBound = new Rect();
	/**
	 * ��¼�û����Ƶ�Path
	 */
	private Path mPath = new Path();
	private boolean isComplete = false;
	private Canvas mCanvas;
	private int mLastX;
	private int mLastY;
	private Bitmap mBitmap;
	private int width;
	private int height;

	public boolean reset = true;

	private TextPaint textPaint;
	private StaticLayout staticLayout = null;

	public ScratchcardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public ScratchcardView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public ScratchcardView(Context context) {
		this(context, null);
	}

	private void initPaint() {
		isComplete = false;
		prizePaint = new Paint();
		prizePaint.setStyle(Style.FILL);
		prizePaint.setTextScaleX(2f);
		prizePaint.setColor(Color.BLACK);
		prizePaint.setTextSize(32);
		prizePaint.getTextBounds(mText, 0, mText.length(), mTextBound);

		// ���û���
		scratchPaint = new Paint();
		scratchPaint.setColor(Color.parseColor("#D3D3D3"));
		scratchPaint.setAntiAlias(true);
		scratchPaint.setDither(true);
		scratchPaint.setStyle(Paint.Style.STROKE);
		scratchPaint.setStrokeJoin(Paint.Join.ROUND); // Բ��
		scratchPaint.setStrokeCap(Paint.Cap.ROUND); // Բ��
		// ���û��ʿ��
		scratchPaint.setStrokeWidth(20);
		mPath = new Path();

		textPaint = new TextPaint();
		textPaint.setColor(Color.RED);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(20);
		textPaint.setTextScaleX(2f);
		textPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		Utils.LogShow(" onMeasure width", "" + width);
		Utils.LogShow("  onMeasure height", "" + height);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ���ƽ���
		/*
		 * staticLayout = new StaticLayout(mText, textPaint, width,
		 * Alignment.ALIGN_OPPOSITE, 1, 0, false); staticLayout.draw(canvas);
		 */
		canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,
				getHeight() / 2 + mTextBound.height() / 2, textPaint);
		// ���Ƹ��ǲ�
		if (reset) {
			initCoverDraw(width, height);
		}
		// ���Ʋ�����
		if (!isComplete) {
			drawPath();
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
		reset = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			mPath.moveTo(mLastX, mLastY);
			break;
		case MotionEvent.ACTION_MOVE:

			int dx = Math.abs(x - mLastX);
			int dy = Math.abs(y - mLastY);

			if (dx > 3 || dy > 3)
				mPath.lineTo(x, y);

			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
			new Thread(mRunnable).start();
			break;
		}
		invalidate();
		return true;
	}

	/**
	 * ͳ�Ʋ�����������
	 */
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			int[] mPixels;
			int w = getWidth();
			int h = getHeight();

			float wipeArea = 0;
			float totalArea = w * h;
			Bitmap bitmap = mBitmap;

			mPixels = new int[w * h];

			/**
			 * �õ����е�������Ϣ
			 */
			bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

			/**
			 * ����ͳ�Ʋ���������
			 */
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					int index = i + j * w;
					if (mPixels[index] == 0) {
						wipeArea++;
					}
					// Utils.LogShow("��ǰ����ֵ",""+mPixels[index]);
				}
			}

			/**
			 * ������ռ�ٷֱȣ�����һЩ����
			 */
			if (wipeArea > 0 && totalArea > 0) {
				int percent = (int) (wipeArea * 100 / totalArea);
				Log.e("TAG", percent + "");

				if (percent > 70) {
					isComplete = true;
					postInvalidate();
					Log.e("TAG", "�������ﵽ70%�������Զ����");
				}
			}
		}

	};

	/**
	 * @return the reset
	 */
	public boolean isReset() {
		return reset;
	}

	/**
	 * @param reset
	 *            the reset to set
	 */
	public void setReset(boolean reset) {
		this.reset = reset;
		if (this.reset) {
			isComplete = false;
			mLastX = 0;
			mLastY = 0;
		}
	}

	private void clear() {
		initPaint();
		initCoverDraw(width, height);
		invalidate();
	}

	/**
	 * ��������
	 */
	private void drawPath() {
		scratchPaint.setStyle(Paint.Style.STROKE);
		scratchPaint
				.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		mCanvas.drawPath(mPath, scratchPaint);
	}

	private void initCoverDraw(int width, int height) {
		Utils.LogShow("width", "" + width);
		Utils.LogShow("height", "" + height);
		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		scratchPaint.setStyle(Paint.Style.FILL);
		mCanvas.drawRoundRect(new RectF(0, 0, width, height),0, 0,
				scratchPaint);
	}

	/**
	 * @return the mText
	 */
	public String getmText() {
		return mText;
	}

	/**
	 * @param mText
	 *            the mText to set
	 */
	public void setmText(String mText) {
		this.mText = mText;
		clear();
	}

}
