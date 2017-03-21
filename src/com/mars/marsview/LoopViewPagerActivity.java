package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.adapter.SamplePagerAdapter;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.LoopViewPager;

public class LoopViewPagerActivity extends BaseActivity implements
		OnClickListener {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private  LoopViewPager viewpager; 
	
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_loopviewpager);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		viewpager = (LoopViewPager) findViewById(R.id.myLoopViewpager);  
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
		title_tv.setText("LoopViewPager");
	    viewpager.setAdapter(new SamplePagerAdapter()); 
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
