package com.mars.marsview;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.InputView;
import com.mars.marsview.view.MyEditText;

public class InputTextActivity extends BaseActivity implements OnClickListener {

	private InputView inputView = null;
	private InputView numberInput = null;
	private InputView emailInput = null;
	private MyEditText myet_01 = null;
	private Button getInput = null;
	private TextView title_tv;
	private LinearLayout title_backlayout;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_inputtext);
	}

	@Override
	public void initUI() {

		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);

		inputView = (InputView) findViewById(R.id.nameInput);
//		inputView = new InputView(this);
		numberInput = (InputView) findViewById(R.id.numberInput);
		emailInput = (InputView) findViewById(R.id.emailInput);
		myet_01 = (MyEditText) findViewById(R.id.myet_01);
		getInput = (Button) findViewById(R.id.getInput);

		
		inputView.setInputLimitLenth(10);

		numberInput.setViewVisiable(1, 0, 0);
		inputView.setViewVisiable(1, 0, 0);
		emailInput.setViewVisiable(1, 0, 1);

		emailInput.setEdtDigits(3, -1);
		numberInput.setEdtDigits(2, 11);
		inputView.setEdtDigits(1, -1);

		emailInput.setInputOption("邮箱");
		numberInput.setInputOption("手机号");
		inputView.setInputOption("用户名");

		emailInput.setHint("请输入有效邮箱");
		numberInput.setHint("请输入有效手机号");
		inputView.setHint("请输入姓名");
		numberInput.setInputDelete(R.drawable.visible_01);
		numberInput.setPwdInputType(true);
	}

	@Override
	public void registerEvent() {
		inputView.registerEvents();
		numberInput.registerEvents();
		emailInput.registerEvents();
		getInput.setOnClickListener(this);
		title_backlayout.setOnClickListener(this);
		myet_01.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public void initParams() {

	}

	@Override
	public void callFunc() {
		title_tv.setText("InputText");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		case R.id.getInput:
			TranslateAnimation translate = new TranslateAnimation(-20, 20, 0, 0);
			translate.setDuration(100);
			translate.setRepeatCount(3);
			translate.setRepeatMode(Animation.REVERSE);
			if (Utils.isNotEmpty(inputView.getInputText())
					&& Utils.isNotEmpty(numberInput.getInputText())
					&& Utils.isNotEmpty(emailInput.getInputText())) {
				if (!Utils.isPhoneNumber(numberInput.getInputText())) {
					numberInput.startAnimation(translate);
					ToastManager.show(this, "手机号码格式不正确", 0);
					return;
				}
				if (!Utils.isEmail(emailInput.getInputText())) {
					emailInput.startAnimation(translate);
					ToastManager.show(this, "邮箱格式不正确", 0);
					return;
				}
			} else {
				if (!Utils.isNotEmpty(inputView.getInputText())) {
					inputView.startAnimation(translate);
					ToastManager.show(this, "姓名不能为空", 0);
				} else if (!Utils.isNotEmpty(numberInput.getInputText())) {
					numberInput.startAnimation(translate);
					ToastManager.show(this, "手机号码不能为空", 0);
				} else {
					emailInput.startAnimation(translate);
					ToastManager.show(this, "邮箱不能为空", 0);
				}
			}
			break;
		default:
			break;
		}
	}
}
