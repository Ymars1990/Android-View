package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.LoadingProgressDialog;

public class LoadingActivity extends BaseActivity {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private LoadingProgressDialog loadingDialog;
	private Button loading01;
	private Button loading02;
	private Button loading03;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_loading);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		loading01 = (Button) this.findViewById(R.id.loading01);
		loading02 = (Button) this.findViewById(R.id.loading02);
		loading03 = (Button) this.findViewById(R.id.loading03);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		loading01.setOnClickListener(this);
		loading02.setOnClickListener(this);
		loading03.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("各种加载效果");

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
		try {
			switch (v.getId()) {
			case R.id.title_backlayout:
				Utils.closeActivity(this, 0);
				break;
			case R.id.loading01:
				showLoadingDialog(1);
				break;
			case R.id.loading02:
				showLoadingDialog(2);
				break;
			case R.id.loading03:
				showLoadingDialog(3);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showLoadingDialog(int scaleType) {
		if (loadingDialog == null) {
			loadingDialog = new LoadingProgressDialog(this).builder();
			loadingDialog.setCancelable(true);
			loadingDialog.setCanceledOnTouchOutside(true);
		}
		loadingDialog.setLoadingType(scaleType);
		loadingDialog.show();
	}
}
