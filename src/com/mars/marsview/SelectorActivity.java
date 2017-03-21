package com.mars.marsview;

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.adapter.SelectorAapter.SelectorImp;
import com.mars.marsview.entity.SelectorInfo;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.SelectorLayout;

public class SelectorActivity extends BaseActivity implements OnClickListener,
		SelectorImp {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private SelectorLayout mySelector;
	private Button single_checkbox_bt;
	private ArrayList<SelectorInfo> data;
	private int single_checkbox = 0;// 0 单选 1、多选
	private Context mContext;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_selector);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mySelector = (SelectorLayout) this.findViewById(R.id.mySelector);
		single_checkbox_bt = (Button) this
				.findViewById(R.id.single_checkbox_bt);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		single_checkbox_bt.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
		data = new ArrayList<SelectorInfo>();
		for (int i = 0; i < 20; i++) {
			data.add(new SelectorInfo(false, "选项" + i));
		}
	}

	@Override
	public void callFunc() {
		title_tv.setText("Selector");
		mySelector.setData(data, this, single_checkbox);
		if (single_checkbox == 0) {
			single_checkbox_bt.setText("单选");
		} else {
			single_checkbox_bt.setText("多选");
		}
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
		case R.id.single_checkbox_bt:
			for (int i = 0; i < data.size(); i++) {
				data.get(i).setSelected(false);
			}
			if (single_checkbox == 0) {
				single_checkbox = 1;
				single_checkbox_bt.setText("多选");
			} else {
				single_checkbox = 0;
				single_checkbox_bt.setText("单选");
			}
			mySelector.setData(data, this, single_checkbox);
			break;
		default:
			break;
		}
	}

	@Override
	public void updateSelectorInfo(int position) {
		if (single_checkbox == 1) {
			data.get(position).setSelected(!data.get(position).isSelected());
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (position == i) {
					data.get(i).setSelected(
							!data.get(i).isSelected());
				} else {
					data.get(i).setSelected(false);
				}
			}
		}
		mySelector.setData(data, this, single_checkbox);
	}
}
