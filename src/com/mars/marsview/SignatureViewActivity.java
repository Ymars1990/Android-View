package com.mars.marsview;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.SignatureView;

public class SignatureViewActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private SignatureView mSignatureView;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_signatureview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mSignatureView = (SignatureView)findViewById(R.id.sv_sign);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public void callFunc() {
		title_tv.setText("SignatureView");
		mSignatureView.setTextContext("123456789");
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
		}
	}
}
