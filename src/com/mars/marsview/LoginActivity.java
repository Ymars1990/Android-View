package com.mars.marsview;

import com.mars.marsview.utils.SharedPreferencesMannager;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.animation.ViewEffect;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnTouchListener {
	private Context mContext;
	private EditText login_id;
	private TextView login_up;
	private TextView login_forget_passWord;
	private EditText login_pwd;
	private Button login_bt;
	private Drawable del;
	private String id;
	private String pwd;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_login);

	}

	@Override
	public void initUI() {
		login_id = (EditText) this.findViewById(R.id.login_id);
		login_pwd = (EditText) this.findViewById(R.id.login_pwd);
		login_bt = (Button) this.findViewById(R.id.login_bt);
		login_up = (TextView) this.findViewById(R.id.login_sign_up);
		login_forget_passWord = (TextView) this
				.findViewById(R.id.login_forget_passWord);
	}

	@Override
	public void registerEvent() {
		login_bt.setOnClickListener(this);
		login_up.setOnClickListener(this);
		login_forget_passWord.setOnClickListener(this);
		login_id.addTextChangedListener(new MyTextWatcherListener(login_id));
		login_pwd.addTextChangedListener(new MyTextWatcherListener(login_pwd));
		login_id.setOnTouchListener(this);
		login_pwd.setOnTouchListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		initDarable();
	}

	private void initDarable() {
		del = getResources().getDrawable(R.drawable.del);
		del.setBounds(0, 0, del.getMinimumWidth(), del.getMinimumHeight());

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 1);
		}
		return super.onKeyDown(keyCode, event);
	}

	private Intent intent;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_bt:
			getInput();
			if (validateIDAndPwd() == 0) {
				intent = new Intent(this, MainActivity.class);
				Utils.startActivity(this, intent);
				Utils.closeActivity(this, 0);
			}
			break;
		case R.id.login_sign_up:
			// 注册界面
			intent = new Intent(this, SignUpActivity.class);
			Utils.startActivity(this, intent);
			break;
		case R.id.login_forget_passWord:
			// 密码重置界面
			intent = new Intent(this, ResetpwdActivity.class);
			Utils.startActivity(this, intent);
			break;
		}
	}

	private void getInput() {
		id = login_id.getText().toString();
		pwd = login_pwd.getText().toString();
	}

	private int validateIDAndPwd() {
		if (Utils.isNotEmpty(id) && Utils.isNotEmpty(pwd)) {
			if (id.equals(SharedPreferencesMannager.getSpValue("login_id", 3))
					&& pwd.equals(SharedPreferencesMannager.getSpValue(
							"login_pwd", 3))) {
				return 0;
			} else if (!id.equals(SharedPreferencesMannager.getSpValue(
					"login_id", 3))) {
				ToastManager.show(mContext, "账户有误！", 0);
				return 3;
			} else {
				ToastManager.show(mContext, "密码有误！", 0);
				return 4;
			}
		} else if (!Utils.isNotEmpty(id)) {
			ViewEffect.setAnimation(3, login_id);
			return 1;
		} else {
			ViewEffect.setAnimation(3, login_pwd);
			return 2;
		}
	}

	public class MyTextWatcherListener implements TextWatcher {
		private View v;

		public MyTextWatcherListener(View v) {
			this.v = v;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.length() > 0) {
				((EditText) v).setCompoundDrawablesWithIntrinsicBounds(null,
						null, del, null);
			} else {
				((EditText) v).setCompoundDrawablesWithIntrinsicBounds(null,
						null, null, null);
			}
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.login_id:
			if (login_id.getCompoundDrawables()[2] == null) {
				return false;
			}
			break;
		case R.id.login_pwd:
			if (login_pwd.getCompoundDrawables()[2] == null) {
				return false;
			}
			break;
		default:
			break;
		}
		// 这里一定要对点击事件类型做一次判断，否则你的点击事件会被执行2次
		if (event.getAction() != MotionEvent.ACTION_UP) {
			return false;
		}
		if (event.getX() > (v.getWidth() - ((EditText) v)
				.getCompoundDrawables()[2].getBounds().width())) {
			((EditText) v).setText("");
			return true;
		} else {
			return false;
		}
	}
}
