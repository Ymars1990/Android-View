package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TagLayout extends ViewGroup {

	private List<Line> mLines = new LinkedList<Line>(); // ������¼�������ж��ٸ���
	private Line mCurrentLine; // ��ǰ��
	private int horizontalSpace = 10; // �ؼ�֮��ļ�϶
	private int verticalSpace = 10; // ��֮��ļ�϶

	public TagLayout(Context context) {
		this(context, null);
	}

	public TagLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// ��ռ�¼,,,����վͻ������
		mLines.clear();
		mCurrentLine = null;
		// 1,��ȡ����Ŀ��
		int width = MeasureSpec.getSize(widthMeasureSpec);

		// 2,��ȡ�еĿ��
		int maxWidth = width - getPaddingLeft() - getPaddingRight();

		// 3,��ȡ����
		for (int i = 0; i < getChildCount(); i++) {

			View child = getChildAt(i);
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			// �������ӵĿ��
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// ��������ӵ�����,������ӵ�������
			if (mCurrentLine == null) {
				mCurrentLine = new Line(maxWidth, horizontalSpace);
				mCurrentLine.addView(child);
				mLines.add(mCurrentLine);
			} else {
				if (mCurrentLine.canAdd(child)) {
					// ���Լ���
					mCurrentLine.addView(child);
				} else {
					// ����
					mCurrentLine = new Line(maxWidth, horizontalSpace);
					// ��ӵ�list
					mLines.add(mCurrentLine);
					// ��Ӻ���
					mCurrentLine.addView(child);
				}
			}
		}
		// ��ȡ�Լ��ĸ߶�
		int measuredHeight = getPaddingTop() + getPaddingBottom();

		for (int i = 0; i < mLines.size(); i++) {
			measuredHeight += mLines.get(i).height;
		}
		measuredHeight = measuredHeight + (mLines.size() - 1) * verticalSpace;
		// �����Լ��Ŀ�Ⱥ͸߶�
		setMeasuredDimension(width, measuredHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int marginTop = getPaddingTop();
		int marginLeft = getPaddingLeft();
		// ��line�Լ�ȥ�ڷ�
		for (int i = 0; i < mLines.size(); i++) {
			Line line = mLines.get(i);
			line.layout(marginLeft, marginTop);
			marginTop += verticalSpace + line.height;
		}
	}

	/**
	 * ������¼ÿ�пؼ��İڷ�
	 */
	private class Line {
		private List<View> mViews = new ArrayList<View>();
		private int maxWidth; // �еĿ��
		private int space; // �е�ˮƽ��϶
		private int usedWidth; // ��ʹ���˵Ŀ��
		public int height; // �и�

		public Line(int maxWidth, int horizontalSpace) {
			this.maxWidth = maxWidth;
			this.space = horizontalSpace;
		}

		/**
		 * ����ӿؼ�
		 * 
		 * @param child
		 */
		public void addView(View child) {
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			if (mViews.size() == 0) {
				// ���û�пؼ��������
				if (childWidth > maxWidth) {
					usedWidth = maxWidth;
					height = childHeight;
				} else {
					usedWidth = childWidth;
					height = childHeight;
				}
			} else {
				// �пؼ�
				usedWidth = usedWidth + space + childWidth;
				height = (childHeight > height) ? childHeight : height;
			}
			mViews.add(child);
		}

		/**
		 * �ж��Ƿ��������ӿؼ�
		 * 
		 * @param child
		 * @return
		 */
		public boolean canAdd(View child) {
			int width = child.getMeasuredWidth();
			// line��û��viewʱ
			if (mViews.size() == 0) {
				// ֻҪû�У��Ϳ��Լ�
				return true;
			}
			if (usedWidth + width + space > maxWidth) {
				return false;
			} else {
				return true;
			}
		}

		/**
		 * �ڷ���
		 * 
		 * @param marginLeft
		 * @param marginTop
		 */
		public void layout(int marginLeft, int marginTop) {
			int extraWidth = maxWidth - usedWidth;
			int avgWidth = (int) (extraWidth * 1f / mViews.size() + 0.5f);
			// ����ؼ����������ҵ�λ��
			for (int i = 0; i < mViews.size(); i++) {

				View view = mViews.get(i);

				int viewWidth = view.getMeasuredWidth();
				int viewHeight = view.getMeasuredHeight();

				if (avgWidth > 0) {
					// ���µ�ȥ�������ӵĿ��
					int specWidth = MeasureSpec.makeMeasureSpec(viewWidth
							+ avgWidth, MeasureSpec.EXACTLY);
					int specHeight = MeasureSpec.makeMeasureSpec(viewHeight,
							MeasureSpec.EXACTLY);
					view.measure(specWidth, specHeight);
					// ���»�ȡ��Ⱥ͸߶�
					viewWidth = view.getMeasuredWidth();
					viewHeight = view.getMeasuredHeight();
				}

				int extraTop = (int) ((height - viewHeight) / 2f + 0.5);

				int left = marginLeft;
				int top = marginTop + extraTop;
				int right = left + viewWidth;
				int bottom = top + viewHeight;
				// �ڷ�ÿһ�����ӵ�λ��
				view.layout(left, top, right, bottom);
				marginLeft += viewWidth + space;
			}
		}
	}
}
