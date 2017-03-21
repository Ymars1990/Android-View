package com.mars.marsview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.MyPopuWindow;

public class MyExpandTabViewActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private TextView title_nextTv;
	private LinearLayout title_backlayout;
	private RelativeLayout title_nextlayout;
	private Context mContext;
	private MyPopuWindow myPopu;
	private ArrayList<ArrayList<String>> data;
	private LinkedHashMap<String, String> selectParamsMap;
	private int mainSelect = 1;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_myexpandtabview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_nextTv = (TextView) this.findViewById(R.id.title_nextTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		title_nextlayout = (RelativeLayout) this
				.findViewById(R.id.title_nextlayout);
		title_nextlayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		title_nextlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		data = new ArrayList<ArrayList<String>>();
		initData();
		myPopu = new MyPopuWindow(mContext, this, data);
		myPopu.setMyOutsideTouchable(true);
	}

	private void initData() {
		ArrayList<String> data01 = new ArrayList<String>();
		data01.add("2016/07/07");
		ArrayList<String> data02 = new ArrayList<String>();
		data02.add("622244********3259");
		ArrayList<String> data03 = new ArrayList<String>();
		ArrayList<String> data04 = new ArrayList<String>();
		data04.add("0.10");
		data04.add("1.10");
		data04.add("1999.00");
		data.add(data01);
		data.add(data02);
		data.add(data03);
		data.add(data04);

	}

	@Override
	public void callFunc() {
		title_tv.setText("MyExpandTabView");
		title_nextTv.setText("Show");
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
		case R.id.title_nextlayout:
			ToastManager.show(mContext, "正在弹出pop", 0);
			myPopu.showAsDropDown(title_nextlayout);
			break;
		case R.id.popuReset:
			ToastManager.show(mContext, "popuReset点击", 0);
			myPopu.resetSelect();
			myPopu.resetSelectCount();
			myPopu.resetMainItem(mainSelect, mContext);
			myPopu.resetLinkHashMap();
			break;
		case R.id.popuConfirm:
			ToastManager.show(mContext, "popuConfirm点击", 0);
			myPopu.resetSelect();
			myPopu.resetSelectCount();
			myPopu.resetMainItem(mainSelect, mContext);
			myPopu.dismiss();
			selectParamsMap = myPopu.getSelectParams();
			showMapData();
			myPopu.resetLinkHashMap();
			break;
		case R.id.popuMainItem01_layout:
			ToastManager.show(mContext, "popuMainItem01点击", 0);
			mainSelect = 1;
			myPopu.resetMainItem(mainSelect, mContext);
			break;
		case R.id.popuMainItem02_layout:
			ToastManager.show(mContext, "popuMainItem02点击", 0);
			mainSelect = 2;
			myPopu.resetMainItem(mainSelect, mContext);
			break;
		case R.id.popuMainItem03_layout:
			ToastManager.show(mContext, "popuMainItem03点击", 0);
			mainSelect = 3;
			myPopu.resetMainItem(mainSelect, mContext);
			break;
		case R.id.popuMainItem04_layout:
			ToastManager.show(mContext, "popuMainItem04点击", 0);
			mainSelect = 4;
			myPopu.resetMainItem(mainSelect, mContext);
			break;
		default:
			break;
		}
	}

	private void showMapData() {
		Iterator iter = selectParamsMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Utils.LogShow("Key", entry.getKey());
			Utils.LogShow("Value", entry.getValue());

		}
	}
}
