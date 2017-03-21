package com.mars.marsview;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;

public class ViewPagerShowActivity extends BaseActivity implements
		OnClickListener {

	private Button horizontalVpShow;
	private Button verticalVpShow;
	private Button loopVpShow;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_viewpagershow);

	}

	@Override
	public void initUI() {
		horizontalVpShow = (Button) this
				.findViewById(R.id.horizontalViewPagerShow);
		verticalVpShow = (Button) this.findViewById(R.id.verticalViewPagerShow);
		loopVpShow = (Button) this.findViewById(R.id.loopViewPagerShow);

		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
	}

	@Override
	public void registerEvent() {
		horizontalVpShow.setOnClickListener(this);
		verticalVpShow.setOnClickListener(this);
		loopVpShow.setOnClickListener(this);
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("ViewPager");
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
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.horizontalViewPagerShow:
			intent.setClass(this,HorizontalViewpagerActivity.class);
			Utils.startActivity(this, intent);
			break;
		case R.id.verticalViewPagerShow:
			intent.setClass(this,VerticalViewPagerActivity.class);
			Utils.startActivity(this, intent);
			break;
		case R.id.loopViewPagerShow:
			intent.setClass(this,LoopViewPagerActivity.class);
			Utils.startActivity(this, intent);
			break;
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;

		default:
			break;
		}

	}
}
