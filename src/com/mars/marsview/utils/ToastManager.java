package com.mars.marsview.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastManager {
	public static  Toast toast;	
	@SuppressWarnings("static-access")
	public static void show(Context context, String msg, int position) {
		if (toast == null) {
			toast = new Toast(context);
		}
		if (position == 1) {
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
