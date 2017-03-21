package com.mars.marsview.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mars.marsview.R;
import com.mars.marsview.adapter.SelectorAapter;
import com.mars.marsview.adapter.SelectorAapter.SelectorImp;
import com.mars.marsview.entity.SelectorInfo;
import com.mars.marsview.utils.Utils;

public class SelectorLayout extends LinearLayout {
	private Context context;
	private ArrayList<SelectorInfo> data;
	private ListView selectorLv;
	private SelectorAapter adapter;
	private int single_checkbox = 0;// 0 单选 1、多选

	public SelectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.selector_layout, this,
				true);
		initView();
	}

	public SelectorLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SelectorLayout(Context context) {
		this(context, null);
	}

	public void setData(ArrayList<SelectorInfo> data, SelectorImp selectorImp,int single_checkbox) {
		this.data = data;
		for (int i = 0; i < data.size(); i++) {
			Utils.LogShow("data" + i, data.get(i).toString());
		}
		if (adapter == null) {
			adapter = new SelectorAapter(context, this.data, selectorImp,single_checkbox);
			setAdapter();
		} else {
			adapter.updateListView(data,single_checkbox);
		}
	}

	private void initView() {
		selectorLv = (ListView) findViewById(R.id.selectorLv);
	}

	private void setAdapter() {
		selectorLv.setAdapter(adapter);
	}

	public int getSingle_checkbox() {
		return single_checkbox;
	}

	public void setSingle_checkbox(int single_checkbox) {
		this.single_checkbox = single_checkbox;
	}

}
