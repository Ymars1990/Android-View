package com.mars.marsview.view;

import com.mars.marsview.R;
import com.mars.marsview.view.loading.LoadingView01;
import com.mars.marsview.view.loading.LoadingView02;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LoadingProgressDialog {
	private int loadingType; // 1 、钟表式 2、跑马灯式
	private String msg;
	private Context context;
	private LinearLayout layout;
	private Display display = null;
	private Dialog dialog = null;
	private LoadingView01 loadView;
	private LoadingView02 loadView02;

	public LoadingProgressDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}
	
	public LoadingProgressDialog builder() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.loadingdialog, null);
		layout = (LinearLayout) view.findViewById(R.id.loadinglayout);
		loadView = (LoadingView01) view.findViewById(R.id.loadingView);
		loadView02 = (LoadingView02) view.findViewById(R.id.loadingView02);
		dialog = new Dialog(context, R.style.LaodDialogStyle);
		dialog.setContentView(view);
		Window dialogWin = dialog.getWindow();
		dialogWin.setWindowAnimations(R.style.scaleWindowAnim); // 设置窗口弹出动画
		// 调整dialog背景大小

		layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.6), LayoutParams.MATCH_PARENT));

		// layout.setLayoutParams(new FrameLayout.LayoutParams(
		// LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return this;
	}

	// 设置返回键是否关闭dialog
	public void setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
	}

	// 设置外部点击可取消
	public void setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		if (isShow()) {
			dialog.dismiss();
		}
	}

	public boolean isShow() {
		return dialog != null && dialog.isShowing();
	}

	public int getLoadingType() {
		return loadingType;
	}

	public void setLoadingType(int loadingType) {
		this.loadingType = loadingType;
		switch (loadingType) {
		case 1:
			loadView.setScaleType(1);
			loadView.setVisibility(View.VISIBLE);
			loadView02.setVisibility(View.GONE);
			break;
		case 2:
			loadView.setScaleType(2);
			loadView.setVisibility(View.VISIBLE);
			loadView02.setVisibility(View.GONE);
			break;
		case 3:
			loadView02.setVisibility(View.VISIBLE);
			loadView.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
