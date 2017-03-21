package com.mars.marsview.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String mCategoryName;
	private List<GroupItem> mCategoryItem = new ArrayList<GroupItem>();
	
	public Category(String mCategroyName) {
		mCategoryName = mCategroyName;
	}
	
	
	public void addItem(GroupItem pItem) {
		mCategoryItem.add(pItem);
	}
	
	public String getmCategoryName() {
		return mCategoryName;
	}
	
	public Object getItem(int pPosition) {
		// Category排在第一位
		if (pPosition == 0) {
			return mCategoryName;
		} else {
			return mCategoryItem.get(pPosition - 1);
		}
	}
	
	
	/**
	 * 当前类别Item总数。Category也需要占用一个Item
	 * @return 
	 */
	public int getItemCount() {
		return mCategoryItem.size() + 1;
	}
}
