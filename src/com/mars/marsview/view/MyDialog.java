package com.mars.marsview.view;

import com.mars.marsview.R;
import com.mars.marsview.adapter.WheelViewAdapter;
import com.mars.marsview.interfaceutils.OnWheelChangedListener;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class MyDialog extends Dialog implements
		android.view.View.OnClickListener, OnWheelChangedListener {

	private Button topCancel;
	private Button topConfirm;
	private Button bottomCancel;
	private Button bottomConfirm;
	private LinearLayout toplayout;
	private LinearLayout bottomlayout;
	private LinearLayout wheelview_selelctlayout;
	private WheelView mainWheelView;
	private WheelView secondWheelView;
	private int mainWheelItemIndex = 0;
	private int secondWheelItemIndex = 0;
	private int width;
	private int height;

	public MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int width, int height) {
		super(context,R.style.ActionSheetDialogStyle);
		this.width = width;
		this.height = height;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_wheelview_dialog);
		initUI();
		registerEvent();
		setUpListener();
	}

	private void initUI() {
		topCancel = (Button) this
				.findViewById(R.id.mywheelviewdialog_topcancel);
		topConfirm = (Button) this
				.findViewById(R.id.mywheelviewdialog_topconfirm);
		bottomCancel = (Button) this
				.findViewById(R.id.mywheelviewdialog_bottomcancel);
		bottomConfirm = (Button) this
				.findViewById(R.id.mywheelviewdialog_bottomconfirm);
		toplayout = (LinearLayout) this
				.findViewById(R.id.mywheelviewdialog_toplayout);
		bottomlayout = (LinearLayout) this
				.findViewById(R.id.mywheelviewdialog_bottomlayout);
		mainWheelView = (WheelView) this.findViewById(R.id.wheelview_mainkey);
		secondWheelView = (WheelView) this
				.findViewById(R.id.wheelview_secondkey);
		wheelview_selelctlayout = (LinearLayout) this
				.findViewById(R.id.wheelview_selelctlayout);
	}

	public void setGravity(int gravity) {
		LayoutParams lparams = null;
		switch (gravity) {
		case 0:
			lparams = new LayoutParams(width, height / 3);
			toplayout.setVisibility(View.GONE);
			bottomlayout.setVisibility(View.VISIBLE);
			break;
		case 1:
			lparams = new LayoutParams(width - 40, height / 3);
			toplayout.setVisibility(View.GONE);
			bottomlayout.setVisibility(View.VISIBLE);
			break;
		case 2:
			lparams = new LayoutParams(width, height / 3);
			bottomlayout.setVisibility(View.GONE);
			toplayout.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		wheelview_selelctlayout.setLayoutParams(lparams);
	}

	private void registerEvent() {
		topCancel.setOnClickListener(this);
		topConfirm.setOnClickListener(this);
		bottomCancel.setOnClickListener(this);
		bottomConfirm.setOnClickListener(this);
	}

	private void setUpListener() {
		// 添加change事件
		mainWheelView.addChangingListener(this);
		// 添加change事件
		secondWheelView.addChangingListener(this);
	}

	public void setDateAdapter(int wheelIndex, WheelViewAdapter apater) {
		switch (wheelIndex) {
		case 1:
			mainWheelView.setViewAdapter(apater);
			break;
		case 2:
			secondWheelView.setViewAdapter(apater);
			break;
		default:
			break;
		}

	}

	public void adapterSetCurrentItem(int wheelviewIndex, int itemIndex) {
		switch (wheelviewIndex) {
		case 1:
			mainWheelView.setCurrentItem(itemIndex);
			break;
		case 2:
			secondWheelView.setCurrentItem(itemIndex);

			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mywheelviewdialog_topcancel:
			dialogdismiss();
			break;
		case R.id.mywheelviewdialog_topconfirm:
			getData(mainWheelItemIndex, secondWheelItemIndex);
			break;
		case R.id.mywheelviewdialog_bottomcancel:
			dialogdismiss();
			break;
		case R.id.mywheelviewdialog_bottomconfirm:
			getData(mainWheelItemIndex, secondWheelItemIndex);
			break;
		default:
			break;
		}
	}

	public void dialogdismiss() {
		if (this.isShowing()) {
			dismiss();
		}
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mainWheelView) {
			mainWheelItemIndex = mainWheelView.getCurrentItem();
			updateData(1, mainWheelItemIndex);
		} else if (wheel == secondWheelView) {
			secondWheelItemIndex = secondWheelView.getCurrentItem();
		}
	}

	public abstract void updateData(int wheelIndex, int itemIndex);

	public abstract void getData(int mainWheelItemIndex,
			int secondWheelItemIndex);

}
