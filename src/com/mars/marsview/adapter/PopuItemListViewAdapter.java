package com.mars.marsview.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class PopuItemListViewAdapter extends BaseAdapter {
	private List<String> dataList;
	private ArrayList<Integer> itemSelect;
	private Context mContext;
	private LayoutInflater layoutInflater;

	public PopuItemListViewAdapter(Context mContext, List<String> dataList,ArrayList<Integer> itemSelect) {
		this.mContext = mContext;
		this.dataList = dataList;
		this.itemSelect = itemSelect;
		this.layoutInflater = LayoutInflater.from(this.mContext);

	}
	
	@SuppressWarnings("unchecked")
	public void setDataList(ArrayList<Integer> itemSelect,ArrayList<String> dataList) {
		if (itemSelect != null) {
			this.itemSelect = (ArrayList<Integer>)itemSelect.clone();
			this.dataList = (ArrayList<String>)dataList.clone();
			notifyDataSetChanged();
		}
	}
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.popu_item_listview_layout, null);
			viewHolder.popu_list_item_tv = (TextView) convertView
					.findViewById(R.id.popu_list_item_tv);
			viewHolder.popu_list_item_img = (ImageView) convertView
					.findViewById(R.id.popu_list_item_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Utils.LogShow("position:"+position, position);
		viewHolder.popu_list_item_tv.setText(dataList.get(position));
		if(itemSelect.get(position)==0){
			viewHolder.popu_list_item_img.setVisibility(View.GONE);
		}else{
			viewHolder.popu_list_item_img.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	private final class ViewHolder {
		public TextView popu_list_item_tv;
		public ImageView popu_list_item_img;
	}
}
