package com.mars.marsview.view;

import com.mars.marsview.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;
/*
 * MPOS设备自定义
 */
@SuppressLint("DrawAllocation")
public class MposView extends View {
	private Context context;
	private int view1Color = 0xFF4C9993;//View最外层颜色
	private int view2Color = 0xFF27384A;//View显示SN层颜色
	private Paint view1Paint;
	private Paint view2Paint;
	private Paint textPaint;
	private float mRadius = 15;//弧形半径
	private FontMetrics fm;
	public MposView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);

	}

	public MposView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context);

	}

	public MposView(Context context) {
		this(context, null);
		init(context);

	}
	private void init(Context context){
		this.context = context;
		mRadius = Utils.dip2px(context, mRadius);

		view1Paint = new Paint();
		view1Paint.setColor(view1Color);
		view1Paint.setAntiAlias(true);
		view1Paint.setStyle(Paint.Style.FILL);
		
		view2Paint = new Paint();
		view2Paint.setColor(view2Color);
		view2Paint.setAntiAlias(true);
		view2Paint.setStyle(Paint.Style.FILL);
		
		textPaint = new Paint();
		textPaint.setColor(0xFFFFFFFF);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(Utils.sp2px(15, context));
		textPaint.setStyle(Paint.Style.FILL);	
		fm = textPaint.getFontMetrics();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		//最外层矩形
		RectF rect1 = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rect1, mRadius, mRadius, view1Paint);
		//设备Text显示
		float textHeight = fm.bottom - fm.top;
		float textBaseY = height - (height - textHeight)
				/ 2 + fm.bottom;
		/*canvas.drawText("Mpos蓝牙设备", width / 2 - (int) textPaint.measureText("Mpos蓝牙设备")
				/ 2, textHeight, textPaint);*/
		//mpos显示屏 SN号
		RectF rect2 = new RectF(width*0.1f,(int)(textHeight*1.5), width*0.9f, height*3/9);
		canvas.drawRoundRect(rect2, mRadius/2, mRadius/2, view2Paint);
		canvas.drawText("SN10000000000", width / 2 - (int) textPaint.measureText("SN10000000000")
				/ 2, textHeight*2.5f, textPaint);
		//mpos按键
		RectF rect3 =null;
		textPaint.setTextSize(Utils.sp2px(20, context));
		fm = textPaint.getFontMetrics();
		textHeight = fm.bottom - fm.top;
		textBaseY = height/9 - (height/9 - textHeight)
				/ 2 + fm.bottom;
		for(int i=1;i<4;i++){
			for(int j=1;j<4;j++){
			 rect3 = new RectF(width*0.1f+(j-1)*width*0.3f, height*3/9+i*textHeight/2+(i-1)*height/9, 
					j*(width*0.3f), height*4/9+i*textHeight/2+(i-1)*height/9);
			 canvas.drawRoundRect(rect3, mRadius/2, mRadius/2, view2Paint);
			 int baseline = (int) ((rect3.bottom + rect3.top - fm.bottom - fm.top) / 2);  		 
			 canvas.drawText(""+(j+(i-1)*3), width*0.2f+(j-1)*width*0.3f - (int) textPaint.measureText(""+(j+(i-1)*3))
					/ 2,baseline, textPaint);
			}
		}
	}
}
