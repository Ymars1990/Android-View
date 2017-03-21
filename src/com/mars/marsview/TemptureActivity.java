package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.Tempture;
import com.mars.marsview.view.animation.ViewEffect;

public class TemptureActivity extends BaseActivity implements OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private Tempture tempture_trangleview;
	private TimeCallFunction tcFunc;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_tempture);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		tempture_trangleview = (Tempture) this
				.findViewById(R.id.tempture_trangleview);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		tcFunc = new TimeCallFunction(1000*3, 100) {
			@Override
			public void callTimerTaskFuc() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Utils.LogShow("Index", "回调执行"+tempture_trangleview.getIndex());
						if(tempture_trangleview.getIndex()>=60&&tempture_trangleview.getIndex()<100){
							ViewEffect.startFlick(tempture_trangleview);
							Utils.LogShow("回调", "闪烁执行");
						}else if(tempture_trangleview.getIndex()==100){
							Utils.LogShow("回调", "停止闪烁执行");
							ViewEffect.stopFlick(tempture_trangleview);
						}
					}
				});
			}
		};
	}
	
	@Override
	public void callFunc() {
		title_tv.setText("Tempture");
		tempture_trangleview.setShowAsAnimagtion(true);
		tempture_trangleview.setMax(100);
//		ViewEffect.startFlick(tempture_trangleview);
		tcFunc.startTimerTask();
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
		default:
			break;
		}
	}
}
