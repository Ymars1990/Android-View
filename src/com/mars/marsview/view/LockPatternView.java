package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mars.marsview.entity.LockPoint;
import com.mars.marsview.entity.MathUtil;

public class LockPatternView extends View {
	private boolean isInitPoint = false;

	private int width;
	private int height;
	private LockPoint[][] mPoints = new LockPoint[3][3];
	private float dotRadius = 0;
	private List<LockPoint> sPoints = new ArrayList<LockPoint>();
	private boolean checking = false;
	private long CLEAR_TIME = 200;
	private int pwdMaxLen = 9;
	private int pwdMinLen = 4;
	private boolean isTouch = true;
	private Paint arrowPaint;
	private Paint linePaint;
	private Paint selectedPaint;
	private Paint errorPaint;
	private Paint normalPaint;
	private int errorColor = 0xffea0945;
	private int selectedColor = 0xff0596f6;
	private int outterSelectedColor = 0xff8cbad8;
	private int outterErrorColor = 0xff901032;
	private int dotColor = 0xffd9d9d9;
	private int outterDotColor = 0xff929292;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	boolean movingNoPoint = false;
	float moveingX, moveingY;

	public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public LockPatternView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public LockPatternView(Context context) {
		this(context, null);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isInitPoint) {
			initPoint();
		}
		drawPoint(canvas);
	}

	// »­point
	private void drawPoint(Canvas canvas) {
		boolean inErrorState = false;
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (p.state == LockPoint.STATE_CHECK) {
					selectedPaint.setColor(outterSelectedColor);
					canvas.drawCircle(p.x, p.y, dotRadius, selectedPaint);
					selectedPaint.setColor(selectedColor);
					canvas.drawCircle(p.x, p.y, dotRadius / 4, selectedPaint);
				} else if (p.state == LockPoint.STATE_ERROR) {
					inErrorState = true;
					errorPaint.setColor(outterErrorColor);
					canvas.drawCircle(p.x, p.y, dotRadius, errorPaint);
					errorPaint.setColor(errorColor);
					canvas.drawCircle(p.x, p.y, dotRadius / 4, errorPaint);
				} else {
					normalPaint.setColor(dotColor);
					canvas.drawCircle(p.x, p.y, dotRadius, normalPaint);
					normalPaint.setColor(outterDotColor);
					canvas.drawCircle(p.x, p.y, dotRadius / 4, normalPaint);
				}
			}
		}

		if (inErrorState) {
			arrowPaint.setColor(errorColor);
			linePaint.setColor(errorColor);
		} else {
			arrowPaint.setColor(selectedColor);
			linePaint.setColor(selectedColor);
		}

		if (sPoints.size() > 0) {
			int tmpAlpha = mPaint.getAlpha();
			LockPoint tp = sPoints.get(0);
			for (int i = 1; i < sPoints.size(); i++) {
				LockPoint p = sPoints.get(i);
				drawLine(tp, p, canvas, linePaint);
				drawArrow(canvas, arrowPaint, tp, p, dotRadius / 4, 38);
				tp = p;
			}
			if (this.movingNoPoint) {
				drawLine(tp, new LockPoint(moveingX, moveingY, -1), canvas,
						linePaint);
			}
			mPaint.setAlpha(tmpAlpha);
		}
	}

	private void drawArrow(Canvas canvas, Paint paint, LockPoint start,
			LockPoint end, float arrowHeight, int angle) {
		double d = MathUtil.distance(start.x, start.y, end.x, end.y);
		float sin_B = (float) ((end.x - start.x) / d);
		float cos_B = (float) ((end.y - start.y) / d);
		float tan_A = (float) Math.tan(Math.toRadians(angle));
		float h = (float) (d - arrowHeight - dotRadius * 1.1);
		float l = arrowHeight * tan_A;
		float a = l * sin_B;
		float b = l * cos_B;
		float x0 = h * sin_B;
		float y0 = h * cos_B;
		float x1 = start.x + (h + arrowHeight) * sin_B;
		float y1 = start.y + (h + arrowHeight) * cos_B;
		float x2 = start.x + x0 - b;
		float y2 = start.y + y0 + a;
		float x3 = start.x + x0 + b;
		float y3 = start.y + y0 - a;
		Path path = new Path();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLine(LockPoint start, LockPoint end, Canvas canvas,
			Paint paint) {
		double d = MathUtil.distance(start.x, start.y, end.x, end.y);
		float rx = (float) ((end.x - start.x) * dotRadius / 4 / d);
		float ry = (float) ((end.y - start.y) * dotRadius / 4 / d);
		canvas.drawLine(start.x + rx, start.y + ry, end.x - rx, end.y - ry,
				paint);
	}

	// ³õÊ¼»¯µã
	private void initPoint() {
		width = getWidth();
		height = getHeight();
		float x = 0;
		float y = 0;

		if (width > height) {
			x = (width - height) / 2;
			width = height;
		} else {
			y = (height - width) / 2;
			height = width;
		}
		int leftPadding = 15;
		float dotPadding = width / 3 - leftPadding;
		float middleX = width / 2;
		float middleY = height / 2;
		mPoints[0][0] = new LockPoint(x + middleX - dotPadding, y + middleY
				- dotPadding, 1);
		mPoints[0][1] = new LockPoint(x + middleX, y + middleY - dotPadding, 2);
		mPoints[0][2] = new LockPoint(x + middleX + dotPadding, y + middleY
				- dotPadding, 3);
		mPoints[1][0] = new LockPoint(x + middleX - dotPadding, y + middleY, 4);
		mPoints[1][1] = new LockPoint(x + middleX, y + middleY, 5);
		mPoints[1][2] = new LockPoint(x + middleX + dotPadding, y + middleY, 6);
		mPoints[2][0] = new LockPoint(x + middleX - dotPadding, y + middleY
				+ dotPadding, 7);
		mPoints[2][1] = new LockPoint(x + middleX, y + middleY + dotPadding, 8);
		mPoints[2][2] = new LockPoint(x + middleX + dotPadding, y + middleY
				+ dotPadding, 9);

		dotRadius = width / 10;
		isInitPoint = true;
		initPaints();
	}

	private void initPaints() {
		arrowPaint = new Paint();
		arrowPaint.setColor(selectedColor);
		arrowPaint.setStyle(Style.FILL);
		arrowPaint.setAntiAlias(true);

		linePaint = new Paint();
		linePaint.setColor(selectedColor);
		linePaint.setStyle(Style.STROKE);
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(dotRadius / 9);

		selectedPaint = new Paint();
		selectedPaint.setStyle(Style.STROKE);
		selectedPaint.setAntiAlias(true);
		selectedPaint.setStrokeWidth(dotRadius / 6);

		errorPaint = new Paint();
		errorPaint.setStyle(Style.STROKE);
		errorPaint.setAntiAlias(true);
		errorPaint.setStrokeWidth(dotRadius / 6);

		normalPaint = new Paint();
		normalPaint.setStyle(Style.STROKE);
		normalPaint.setAntiAlias(true);
		normalPaint.setStrokeWidth(dotRadius / 9);
	}

	/**
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public int[] getArrayIndex(int index) {
		int[] ai = new int[2];
		ai[0] = index / 3;
		ai[1] = index % 3;
		return ai;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private LockPoint checkSelectPoint(float x, float y) {
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (MathUtil
						.checkInRound(p.x, p.y, dotRadius, (int) x, (int) y)) {
					return p;
				}
			}
		}
		return null;
	}

	/** 
     * 
     */
	private void reset() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_NORMAL;
		}
		sPoints.clear();
		this.enableTouch();
