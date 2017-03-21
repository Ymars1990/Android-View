package com.mars.marsview.view.animation;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class ViewEffect {
	public static void startFlick(View view) {
		if (null == view) {
			return;
		}
		Animation alphaAnimation = new AlphaAnimation(1, 0);

		alphaAnimation.setDuration(100);

		alphaAnimation.setInterpolator(new LinearInterpolator());

		alphaAnimation.setRepeatCount(Animation.INFINITE);

		alphaAnimation.setRepeatMode(Animation.REVERSE);

		view.startAnimation(alphaAnimation);

	}

	public static void stopFlick(View view) {

		if (null == view) {

			return;
		}
		view.clearAnimation();
	}

	public static void Click(View v) {
		v.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.getBackground().setAlpha(150);
					return false;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setAlpha(255);
					return false;
				}
				return false;
			}
		});
	}

	public static void setAnimation(int visiable, View view) {

		switch (visiable) {
		case 1:
			TranslateAnimation mHiddenAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -1.0f);
			mHiddenAction.setDuration(500);
			view.startAnimation(mHiddenAction);
			view.setVisibility(View.GONE);
			break;
		case 2:
			TranslateAnimation mShowAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -1.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			mShowAction.setDuration(500);
			view.startAnimation(mShowAction);
			view.setVisibility(View.VISIBLE);
			break;
		case 3:
			TranslateAnimation mTranslate = new TranslateAnimation(-20, 20, 0, 0);
			mTranslate.setDuration(100);
			mTranslate.setRepeatCount(3);
			mTranslate.setRepeatMode(Animation.REVERSE);
			view.startAnimation(mTranslate);
			break;
		default:
			break;
		}
	}

}
