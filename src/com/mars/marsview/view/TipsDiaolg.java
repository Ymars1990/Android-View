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
		// ��ȡDialog����
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
		dialogWin.setWindowAnimations(R.style.scaleWindowAnim); // ���ô��ڵ�������
		
		// ����dialog������С
		tipsdialog_layout.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return this;
	}

	// ����View�ɼ���
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

	// ����title
	public void setTitleText(String title) {
		if ("".equals(title) || title == null) {
			tipsTitleText.setText("ϵͳ��ʾ");
		} else {
			tipsTitleText.setText(title);
		}
	}

	// ��������
	public void setContentText(String title) {
		if ("".equals(title) || title == null) {
			tipsContentText.setText("�����������Ժ�����");
		} else {
			tipsContentText.setText(title);
		}
	}

	// ���÷��ؼ��Ƿ�ر�dialog
	public void setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
	}

	// ����titleImg
	public void setTitleImg(int titleImgId) {
		tipsTitleImg.setImageResource(titleImgId);
	}

	// ����ContentImg
	public void settipsContentImg(int contentImgId) {
		tipsContentImg.setImageResource(contentImgId);
	}

	// ��ť�¼�
	public void setBtOnclickListener(OnClickListener listener) {
		tipsBtCancel.setOnClickListener(listener);
		tipsBtConfirm.setOnClickListener(listener);
	}

	// ���ò���Ч��
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