//		invalidate();
	}

	/**
	 * 
	 * 
	 * @param p
	 * @return
	 */
	private int crossPoint(LockPoint p) {
		// reset
		if (sPoints.contains(p)) {
			if (sPoints.size() > 2) {
				//
				if (sPoints.get(sPoints.size() - 1).index != p.index) {
					return 2;
				}
			}
			return 1; //
		} else {
			return 0; //
		}
	}

	/**
	 * 
	 * 
	 * @param point
	 */
	private void addPoint(LockPoint point) {
		if (sPoints.size() > 0) {
			LockPoint lastPoint = sPoints.get(sPoints.size() - 1);
			int dx = Math.abs(lastPoint.getColNum() - point.getColNum());
			int dy = Math.abs(lastPoint.getRowNum() - point.getRowNum());
			if ((dx > 1 || dy > 1) && (dx == 0 || dy == 0 || dx == dy)) {
				// if ((dx > 1 || dy > 1) && (dx != 2 * dy) && (dy != 2 * dx)) {
				int middleIndex = (point.index + lastPoint.index) / 2 - 1;
				LockPoint middlePoint = mPoints[middleIndex / 3][middleIndex % 3];
				if (middlePoint.state != LockPoint.STATE_CHECK) {
					middlePoint.state = LockPoint.STATE_CHECK;
					sPoints.add(middlePoint);
				}
			}
		}
		this.sPoints.add(point);
	}

	/**
	 * 
	 * 
	 * @param points
	 * @return
	 */
	private String toPointString() {
		if (sPoints.size() >= pwdMinLen && sPoints.size() <= pwdMaxLen) {
			StringBuffer sf = new StringBuffer();
			for (LockPoint p : sPoints) {
				sf.append(p.index);
			}
			return sf.toString();
		} else {
			return "";
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//
		if (!isTouch) {
			return false;
		}

		movingNoPoint = false;

		float ex = event.getX();
		float ey = event.getY();
		boolean isFinish = false;
		boolean redraw = false;
		LockPoint p = null;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //
			//
			if (task != null) {
				task.cancel();
				task = null;
				Log.d("task", "touch cancel()");
			}
			//
			reset();
			p = checkSelectPoint(ex, ey);
			if (p != null) {
				checking = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (checking) {
				p = checkSelectPoint(ex, ey);
				if (p == null) {
					movingNoPoint = true;
					moveingX = ex;
					moveingY = ey;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			p = checkSelectPoint(ex, ey);
			checking = false;
			isFinish = true;
			break;
		}
		if (!isFinish && checking && p != null) {

			int rk = crossPoint(p);
			if (rk == 2) //
			{
				// reset();
				// checking = false;

				movingNoPoint = true;
				moveingX = ex;
				moveingY = ey;

				redraw = true;
			} else if (rk == 0) //
			{
				p.state = LockPoint.STATE_CHECK;
				addPoint(p);
				redraw = true;
			}
			// rk == 1

		}

		//
		if (redraw) {

		}
		if (isFinish) {
			if (this.sPoints.size() == 1) {
				this.reset();
			} else if (sPoints.size() < pwdMinLen || sPoints.size() > pwdMaxLen) {
				// mCompleteListener.onPasswordTooMin(sPoints.size());
				error();
				clearPassword();
//				this.reset();
			} else if (mCompleteListener != null) {
				this.disableTouch();
				mCompleteListener.onComplete(toPointString());
				clearPassword();
			}
		}
		this.postInvalidate();
		return true;
	}

	/** 
     * 
     */
	private void error() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_ERROR;
		}
	}

	public void markError() {
		markError(CLEAR_TIME);
	}

	public void markError(final long time) {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_ERROR;
		}
		this.clearPassword(time);
	}

	public void enableTouch() {
		isTouch = true;
	}

	public void disableTouch() {
		isTouch = false;
	}

	private Timer timer = new Timer();
	private TimerTask task = null;

	public void clearPassword() {
		clearPassword(CLEAR_TIME);
	}

	public void clearPassword(final long time) {
		if (time > 1) {
			if (task != null) {
				task.cancel();
				Log.d("task", "clearPassword cancel()");
			}
			postInvalidate();
			task = new TimerTask() {
				public void run() {
					reset();
					postInvalidate();
				}
			};
			Log.d("task", "clearPassword schedule(" + time + ")");
			timer.schedule(task, time);
		} else {
			reset();
			postInvalidate();
		}

	}

	//
	private OnCompleteListener mCompleteListener;

	/**
	 * @param mCompleteListener
	 */
	public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
		this.mCompleteListener = mCompleteListener;
	}

	public interface OnCompleteListener {

		public void onComplete(String password);
	}
}
