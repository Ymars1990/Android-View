package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.NumTipSeekBar;
import com.mars.marsview.view.NumTipSeekBar.OnProgressChangeListener;
import com.mars.marsview.view.ScannerSpeedView;

public class ScannerSpeedViewActivity extends BaseActivity implements
		OnClickListener, OnProgressChangeListener {
	private Context mContext;

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ScannerSpeedView myScannerspeedView;
	private NumTipSeekBar seekBar;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_scannerspeedview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myScannerspeedView = (ScannerSpeedView) this
				.findViewById(R.id.myScannerspeedView);
		seekBar = (NumTipSeekBar) this.findViewById(R.id.seekBar);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		seekBar.setOnProgressChangeListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("ScannSpeedView");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		}
	}

	@Override
	public void onChange(int selectProgress, int id) {
		myScannerspeedView.setProgress(selectProgress);

	}
}
