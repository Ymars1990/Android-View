package com.mars.marsview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mars.marsview.R;

public class SelectListAapter extends BaseAdapter {
	private String selectData[];
	private Context mContext;
	private LayoutInflater layoutInflater;

	public SelectListAapter(Context mContext, String selectData[]) {
		this.mContext = mContext;
		this.selectData = selectData;
		this.layoutInflater = LayoutInflater.from(this.mContext);

	}

	@Override
	public int getCount() {
		return selectData.length;
	}

	@Override
	public Object getItem(int position) {
		return selectData[position];
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
			convertView = layoutInflater.inflate(R.layout.select_item, null);
			viewHolder.select = (TextView) convertView
					.findViewById(R.id.selectItem);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.select.setText(selectData[position]);
		return convertView;
	}

	private final class ViewHolder {
		public TextView select;
	}
}
