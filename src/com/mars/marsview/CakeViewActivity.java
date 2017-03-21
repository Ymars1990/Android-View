package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.marsview.adapter.CakeViewListAapter;
import com.mars.marsview.entity.Cake;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.CakeView;
import com.mars.marsview.view.RoundView;

public class CakeViewActivity extends BaseActivity implements OnClickListener {

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ListView cakeview_lv;
	private CakeView mCakeView;
	private RoundView mRoundView;
	private Cake mCake[];
	private Context mContext;
	private CakeViewListAapter mAdapter;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_cakeview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		cakeview_lv = (ListView) this.findViewById(R.id.cakeview_lv);
		mCakeView = (CakeView) this.findViewById(R.id.mycakeview);
		mRoundView = (RoundView) this.findViewById(R.id.myroundview);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
		mCake = new Cake[] {
				new Cake("衣", 0.08f, mContext.getResources().getColor(
						R.color.pink)),
				new Cake("食", 0.25f, mContext.getResources().getColor(
						R.color.red)),
				new Cake("住", 0.17f, mContext.getResources().getColor(
						R.color.green)),
				new Cake("行", 0.30f, mContext.getResources().getColor(
						R.color.yellow)),
				new Cake("其他", 0.20f, mContext.getResources().getColor(
						R.color.bgblur)) };
		mAdapter = new CakeViewListAapter(mContext, mCake);
	}

	@Override
	public void callFunc() {
		title_tv.setText("CakeView");
		// mCake = new Cake[]{};
		mCakeView.setmCake(mCake);
		mCakeView.setShowViewInAnimation(true);
		cakeview_lv.setAdapter(mAdapter);
		mRoundView.setDate(new float[] { 0.08f, 0.45f, 0.17f, 0.30f, 0.20f });
	}

	//
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
