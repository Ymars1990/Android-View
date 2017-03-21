package com.mars.marsview;

import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ProgressDialog;
import com.mars.marsview.view.SelectDialog;
import com.mars.marsview.view.TipsDiaolg;

public class ProgressDialogActivity extends BaseActivity implements
		OnClickListener {

	private Button loadingDialog1 = null;
	private Button loadingDialog2 = null;
	private Button loadingDialog3 = null;
	private Button loadingDialog4 = null;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private SelectDialog selectDialog = null;

	private Context mContext;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_progressdialog);

	}

	@Override
	public void initUI() {

		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);

		loadingDialog1 = (Button) this.findViewById(R.id.loadingDialog1);
		loadingDialog2 = (Button) this.findViewById(R.id.loadingDialog2);
		loadingDialog3 = (Button) this.findViewById(R.id.loadingDialog3);
		loadingDialog4 = (Button) this.findViewById(R.id.loadingDialog4);
	}

	@Override
	public void registerEvent() {
		loadingDialog1.setOnClickListener(this);
		loadingDialog2.setOnClickListener(this);
		loadingDialog3.setOnClickListener(this);
		loadingDialog4.setOnClickListener(this);
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("ProgressDialog");
	}

	private ProgressDialog showProDialog = null;

	// 对话框
	public void showProgressDialog(String contentMsg) {
		showProDialog = new ProgressDialog(this).builder();
		showProDialog.setCancelable(true);
		showProDialog.setCanceledOnTouchOutside(false);
		showProDialog.setContentText(contentMsg);
		showProDialog.setViewVisiable(1, 1);
		showProDialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (showProDialog != null && showProDialog.isShow()) {
				showProDialog.dismiss();
			} else if (selectDialog != null && selectDialog.isShow()) {
				selectDialog.dismiss();
			} else {
				Utils.closeActivity(this, 0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private final int MSG_SUCCESS = 0x01;
	private final int MSG_ERROR = 0x02;
	private final int MSG_DISMISS = 0x03;
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_SUCCESS:
				showProDialog.stopAnimation();
				showProDialog.setContentText("Success");
				showProDialog.setShowAnimation();
				showProDialog.setprodialogContentImg(R.drawable.right);
				myHandler.sendEmptyMessageDelayed(MSG_DISMISS, 2000);
				break;
			case MSG_ERROR:
				showProDialog.stopAnimation();
				showProDialog.setContentText("Error");
				showProDialog.setShowAnimation();
				showProDialog.setprodialogContentImg(R.drawable.error);
				myHandler.sendEmptyMessageDelayed(MSG_DISMISS, 2000);
				break;
			case MSG_DISMISS:
				showProDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};

	// 创建选择对话框
	private void createSelectDialog() {
		selectDialog = new SelectDialog(mContext);
		selectDialog.builder().setCancelable(true);
		selectDialog.setCanceledOnTouchOutside(false);
		selectDialog.addSelectItem("分享到朋友圈",
				new SelectDialog.OnSelcetItemClickListener() {

					@Override
					public void onClick(int which) {
						ToastManager.show(mContext, "成功分享到朋友圈",0);
					}

				});
		selectDialog.addSelectItem("分享到QQ空间",
				new SelectDialog.OnSelcetItemClickListener() {

					@Override
					public void onClick(int which) {
						ToastManager.show(mContext, "成功分享到QQ空间",0);
					}

				});
		selectDialog.addSelectItem("分享到微博",
				new SelectDialog.OnSelcetItemClickListener() {

					@Override
					public void onClick(int which) {
						ToastManager.show(mContext, "成功分享到微博",0);
					}

				});
		selectDialog.addSelectItem("分享到社区",
				new SelectDialog.OnSelcetItemClickListener() {

					@Override
					public void onClick(int which) {
						ToastManager.show(mContext, "成功分享到社区",0);
					}

				});
		selectDialog.show();
	}

	private TipsDiaolg tipsDialog = null;

	// 对话框
	public void showTipsDialog(String title, String message) {
		tipsDialog = new TipsDiaolg(this);
		tipsDialog.builder().setCancelable(false);
		tipsDialog.setBtOnclickListener(this);
		tipsDialog.setTitleText(title);
		tipsDialog.setContentText(message);
		tipsDialog.setViewVisiable(0, 1, 0, 1);
		tipsDialog.setBtVisiable(0, 1);
		tipsDialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		case R.id.loadingDialog1:
			showProgressDialog("Loading...");
			myHandler.sendEmptyMessageDelayed(MSG_SUCCESS, 3000);
			break;
		case R.id.loadingDialog2:
			showProgressDialog("Loading...");
			myHandler.sendEmptyMessageDelayed(MSG_ERROR, 3000);
			break;
		case R.id.loadingDialog3:
			createSelectDialog();
			break;
		case R.id.loadingDialog4:
			showTipsDialog("对话框", "单按钮对话框");
			break;
		case R.id.tipsBtConfirm:
			tipsDialog.dismiss();
			break;
		default:
			break;
		}
	}
}
