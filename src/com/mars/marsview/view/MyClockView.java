package com.mars.marsview.view;

import java.util.Calendar;

import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class MyClockView extends View {

	private int circleColor = 0xFF000000;
	private int SecCircleColor = 0xFFC7EDCC;
	private Paint circlePaint;
	private Paint circlecenterPaint;
	private Paint mPaintText;
	private Paint mPaintHour;
	private Paint mPaintSecCircle;
	private Paint mPaintSecText;
	private Paint mPaintMinute;
	private Paint mPaintSecond;
	private Calendar mCalendar;
	private int drawMode = 1;// 1、线 2、不规则
	// 每隔一秒，在handler中调用一次重新绘制方法
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0x01:
				mCalendar = Calendar.getInstance();
				invalidate();// 告诉UI主线程重新绘制
				handler.sendEmptyMessageDelayed(0x01, 1000);
				break;
			default:
				break;
			}
		}
	};

	public MyClockView(Context context) {
		super(context);
		initPaint();
	}

	public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public MyClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		// 外圆
		circlePaint = new Paint();
		circlePaint.setColor(circleColor);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(5.0f);
		circlePaint.setAntiAlias(true); // 消除锯齿
		// 圆心
		circlecenterPaint = new Paint();
		circlecenterPaint.setColor(circleColor);
		circlecenterPaint.setStyle(Paint.Style.FILL);
		circlecenterPaint.setAntiAlias(true); // 消除锯齿
		// 文字
		mPaintText = new Paint();
		mPaintText.setColor(Color.BLUE);
		mPaintText.setTextAlign(Paint.Align.CENTER);
		mPaintText.setTextSize(40);
		mPaintText.setAntiAlias(true); // 消除锯齿
		// 秒
		mPaintSecText = new Paint();
		mPaintSecText.setColor(Color.BLUE);
		mPaintSecText.setTextAlign(Paint.Align.CENTER);
		mPaintSecText.setTextSize(20);
		mPaintSecText.setAntiAlias(true); // 消除锯齿
		// 秒
		mPaintSecCircle = new Paint();
		mPaintSecCircle.setColor(SecCircleColor);
		mPaintSecCircle.setTextAlign(Paint.Align.CENTER);
		mPaintSecCircle.setAntiAlias(true); // 消除锯齿
		// 时针
		mPaintHour = new Paint();
		mPaintHour.setStrokeWidth(4);
		mPaintHour.setColor(Color.BLACK);
		mPaintHour.setAntiAlias(true); // 消除锯齿
		// 分针
		mPaintMinute = new Paint();
		mPaintMinute.setStrokeWidth(3);
		mPaintMinute.setColor(Color.BLUE);
		mPaintMinute.setAntiAlias(true); // 消除锯齿
		// 秒钟
		mPaintSecond = new Paint();
		mPaintSecond.setStrokeWidth(2);
		mPaintSecond.setColor(circleColor);
		mPaintSecond.setAntiAlias(true); // 消除锯齿

		mCalendar = Calendar.getInstance();
		handler.sendEmptyMessageDelayed(0x01, 1000);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画外圆
		int centerX = getWidth() / 2; // 获取圆心的x坐标
		int centerY = getHeight() / 2; // 获取圆心的x坐标
		float radius = centerX * 2 / 3 - 10;
		canvas.drawCircle(centerX, centerY, radius, circlePaint);

		// 画数字
		for (int i = 1; i < 61; i++) {
			canvas.save();// 保存当前画布
			canvas.rotate(360 / 60 * i, centerX, centerY);
			if (i % 5 == 0) {
				canvas.drawLine(centerX, centerY - radius, centerX, centerY
						- radius + 30, circlePaint);
				/*
				 * canvas.drawText("" + i/5, centerX, centerY - radius + 70,
				 * mPaintText);
				 */
			}
			canvas.drawLine(centerX, centerY - radius, centerX, centerY
					- radius + 15, circlePaint);
			canvas.restore();//
		}
		// 文字刻度
		FontMetrics fm = mPaintText.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		float xoffset = 0.0f;
		float yoffset = 0.0f;
		for (int i = 1; i < 13; i++) {
			float textWidth = mPaintText.measureText("" + i);
			// Utils.LogShow("数字宽度", "" + textWidth);
			// Utils.LogShow("数字高度", "" + textY);
			xoffset = (float) ((radius - 50) * Math.sin((double) (360 / 12 * i)
					* Math.PI / 180));
			yoffset = (float) ((radius - 50) * Math.cos((double) (360 / 12 * i)
					* Math.PI / 180));
			canvas.drawText("" + i, centerX + xoffset, centerY + textY
					- yoffset, mPaintText);
		}
		// canvas.drawCircle(centerX, centerY, radius - 30, circlePaint);
		int minute = mCalendar.get(Calendar.MINUTE);// 得到当前分钟数
		int hour = mCalendar.get(Calendar.HOUR);// 得到当前小时数
		int sec = mCalendar.get(Calendar.SECOND);// 得到当前秒数
		Utils.LogShow("sec", sec);
		Utils.LogShow("minute", minute);
		Utils.LogShow("hour", hour);
		switch (drawMode) {
		case 1:
			drawSecMinHourRegular(canvas, centerX, centerY, radius, minute,
					hour, sec);
			break;
		case 2:
			drawSecMinHourIrregular(canvas, centerX, centerY, radius, minute,
					hour, sec);
			break;

		default:
			break;
		}
		// 画当前秒圆
		drawSecCircle(canvas, centerX, centerY, radius, sec);
		// 圆心
		canvas.drawCircle(centerX, centerY, 4, circlecenterPaint);
	}

	private void drawSecCircle(Canvas canvas, int centerX, int centerY,
			float radius, int sec) {
		float xoffset;
		float yoffset;
		float secDegree = sec / 60.0f * 360;// 得到秒针旋转的角度
		canvas.save();
		canvas.rotate(secDegree, centerX, centerY);
		canvas.drawCircle(centerX, centerY - (radius - 100), 15, mPaintSecCircle);
		canvas.restore();
		FontMetrics fm1 = mPaintSecText.getFontMetrics();
		float sectextY = -fm1.descent + (fm1.descent - fm1.ascent) / 2;
		xoffset = (float) ((radius - 100) * Math.sin((double) (360 /60 * sec)
				* Math.PI / 180));
		yoffset = (float) ((radius - 100) * Math.cos((double) (360 / 60 * sec)
				* Math.PI / 180));
		canvas.drawText("" + sec, centerX + xoffset, centerY + sectextY - yoffset,
				mPaintSecText);
	}

	private void drawSecMinHourRegular(Canvas canvas, int centerX, int centerY,
			float radius, int minute, int hour, int sec) {
		// 画时针
		float hourDegree = (hour + minute / 60f + sec / 3600f) / 12f * 360;// 得到时针旋转的角度
		canvas.save();
		canvas.rotate(hourDegree, centerX, centerY);
		canvas.drawLine(centerX, centerY + 30, centerX, centerY
				- (radius - 160), mPaintHour);
		canvas.restore();

		// 画分针
		float minDegree = (minute + sec / 60f) / 60.0f * 360;// 得到分针旋转的角度
		canvas.save();
		canvas.rotate(minDegree, centerX, centerY);
		canvas.drawLine(centerX, centerY + 40, centerX, centerY
				- (radius - 140), mPaintMinute);
		canvas.restore();

		// 画秒针
		float secDegree = sec / 60.0f * 360;// 得到秒针旋转的角度
		canvas.save();
		canvas.rotate(secDegree, centerX, centerY);
		canvas.drawLine(centerX, centerY + 50, centerX, centerY
				- (radius - 120), mPaintSecond);
		canvas.restore();
	}

	private void drawSecMinHourIrregular(Canvas canvas, int centerX,
			int centerY, float radius, int minute, int hour, int sec) {
		// 画秒针，不规则图形（四个点）
		float hourDegree = (hour + minute / 60f + sec / 3600f) / 12f * 360;
		canvas.save();
		canvas.rotate(hourDegree, centerX, centerY);
		float hourX1 = centerX - 10;
		float hourY1 = centerY;
		float hourX2 = centerX;
		float hourY2 = centerY + 20;
		/*
		 * float hourY2 = (float) (centerY + 20 Math.sin(60 * Math.PI / 180));
		 */float hourX3 = centerX + 10;
		float hourY3 = centerY;
		float hourX4 = centerX;
		float hourY4 = centerY - (radius - 150);
		Path hourpath = new Path();
		hourpath.moveTo(hourX1, hourY1);
		hourpath.lineTo(hourX2, hourY2);
		hourpath.lineTo(hourX3, hourY3);
		hourpath.lineTo(hourX4, hourY4);
		hourpath.close();
		canvas.drawPath(hourpath, mPaintHour);
		canvas.restore();

		// 画分针，不规则图形（四个点）
		float minDegree = (minute + sec / 60.0f) / 60.0f * 360;// 得到分针旋转的角度
		canvas.save();
		canvas.rotate(minDegree, centerX, centerY);
		float minX1 = centerX - 8;
		float minY1 = centerY;
		float minX2 = centerX;
		float minY2 = centerY + 30;
		/*
		 * float minY2 = (float) (centerY +16 Math.sin(60 * Math.PI / 180));
		 */
		float minX3 = centerX + 8;
		float minY3 = centerY;
		float minX4 = centerX;
		float minY4 = centerY - (radius - 120);
		Path minpath = new Path();
		minpath.moveTo(minX1, minY1);
		minpath.lineTo(minX2, minY2);
		minpath.lineTo(minX3, minY3);
		minpath.lineTo(minX4, minY4);
		minpath.close();
		canvas.drawPath(minpath, mPaintMinute);
		canvas.restore();

		// 画秒针，不规则图形（四个点）
		float secDegree = sec / 60.0f * 360;
		canvas.save();
		canvas.rotate(secDegree, centerX, centerY);
		float secX1 = centerX - 6;
		float secY1 = centerY;
		float secX2 = centerX;
		/*
		 * float secY2 = (float) (centerY + 12 Math.sin(60 * Math.PI / 180));
		 */float secY2 = centerY + 40;
		float secX3 = centerX + 6;
		float secY3 = centerY;
		float secX4 = centerX;
		float secY4 = centerY - (radius - 100);
		Path secpath = new Path();
		secpath.moveTo(secX1, secY1);
		secpath.lineTo(secX2, secY2);
		secpath.lineTo(secX3, secY3);
		secpath.lineTo(secX4, secY4);
		secpath.close();
		canvas.drawPath(secpath, mPaintSecond);
		canvas.restore();
	}

	public int getDrawMode() {
		return drawMode;
	}

	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
	}
}
