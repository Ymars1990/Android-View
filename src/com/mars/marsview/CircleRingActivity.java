package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.IconView;
import com.mars.marsview.view.IconView.INDEX_TYPE;

public class CircleRingActivity extends BaseActivity implements OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private IconView myIconView;
	private IconView myIconView01;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_circlering);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myIconView =(IconView)this.findViewById(R.id.myIconView);
		myIconView01 =(IconView)this.findViewById(R.id.myIconView01);
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
		title_tv.setText("RingView");
		myIconView.setIconTpye(1);
		myIconView01.setIconTpye(2);
		myIconView01.setIndex(INDEX_TYPE.INDEX_TYPE_X);
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
			// new TakeShotThread().start();
			Utils.closeActivity(this, 0);

			break;
		}
	}
}
