package com.mars.marsview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;

public class ToastDialog {
	private LinearLayout toastdialogLayout;
	private Dialog dialog = null;
	private TextView toastConetent;
	public enum DURATION{LONG,SHORT};
	private Context context;
	private TimeCallFunction tcFunc;

	public ToastDialog(Context context,DURATION duration) {
		this.context = context;
		long x = 0;
		if(duration==DURATION.LONG){
			x = 3*1000;
		}else{
			x = 2*1000;
		}
		tcFunc = new TimeCallFunction(x, 1000) {
			@Override
			public void callTimerTaskFuc() {
				dialog.dismiss();
			}
		};
	}

	public void builder(String content) {

		View view = LayoutInflater.from(context).inflate(R.layout.toastdialog,
				null);
		toastdialogLayout = (LinearLayout) view
				.findViewById(R.id.toastdialogLayout);
		toastConetent = (TextView) view.findViewById(R.id.toastConetent);
		if (Utils.isNotEmpty(content)) {
			toastConetent.setText(content);
		}
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);
		toastdialogLayout.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		show();
	}

	private void show() {
		dialog.show();
		tcFunc.showTimerTask();
	}
}
