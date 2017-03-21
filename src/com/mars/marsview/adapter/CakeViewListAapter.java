package com.mars.marsview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.entity.Cake;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.TrangleView;

public class CakeViewListAapter extends BaseAdapter {
	private Cake data[];
	private Context mContext;
	private LayoutInflater layoutInflater;

	public CakeViewListAapter(Context mContext, Cake data[]) {
		this.mContext = mContext;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(this.mContext);

	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
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
			convertView = layoutInflater.inflate(R.layout.cakeview_item, null);
			viewHolder.cakeview_item_titletxt = (TextView) convertView
					.findViewById(R.id.cakeTitle_tv);
			viewHolder.cakeview_item_weighttxt = (TrangleView) convertView
					.findViewById(R.id.cakeWeight_tv);
			viewHolder.cakeData_tv = (TextView) convertView
					.findViewById(R.id.cakeData_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.cakeview_item_titletxt.setText(data[position].getTitle());
		viewHolder.cakeData_tv.setText(Utils.transfloat2(data[position].getWeight()));
		viewHolder.cakeview_item_weighttxt.setWidthWeight(data[position]
				.getWeight());
		viewHolder.cakeview_item_weighttxt.setmTrangleView_color(data[position]
				.getColor());
		viewHolder.cakeview_item_weighttxt.setShowInDymatic(true);
		return convertView;
	}

	private final class ViewHolder {
		public TextView cakeview_item_titletxt;
		public TextView cakeData_tv;
		public TrangleView cakeview_item_weighttxt;

	}
}
