package com.mars.marsview.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.marsview.R;
import com.mars.marsview.entity.Category;
import com.mars.marsview.entity.GroupItem;
import com.mars.marsview.utils.Utils;

public class MyGroupListViewAapter extends BaseAdapter {
	private Context mContext;
	private static final int TYPE_CATEGORY_ITEM = 0;
	private static final int TYPE_ITEM = 1;

	private ArrayList<Category> mListData;
	private LayoutInflater layoutInflater;

	public MyGroupListViewAapter(Context mContext, ArrayList<Category> mListData) {
		this.mContext = mContext;
		this.mListData = mListData;
		this.layoutInflater = LayoutInflater.from(this.mContext);

	}

	@Override
	public int getCount() {
		int count = 0;
		if (null != mListData) {
			// 所有分类中item的总和是ListVIew Item的总个数
			for (Category category : mListData) {
				count += category.getItemCount();
			}
		}

		return count;
	}

	@Override
	public Object getItem(int position) {

		// 异常情况处理
		if (null == mListData || position < 0 || position > getCount()) {
			return null;
		}

		// 同一分类内，第一个元素的索引值
		int categroyFirstIndex = 0;

		for (Category category : mListData) {
			int size = category.getItemCount();
			// 在当前分类中的索引值
			int categoryIndex = position - categroyFirstIndex;
			// item在当前分类内
			if (categoryIndex < size) {
				return category.getItem(categoryIndex);
			}

			// 索引移动到当前分类结尾，即下一个分类第一个元素索引
			categroyFirstIndex += size;
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// 异常情况处理
		if (null == mListData || position < 0 || position > getCount()) {
			return TYPE_ITEM;
		}
		int categroyFirstIndex = 0;
		for (Category category : mListData) {
			int size = category.getItemCount();
			// 在当前分类中的索引值
			int categoryIndex = position - categroyFirstIndex;
			if (categoryIndex == 0) {
				return TYPE_CATEGORY_ITEM;
			}
			categroyFirstIndex += size;
		}
		return TYPE_ITEM;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int itemViewType = getItemViewType(position);
		switch (itemViewType) {
		case TYPE_CATEGORY_ITEM:
			ViewHolderTag mViewHolderTag = null;
			if (null == convertView) {
				mViewHolderTag = new ViewHolderTag();
				convertView = layoutInflater.inflate(
						R.layout.grouplistview_tag, null);
				mViewHolderTag.tag = (TextView) convertView
						.findViewById(R.id.group_tag);
				mViewHolderTag.tag_img = (ImageView) convertView
						.findViewById(R.id.group_tag_img);
				convertView.setTag(mViewHolderTag);
			} else {
				mViewHolderTag = (ViewHolderTag) convertView.getTag();
			}
			mViewHolderTag.tag.setText((String) getItem(position));
			if ("NBA球员".equals((String) getItem(position))) {
				mViewHolderTag.tag_img.setImageResource(R.drawable.basketball);
			} else if ("足球运动员".equals((String) getItem(position))) {
				mViewHolderTag.tag_img.setImageResource(R.drawable.football);
			} else {
				mViewHolderTag.tag_img.setImageResource(R.drawable.athletes);
			}
			break;
		case TYPE_ITEM:
			ViewHolderContent mViewHolderContent = null;
			if (null == convertView) {
				mViewHolderContent = new ViewHolderContent();
				convertView = layoutInflater.inflate(
						R.layout.grouplistview_item, null);
				mViewHolderContent.item = (TextView) convertView
						.findViewById(R.id.group_item);
				mViewHolderContent.item_img = (ImageView) convertView
						.findViewById(R.id.group_item_img);
				convertView.setTag(mViewHolderContent);
			} else {
				mViewHolderContent = (ViewHolderContent) convertView.getTag();
			}
			// 绑定数据
			mViewHolderContent.item.setText(((GroupItem) getItem(position))
					.getItemConent());
			Utils.LogShow("url",
					((GroupItem) getItem(position)).getItemConentIcon());
			Glide.with(mContext)
					.load(((GroupItem) getItem(position)).getItemConentIcon())
					.placeholder(R.drawable.default_img)
					.into(mViewHolderContent.item_img);
			break;

		}
		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItemViewType(position) != TYPE_CATEGORY_ITEM;
	}

	private class ViewHolderTag {
		public TextView tag;
		public ImageView tag_img;
	}

	private class ViewHolderContent {
		public TextView item;
		public ImageView item_img;
	}
}
