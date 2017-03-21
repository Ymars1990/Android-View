package com.mars.marsview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mars.marsview.R;

public class ImageListViewAdapter extends BaseAdapter {
	List<String> items;
	Context context;

	public ImageListViewAdapter(Context context, List<String> items) {
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.imagelist_item, null);
			viewHolder.iv = (ImageView) convertView
					.findViewById(R.id.img_list_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Glide.with(context).load(items.get(position))
				.placeholder(R.drawable.default_img).into(viewHolder.iv);
		return convertView;
	}

	private final class ViewHolder {
		public ImageView iv;
	}
}
