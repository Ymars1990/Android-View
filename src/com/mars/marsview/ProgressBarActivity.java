package com.mars.marsview;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.HorizontalProgressBar;
import com.mars.marsview.view.RoundProgressBar;

public class ProgressBarActivity extends BaseActivity implements
		OnClickListener {
	private HorizontalProgressBar showProgressBar;
	private RoundProgressBar showRound;
	private static int MSG = 0x01;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int progress = showProgressBar.getProgress();
			if (progress < 100) {
				progress++;
				showProgressBar.setProgress(progress);
				showRound.setProgress(progress);
				myHandler.sendEmptyMessageDelayed(MSG, 300);
			} else {
				progress = 0;
				showProgressBar.setProgress(progress);
				showRound.setProgress(progress);
				myHandler.sendEmptyMessage(MSG);
			}
		};
	};

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_progressbar);
	}

	@Override
	public void initUI() {
		showProgressBar = (HorizontalProgressBar) this
				.findViewById(R.id.showProgressBar);
		showRound = (RoundProgressBar) this.findViewById(R.id.showRound);
		myHandler.sendEmptyMessage(MSG);
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);


	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {

	}

	@Override
	public void callFunc() {
		title_tv.setText("ProgressBar");
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
