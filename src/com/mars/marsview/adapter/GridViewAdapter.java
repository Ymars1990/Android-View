package com.mars.marsview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.marsview.R;
import com.mars.marsview.entity.GridViewInfo;
import com.mars.marsview.entity.GroupItem;

public class GridViewAdapter extends BaseAdapter{
	private List<GridViewInfo> items;
	private Context context;
	public GridViewAdapter(Context context,List<GridViewInfo> items){
		this.context = context;
		this.items = items;
	}
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
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
			convertView = LayoutInflater.from(this.context).inflate(R.layout.gridview_item, null);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.gvName);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.gvImg);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name .setText(items.get(position).getPlayerName());
		Glide.with(context)
		.load(items.get(position).getImgUrl())
		.placeholder(R.drawable.default_img)
		.into(viewHolder.img);
		return convertView;
	}
	
	
	private final class ViewHolder {
		public TextView name;
		public ImageView img;
	}

}
