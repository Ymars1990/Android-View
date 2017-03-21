package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mars.marsview.R;

public class MyFlowLayout extends ViewGroup {
    private int lineSpacing = 0;


	public MyFlowLayout(Context context) {
		this(context, null);
	}

	public MyFlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyFlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.FlowLayout);
		lineSpacing = mTypedArray.getDimensionPixelSize(
				R.styleable.FlowLayout_lineSpacing, 0);
		mTypedArray.recycle();
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mPaddingLeft = getPaddingLeft();
		int mPaddingRight = getPaddingRight();
		int mPaddingTop = getPaddingTop();
		int mPaddingBottom = getPaddingBottom();

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int lineUsed = mPaddingLeft + mPaddingRight;
		int lineY = mPaddingTop;
		int lineHeight = 0;
		for (int i = 0; i < this.getChildCount(); i++) {
			View child = this.getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}
			int spaceWidth = 0;
			int spaceHeight = 0;
			LayoutParams childLp = child.getLayoutParams();
			if (childLp instanceof MarginLayoutParams) {
				measureChildWithMargins(child, widthMeasureSpec,0,
						heightMeasureSpec, lineY);
				MarginLayoutParams mlp = (MarginLayoutParams) childLp;
				spaceWidth = mlp.leftMargin + mlp.rightMargin;
				spaceHeight = mlp.topMargin + mlp.bottomMargin;
			} else {
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}

			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			spaceWidth += childWidth;
			spaceHeight += childHeight;

			if (lineUsed + spaceWidth > widthSize) {
				// approach the limit of width and move to next line
				lineY += lineHeight + lineSpacing;
				lineUsed = mPaddingLeft + mPaddingRight;
				lineHeight = 0;
			}
			if (spaceHeight > lineHeight) {
				lineHeight = spaceHeight;
			}
			lineUsed += spaceWidth;
		}
		setMeasuredDimension(widthSize,
				heightMode == MeasureSpec.EXACTLY ? heightSize : lineY
						+ lineHeight + mPaddingBottom);
	}

	/**
	 * 存储所有的View，按行记录
	 */
	private List<List<View>> mAllViews = new ArrayList<List<View>>();
	/**
	 * 记录每一行的最大高度
	 */
	private List<Integer> mLineHeight = new ArrayList<Integer>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mAllViews.clear();
		mLineHeight.clear();
		int width = getWidth();
		int lineWidth = 0;
		int lineHeight = 0;
		// 存储每一行所有的childView
		List<View> lineViews = new ArrayList<View>();
		int cCount = getChildCount();
		// 遍历所有的孩子
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;
			// 如果已经需要换行
			if (childWidth + lineWidth > width) {
				// 记录这一行所有的View以及最大高度
				mLineHeight.add(lineHeight);
				// 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
				mAllViews.add(lineViews);
				lineWidth = 0;// 重置行宽
				lineViews = new ArrayList<View>();
			}
			/**
			 * 如果不需要换行，则累加
			 */
			lineWidth += childWidth;
			lineHeight = Math.max(lineHeight, childHeight);
			lineViews.add(child);
		}
		// 记录最后一行
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);

		int left = getPaddingLeft();
		int top = getPaddingTop();
		// 得到总行数
		int lineNums = mAllViews.size();
		for (int i = 0; i < lineNums; i++) {
			// 每一行的所有的views
			lineViews = mAllViews.get(i);
			// 当前行的最大高度
			lineHeight = mLineHeight.get(i);
			// 遍历当前行所有的View
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child
						.getLayoutParams();
				// 计算childView的left,top,right,bottom
				int lc = left + lp.leftMargin;
				int tc = top + lp.topMargin;
				int rc = lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();
				child.layout(lc, tc, rc, bc);
				left += child.getMeasuredWidth() + lp.rightMargin
						+ lp.leftMargin;
			}
			left = getPaddingLeft();
			top += lineHeight;
		}

	}

}
