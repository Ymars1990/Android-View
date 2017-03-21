package com.mars.marsview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.adapter.ArrayWheelAdapter;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.MyDialog;

public class WheelViewActivity extends BaseActivity implements OnClickListener {

	private Button top = null;
	private Button bottom = null;
	private Button center = null;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private MyDialog myDialog = null;
	private int width;
	private int height;
	private Context mContext;
	private Window window;
	private WindowManager.LayoutParams lp;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_wheelview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		top = (Button) this.findViewById(R.id.topDialog_wheelview);
		bottom = (Button) this.findViewById(R.id.bottomDialog_wheelview);
		center = (Button) this.findViewById(R.id.centerDialog_wheelview);

	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		top.setOnClickListener(this);
		bottom.setOnClickListener(this);
		center.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
		getWidthHeight();
	}

	@Override
	public void callFunc() {
		title_tv.setText("WheelView");
		initMyDialog();
		initProvinceDatas();
	}

	private void getWidthHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
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
		case R.id.topDialog_wheelview:
			showMyDialog(0);
			break;
		case R.id.bottomDialog_wheelview:
			showMyDialog(2);
			break;
		case R.id.centerDialog_wheelview:
			showMyDialog(1);
			break;
		case R.id.title_backlayout:
			// new TakeShotThread().start();
			Utils.closeActivity(this, 0);

			break;
		default:
			break;
		}
	}

	String[] cities = null;

	private void initMyDialog() {
		myDialog = new MyDialog(mContext, width, height) {
			@Override
			public void updateData(int wheelIndex, int itemIndex) {
				Utils.LogShow("WheelView", "正在转动...");
				// setDialogData();
				mCurrentProviceName = mProvinceDatas[itemIndex];
				cities = null;
				cities = mCitisDatasMap.get(mCurrentProviceName);
				if (cities == null) {
					cities = new String[] { "" };
				}
				myDialog.setDateAdapter(2, new ArrayWheelAdapter<String>(
						WheelViewActivity.this, cities));
				myDialog.adapterSetCurrentItem(2, 0);
			}

			@Override
			public void getData(int mainWheelItemIndex, int secondWheelItemIndex) {
				Utils.LogShow("WheelView", "省份:"
						+ mProvinceDatas[mainWheelItemIndex] + " 城市:"
						+ cities[secondWheelItemIndex]);
				myDialog.dialogdismiss();
			}
		};
		window = myDialog.getWindow();
		myDialog.setCancelable(true);
	}

	private void setDialogData(int index) {
		switch (index) {
		case 1:
			myDialog.setDateAdapter(1, new ArrayWheelAdapter<String>(
					WheelViewActivity.this, mProvinceDatas));
			myDialog.adapterSetCurrentItem(1, 0);
			break;
		case 2:
			cities = null;
			cities = mCitisDatasMap.get(mProvinceDatas[0]);
			if (cities == null) {
				cities = new String[] { "" };
			}
			myDialog.setDateAdapter(2, new ArrayWheelAdapter<String>(
					WheelViewActivity.this, cities));
			myDialog.adapterSetCurrentItem(2, 0);
			break;

		default:
			break;
		}
	}

	private void showMyDialog(int gravity) {
		lp = window.getAttributes();
		switch (gravity) {
		case 0:
			lp.x = 0; // 新位置X坐标
			lp.y = (int) Utils.dip2px(mContext,50); // 新位置Y坐标
			window .setAttributes(lp);
			window.setGravity(Gravity.TOP);
			myDialog.show();
			break;
		case 1:
			lp.x = 0; // 新位置X坐标
			lp.y = 0; // 新位置Y坐标
			window .setAttributes(lp);
			window.setGravity(Gravity.CENTER);
			myDialog.show();
			break;
		case 2:
			lp.x = 0; // 新位置X坐标
			lp.y = 0; // 新位置Y坐标
			window .setAttributes(lp);
			window.setGravity(Gravity.BOTTOM);
			myDialog.show();
			break;
		default:
			break;
		}
		setDialogData(1);
		setDialogData(2);
		myDialog.setGravity(gravity);
	}
}
