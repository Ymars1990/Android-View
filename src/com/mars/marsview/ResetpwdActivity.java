package com.mars.marsview;

import com.mars.marsview.utils.SharedPreferencesMannager;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResetpwdActivity extends BaseActivity {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private EditText resetpwd_opwd;
	private EditText resetpwd_npwd;
	private EditText resetpwd_repwd;
	private String opwd;
	private String npwd;
	private String repwd;
	private Button resetpwd_bt;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_resetpwd);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		resetpwd_opwd = (EditText) this.findViewById(R.id.resetpwd_orignalpwd);
		resetpwd_npwd = (EditText) this.findViewById(R.id.resetpwd_newpwd);
		resetpwd_repwd = (EditText) this.findViewById(R.id.resetpwd_repwd);
		resetpwd_bt = (Button) this.findViewById(R.id.resetpwd_bt);

	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		resetpwd_bt.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	private void getInput() {
		opwd = resetpwd_opwd.getText().toString();
		npwd = resetpwd_npwd.getText().toString();
		repwd = resetpwd_repwd.getText().toString();

	}

	@Override
	public void callFunc() {
		title_tv.setText("Reset PassWord");
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
		case R.id.resetpwd_bt:
			getInput();
			if (validateIDAndPwd() == 0) {
				Speditor();
				Utils.closeActivity(this, 0);
			}
			break;
		}
	}
	private void Speditor(){
		SharedPreferencesMannager.putSpValue("login_pwd",npwd, 3);
	}
	private String getSpValue(){
		return (String) SharedPreferencesMannager.getSpValue("login_pwd", 3);
	}
	private int validateIDAndPwd() {
		if (Utils.isNotEmpty(opwd) && Utils.isNotEmpty(npwd)
				&& Utils.isNotEmpty(repwd)) {
			if (opwd.equals(getSpValue())) {
				if (npwd.equals(repwd)) {
					ToastManager.show(mContext, "密码修改成功！", 0);
					return 0;
				} else {
					ToastManager.show(mContext, "两次密码不一致！", 0);
					return 4;
				}
			} else {
				ToastManager.show(mContext, "原始密码不正确！", 0);
				return 5;
			}

		} else if (!Utils.isNotEmpty(opwd)) {
			ToastManager.show(mContext, "请输入原始密码", 0);
			return 1;
		} else if (!Utils.isNotEmpty(npwd)) {
			ToastManager.show(mContext, "请输入新密码密码", 0);
			return 2;
		} else {
			ToastManager.show(mContext, "请输入确认密码", 0);
			return 3;
		}
	}
}
