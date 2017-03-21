package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.timeline.TimelineView;

public class MyTimelineActivity extends BaseActivity {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private TimelineView mtlv;
	private Button changeStep;
	private String stepDesc[];
	private int index = 1;
	private int stepCount = 5;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_mytimeline);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mtlv = (TimelineView) this.findViewById(R.id.mtlv);
		changeStep = (Button) this.findViewById(R.id.changeStep);
		mtlv.setParams(stepCount, 0xFFB6B6B6, 0xFF11B2DC, stepDesc);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		changeStep.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		stepDesc = new String[stepCount];
		for (int i = 0; i < stepCount; i++) {
			stepDesc[i] = "步骤0" + (i + 1);
		}
	}

	@Override
	public void callFunc() {
		title_tv.setText("时间轴效果");

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
			case R.id.changeStep:
				mtlv.setStepIndex(index++);
				index %= (stepCount + 1);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
