package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.LockPatternView;
import com.mars.marsview.view.LockPatternView.OnCompleteListener;

public class LockPatternViewActivity extends BaseActivity implements
		OnClickListener, OnCompleteListener {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;

	private LockPatternView myLockPatternView;
	private Button setPwd;
	private Button validatePwd;
	private String pwd;
	private String drawPwd;
	private int SetttingOrValidate = 1;// 1°¢…Ë÷√pwd 2°¢ —È÷§pwd

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_lockpatternview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myLockPatternView = (LockPatternView) this
				.findViewById(R.id.myLockPatternView);
		setPwd = (Button) this.findViewById(R.id.setPwd);
		validatePwd = (Button) this.findViewById(R.id.validatePwd);
		validatePwd.setVisibility(View.GONE);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		validatePwd.setOnClickListener(this);
		setPwd.setOnClickListener(this);
		myLockPatternView.setOnCompleteListener(this);
	}

	@Override
	public void initParams() {
		mContext = this; // TODO Auto-generated method stub

	}

	@Override
	public void callFunc() {
		title_tv.setText("LockPatternView");

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
		case R.id.setPwd:
			drawPwd = null;
//			myLockPatternView.clearPassword();
			if (Utils.isNotEmpty(pwd)) {
				SetttingOrValidate = 2;
			} else {
				ToastManager
						.show(mContext,
								"Sorry You never setted password!Please setting password frist!",
								0);
			}
			break;
		case R.id.validatePwd:
//			pwd = "12345";
			validateLockPattern();
			break;
		}
	}

	private void validateLockPattern() {
		if (SetttingOrValidate == 2) {
			if (Utils.isNotEmpty(drawPwd)) {
				if (drawPwd.equals(pwd)) {
					ToastManager.show(mContext,
							"Congratulation You get in!", 0);
				} else {
					myLockPatternView.markError();
					ToastManager.show(mContext, "Sorry You get wrong!", 0);
				}
			}else{
				ToastManager
				.show(mContext,
						"Sorry Please drawing password frist!",
						0);
			}
		}else{
			ToastManager
			.show(mContext,
					"Sorry You never setted password!Please setting password frist!",
					0);
		}
	}

	@Override
	public void onComplete(String password) {
		if (SetttingOrValidate == 1) {
			pwd = password;
		} else {
			drawPwd = password;
			validateLockPattern();
		}
		Utils.LogShow("password", password);
	}
}
