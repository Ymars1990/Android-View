package com.mars.marsview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;

public class AnimationActivity extends BaseActivity implements OnClickListener,
		AnimationListener {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private LinearLayout animationImageView_layout;
	private ImageView animationImageView;
	private ImageView animationFrameImageView;
	private Button animation01;
	private Button animation02;
	private Button animation03;
	private Button animation04;
	private Button animation05;
	private Button animation06;

	private int animationType = 1;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_animation);

	}

	@Override
	public void initUI() {

		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		animationImageView_layout = (LinearLayout) this
				.findViewById(R.id.animationImageView_layout);
		animationImageView = (ImageView) this
				.findViewById(R.id.animationImageView);
		animationFrameImageView = (ImageView) this
				.findViewById(R.id.animationFrameImageView);
		animation01 = (Button) this.findViewById(R.id.animation01);
		animation02 = (Button) this.findViewById(R.id.animation02);
		animation03 = (Button) this.findViewById(R.id.animation03);
		animation04 = (Button) this.findViewById(R.id.animation04);
		animation05 = (Button) this.findViewById(R.id.animation05);
		animation06 = (Button) this.findViewById(R.id.animation06);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		animation01.setOnClickListener(this);
		animation02.setOnClickListener(this);
		animation03.setOnClickListener(this);
		animation04.setOnClickListener(this);
		animation05.setOnClickListener(this);
		animation06.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("Animation");

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
		case R.id.animation01:
			animationType = 1;
			animationImageView_layout.setVisibility(View.VISIBLE);
			animationImageView.setVisibility(View.VISIBLE);
			animationFrameImageView.setVisibility(View.GONE);
			setAlphaAnimation(animationImageView);
			stopFrameAnimation(animationFrameImageView);

			break;
		case R.id.animation02:
			animationType = 2;
			animationImageView_layout.setVisibility(View.VISIBLE);
			animationImageView.setVisibility(View.VISIBLE);
			animationFrameImageView.setVisibility(View.GONE);
			setScaleAnimation(animationImageView);
			stopFrameAnimation(animationFrameImageView);

			break;
		case R.id.animation03:
			animationType = 3;
			animationImageView_layout.setVisibility(View.VISIBLE);
			animationImageView.setVisibility(View.VISIBLE);
			animationFrameImageView.setVisibility(View.GONE);
			setTranslateAnimation(animationImageView);
			stopFrameAnimation(animationFrameImageView);

			break;
		case R.id.animation04:
			animationType = 4;
			animationImageView_layout.setVisibility(View.VISIBLE);
			animationImageView.setVisibility(View.VISIBLE);
			animationFrameImageView.setVisibility(View.GONE);
			setRotateAnimation(animationImageView);
			stopFrameAnimation(animationFrameImageView);
			break;
		case R.id.animation05:
			animationType = 5;
			animationImageView_layout.setVisibility(View.VISIBLE);
			animationFrameImageView.setVisibility(View.VISIBLE);
			animationImageView.setVisibility(View.GONE);
			;
			setFrameAnimation(animationFrameImageView);
			break;
		case R.id.animation06:
			stopFrameAnimation(animationFrameImageView);
			Intent intent = new Intent(this, AddViewViewGroupActivity.class);
			Utils.startActivity(this, intent);
			break;
		}
	}

	private void setAlphaAnimation(View view) {
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(3000);
		view.startAnimation(animation);
		animation.setAnimationListener(this);
	}

	private void setScaleAnimation(View view) {
	/*	ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(3000);
		view.startAnimation(animation);
		animation.setAnimationListener(this);*/
		AnimationSet animationSet = (AnimationSet) AnimationUtils
				.loadAnimation(this,
						R.anim.scale_show);
		view.startAnimation(animationSet);
		animationSet.setAnimationListener(this);
		
		
		
	}

	private void setTranslateAnimation(View view) {
		TranslateAnimation animation = new TranslateAnimation(0.0f,
				Utils.getWindowsWidth(this), 0.0f, Utils.getWindowsHeight(this));
		animation.setDuration(3000);
		view.startAnimation(animation);
		animation.setAnimationListener(this);


	}

	private void setRotateAnimation(View view) {
		RotateAnimation animation = new RotateAnimation(0.0f, -360.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(3000);
		view.startAnimation(animation);
		animation.setAnimationListener(this);
	}

	private void setFrameAnimation(View view) {
		AnimationDrawable animation = (AnimationDrawable) ((ImageView) view)
				.getDrawable();
		if (animation != null)
			animation.start();
	}

	private void stopFrameAnimation(View view) {
		AnimationDrawable animation = (AnimationDrawable) ((ImageView) view)
				.getDrawable();
		if (animation != null)
			animation.stop();
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		animationImageView_layout.setVisibility(View.GONE);
		animation.cancel();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		switch (animationType) {
		case 1:

			break;

		default:
			break;
		}
	}
}
