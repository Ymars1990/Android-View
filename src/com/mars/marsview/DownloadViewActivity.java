package com.mars.marsview;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ArrowDownloadButton;
import com.mars.marsview.view.DownloadView;

public class DownloadViewActivity extends BaseActivity {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private TimeCallFunction tcFunc;
	private int percent = 0;
	private DownloadView downloadView;
	private int count = 0;
	private int progress = 0;
	private ArrowDownloadButton button;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_downloadveiw);
	}
	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		downloadView = (DownloadView) this.findViewById(R.id.myDownloadView);
		button = (ArrowDownloadButton) findViewById(R.id.myArrowDownloadButton);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		button.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("DownloadView");
		tcFunc = new TimeCallFunction(1000 * 10, 100) {
			@Override
			public void callTimerTaskFuc() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (percent == -1) {
							downloadView.setError(true);
						} else {
							downloadView.setPercent(percent);
							percent++;
						}
					}
				});
			}
		};
		tcFunc.startTimerTask();
		
//		downloadView.setPercent(30);

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
		case R.id.myArrowDownloadButton:
			if ((count % 2) == 0) {
				button.startAnimating();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								progress = progress + 1;
								button.setProgress(progress);
							}
						});
					}
				}, 800, 20);
			} else {
				button.reset();
			}
			count++;
			break;
		}
	}

}
