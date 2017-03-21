package com.mars.marsview.view;

import java.math.BigDecimal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.mars.marsview.R;


/**
 * author��   tc
 * date��     2016/4/30 & 9:30
 * version    1.0.0
 * description  ������view,��ť��������ʾ��seekbar������isEnabled=false�����Խ��ô������ý��ȵĹ���
 * modify by
 */
public class NumTipSeekBar extends View {
    private static final String TAG = NumTipSeekBar.class.getSimpleName();
    private RectF mTickBarRecf;

    /**
     * Ĭ�ϵĿ̶���paint
     */
    private Paint mTickBarPaint;
    /**
     * Ĭ�Ͽ̶����ĸ߶�
     */
    private float mTickBarHeight;
    /**
     * Ĭ�Ͽ̶�������ɫ
     */
    private int mTickBarColor;
    //------------------
    /**
     * Բ�ΰ�ťpaint
     */
    private Paint mCircleButtonPaint;
    /**
     * Բ�ΰ�ť��ɫ
     */
    private int mCircleButtonColor;
    /**
     * Բ�ΰ�ť�ı���ɫ
     */
    private int mCircleButtonTextColor;
    /**
     * Բ�ΰ�ť�ı���С
     */
    private float mCircleButtonTextSize;
    /**
     * Բ�ΰ�ť�İ뾶
     */
    private float mCircleButtonRadius;
    /**
     * Բ�ΰ�ť��recf����������
     */
    private RectF mCircleRecf;
    /**
     * Բ�ΰ�ťbutton�ı�����paint
     */
    private Paint mCircleButtonTextPaint;
    
    private int id ;
    //----------------------
    /**
     * �������߶ȴ�С
     */
    private float mProgressHeight;
    /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
     * ������paint
     */
    private Paint mProgressPaint;
    /**
     * ������ɫ
     */
    private int mProgressColor;

    /**
     * ��������recf����������
     */
    private RectF mProgressRecf;
    /**
     * ѡ�еĽ���ֵ
     */
    private int mSelectProgress;
    /**
     * �������ֵ
     */
    private int mMaxProgress = DEFAULT_MAX_VALUE;
    /**
     * Ĭ�ϵ����ֵ
     */
    private static final int DEFAULT_MAX_VALUE = 10;


    /**
     * view���ܽ��ȿ�ȣ���ȥpaddingtop�Լ�bottom
     */
    private int mViewWidth;
    /**
     * view���ܽ��ȸ߶ȣ���ȥpaddingtop�Լ�bottom
     */
    private int mViewHeight;
    /**
     * Բ��button��Բ�����꣬Ҳ��progress�����������ұߵĵ�����
     */
    private float mCirclePotionX;
    /**
     * �Ƿ���ʾԲ�ΰ�ť���ı�
     */
    private boolean mIsShowButtonText;
    /**
     * �Ƿ���ʾԲ�ΰ�ť
     */
    private boolean mIsShowButton;
    /**
     * �Ƿ���ʾԲ��
     */
    private boolean mIsRound;
    /**
     * ��ʼ�Ľ���ֵ�������1��ʼ��ʾ
     */
    private int mStartProgress;

    
    /**
	 * @return the progressIncrement
	 */
	public int getProgressIncrement() {
		return progressIncrement;
	}

	/**
	 * @param progressIncrement the progressIncrement to set
	 */
	public void setProgressIncrement(int progressIncrement) {
		this.progressIncrement = progressIncrement;
	}

	/**
	 * @return the progressText
	 */
	public String getProgressText() {
		return progressText;
	}

	/**
	 * @param progressText the progressText to set
	 */
	public void setProgressText(String progressText) {
		this.progressText = progressText;
	}

	private int progressIncrement = 0;
    private String progressText = "";

    /**
     * �����������仯
     */
    public interface OnProgressChangeListener {
        void onChange(int selectProgress,int id);
    }

    /**
     * �����������仯
     */
    private OnProgressChangeListener mOnProgressChangeListener;

    /**
     * ���ý������仯�ļ�����
     *
     * @param onProgressChangeListener
     */
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    public NumTipSeekBar(Context context) {
        this(context, null);
    }

