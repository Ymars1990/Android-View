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
	private int stepCount = 4;// �������� Ĭ��ֵ����3
	private int stepIndex = 0;// �������� Ĭ��ֵΪ1

	private int unfinishedCorlor = 0xFFB6B6B6;
	private int finishedCorlor = 0xFF11B2DC;
	private String stepDesc[];// ����ÿ�����������������ĸ���
	private float v = 0.0f;
	private int drawFlag = 1;// 1�� ValueAnimator 2��index �ֲ�����
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
		int cx = getWidth() / 2; // view ����x����
		int cy = getHeight() / 2; // view����y����
		FontMetrics fm = textPiant.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		// ��step Բ�νڵ�
		float raius = 10f;
		for (int i = 1; i <= stepCount; i++) {
			unfinishedPiant.setStyle(Paint.Style.FILL);
			canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius,
					unfinishedPiant);
			unfinishedPiant.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 2,
					unfinishedPiant);
			if (i < stepIndex) {// С�ڵ�ǰstepIndex ֱ�ӻ�Բ
				finishedPiant.setStyle(Paint.Style.FILL);
				canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 4,
						finishedPiant);
				finishedPiant.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(cx * 2 / (stepCount + 1) * i, cy, raius + 8,
						finishedPiant);
			} else if (stepIndex > 0) {// ���ڵ�ǰstepIndex ����stepIndex ������1 index
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
			// ��desc
			if (stepDesc != null) {
				String prct = (stepDesc[i - 1]);
				float textX = textPiant.measureText(prct);
				canvas.drawText(stepDesc[i - 1], cx * 2 / (stepCount + 1) * i
						- textX / 2, cy + textY + 50, textPiant);
			}
		}
		// ���ڵ������� ��Ӱ��
		for (int i = 1; i < stepCount; i++) {
			unfinishedPiant.setStyle(Paint.Style.FILL);
			finishedPiant.setStyle(Paint.Style.FILL);
			canvas.drawLine(cx * 2 / (stepCount + 1) * i + raius + 8, cy, cx
					* 2 / (stepCount + 1) * (i + 1) - raius - 8, cy,
					unfinishedPiant);
		}
		// ����ǰ����stepIndex ������
		/*
		 * ���stepIndex==1,������ ���stepIndex>1,i==stepIndex-1 �±�ʾ����
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
