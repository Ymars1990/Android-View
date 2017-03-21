package com.mars.marsview.view.timeline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class TimelineView extends View {
	private int stepCount = 4;// 步骤总数 默认值等于3
	private int stepIndex = 0;// 步骤索引 默认值为1

	private int unfinishedCorlor = 0xFFB6B6B6;
	private int finishedCorlor = 0xFF11B2DC;
	private String stepDesc[];// 建议每个文字描述不超过四个字
	private float v = 0.0f;
	private int drawFlag = 1;// 1、 ValueAnimator 2、index 分布绘制
	private long duration = 600;

	/**
	 * @return the stepDesc
	 */
	public String[] getStepDesc() {
		return stepDesc;
	}

	/**
	 * @param stepDesc
	 *            the stepDesc to set
	 */
	public void setStepDesc(String[] stepDesc) {
		this.stepDesc = stepDesc;
		invalidate();
	}

	private Paint textPiant;
	private Paint finishedPiant;
	private Paint unfinishedPiant;

	private int indexCount = 20;
	private int index = indexCount;

	public TimelineView(Context context) {
		super(context);
		initPaint();
	}

	public TimelineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public TimelineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		textPiant = new Paint();
		textPiant.setColor(Color.BLACK);
		textPiant.setStyle(Paint.Style.FILL);
		textPiant.setStrokeWidth(5.0f);
		textPiant.setTextSize(30f);
		textPiant.setAntiAlias(true);

		unfinishedPiant = new Paint();
		unfinishedPiant.setColor(unfinishedCorlor);
		unfinishedPiant.setStyle(Paint.Style.FILL);
		unfinishedPiant.setStrokeWidth(2.0f);
		unfinishedPiant.setAntiAlias(true);

		finishedPiant = new Paint();
		finishedPiant.setColor(finishedCorlor);
		finishedPiant.setStyle(Paint.Style.FILL);
		finishedPiant.setStrokeWidth(3.0f);
		finishedPiant.setAntiAlias(true);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int cx = getWidth() / 2; // view 中心x坐标
		int cy = getHeight() / 2; // view中心y坐标
		FontMetrics fm = textPiant.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		// 画step 圆形节点
		float raius = 10f;
		for (int i = 1; i <= stepCount; i++) {
			unfinishedPiant.setStyle(Paint.Style.FILL);
			canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius,
					unfinishedPiant);
			unfinishedPiant.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 2,
					unfinishedPiant);
			if (i < stepIndex) {// 小于当前stepIndex 直接画圆
				finishedPiant.setStyle(Paint.Style.FILL);
				canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 4,
						finishedPiant);
				finishedPiant.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 8,
						finishedPiant);
			} else if (stepIndex > 0) {// 等于当前stepIndex 而且stepIndex 不等于1 index
										// ==50
				switch (drawFlag) {
				case 1:
					if (v == 1.0f || stepIndex == 1) {
						finishedPiant.setStyle(Paint.Style.FILL);
						canvas.drawCircle(cx * 2 / (stepCount + 1) * stepIndex,
								cy, raius + 4, finishedPiant);
						finishedPiant.setStyle(Paint.Style.STROKE);
						canvas.drawCircle(cx * 2 / (stepCount + 1) * stepIndex,
								cy, raius + 8, finishedPiant);
					}
					break;
				case 2:
					if (index == indexCount || stepIndex == 1) {
						finishedPiant.setStyle(Paint.Style.FILL);
						canvas.drawCircle(cx * 2 / (stepCount + 1) * stepIndex,
								cy, raius + 4, finishedPiant);
						finishedPiant.setStyle(Paint.Style.STROKE);
						canvas.drawCircle(cx * 2 / (stepCount + 1) * stepIndex,
								cy, raius + 8, finishedPiant);
					}

					break;

				default:
					break;
				}

			}
			// 画desc
			if (stepDesc != null) {
				String prct = (stepDesc[i - 1]);
				float textX = textPiant.measureText(prct);
				canvas.drawText(stepDesc[i - 1], cx * 2 / (stepCount + 1) * i
						- textX / 2, cy + textY + 50, textPiant);
			}
		}
		// 画节点连接线 阴影线
		for (int i = 1; i < stepCount; i++) {
			unfinishedPiant.setStyle(Paint.Style.FILL);
			finishedPiant.setStyle(Paint.Style.FILL);
			canvas.drawLine(cx * 2 / (stepCount + 1) * i + raius + 8, cy, cx
					* 2 / (stepCount + 1) * (i + 1) - raius - 8, cy,
					unfinishedPiant);
		}
		// 画当前步骤stepIndex 连接线
		/*
		 * 如果stepIndex==1,不画线 如果stepIndex>1,i==stepIndex-1 下表示画线
		 */
		for (int i = 1; i < stepCount; i++) {
			if (stepIndex > 0) {
				if (stepIndex == 1) {
					break;
				} else if (i == stepIndex - 1) {
					switch (drawFlag) {
					case 1:
						canvas.drawLine(cx * 2 / (stepCount + 1) * i + raius
								+ 8, cy, cx * 2 / (stepCount + 1) * i + raius
								+ 8
								+ (cx * 2 / (stepCount + 1) - 2 * raius - 16)
								* v, cy, finishedPiant);
						break;
					case 2:
						canvas.drawLine(cx * 2 / (stepCount + 1) * i + raius
								+ 8, cy, cx * 2 / (stepCount + 1) * i + raius
								+ 8
								+ (cx * 2 / (stepCount + 1) - 2 * raius - 16)
								* index / indexCount, cy, finishedPiant);
						break;
					}
				} else if (i < stepIndex - 1) {
					canvas.drawLine(cx * 2 / (stepCount + 1) * i + raius + 8,
							cy, cx * 2 / (stepCount + 1) * (i + 1) - raius - 8,
							cy, finishedPiant);
				}
			}
		}
		if (drawFlag == 2) {
			if (index < indexCount) {
				index++;
				invalidate();
			}
		}

	}

	private void startDrawLine() {
		ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
		animator.setDuration(duration);
		animator.setInterpolator(new LinearInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				v = value;
				invalidate();
			}
		});
		animator.start();

	}

	public void setParams(int stepCount, int unfinishedCorlor,
			int finishedCorlor, String stepDesc[]) {
		this.stepCount = stepCount;
		this.unfinishedCorlor = unfinishedCorlor;
		this.finishedCorlor = finishedCorlor;
		this.stepDesc = stepDesc;
		invalidate();
	}

	public int getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
		switch (drawFlag) {
		case 1:
			startDrawLine();
			break;
		case 2:
			index = 1;
			break;
		default:
			break;
		}
		invalidate();
	}
}
