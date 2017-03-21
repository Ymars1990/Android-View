package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ImageMannager;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.MyRoundImageView;

public class MyRoundImageViewActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private MyRoundImageView myroundimageview01;
	private MyRoundImageView myroundimageview02;
	private MyRoundImageView myroundimageview03;
	private MyRoundImageView myroundimageview04;
	private MyRoundImageView myroundimageview05;
	private MyRoundImageView myroundimageview06;
	private MyRoundImageView myroundimageview07;
	private MyRoundImageView myroundimageview08;
	private MyRoundImageView myroundimageview09;
	private MyRoundImageView myroundimageview10;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_myroundimageview);
		// TODO Auto-generated method stub

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myroundimageview01 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview01);
		myroundimageview02 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview02);
		myroundimageview03 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview03);
		myroundimageview04 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview04);
		myroundimageview05 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview05);
		myroundimageview06 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview06);
		myroundimageview07 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview07);
		myroundimageview08 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview08);
		myroundimageview09 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview09);
		myroundimageview10 = (MyRoundImageView) this
				.findViewById(R.id.myroundimageview10);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		myroundimageview10.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		// TODO Auto-generated method stub
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("RoundImageView");
		myroundimageview01.setImageResource(R.drawable.icon);
		myroundimageview04.setImageResource(R.drawable.icon);

		myroundimageview02.setImageResource(R.drawable.icon2);
		myroundimageview05.setImageResource(R.drawable.icon2);

		myroundimageview03.setImageResource(R.drawable.lanucherbg);
		myroundimageview06.setImageResource(R.drawable.lanucherbg);

		myroundimageview07.setImageResource(R.drawable.roundimg);
		myroundimageview08.setImageResource(R.drawable.roundimg);

		myroundimageview09.setImageResource(R.drawable.icon4);
		myroundimageview10.setImageResource(R.drawable.icon4);
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
		case R.id.myroundimageview10:
			ImageMannager.saveFileHighQuality(Utils
					.drawableToBitamp(getResources().getDrawable(
							R.drawable.mars)));
			break;
		default:
			break;
		}
	}
}
