package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ToastDialog;
import com.mars.marsview.view.ToastDialog.DURATION;
import com.mars.marsview.view.ViewClickEffect;

public class FlowlayoutActivity extends BaseActivity implements OnClickListener {

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private TextView show01;
	private TextView show02;
	private TextView show03;
	private TextView show04;
	private TextView show05;
	private TextView show06;
	private TextView show07;
	private TextView show08;
	private TextView show09;
	private Context mContext;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_flowlayout);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		show01 = (TextView) this.findViewById(R.id.show01);
		show02 = (TextView) this.findViewById(R.id.show02);
		show03 = (TextView) this.findViewById(R.id.show03);
		show04 = (TextView) this.findViewById(R.id.show04);
		show05 = (TextView) this.findViewById(R.id.show05);
		show06 = (TextView) this.findViewById(R.id.show06);
		show07 = (TextView) this.findViewById(R.id.show07);
		show08 = (TextView) this.findViewById(R.id.show08);
		show09 = (TextView) this.findViewById(R.id.show09);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		show01.setOnClickListener(this);
		show02.setOnClickListener(this);
		show03.setOnClickListener(this);
		show04.setOnClickListener(this);
		show05.setOnClickListener(this);
		show06.setOnClickListener(this);
		show07.setOnClickListener(this);
		show08.setOnClickListener(this);
		show09.setOnClickListener(this);
		ViewClickEffect.Click(show01);
		ViewClickEffect.Click(show02);
		ViewClickEffect.Click(show03);
		ViewClickEffect.Click(show04);
		ViewClickEffect.Click(show05);
		ViewClickEffect.Click(show06);
		ViewClickEffect.Click(show07);
		ViewClickEffect.Click(show08);
		ViewClickEffect.Click(show09);
	}

	@Override
	public void initParams() {
	}

	@Override
	public void callFunc() {
		title_tv.setText("FlowLayout");
		mContext = this;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		case R.id.show01:
		/*	ToastDialog tsDialog = new ToastDialog(mContext, DURATION.SHORT);
			tsDialog.builder("≤‚ ‘Toast");*/
			ToastManager.show(mContext, "≤‚ ‘", 1);
			break;
		default:
			break;
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

}
