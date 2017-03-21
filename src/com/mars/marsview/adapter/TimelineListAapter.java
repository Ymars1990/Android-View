package com.mars.marsview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.entity.TimeLine;
import com.mars.marsview.view.CircleView;
import com.mars.marsview.view.TrangleView;

public class TimelineListAapter extends BaseAdapter {
	private List<TimeLine> data;
	private Context mContext;
	private LayoutInflater layoutInflater;

	public TimelineListAapter(Context mContext, List<TimeLine> data) {
		this.mContext = mContext;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(this.mContext);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
			convertView = layoutInflater.inflate(R.layout.timeline_item, null);
			viewHolder.timeline_item_steptxt = (TextView) convertView
					.findViewById(R.id.timeline_item_steptxt);
			viewHolder.timeline_item_contenttxt = (TextView) convertView
					.findViewById(R.id.timeline_item_contenttxt);
			viewHolder.timeline_item_circleview = (CircleView) convertView
					.findViewById(R.id.timeline_item_circleview);
			viewHolder.timeline_item_trangleview = (TrangleView) convertView
					.findViewById(R.id.timeline_item_trangleview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.timeline_item_steptxt.setText(data.get(position)
				.getTimelineStep());
		viewHolder.timeline_item_contenttxt.setText(data.get(position)
				.getTimelineContent());
		if (data.get(position).getFlag() == 1) {
			viewHolder.timeline_item_circleview
					.setmCircleview_color(mContext.getResources().getColor(R.color.blue));
			viewHolder.timeline_item_circleview.invalidate();
			viewHolder.timeline_item_trangleview
					.setmTrangleView_color(mContext.getResources().getColor(R.color.blue));
			viewHolder.timeline_item_trangleview.invalidate();
		} else {
			viewHolder.timeline_item_circleview
					.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			viewHolder.timeline_item_circleview.invalidate();
			viewHolder.timeline_item_trangleview
					.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));
			viewHolder.timeline_item_trangleview.invalidate();
		}
		viewHolder.timeline_item_trangleview.setShowInDymatic(false);
		return convertView;
	}

	private final class ViewHolder {
		public TextView timeline_item_steptxt;
		public TextView timeline_item_contenttxt;
		public CircleView timeline_item_circleview;
		public TrangleView timeline_item_trangleview;
	}
}
