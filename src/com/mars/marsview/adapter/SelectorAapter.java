package com.mars.marsview.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.entity.SelectorInfo;
import com.mars.marsview.utils.Utils;

public class SelectorAapter extends BaseAdapter {
	private List<SelectorInfo> list = null;
	private Context mContext;
	private SelectorImp selectorImp;
	private int single_checkbox = 0;// 0 单选 1、多选
	public SelectorAapter(Context mContext, List<SelectorInfo> list,SelectorImp selectorImp
			,int single_checkbox) {
		this.mContext = mContext;
		this.list = list;
		this.selectorImp = selectorImp;
		this.single_checkbox = single_checkbox;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(ArrayList<SelectorInfo> list,int single_checkbox){
		this.single_checkbox = single_checkbox;
		if (list != null) {
			list = (ArrayList<SelectorInfo>) list.clone();
			notifyDataSetChanged();
		}
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.selector_item, null);
			viewHolder.selectorSelectedIv = (ImageView) view.findViewById(R.id.selectorSelectedIv);
			viewHolder.selectorDescTv = (TextView) view.findViewById(R.id.selectorDescTv);
			viewHolder.selectorLinearlayout = (LinearLayout) view.findViewById(R.id.selectorLinearlayout);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.selectorDescTv.setText(list.get(position).getSelectorDesc());
		if(list.get(position).isSelected()){
			viewHolder.selectorSelectedIv.setImageResource(R.drawable.check_yes);
		}else{
			viewHolder.selectorSelectedIv.setImageResource(R.drawable.check_no);
		}
		viewHolder.selectorSelectedIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectorImp.updateSelectorInfo(position);
			}
		});
/*		
		for (long i = 0; i <5; i++) {
			View view1 = LayoutInflater.from(mContext).inflate(R.layout.title_layout, null,
					true);
			viewHolder.selectorLinearlayout .addView(view1);	
		}*/
		return view;
	}
	final static class ViewHolder {
		ImageView selectorSelectedIv;
		TextView selectorDescTv;
		LinearLayout selectorLinearlayout;
	}
	
	public interface SelectorImp{
		void updateSelectorInfo(int position);
	}
}