    public NumTipSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumTipSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * ��ʼ��view������
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.NumTipSeekBar);
        mTickBarHeight = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_tickBarHeight, getDpValue(8));
        mTickBarColor = attr.getColor(R.styleable.NumTipSeekBar_tickBarColor, getResources()
                .getColor(R.color.orange_f6));
        mCircleButtonColor = attr.getColor(R.styleable.NumTipSeekBar_circleButtonColor,
                getResources().getColor(R.color.white));
        mCircleButtonTextColor = attr.getColor(R.styleable.NumTipSeekBar_circleButtonTextColor,
                getResources().getColor(R.color.white));
        mCircleButtonTextSize = attr.getDimension(R.styleable
                .NumTipSeekBar_circleButtonTextSize, getDpValue(16));
        mCircleButtonRadius = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_circleButtonRadius, getDpValue(16));
        mProgressHeight = attr.getDimensionPixelOffset(R.styleable
                .NumTipSeekBar_progressHeight, getDpValue(20));
        mProgressColor = attr.getColor(R.styleable.NumTipSeekBar_progressColor,
                getResources().getColor(R.color.white));
        mSelectProgress = attr.getInt(R.styleable.NumTipSeekBar_selectProgress, 0);
        mStartProgress = attr.getInt(R.styleable.NumTipSeekBar_startProgress, 0);
        mMaxProgress = attr.getInt(R.styleable.NumTipSeekBar_maxProgress, 10);
        mIsShowButtonText = attr.getBoolean(R.styleable.NumTipSeekBar_isShowButtonText, false);
        mIsShowButton = attr.getBoolean(R.styleable.NumTipSeekBar_isShowButton, true);
        mIsRound = attr.getBoolean(R.styleable.NumTipSeekBar_isRound, true);
        initView();

        attr.recycle();


    }

    private void initView() {
        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setAntiAlias(true);

        mCircleButtonPaint = new Paint();
        mCircleButtonPaint.setColor(mCircleButtonColor);
        mCircleButtonPaint.setStyle(Paint.Style.FILL);
        mCircleButtonPaint.setAntiAlias(true);

        mCircleButtonTextPaint = new Paint();
        mCircleButtonTextPaint.setTextAlign(Paint.Align.CENTER);
        mCircleButtonTextPaint.setColor(mCircleButtonTextColor);
        mCircleButtonTextPaint.setStyle(Paint.Style.FILL);
        mCircleButtonTextPaint.setTextSize(mCircleButtonTextSize);
        mCircleButtonTextPaint.setAntiAlias(true);

        mTickBarPaint = new Paint();
        mTickBarPaint.setColor(mTickBarColor);
        mTickBarPaint.setStyle(Paint.Style.FILL);
        mTickBarPaint.setAntiAlias(true);

        mTickBarRecf = new RectF();
        mProgressRecf = new RectF();
        mCircleRecf = new RectF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            //������ò����ã�����ô������ý���
            return false;
        }
        float x = event.getX();
        float y = event.getY();
