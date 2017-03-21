package com.mars.marsview;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.HistogramView;

public class HistogramViewActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private Map<Integer, Float> dataMap;
	private HistogramView myHistogramView;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_histogramview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myHistogramView  = (HistogramView) this.findViewById(R.id.myHistogramView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
		dataMap = new HashMap<Integer, Float>();
		dataMap.put(2007, 4.5f);
		dataMap.put(2008, 4.8f);
		dataMap.put(2009, 5.2f);
		dataMap.put(2010, 6.5f);
		dataMap.put(2011, 7.5f);
		dataMap.put(2012, 8.5f);
		dataMap.put(2013, 9.8f);
		dataMap.put(2014, 12.4f);
		dataMap.put(2015, 13.1f);
		dataMap.put(2016, 15.9f);
	}

	@Override
	public void callFunc() {
		title_tv.setText("HistogramView");
		myHistogramView.setDataMap(dataMap);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		default:
			break;
		}
	}
}
