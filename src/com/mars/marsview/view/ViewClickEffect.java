package com.mars.marsview.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ViewClickEffect {
	public static void Click(View v){
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
}