//        Log.i(TAG, "onTouchEvent: x��" + x);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_DOWN:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_UP:
                if (mOnProgressChangeListener != null) {
                    Log.i(TAG, "onTouchEvent: ����������֪ͨ������-mSelectProgress��"+mSelectProgress);
                    mOnProgressChangeListener.onChange(mSelectProgress,id);
                }
                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void judgePosition(float x) {
        float end = getPaddingLeft() + mViewWidth;
        float start = getPaddingLeft();
        int progress = mSelectProgress;
//        Log.i(TAG, "judgePosition: x-start��" + (x - start));
//        Log.i(TAG, "judgePosition: start:" + start + "  end:" + end + "  mMaxProgress:" +
//                mMaxProgress);
        if (x >= start) {
            double result = (x - start) / mViewWidth * (float) mMaxProgress;
            BigDecimal bigDecimal = new BigDecimal(result).setScale(0, BigDecimal.ROUND_HALF_UP);
//            Log.i(TAG, "judgePosition: progress:" + bigDecimal.intValue() + "  result:" + result
//                    + "  (x - start) / end :" + (x - start) / end);
            progress = bigDecimal.intValue();
            if (progress > mMaxProgress) {
//                Log.i(TAG, "judgePosition:x > end  �������귶Χ:");
                progress = mMaxProgress;
            }
        } else if (x < start) {
//            Log.i(TAG, "judgePosition: x < start �������귶Χ:");
            progress = 0;
        }
        if (progress != mSelectProgress) {
            //�����仯��֪ͨview���»���
        	setSelectProgress(progress, false);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        initValues(width, height);
        if (mIsRound) {
            canvas.drawRoundRect(mTickBarRecf, mProgressHeight / 2, mProgressHeight / 2,
                    mTickBarPaint);
            canvas.drawRoundRect(mProgressRecf, mProgressHeight / 2, mProgressHeight / 2,
                    mProgressPaint);
        } else {
            canvas.drawRect(mTickBarRecf, mTickBarPaint);
            canvas.drawRect(mProgressRecf, mProgressPaint);
        }
//        canvas.drawArc(mCircleRecf, 0, 360, true, mCircleButtonPaint);
        if (mIsShowButton) {
            canvas.drawCircle(mCirclePotionX, mViewHeight / 2, mCircleButtonRadius,
                    mCircleButtonPaint);
        }
        if (mIsShowButtonText) {
            Paint.FontMetricsInt fontMetrics = mCircleButtonTextPaint.getFontMetricsInt();
            int baseline = (int) ((mCircleRecf.bottom + mCircleRecf.top - fontMetrics.bottom -
                    fontMetrics
                            .top) / 2);
            // ����������ʵ��ˮƽ���У�drawText��Ӧ��Ϊ����targetRect.centerX()
            canvas.drawText(String.valueOf((mSelectProgress+progressIncrement)+progressText), mCircleRecf.centerX
                            (), baseline,
                    mCircleButtonTextPaint);

        }
    }

    private void initValues(int width, int height) {
        mViewWidth = width - getPaddingRight() - getPaddingLeft();
        mViewHeight = height;
        mCirclePotionX = (float) (mSelectProgress - mStartProgress) /
                (mMaxProgress - mStartProgress) * mViewWidth + getPaddingLeft();
//        Log.i(TAG, "initValues: mViewWidth=" + mViewWidth + "  mViewHeight=" + mViewHeight + "
// mCirclePotionX=" + mCirclePotionX + "  mSelectProgress=" + mSelectProgress + " mMaxProgress="
// + mMaxProgress + " getPaddingLeft()=" + getPaddingLeft());
        if (mTickBarHeight > mViewHeight) {
            //����̶����ĸ߶ȴ���view����ĸ߶ȵ�1/2������ʾ�����������Դ����¡�
            mTickBarHeight = mViewHeight;
        }
        mTickBarRecf.set(getPaddingLeft(), (mViewHeight - mTickBarHeight) / 2,
                mViewWidth + getPaddingLeft(), mTickBarHeight / 2 +
                        mViewHeight / 2);
        if (mProgressHeight > mViewHeight) {
            //����̶����ĸ߶ȴ���view����ĸ߶ȵ�1/2������ʾ�����������Դ����¡�
            mProgressHeight = mViewHeight;
        }

        mProgressRecf.set(getPaddingLeft(), (mViewHeight - mProgressHeight) / 2,
                mCirclePotionX, mProgressHeight / 2 + mViewHeight / 2);

        if (mCircleButtonRadius > mViewHeight / 2) {
            //���Բ�ΰ�ť�İ뾶����view����ĸ߶ȵ�1/2������ʾ�����������Դ����¡�
            mCircleButtonRadius = mViewHeight / 2;
        }
        mCircleRecf.set(mCirclePotionX - mCircleButtonRadius, mViewHeight / 2 -
                        mCircleButtonRadius / 2,
                mCirclePotionX + mCircleButtonRadius, mViewHeight / 2 +
                        mCircleButtonRadius / 2);
    }

    /**
     * seekbar����Ŀ̶����߶�
     *
     * @return
     */
    public float getTickBarHeight() {
        return mTickBarHeight;
    }

    /**
     * ����seekbar����Ŀ̶����߶�
     *
     * @param tickBarHeight
     */
    public void setTickBarHeight(float tickBarHeight) {
        mTickBarHeight = tickBarHeight;
    }

    /**
     * seekbar����Ŀ̶�����ɫ
     *
     * @return
     */
    public int getTickBarColor() {
        return mTickBarColor;
    }

    /**
     * ����seekbar����Ŀ̶�����ɫ
     *
     * @param tickBarColor
     */
    public void setTickBarColor(int tickBarColor) {
        mTickBarColor = tickBarColor;
    }

    /**
     * Բ�ΰ�ť��ɫ
     *
     * @return
     */
    public int getCircleButtonColor() {
        return mCircleButtonColor;
    }

    /**
     * ����Բ�ΰ�ť��ɫ
     *
     * @param circleButtonColor
     */
    public void setCircleButtonColor(int circleButtonColor) {
        mCircleButtonColor = circleButtonColor;
    }

    /**
     * Բ�ΰ�ť�ı���ɫ
     *
     * @return
     */
    public int getCircleButtonTextColor() {
        return mCircleButtonTextColor;
    }

    /**
     * ����Բ�ΰ�ť�ı���ɫ
     *
     * @param circleButtonTextColor
     */
    public void setCircleButtonTextColor(int circleButtonTextColor) {
        mCircleButtonTextColor = circleButtonTextColor;
    }

    /**
     * Բ�ΰ�ť�ı������С
     *
     * @return
     */
    public float getCircleButtonTextSize() {
        return mCircleButtonTextSize;
    }

    /**
     * ����Բ�ΰ�ť�ı������С
     *
     * @param circleButtonTextSize
     */
    public void setCircleButtonTextSize(float circleButtonTextSize) {
        mCircleButtonTextSize = circleButtonTextSize;
    }

    /**
     * Բ�ΰ�ť�뾶
     *
     * @return
     */
    public float getCircleButtonRadius() {
        return mCircleButtonRadius;
    }

    /**
     * ����Բ�ΰ�ť�뾶
     *
     * @param circleButtonRadius
     */
    public void setCircleButtonRadius(float circleButtonRadius) {
        mCircleButtonRadius = circleButtonRadius;
    }

    /**
     * �������߶�
     *
     * @return
     */
    public float getProgressHeight() {
        return mProgressHeight;
    }

    /**
     * ���ý������߶�
     *
     * @param progressHeight
     */
    public void setProgressHeight(float progressHeight) {
        mProgressHeight = progressHeight;
    }

    /**
     * ��������ɫ
     *
     * @return
     */
    public int getProgressColor() {
        return mProgressColor;
    }

    /**
     * ���ý�������ɫ
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    /**
     * ����������ֵ
     *
     * @return
     */
    public int getMaxProgress() {
        return mMaxProgress;
    }

    /**
     * ��������������ֵ
     *
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    /**
     * ���õ�ǰѡ�е�ֵ
     *
     * @param selectProgress ����
     */
    public void setSelectProgress(int selectProgress) {
        this.setSelectProgress(selectProgress, false);
    }

    /**
     * ���õ�ǰѡ�е�ֵ
     *
     * @param selectProgress   ����
     * @param isNotifyListener �Ƿ�֪ͨprogresschangelistener
     */
    public void setSelectProgress(int selectProgress, boolean isNotifyListener) {
        getSelectProgressValue(selectProgress);
        Log.i(TAG, "mSelectProgress: " + mSelectProgress + "  mMaxProgress: " +
                mMaxProgress);
        if (mOnProgressChangeListener != null && isNotifyListener) {
            mOnProgressChangeListener.onChange(mSelectProgress,id);
        }
        invalidate();
    }

    /**
     * ���õ�ǰѡ�е�ֵ
     *
     * @param selectProgress ����
     */
    public void setTouchSelctProgress(int selectProgress) {
        getSelectProgressValue(selectProgress);
        invalidate();
    }

    /**
     * ���㵱ǰѡ�еĽ�������ֵ
     *
     * @param selectProgress ����
     */
    private void getSelectProgressValue(int selectProgress) {
        mSelectProgress = selectProgress;
        if (mSelectProgress > mMaxProgress) {
            mSelectProgress = mMaxProgress;
        } else if (mSelectProgress <= mStartProgress) {
            mSelectProgress = mStartProgress;
        }
    }


    /**
     * ��ȡ��ǰ��ѡ��ֵ
     *
     * @return
     */
    public int getSelectProgress() {
        return mSelectProgress;
    }

    /**
     * ��ʼ�Ŀ̶�ֵ
     *
     * @return
     */
    public int getStartProgress() {
        return mStartProgress;
    }

    /**
     * ������ʼ�̶�ֵ
     *
     * @param startProgress
     */
    public void setStartProgress(int startProgress) {
        mStartProgress = startProgress;
    }

    /**
     * ��ȡdp��Ӧ��pxֵ
     *
     * @param w
     * @return
     */
    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext()
                .getResources().getDisplayMetrics());
    }
}

