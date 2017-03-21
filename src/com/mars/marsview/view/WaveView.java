package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.mars.marsview.R;
import com.mars.marsview.entity.Point;

public class WaveView extends View {
	 private int mProgress;//����
	    private int mTimeStep = 100;//ʱ����
	    private int mSpeed = 5;//�������ƶ��ľ���
	    private int mViewHeight;//��ͼ���
	    private int mViewWidth;//��ͼ���
	    private int mLevelLine;// ��׼��


	    private int mWaveLength;//���� �ݶ�view���Ϊһ������
	    private int mStrokeWidth;//԰���߿�
	    private RectF rectF;//Բ������
	    private int mWaveHeight;//����߶�
	    private int mLeftWaveMoveLength;//��ƽ�Ƶľ��룬�������Ʋ������λ��
	    private int mWaveColor;//������ɫ
	    private Paint mPaint;//����
	    private Paint mCirclePaint;//Բ������
	    private Paint mBorderPaint;//�߽续��
	    private int   mBorderWidth=4;//�߽���
	    private Paint mTextPaint;//���ֻ���
	    private Path mPath;//�滭��
	    private List<Point> mPoints;//��ļ���
	    private boolean isMeasure = false;//�Ƿ��Ѳ�����
	    private boolean isCircle=false;//�Ƿ�Բ��Ĭ��false�������Դ�������
	    //������Ϣ
	    private Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {

	            initWaveMove();
	        }
	    };

	    /**
	     * ��ʼ�������ƶ�
	     */
	    private void  initWaveMove(){
	        mLeftWaveMoveLength+=mSpeed;//�������ƶ���������mSpeed;
	        if (mLeftWaveMoveLength>=mWaveLength){//�����ӵ�һ������ʱ�ظ���0
	            mLeftWaveMoveLength=0;
	        }
	        invalidate();

	    }
	    public WaveView(Context context) {
	        this(context, null);
	    }

	    public WaveView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }

	    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);

	        getAttr(context, attrs, defStyleAttr);
	        init();

	    }

	    /**
	     * ��ʼ������
	     */
	    private void init() {
	        mPoints = new ArrayList<Point>();
	        //���˹켣����
	        mPaint = new Paint();
	        mPaint.setAntiAlias(true);
	        mPaint.setColor(mWaveColor);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);


	        mPath = new Path();


	        //���ֻ���
	        mTextPaint=new Paint();
	        mTextPaint.setColor(Color.RED);
	        mTextPaint.setTextAlign(Paint.Align.CENTER);
	        mTextPaint.setTextSize(48);


	        //Բ������
	        mCirclePaint=new Paint();
	        mCirclePaint.setAntiAlias(true);
	        mCirclePaint.setColor(Color.WHITE);
	        mCirclePaint.setStyle(Paint.Style.STROKE);
	        //�߽��߻���
	        mBorderPaint=new Paint();
	        mBorderPaint.setAntiAlias(true);
	        mBorderPaint.setColor(mWaveColor);
	        mBorderPaint.setStrokeWidth(mBorderWidth);
	        mBorderPaint.setStyle(Paint.Style.STROKE);


	    }

	    /**
	     * ��ȡ�Զ��������ֵ
	     *
	     * @param attrs
	     */
	    private void getAttr(Context context, AttributeSet attrs, int defStyle) {

	        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LD_WaveView, defStyle, 0);

	        mWaveColor = a.getColor(R.styleable.LD_WaveView_wave_color, Color.RED);
	        isCircle=a.getBoolean(R.styleable.LD_WaveView_wave_circle,false);
	        a.recycle();

	    }


	    /**
	     *
	     * @param widthMeasureSpec
	     * @param heightMeasureSpec
	     */
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        if (!isMeasure&&Math.abs(getMeasuredHeight()-getMeasuredWidth())<50) {//ֻ����һ�ξ͹��� ��relativelayout��ʱ��Ҫ�������� �Ӹ�����ж�
	            mViewHeight = getMeasuredHeight();
	            mViewWidth = getMeasuredWidth();
	            mLevelLine = mViewHeight;  //��ʼ������׼λ��       ��ʼλ��ͼ��ײ�
	            {
	                mLevelLine = mViewHeight * (100-mProgress) / 100;
	                if (mLevelLine < 0) mLevelLine = 0;
	            }
	            //���㲨��ֵ
	            mWaveHeight = mViewHeight / 20;//�����ݶ�Ϊview�߶ȵ�1/20�������Ҫ���� ������set������ֵ;
	            mWaveLength = getMeasuredWidth();

	            //�������еĵ� ����ȡ���Ϊ��������  ����������һ������ ������������Ҫ9����
	            for (int i = 0; i < 9; i++) {
	                int y = 0;
	                switch (i % 4) {
	                    case 0:
	                        y = mViewHeight;
	                        break;
	                    case 1:
	                        y =mViewHeight+ mWaveHeight;
	                        break;
	                    case 2:
	                        y = mViewHeight;
	                        break;
	                    case 3:
	                        y = mViewHeight-mWaveHeight;
	                        break;
	                }
	                Point point = new Point(-mWaveLength + i * mWaveLength / 4, y);
	                mPoints.add(point);
	            }
	            /**
	             * ����Բ�����
	             */
	            int mIncircleRadius=mViewHeight<mViewWidth?mViewHeight/2:mViewWidth/2;//����Բ�뾶

	            int mcircumcircleRadius= (int) (Math.sqrt((float)(Math.pow(mViewHeight/2,2)+Math.pow(mViewWidth/2,2)))+0.5);//���Բ�뾶
	            int radius=mcircumcircleRadius/2+mIncircleRadius/2;

	            rectF=new RectF(mViewWidth/2-radius,mViewHeight/2-radius,mViewWidth/2+radius,mViewHeight/2+radius);
	            mStrokeWidth=mcircumcircleRadius-mIncircleRadius;
	            mCirclePaint.setStrokeWidth(mStrokeWidth);//�����п�ȵ�  ���������ַ�ʽ��Բ��
	            isMeasure = true;
	        }
	    }

	    @Override
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        /**
	         * ��������
	         */
	        mPath.reset();
	        int i = 0;
	        mPath.moveTo(mPoints.get(0).getX()+mLeftWaveMoveLength, mPoints.get(0).getY()-mViewHeight*mProgress/100);
	        for (; i < mPoints.size() - 2; i += 2) {
	            mPath.quadTo(mPoints.get(i + 1).getX()+mLeftWaveMoveLength, mPoints.get(i + 1).getY()-mViewHeight*mProgress/100, mPoints.get(i + 2).getX()+mLeftWaveMoveLength, mPoints.get(i + 2).getY()-mViewHeight*mProgress/100);
	        }
	        mPath.lineTo(mPoints.get(i).getX()+mLeftWaveMoveLength, mViewHeight);
	        mPath.lineTo(mPoints.get(0).getX()+mLeftWaveMoveLength, mViewHeight);
	        mPath.close();
	        /**
	         * ���ƹ켣
	         */
	        canvas.drawPath(mPath,mPaint);
	        Rect rect = new Rect();

	        String progress=String.format("%d%%",mProgress);
	        mTextPaint.getTextBounds(progress,0,progress.length(), rect);
	        int textHeight = rect.height();
	        if (mProgress>=50)//������ȴﵽ50 ��ɫ��Ϊ��ɫ��û�취�����������м� ������ɫ������
	            mTextPaint.setColor(Color.WHITE);
	        else
	        mTextPaint.setColor(mWaveColor);
	        canvas.drawText(progress,0,progress.length(),mViewWidth/2,mViewHeight/2+textHeight/2,mTextPaint);

	        if (isCircle) {
	            /**
	             * ����Բ��
	             */

	            canvas.drawArc(rectF, 0, 360, true, mCirclePaint);
	            Paint circlePaint = new Paint();
	            circlePaint.setStrokeWidth(5);
	            circlePaint.setColor(Color.WHITE);
	            circlePaint.setAntiAlias(true);
	            circlePaint.setStyle(Paint.Style.STROKE);
	            canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mViewHeight / 2, circlePaint);
	            /**
	             * ���Ʊ߽�
	             */

	            mBorderPaint.setStrokeWidth(mBorderWidth/2);
	        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewHeight/2-mBorderWidth/2,mBorderPaint);
	        }else {
	            /**
	             * ���ƾ��α߿�
	             */
	            canvas.drawRect(0,0,mViewWidth,mViewHeight,mBorderPaint);
	        }
	        //
	        handler.sendEmptyMessageDelayed(0,mTimeStep);
	    }

	    /**
	     * ���ý���  ��׼��
	     * @param mProgress
	     */
	    public void setmProgress(int mProgress) {
	        this.mProgress = mProgress;
	        mLevelLine=(100-mProgress)*mViewHeight/100;
	    }

	    /**
	     * �����Ƿ�ΪԲ��
	     * @param circle
	     */
	    public void setCircle(boolean circle) {
	        isCircle = circle;
	    }

}