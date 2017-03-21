package com.mars.marsview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mars.marsview.R;

public class TipsDiaolg {
	private ImageView tipsTitleImg = null;
	private ImageView tipsContentImg = null;
	private TextView tipsTitleText = null;
	private TextView tipsContentText = null;
	private TextView tipsBtCancel = null;
	private TextView tipsBtConfirm = null;

	private View tipsBtdivideline;
	private Context context;
	private LinearLayout tipsdialog_layout;

	private Display display = null;
	private Dialog dialog = null;

	public TipsDiaolg(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public TipsDiaolg builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.tipsdialog,
				null);
		tipsdialog_layout = (LinearLayout) view
				.findViewById(R.id.tipsdialog_layout);
		tipsTitleImg = (ImageView) view.findViewById(R.id.tipsTitleImg);
		tipsContentImg = (ImageView) view.findViewById(R.id.tipsContentImg);
		tipsContentText = (TextView) view.findViewById(R.id.tipsContent);
		tipsTitleText = (TextView) view.findViewById(R.id.tipsTitleText);
		tipsBtCancel = (TextView) view.findViewById(R.id.tipsBtCancel);
		tipsBtConfirm = (TextView) view.findViewById(R.id.tipsBtConfirm);
		tipsBtdivideline = (View) view.findViewById(R.id.tipsBtdivideline);
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);
		Window dialogWin = dialog.getWindow();
		dialogWin.setWindowAnimations(R.style.scaleWindowAnim); // 设置窗口弹出动画
		
		// 调整dialog背景大小
		tipsdialog_layout.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return this;
	}

	// 设置View可见性
	public void setViewVisiable(int titleImg, int titleText, int contentImg,
			int contentText) {
		showView(tipsTitleImg, titleImg);
		showView(tipsTitleText, titleText);
		showView(tipsContentImg, contentImg);
		showView(tipsContentText, contentText);
	}

	public void showView(View view, int showFlag) {
		if (showFlag == 1) {
			view.setVisibility(View.VISIBLE);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	// 设置title
	public void setTitleText(String title) {
		if ("".equals(title) || title == null) {
			tipsTitleText.setText("系统提示");
		} else {
			tipsTitleText.setText(title);
		}
	}

	// 设置内容
	public void setContentText(String title) {
		if ("".equals(title) || title == null) {
			tipsContentText.setText("数据有误，请稍后重试");
		} else {
			tipsContentText.setText(title);
		}
	}

	// 设置返回键是否关闭dialog
	public void setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
	}

	// 设置titleImg
	public void setTitleImg(int titleImgId) {
		tipsTitleImg.setImageResource(titleImgId);
	}

	// 设置ContentImg
	public void settipsContentImg(int contentImgId) {
		tipsContentImg.setImageResource(contentImgId);
	}

	// 按钮事件
	public void setBtOnclickListener(OnClickListener listener) {
		tipsBtCancel.setOnClickListener(listener);
		tipsBtConfirm.setOnClickListener(listener);
	}

	// 设置布局效果
	private void setLayout(int btVisibale) {
		if (btVisibale == 2) {
			tipsBtCancel
					.setBackgroundResource(R.drawable.alertdialog_left_selector);
			tipsBtConfirm

			.setBackgroundResource(R.drawable.alertdialog_right_selector);
		} else {
			tipsBtdivideline.setVisibility(View.GONE);
			tipsBtConfirm
					.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}
	}

	public void setBtVisiable(int cancel, int confirm) {
		if (cancel == 0) {
			tipsBtCancel.setVisibility(View.GONE);
		} else {
			tipsBtCancel.setVisibility(View.VISIBLE);
			tipsBtdivideline.setVisibility(View.VISIBLE);
		}
		if (confirm == 0) {
			tipsBtConfirm.setVisibility(View.GONE);
		} else {
			tipsBtConfirm.setVisibility(View.VISIBLE);
		}
		if (cancel == 0 && confirm == 1) {
			setLayout(1);
		} else {
			setLayout(2);
		}
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
