package com.mars.marsview;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.MyTextView;

public class MyTextViewActivity extends BaseActivity implements
		OnClickListener, OnTouchListener {
	private MyTextView myTextView01;
	private MyTextView myTextView02;
	private MyTextView myTextView03;
	private MyTextView myTextView04;
	private TextView title_tv;
	private LinearLayout title_backlayout;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_mytextview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myTextView01 = (MyTextView) this.findViewById(R.id.myTextView01);
		myTextView01.setShowIndicatorTriangle(true);
		myTextView02 = (MyTextView) this.findViewById(R.id.myTextView02);
		myTextView02.setShowIndicatorTriangle(false);
		myTextView03 = (MyTextView) this.findViewById(R.id.myTextView03);
		myTextView03.setShowIndicatorLine(true);
		myTextView04 = (MyTextView) this.findViewById(R.id.myTextView04);
		myTextView04.setShowIndicatorLine(false);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		myTextView01.setOnClickListener(this);
		myTextView02.setOnClickListener(this);
		myTextView02.setTextview_bgcolor(0xFFCCCCCC);
		myTextView01.setTextview_bgcolor(0xFF5DD6F7);
		myTextView03.setOnClickListener(this);
		myTextView04.setOnClickListener(this);
		myTextView03.setTextview_bgcolor(0xFFCCCCCC);
		myTextView04.setTextview_bgcolor(0xFF5DD6F7);
	}

	@Override
	public void initParams() {
	}

	@Override
	public void callFunc() {
		title_tv.setText("TextView");
		myTextView01.setTextContext("火星人");
		myTextView02.setTextContext("地球人");
		myTextView03.setTextContext("火星人");
		myTextView04.setTextContext("地球人");
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
		case R.id.myTextView01:
			myTextView01.setShowIndicatorTriangle(true);
			myTextView02.setShowIndicatorTriangle(false);
			myTextView02.setTextview_bgcolor(0xFFCCCCCC);
			myTextView01.setTextview_bgcolor(0xFF5DD6F7);
			break;
		case R.id.myTextView02:
			myTextView02.setShowIndicatorTriangle(true);
			myTextView01.setShowIndicatorTriangle(false);
			myTextView01.setTextview_bgcolor(0xFFCCCCCC);
			myTextView02.setTextview_bgcolor(0xFF5DD6F7);
			break;
		case R.id.myTextView03:
			myTextView03.setShowIndicatorLine(true);
			myTextView04.setShowIndicatorLine(false);
			myTextView04.setTextview_bgcolor(0xFFCCCCCC);
			myTextView03.setTextview_bgcolor(0xFF5DD6F7);
			break;
		case R.id.myTextView04:
			myTextView04.setShowIndicatorLine(true);
			myTextView03.setShowIndicatorLine(false);
			myTextView03.setTextview_bgcolor(0xFFCCCCCC);
			myTextView04.setTextview_bgcolor(0xFF5DD6F7);
			break;

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			Utils.LogShow("Touch ", "ACTION_DOWN");
			touchEventHandler(v, MotionEvent.ACTION_DOWN);
			break;
		case MotionEvent.ACTION_UP:// 抬起
			Utils.LogShow("Touch ", "ACTION_UP");
			touchEventHandler(v, MotionEvent.ACTION_UP);
			break;
		default:
			break;
		}
		return true;
	}

	private void touchEventHandler(View v, int motionevent) {
		switch (v.getId()) {
		case R.id.myTextView01:
			if (motionevent == MotionEvent.ACTION_DOWN) {
				myTextView01.setTextview_bgcolor(0xFF5DD6F7);
			} else if (motionevent == MotionEvent.ACTION_UP) {
				myTextView01.setTextview_bgcolor(0xFF11B2DC);
			}
			break;
		case R.id.myTextView02:
			if (motionevent == MotionEvent.ACTION_DOWN) {
				myTextView02.setTextview_bgcolor(0xFF5DD6F7);
			} else if (motionevent == MotionEvent.ACTION_UP) {
				myTextView02.setTextview_bgcolor(0xFF11B2DC);
			}
			break;
		case R.id.myTextView03:
			if (motionevent == MotionEvent.ACTION_DOWN) {
				myTextView03.setTextview_bgcolor(0xFF5DD6F7);
			} else if (motionevent == MotionEvent.ACTION_UP) {
				myTextView03.setTextview_bgcolor(0xFF11B2DC);
			}
			break;
		case R.id.myTextView04:
			if (motionevent == MotionEvent.ACTION_DOWN) {
				myTextView04.setTextview_bgcolor(0xFF5DD6F7);
			} else if (motionevent == MotionEvent.ACTION_UP) {
				myTextView04.setTextview_bgcolor(0xFF11B2DC);
			}
			break;

		default:
			break;
		}
	}
}
