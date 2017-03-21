package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.adapter.PopuItemListViewAdapter;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.animation.ViewEffect;

public class MyPopuWindow extends PopupWindow implements OnItemClickListener {
	private View mypopuwindowView;
	private Button reset;
	private Button confirm;
	private TextView popuMainItem01;
	private TextView popuMainItem02;
	private TextView popuMainItem03;
	private TextView popuMainItem04;
	private TextView popuMainItem01Text;
	private TextView popuMainItem02Text;
	private TextView popuMainItem03Text;
	private TextView popuMainItem04Text;
	private LinearLayout popuMainItem01_layout;
	private LinearLayout popuMainItem02_layout;
	private LinearLayout popuMainItem03_layout;
	private LinearLayout popuMainItem04_layout;
	private ListView mypopuSecondaryLv;
	private PopuItemListViewAdapter mAdapter;
	private ArrayList<ArrayList<Integer>> itemSelect = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<String>> data;
	private int selectIndex = 1;
	private int selectCount[] = new int[] { 0, 0, 0, 0 };
	private Context mContext;
	private LinkedHashMap<String, String> selectParamsMap = new LinkedHashMap<String, String>();

	public MyPopuWindow(Context mContext, OnClickListener listener,
			ArrayList<ArrayList<String>> data) {
		mypopuwindowView = LayoutInflater.from(mContext).inflate(
				R.layout.mypopuwindowview, null);
		this.data = data;
		this.mContext = mContext;
		initArrayList();
		initSelect();
		initUI();
		registerEvent(listener);
		popuInit();

	}

	private void initArrayList() {
		ArrayList<Integer> item01 = new ArrayList<Integer>();
		ArrayList<Integer> item02 = new ArrayList<Integer>();
		ArrayList<Integer> item03 = new ArrayList<Integer>();
		ArrayList<Integer> item04 = new ArrayList<Integer>();
		itemSelect.add(item01);
		itemSelect.add(item02);
		itemSelect.add(item03);
		itemSelect.add(item04);
	}

	private void initSelect() {
		for (int j = 0; j < data.size(); j++) {
			for (int i = 0; i < data.get(j).size(); i++) {
				itemSelect.get(j).add(0);
			}
		}
	}
	
	public void resetSelect(){
		for (int j = 0; j < data.size(); j++) {
			for (int i = 0; i < data.get(j).size(); i++) {
				itemSelect.get(j).set(i, 0);
			}
		}
	}
	private void popuInit() {
		setContentView(mypopuwindowView);
		// 设置宽度
		setWidth(LayoutParams.MATCH_PARENT);
		// setWidth(display.getWidth());
		// 设置高度
		setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
	}

