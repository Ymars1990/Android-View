package com.mars.marsview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.SharedPreferencesMannager;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;

public class SignUpActivity extends BaseActivity implements TextWatcher,
OnTouchListener {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private EditText signup_id;
	private EditText signup_pwd;
	private EditText signup_repwd;
	private Drawable del;
	private String id;
	private String pwd;
	private String repwd;
	private Button signup_bt;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_signup);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		signup_id = (EditText) this.findViewById(R.id.sign_up_id);
		signup_pwd = (EditText) this.findViewById(R.id.sign_up_pwd);
		signup_repwd = (EditText) this.findViewById(R.id.sign_up_repwd);
		signup_bt = (Button) this.findViewById(R.id.sign_up_bt);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		signup_bt.setOnClickListener(this);
		
		signup_id.addTextChangedListener(this);
		signup_id.setOnTouchListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	private void getInput() {
		id = signup_id.getText().toString();
		pwd = signup_pwd.getText().toString();
		repwd = signup_repwd.getText().toString();
		
	}
	
	@Override
	public void callFunc() {
		title_tv.setText("Sign Up");

		initDarable();
	}
	private void initDarable() {
		del = getResources().getDrawable(R.drawable.del);
		del.setBounds(0, 0, del.getMinimumWidth(), del.getMinimumHeight());

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
		case R.id.sign_up_bt:
			getInput();
			if(validateIDAndPwd()==0){
				Speditor();
				Utils.closeActivity(this, 0);
			}
			break;
		}
	}
	
	private int validateIDAndPwd() {
		if(Utils.isNotEmpty(id)
				&&Utils.isNotEmpty(pwd)
				&&Utils.isNotEmpty(repwd)){
			if(pwd.equals(repwd)){
				ToastManager.show(mContext, "注册成功！", 0);
				return 0;
			}else{
				ToastManager.show(mContext, "两次密码不一致！", 0);
				return 4;
			}
			
		}else if(!Utils.isNotEmpty(id)){
			ToastManager.show(mContext, "请输入账户", 0);
			return 1;
		}else if(!Utils.isNotEmpty(pwd)){
			ToastManager.show(mContext, "请输入密码", 0);
			return 2;
		}else{
			ToastManager.show(mContext, "请输入确认密码", 0);
			return 3;
		}
	}
	private void Speditor(){
		SharedPreferencesMannager.putSpValue("login_id", id, 3);
		SharedPreferencesMannager.putSpValue("login_pwd",pwd, 3);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (signup_id.getCompoundDrawables()[2] == null) {
			return false;
		}
		// 这里一定要对点击事件类型做一次判断，否则你的点击事件会被执行2次
		if (event.getAction() != MotionEvent.ACTION_UP) {
			return false;
		}
		if (event.getX() > (signup_id.getWidth() - signup_id
				.getCompoundDrawables()[2].getBounds().width())) {
			signup_id.setText("");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			signup_id.setCompoundDrawablesWithIntrinsicBounds(null, null, del,
					null);
		} else {
			signup_id.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
					null);
		}
	}
}
