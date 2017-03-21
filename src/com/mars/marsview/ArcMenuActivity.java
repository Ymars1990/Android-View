package com.mars.marsview;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.view.CircleMenuView;
import com.mars.marsview.view.CircleMenuView.CircleMenuOnClicker;
import com.mars.marsview.view.SliderWheelMenu;

public class ArcMenuActivity extends BaseActivity implements
		CircleMenuOnClicker {
	private SliderWheelMenu mSliderMenu;
	private CircleMenuView mCircleMenuView;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_arcmenu);

	}

	@Override
	public void initUI() {

		mCircleMenuView = (CircleMenuView) findViewById(R.id.mCircleMenuView);
		mCircleMenuView.setmOnclicker(this);
		mSliderMenu = (SliderWheelMenu) findViewById(R.id.sliderMenu);
		mSliderMenu.setOnMenuSelected(new SliderWheelMenu.OnMenuSelected() {
			@Override
			public void menuSelect(int position) {
			}
		});
		mSliderMenu.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

	}

	@Override
	public void registerEvent() {
		mCircleMenuView.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOnClickerPosition(int pos) {
		switch (pos) {
		case 1:
//			ToastManager.show(mContext, "支付宝", 0);
			break;
		case 2:
//			ToastManager.show(mContext, "微信", 0);
			break;
		case 3:
//			ToastManager.show(mContext, "京东钱包", 0);
			break;

		default:
			break;
		}
	}

}
