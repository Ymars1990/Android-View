package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.WaveView;

public class WaveViewActivity extends BaseActivity {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private WaveView myWaveView;
	private TimeCallFunction tcFunc;
	private int percent = 0;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_waveveiw);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myWaveView = (WaveView) this.findViewById(R.id.myWaveView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("WaveView");
		tcFunc = new TimeCallFunction(1000 * 10, 100) {
			@Override
			public void callTimerTaskFuc() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(percent<=100){
							myWaveView.setmProgress(percent++);
						}
					}
				});
			}
		};
		tcFunc.startTimerTask();
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

}
