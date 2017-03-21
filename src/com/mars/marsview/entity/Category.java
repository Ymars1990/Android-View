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
		// Category���ڵ�һλ
		if (pPosition == 0) {
			return mCategoryName;
		} else {
			return mCategoryItem.get(pPosition - 1);
		}
	}
	
	
	/**
	 * ��ǰ���Item������CategoryҲ��Ҫռ��һ��Item
	 * @return 
	 */
	public int getItemCount() {
		return mCategoryItem.size() + 1;
	}
}