	private void initUI() {
		reset = (Button) mypopuwindowView.findViewById(R.id.popuReset);
		confirm = (Button) mypopuwindowView.findViewById(R.id.popuConfirm);
		popuMainItem01 = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem01);
		popuMainItem02 = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem02);
		popuMainItem03 = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem03);
		popuMainItem04 = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem04);
		popuMainItem01Text = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem01Text);
		popuMainItem02Text = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem02Text);
		popuMainItem03Text = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem03Text);
		popuMainItem04Text = (TextView) mypopuwindowView
				.findViewById(R.id.popuMainItem04Text);
		popuMainItem01_layout = (LinearLayout) mypopuwindowView
				.findViewById(R.id.popuMainItem01_layout);
		popuMainItem02_layout = (LinearLayout) mypopuwindowView
				.findViewById(R.id.popuMainItem02_layout);
		popuMainItem03_layout = (LinearLayout) mypopuwindowView
				.findViewById(R.id.popuMainItem03_layout);
		popuMainItem04_layout = (LinearLayout) mypopuwindowView
				.findViewById(R.id.popuMainItem04_layout);
		mypopuSecondaryLv = (ListView) mypopuwindowView
				.findViewById(R.id.mypopuSecondaryLv);
		resetMainItemText();
	}

	private void registerEvent(OnClickListener listener) {
		reset.setOnClickListener(listener);
		confirm.setOnClickListener(listener);
		popuMainItem01_layout.setOnClickListener(listener);
		popuMainItem02_layout.setOnClickListener(listener);
		popuMainItem03_layout.setOnClickListener(listener);
		popuMainItem04_layout.setOnClickListener(listener);
		mypopuSecondaryLv.setOnItemClickListener(this);
		ViewEffect.Click(reset);
		ViewEffect.Click(confirm);
	}

	private void resetMainItemText() {
		if (Utils.isNotEmpty(popuMainItem01Text.getText().toString())
				&& !"0".equals(popuMainItem01Text.getText().toString())) {
			popuMainItem01Text.setVisibility(View.VISIBLE);
		} else {
			popuMainItem01Text.setVisibility(View.GONE);
		}
		if (Utils.isNotEmpty(popuMainItem02Text.getText().toString())
				&& !"0".equals(popuMainItem02Text.getText().toString())) {
			popuMainItem02Text.setVisibility(View.VISIBLE);
		} else {
			popuMainItem02Text.setVisibility(View.GONE);
		}
		if (Utils.isNotEmpty(popuMainItem03Text.getText().toString())
				&& !"0".equals(popuMainItem03Text.getText().toString())) {
			popuMainItem03Text.setVisibility(View.VISIBLE);
		} else {
			popuMainItem03Text.setVisibility(View.GONE);
		}
		if (Utils.isNotEmpty(popuMainItem04Text.getText().toString())
				&& !"0".equals(popuMainItem04Text.getText().toString())) {
			popuMainItem04Text.setVisibility(View.VISIBLE);
		} else {
			popuMainItem04Text.setVisibility(View.GONE);
		}
	}

	public void resetMainItem(int onclickIndex, Context mContext) {
		popuMainItem01.setBackgroundColor(mContext.getResources().getColor(
				R.color.white));
		popuMainItem02.setBackgroundColor(mContext.getResources().getColor(
				R.color.white));
		popuMainItem03.setBackgroundColor(mContext.getResources().getColor(
				R.color.white));
		popuMainItem04.setBackgroundColor(mContext.getResources().getColor(
				R.color.white));
		popuMainItem01_layout.setBackgroundColor(mContext.getResources()
				.getColor(R.color.white));
		popuMainItem02_layout.setBackgroundColor(mContext.getResources()
				.getColor(R.color.white));
		popuMainItem03_layout.setBackgroundColor(mContext.getResources()
				.getColor(R.color.white));
		popuMainItem04_layout.setBackgroundColor(mContext.getResources()
				.getColor(R.color.white));
		setText2ItemText("" + selectCount[0], "" + selectCount[1], ""
				+ selectCount[2], "" + selectCount[3]);
		if (onclickIndex != 0) {
			selectIndex = onclickIndex;
		}
		switch (onclickIndex) {
		case 1:
			popuMainItem01.setBackgroundColor(mContext.getResources().getColor(
					R.color.item_selected));
			popuMainItem01_layout.setBackgroundColor(mContext.getResources()
					.getColor(R.color.item_selected));
			break;
		case 2:
			popuMainItem02.setBackgroundColor(mContext.getResources().getColor(
					R.color.item_selected));
			popuMainItem02_layout.setBackgroundColor(mContext.getResources()
					.getColor(R.color.item_selected));
			break;
		case 3:
			popuMainItem03.setBackgroundColor(mContext.getResources().getColor(
					R.color.item_selected));
			popuMainItem03_layout.setBackgroundColor(mContext.getResources()
					.getColor(R.color.item_selected));
			break;
		case 4:
			popuMainItem04.setBackgroundColor(mContext.getResources().getColor(
					R.color.item_selected));
			popuMainItem04_layout.setBackgroundColor(mContext.getResources()
					.getColor(R.color.item_selected));
			break;
		default:

			break;
		}
		updateMainListView(mContext, data.get(selectIndex - 1));
//		mAdapter.setDataList(itemSelect.get(selectIndex - 1),data.get(selectIndex - 1));
		resetMainItemText();
	}

	public void setText2ItemText(String text01, String text02, String text03,
			String text04) {
		popuMainItem01Text.setText(text01);
		popuMainItem02Text.setText(text02);
		popuMainItem03Text.setText(text03);
		popuMainItem04Text.setText(text04);
		resetMainItemText();
	}

	private void updateMainListView(Context mContext, ArrayList<String> dataList) {
		mAdapter = new PopuItemListViewAdapter(mContext, dataList,
				itemSelect.get(selectIndex - 1));
		mypopuSecondaryLv.setAdapter(mAdapter);
	}

	public void setMyOutsideTouchable(boolean Touchable) {
		setOutsideTouchable(Touchable);
	}

	public void resetSelectCount() {
		selectCount = new int[] { 0, 0, 0, 0 };
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (itemSelect.get(selectIndex - 1).get(position) == 0) {
			itemSelect.get(selectIndex - 1).set(position, 1);
			selectCount[selectIndex - 1]++;
			selectParamsMap.put((selectIndex - 1) + ":" + position,
					data.get(selectIndex - 1).get(position));
		} else {
			itemSelect.get(selectIndex - 1).set(position, 0);
			selectCount[selectIndex - 1]--;
			selectParamsMap.remove((selectIndex - 1) + ":" + position);
		}
		setText2ItemText("" + selectCount[0], "" + selectCount[1], ""
				+ selectCount[2], "" + selectCount[3]);
		mAdapter.setDataList(itemSelect.get(selectIndex - 1),data.get(selectIndex - 1));
		// mAdapter.notifyDataSetChanged();
	}

	public LinkedHashMap<String, String> getSelectParams() {
		return selectParamsMap;
	}

	public void resetLinkHashMap() {
		selectParamsMap.clear();
	}
}
