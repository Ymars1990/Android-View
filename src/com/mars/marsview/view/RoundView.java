package com.mars.marsview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.mars.marsview.R;

public class RoundView extends View {
	int w, h;
	// �ٷֱ�������Ϊ�ⲿ����ı�������
	private float arrPer[];
	// ��ɫ����
	private  int colors[] = new int[]{};

	public RoundView(Context context) {
		super(context);
	}

	public RoundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ��ȡ��Ļ�Ŀ��
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		w = wm.getDefaultDisplay().getWidth();
		h = wm.getDefaultDisplay().getHeight();
		colors = new int[]{ context.getResources().getColor(R.color.yellow),
				context.getResources().getColor(R.color.green),
				context.getResources().getColor(R.color.blue),
				context.getResources().getColor(R.color.red) };
	}

	public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float cirX = w / 2;
		float cirY = h / 2;
		float radius = w / 4;// 150;

		float arcLeft = cirX - radius;
		float arcTop = cirY - radius;
		float arcRight = cirX + radius;
		float arcBottom = cirY + radius;
		// ���ʳ�ʼ��
		Paint PaintArc = new Paint();
		Paint PaintLabel = new Paint();
		PaintLabel.setColor(Color.WHITE);
		PaintLabel.setTextSize(50);
		PaintLabel.setTextAlign(Paint.Align.CENTER);
		PaintArc.setAntiAlias(true);
		PaintLabel.setAntiAlias(true);

		// λ�ü�����
		XChartCalc xcalc = new XChartCalc();

		float Percentage = 0.0f;
		float CurrPer = 0.0f;
		int i = 0;
		for (i = 0; i < arrPer.length; i++) {
			
			// ���ٷֱ�ת��Ϊ��ͼ��ʾ�Ƕ�
			Percentage = 360 * (arrPer[i]);
			Percentage = (float) (Math.round(Percentage * 100)) / 100;
			// ������ɫ
			PaintArc.setColor(colors[i]);
			// if (true) //ָ��ͻ���ĸ���
			// {
			// ƫ��Բ�ĵ�λ��
			float newRadius = radius / 10;
			// ����ٷֱȱ�ǩ
			xcalc.CalcArcEndPointXY(cirX, cirY, newRadius, CurrPer + Percentage
					/ 2);

			arcLeft = xcalc.getPosX() - radius;
			arcTop = xcalc.getPosY() - radius;
			arcRight = xcalc.getPosX() + radius;
			arcBottom = xcalc.getPosY() + radius;
			RectF arcRF1 = new RectF(arcLeft, arcTop, arcRight, arcBottom);

			// �ڱ�ͼ����ʾ��ռ����
			canvas.drawArc(arcRF1, CurrPer, Percentage, true, PaintArc);

			// ����ٷֱȱ�ǩ
			xcalc.CalcArcEndPointXY(xcalc.getPosX(), xcalc.getPosY(), radius
					- radius / 2, CurrPer + Percentage / 2);
			// ��ʶ
			canvas.drawText((float) (Math.round(arrPer[i] * 10)) / 10 + "",
					xcalc.getPosX(), xcalc.getPosY(), PaintLabel);
			// } else {
			// //�ڱ�ͼ����ʾ��ռ����
			// canvas.drawArc(arcRF0, CurrPer, Percentage, true, PaintArc);
			// //����ٷֱȱ�ǩ
			// xcalc.CalcArcEndPointXY(cirX, cirY, radius - radius / 2 / 2,
			// CurrPer + Percentage / 2);
			// //��ʶ
			// canvas.drawText(Float.toString(arrPer[i]) + "%", xcalc.getPosX(),
			// xcalc.getPosY(), PaintLabel);
			//
			// }

			// �´ε���ʼ�Ƕ�
			CurrPer += Percentage;
		}

	}

	public void setDate(float[] floats) {
		arrPer = floats;
		invalidate();
	}

	/**
	 * ͼ����صļ�����
	 * <p/>
	 * author:xiongchuanliang date:2014-4-9
	 */

	public class XChartCalc {

		// Positionλ��
		private float posX = 0.0f;
		private float posY = 0.0f;

		public XChartCalc() {

		}

		// ��Բ�����꣬�뾶�����νǶȣ������������������Բ��������xy����
		public void CalcArcEndPointXY(float cirX, float cirY, float radius,
				float cirAngle) {

			// ���Ƕ�ת��Ϊ����
			float arcAngle = (float) (Math.PI * cirAngle / 180.0);
			if (cirAngle < 90) {
				posX = cirX + (float) (Math.cos(arcAngle)) * radius;
				posY = cirY + (float) (Math.sin(arcAngle)) * radius;
			} else if (cirAngle == 90) {
				posX = cirX;
				posY = cirY + radius;
			} else if (cirAngle > 90 && cirAngle < 180) {
				arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
				posX = cirX - (float) (Math.cos(arcAngle)) * radius;
				posY = cirY + (float) (Math.sin(arcAngle)) * radius;
			} else if (cirAngle == 180) {
				posX = cirX - radius;
				posY = cirY;
			} else if (cirAngle > 180 && cirAngle < 270) {
				arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
				posX = cirX - (float) (Math.cos(arcAngle)) * radius;
				posY = cirY - (float) (Math.sin(arcAngle)) * radius;
			} else if (cirAngle == 270) {
				posX = cirX;
				posY = cirY - radius;
			} else {
				arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
				posX = cirX + (float) (Math.cos(arcAngle)) * radius;
				posY = cirY - (float) (Math.sin(arcAngle)) * radius;
			}

		}

		// ////////////
		public float getPosX() {
			return posX;
		}

		public float getPosY() {
			return posY;
		}
		// ////////////
	}
}
