package com.mars.marsview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mars.marsview.R;

public class ProgressDialog {
	private ImageView prodialogContentImg = null;
	private ImageView prodialogResultImg = null;
	private TextView prodialogContentText = null;
	private LinearLayout prodialogBtLayout = null;

	private Context context;
	private LinearLayout prodialogdialog_layout;

	private Display display = null;
	private Dialog dialog = null;

	public ProgressDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ProgressDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.progressdialog, null);
		prodialogdialog_layout = (LinearLayout) view
				.findViewById(R.id.prodialogdialog_layout);
		prodialogContentImg = (ImageView) view
				.findViewById(R.id.prodialogContentImg);
		prodialogResultImg = (ImageView) view
				.findViewById(R.id.prodialogResultImg);
		prodialogContentText = (TextView) view
				.findViewById(R.id.prodialogContent);
		prodialogContentImg.setVisibility(View.VISIBLE);
		prodialogResultImg.setVisibility(View.GONE);
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		prodialogContentImg.startAnimation(hyperspaceJumpAnimation);

		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);
		Window dialogWin = dialog.getWindow();
		dialogWin.setWindowAnimations(R.style.scaleWindowAnim); // 设置窗口弹出动画
		// 调整dialog背景大小
		prodialogdialog_layout.setLayoutParams(new FrameLayout.LayoutParams(
				(int) (display.getWidth() * 0.8), LayoutParams.MATCH_PARENT));
		return this;
	}

	public void stopAnimation() {
		prodialogContentImg.clearAnimation();
	}

	public void setShowAnimation() {
		prodialogContentImg.setVisibility(View.GONE);
		prodialogResultImg.setVisibility(View.VISIBLE);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);// 创建一个AlphaAnimation 对象渐变从1->0
		aa.setDuration(1000);// 设置持续时间
		aa.setFillAfter(true);// 设置最后的动画效果，这里是显示状态（最后能够看到这个View)
		prodialogResultImg.startAnimation(aa);
	}

	// 设置View可见性
	public void setViewVisiable(int contentImg, int contentText) {
		showView(prodialogContentImg, contentImg);
		showView(prodialogContentText, contentText);
	}

	public void showView(View view, int showFlag) {
		if (showFlag == 1) {
			view.setVisibility(View.VISIBLE);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	// 设置内容
	public void setContentText(String title) {
		if ("".equals(title) || title == null) {
			prodialogContentText.setVisibility(View.GONE);
		} else {
			prodialogContentText.setText(title);
		}
	}

	// 设置返回键是否关闭dialog
	public void setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
	}

	// 设置外部点击可取消
	public void setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
	}

	// 设置ContentImg
	public void setprodialogContentImg(int contentImgId) {
		prodialogResultImg.setImageResource(contentImgId);
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

}
