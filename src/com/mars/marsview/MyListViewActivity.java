package com.mars.marsview;

import com.mars.marsview.adapter.SelectListAapter;
import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyListViewActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	private ListView myListview;
	SelectListAapter mSelectListAapter = null;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext = null;
	private String selectData[] = new String[] { "PullableListview",
			"·Ö×éListView" };

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_mylistview);

	}

	@Override
	public void initUI() {
		myListview = (ListView) this.findViewById(R.id.myListview);
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mSelectListAapter = new SelectListAapter(mContext, selectData);
		myListview.setAdapter(mSelectListAapter);

	}

	@Override
	public void registerEvent() {
		myListview.setOnItemClickListener(this);
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("ListView Ä¿Â¼");
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		switch (position) {
			case 0:
				intent.setClass(mContext, PullableListViewActivity.class);
			break;
			case 1:
				intent.setClass(mContext, GroupListViewActivity.class);
				break;
		}
		Utils.startActivity(this, intent);

	}

}
