package com.mars.marsview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mars.marsview.utils.Utils;

public class AddViewViewGroup extends ViewGroup {

	private int verticalChildSpace = 15;
	private int horzatalChildSpace = 15;
	private int childWidth = 0;
	private int childHeight = 0;
	private int columNum = 3;
	private Context context;

	public AddViewViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public AddViewViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public AddViewViewGroup(Context context) {
		this(context, null);
		this.context = context;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int rw = MeasureSpec.getSize(widthMeasureSpec);
		int rh = 0;
		int sizeWidth  = 0;
		int sizeHeight = 0;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 当前子空间实际占据的宽度
			childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;
			rh += child.getMeasuredHeight() + verticalChildSpace;
		}
		rh += getPaddingTop() + getPaddingBottom() - verticalChildSpace;
		// Utils.LogShow("rh", ""+rh);
		// Utils.LogShow("getChildHeight", ""+childHeight);
		setMeasuredDimension(rw, rh);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left = 0;
		int top = 0;
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			// childWidth = getChildWidth();
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 当前子空间实际占据的宽度
			childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;
			Utils.LogShow("childWidth", "" + childWidth);
			left = (i % columNum) * (childWidth + horzatalChildSpace);
			top = getPaddingTop() + (i / columNum)
					* (childHeight + verticalChildSpace);
			child.layout(left, top, left + childWidth, top + childHeight+getPaddingBottom());
		}
		top += getPaddingBottom();
	}

	public int getVerticalChildSpace() {
		return verticalChildSpace;
	}

	public void setVerticalChildSpace(int verticalChildSpace) {
		this.verticalChildSpace = verticalChildSpace;
	}

	public int getHorzatalChildSpace() {
		return horzatalChildSpace;
	}

	public void setHorzatalChildSpace(int horzatalChildSpace) {
		this.horzatalChildSpace = horzatalChildSpace;
	}

	/**
	 * @return the childWidth
	 */
	public int getChildWidth() {
		return childWidth;
	}

	/**
	 * @param childWidth
	 *            the childWidth to set
	 */
	public void setChildWidth(int childWidth) {
		this.childWidth = childWidth;
	}

	/**
	 * @return the childHeight
	 */
	public int getChildHeight() {
		return childHeight;
	}

	/**
	 * @param childHeight
	 *            the childHeight to set
	 */
	public void setChildHeight(int childHeight) {
		this.childHeight = childHeight;
	}

	/**
	 * @return the columNum
	 */
	public int getColumNum() {
		return columNum;
	}

	/**
	 * @param columNum
	 *            the columNum to set
	 */
	public void setColumNum(int columNum) {
		this.columNum = columNum;
		invalidate();
	}
}
